package com.zzk.aop;




import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ch.qos.logback.core.db.dialect.DBUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.dao.AccessMapper;
import com.zzk.dao.LoginLogMapper;
import com.zzk.dao.OperateLogMapper;
import com.zzk.dao.ProductBaseInfoMapper;
import com.zzk.dao.UserMapper;
import com.zzk.entity.Access;
import com.zzk.entity.LoginLog;
import com.zzk.entity.OperateLog;
import com.zzk.entity.ProductBaseInfo;
import com.zzk.entity.User;
import com.zzk.util.JsonUtils;
import com.zzk.util.MethodAnnotation;
import com.zzk.util.RedisUtil;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * 日志切面
 * @author hsw
 */

@Component
@Aspect
public class LogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductBaseInfoMapper productBaseInfoMapper;
    @Autowired
    private AccessMapper accessMapper;
    @Resource
    private RedisUtil redisUtil;
    private static String[] operateArrry = new String[]{"新增用户","删除用户","修改用户","删除商品","删除线路",
    	"查询订单","新增商品表信息","保存行程总安排","修改商品表信息","修改行程安排"};
    
    public LogAspect() {
        System.out.println("Aop");
    }
    
    static UASparser uasParser = null;  
    
    static {  
        try {  
            uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 

    /**
     * 账号登陆切点
     */
    @Pointcut("execution(* com.zzk.controller..*.loginBusiness(..))")
    public void loginPointcut() { }
    /**
     * 账号登陆切点
     */
    @Pointcut("execution(* com.zzk.controller..*.loginBusinessByCode(..))")
    public void loginByCodePointcut() { }
    /**
     * c端查询商品详情统计
     */
    @Pointcut("execution(* com.zzk.service..*.getProductDetail(..))")
    public void productDetailPointcut() { }
    /**
     * 修改切点
     */
    @Pointcut("execution(* com.zzk.service..*.update(..))")
    public void updatePointcut() { }
    /**
     * 增加切点
     */
    @Pointcut("execution(* com.zzk.service..*.insert(..))")
    public void insertPointcut() { }
    /**
     * 删除切点
     */
    @Pointcut("execution(* com.zzk.service..*.delete(..))")
    public void deletePointcut() { }
    /**
     * 自定义注解切入点
     */
    @Pointcut("@annotation(com.zzk.util.MethodAnnotation)")
    public void methodPointcut() { }
    /**
     * 注解切面
     *
     * @param point
     * @return
     * @throws Throwable
     */
	@Around("methodPointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		String id = UUID.randomUUID().toString();
		String cglibString = "$$EnhancerByCGLIB$$";
		int status = 1;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		//String shopId = request.getParameter("sellerId");
		String token = request.getHeader("Authorization");
		String user ="";
		
		String shopId = "";
		String jsonString = redisUtil.getValue(token);
		if(StringUtils.isNotBlank(jsonString)){
			JSONObject jo = JSONObject.parseObject(jsonString);
			User u = (User)JSONObject.toJavaObject(jo, User.class);
			shopId = u.getMerchatId();
			if(StringUtils.isNotBlank(u.getNickname())){
				user = u.getNickname();
			}else{
				user = u.getPhoneNum();
			}
		}
		 
		String ip = getIp(request);
		String methodRemark = getMthodRemark(point);
		// String methodName = point.getSignature().getName();
		String packages = point.getThis().getClass().getName();
		// 如果是CGLIB动态生成的类
		if (packages.indexOf(cglibString) > -1) { 
			try {
				packages = packages.substring(0, packages.indexOf("$$"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		Object[] methodParam = null;
		Object object;
		try {
			// 获取方法参数
			methodParam = point.getArgs();
			// String param=(String) point.proceed(point.getArgs());
			object = point.proceed();
		} catch (Exception e) {
			// 异常处理记录日志..log.error(e);
			throw e;
		}
		String objId = "";
		Class<?> t = methodParam[0].getClass();
		System.err.println(t);
		if (t.equals(String.class)) {
			objId = (String) methodParam[0];
		} else if (t.equals(ArrayList.class)) {
			objId = "(批量)";
		} else if (t.equals(HashMap.class)) {
			
		} else if (t.equals(Map.class)) {
			
		} else {
			Method method = t.getMethod("getId");
			objId = (String) method.invoke(methodParam[0]);
		}
		System.err.println(objId);
		String content = "";
		for (int i = 0; i < operateArrry.length; i++) {
			if (operateArrry[i].equals(methodRemark)) {
				content = methodRemark;
				break;
			}
		}
		
		OperateLog operateLog = new OperateLog();
		operateLog.setDate(new Date());
		operateLog.setId(id);
		operateLog.setUser(user);
		operateLog.setShopId(shopId);
		operateLog.setIp(ip);
		operateLog.setStatus(status);
		operateLog.setContent(content);
		operateLog.setObjId(objId);
		if(methodRemark.indexOf("新增")>=0){
			operateLog.setType(1);
		}else if(methodRemark.indexOf("修改")>=0){
			operateLog.setType(2);
		}else if(methodRemark.indexOf("删除")>=0){
			operateLog.setType(3);
		}else{
			operateLog.setType(4);
		}
		operateLogMapper.insert(operateLog);
		System.out.println("日志实体：" + operateLog.getUser()
				+ operateLog.getContent());
		
		return object;
	}
    /**
     * 账号密码登陆切面
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "loginPointcut()", returning = "object")
    public void login(JoinPoint joinPoint, Object object) throws Throwable {  
    	this.loginLog(joinPoint, object);
    } 
    /**
     * 统计切面
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @Around(value = "productDetailPointcut()")
    public Object productDetailDeal(ProceedingJoinPoint point){
    	String productId = (String)point.getArgs()[0];
    	String sellerId = "";
    	ProductBaseInfo bean = productBaseInfoMapper.selectByPrimaryKey(productId);
    	if(bean!=null){
    		sellerId = bean.getSellerId();
    	}
    	Access access = new Access();
    	access.setSellerId(sellerId);
    	access.setId(UUID.randomUUID().toString());
		access.setAccessTime(new Date());
		access.setResourceId(productId);
		access.setResourceName(bean.getProductName());
		access.setResourceType(1);
		accessMapper.insertSelective(access);
		Object object=null;
		try {
			object = point.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
    }
    
    /**
     * 动态密码登陆切面
     * @param joinPoint
     * @param object
     * @throws Throwable
     */
    @AfterReturning(value = "loginByCodePointcut()", returning = "object")
    public void loginByCode(JoinPoint joinPoint, Object object) throws Throwable {  
    	this.loginLog(joinPoint, object);
    }
    
    public void loginLog(JoinPoint joinPoint, Object object) throws Throwable {  
    	String shopId = "";
    	
    	
    	
    	// 获取方法名  
    	String methodName = joinPoint.getSignature().getName();
    	//System.err.println(methodName);
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    	String phoneNum = request.getParameter("phoneNum");
    	User user = userMapper.selectByPhoneNum(phoneNum);
    	if(user!=null){
    		shopId = user.getMerchatId();
    	}
    	Result result=(Result)object; 
        String id = UUID.randomUUID().toString();
        String ip = getIp(request);
        if (result==null) {  
            return;  
        }  
        if (joinPoint.getArgs() == null) {
            return;  
        }  
        joinPoint.getArgs();
        // 获取操作内容  
        String content = result.getMessage();  
        String addressJson = "";
		try {
			addressJson = getAddresses("ip="+ip,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObject= JSONObject.parseObject(addressJson);
		JSONObject data = (JSONObject)jsonObject.get("data");
		String country = data.getString("country");
		String region = data.getString("region");
		String city = data.getString("city");
		String address = country+" "+region+" "+city;
		String agent =request.getHeader("User-Agent");
		UserAgentInfo userAgentInfo = LogAspect.uasParser.parse(agent);
		String facility = userAgentInfo.getUaFamily()+";"+ userAgentInfo.getOsName();
		LoginLog loginLog = new LoginLog();  
		
        loginLog.setId(id);  
        loginLog.setIp(ip);
        loginLog.setLoginDate(new Date());
        loginLog.setLoginTime(new Date());
        loginLog.setShopId(shopId);
        
        loginLog.setLoginPlace(address);
        loginLog.setFacility(facility);
        loginLog.setStatus(1);
        loginLog.setLoginStatus(content);
        loginLogMapper.insert(loginLog);
    } 
    
    /*//** 
     * 添加操作日志(后置通知) 
     *  
     * @param joinPoint 
     * @param object 
     *//*  
    @AfterReturning(value = "insertPointcut()", returning = "object")  
    public void insertLog(JoinPoint joinPoint, Object object) throws Throwable {  
        // 判断参数  
    	String shopId = "";
    	String user = "测试用户";
    	String id = UUID.randomUUID().toString();
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        if (joinPoint.getArgs() == null) {// 没有参数  
            return;  
        }  
        // 获取方法名  
        String methodName = joinPoint.getSignature().getName();  
        // 获取操作内容  
        String ip = getIp(request);
        
        String content =  user +"增加";
        
        OperateLog log = new OperateLog();  
        log.setId(id);
        log.setContent(content);
        System.err.println(content);
        log.setIp(ip);  
        log.setDate(new Date());  
        log.setStatus(1);
        log.setShopId(shopId);
        log.setUser(user);
        //operateLogMapper.insert(log);
    } */ 

    /**
     * 方法异常时调用
     *
     * @param ex
     *//*
    public void afterThrowing(Exception ex) {
        System.out.println("afterThrowing");
        System.out.println(ex);
    }*/

    /**
     * 获取方法中的中文备注
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getMthodRemark(ProceedingJoinPoint joinPoint) throws Exception {

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    MethodAnnotation methodCache = m.getAnnotation(MethodAnnotation.class);
                    if (methodCache != null) {
                        methode = methodCache.remark();
                    }
                    break;
                }
            }
        }
        return methode;
    }

    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}
    
    /**
	 * 
	* 根据ip获取地址
	* @param bean
	* @return
    * @author：huashuwen
    * @date：2018-03-08 10:19
	 */
	 public static String getAddresses(String content, String encodingString)  
	            throws UnsupportedEncodingException {  
	        // 这里调用淘宝API  
	        String urlStr = "http://ip.taobao.com/service/getIpInfo.php"; 
	        int minLength = 3;
	        // 从http://whois.pconline.com.cn取得IP所在的省市区信息  
	        String returnStr = getResult(urlStr, content, encodingString);  
	        if (returnStr != null) {  
	            // 处理返回的省市区信息  
	            System.out.println("(1) unicode转换成中文前的returnStr : " + returnStr);  
	            returnStr = decodeUnicode(returnStr);  
	            System.out.println("(2) unicode转换成中文后的returnStr : " + returnStr);  
	            String[] temp = returnStr.split(",");  
	            if(temp.length<minLength){  
	            	//无效IP，局域网测试  
	                return "0";
	            }  
	            return returnStr;  
	        }  
	        return null;  
	    }  
	    /** 
	     * @param urlStr 
	     *            请求的地址 
	     * @param content 
	     *            请求的参数 格式为：name=xxx&pwd=xxx 
	     * @param encoding 
	     *            服务器端请求编码。如GBK,UTF-8等 
	     * @return 
	     */  
	    private static String getResult(String urlStr, String content, String encoding) {  
	        URL url = null;  
	        HttpURLConnection connection = null;  
	        try {  
	            url = new URL(urlStr);  
	            // 新建连接实例  
	            connection = (HttpURLConnection) url.openConnection();
	            connection.setConnectTimeout(2000); 
	            connection.setReadTimeout(2000);
	            connection.setDoOutput(true); 
	            connection.setDoInput(true);  
	            connection.setRequestMethod("POST");  
	            connection.setUseCaches(false); 
	            connection.connect();
	            DataOutputStream out = new DataOutputStream(connection  
	                    .getOutputStream());
	            out.writeBytes(content);  
	            out.flush();
	            out.close();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(  
	                    connection.getInputStream(), encoding));
	            StringBuffer buffer = new StringBuffer();  
	            String line = "";  
	            while ((line = reader.readLine()) != null) {  
	                buffer.append(line);  
	            }  
	            reader.close();  
	            return buffer.toString();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (connection != null) {  
	                connection.disconnect();
	            }  
	        }  
	        return null;  
	    }  
	    /** 
	     * unicode 转换成 中文 
	     *  
	     * @author fanhui 2007-3-15 
	     * @param theString 
	     * @return 
	     */  
	    public static String decodeUnicode(String theString) {  
	        char aChar;  
	        int len = theString.length();  
	        StringBuffer outBuffer = new StringBuffer(len);  
	        for (int x = 0; x < len;) {  
	            aChar = theString.charAt(x++);  
	            if (aChar == '\\') {  
	                aChar = theString.charAt(x++);  
	                if (aChar == 'u') {  
	                    int value = 0;  
	                    int num = 4;
	                    for (int i = 0; i < num; i++) {  
	                        aChar = theString.charAt(x++);  
	                        switch (aChar) {  
	                        case '0':  
	                        case '1':  
	                        case '2':  
	                        case '3':  
	                        case '4':  
	                        case '5':  
	                        case '6':  
	                        case '7':  
	                        case '8':  
	                        case '9':  
	                            value = (value << 4) + aChar - '0';  
	                            break;  
	                        case 'a':  
	                        case 'b':  
	                        case 'c':  
	                        case 'd':  
	                        case 'e':  
	                        case 'f':  
	                            value = (value << 4) + 10 + aChar - 'a';  
	                            break;  
	                        case 'A':  
	                        case 'B':  
	                        case 'C':  
	                        case 'D':  
	                        case 'E':  
	                        case 'F':  
	                            value = (value << 4) + 10 + aChar - 'A';  
	                            break;  
	                        default:  
	                            throw new IllegalArgumentException(  
	                                    "Malformed      encoding.");  
	                        }  
	                    }  
	                    outBuffer.append((char) value);  
	                } else {  
	                    if (aChar == 't') {  
	                        aChar = '\t';  
	                    } else if (aChar == 'r') {  
	                        aChar = '\r';  
	                    } else if (aChar == 'n') {  
	                        aChar = '\n';  
	                    } else if (aChar == 'f') {  
	                        aChar = '\f';  
	                    }  
	                    outBuffer.append(aChar);  
	                }  
	            } else {  
	                outBuffer.append(aChar);  
	            }  
	        }  
	        return outBuffer.toString();
	    }
	    
	    /** 
	     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容 
	     *  
	     * @param args 
	     * @param mName 
	     * @return 
	     */  
	    public String optionContent(Object[] args, String mName) {  
	        if (args == null) {  
	            return null;  
	        }  
	        StringBuffer rs = new StringBuffer();  
	        rs.append(mName);  
	        String className = null;  
	        int index = 1;  
	        // 遍历参数对象  
	        for (Object info : args) {  
	            // 获取对象类型  
	            className = info.getClass().getName();  
	            className = className.substring(className.lastIndexOf(".") + 1);  
	            rs.append("[参数" + index + "，类型:" + className + "，值:"); 
	            
	            // 获取对象的所有方法  
	            Method[] methods = info.getClass().getDeclaredMethods();  
	            // 遍历方法，判断get方法  
	            for (Method method : methods) {  
	                String methodName = method.getName();  
	                // 判断是不是get方法  
	                if (methodName.indexOf("get") == -1) {
	                    continue; 
	                }  
	                Object rsValue = null;  
	                try {  
	                    // 调用get方法，获取返回值  
	                    rsValue = method.invoke(info);  
	                } catch (Exception e) {  
	                    continue;  
	                }  
	                // 将值加入内容中  
	                rs.append("(" + methodName + ":" + rsValue + ")");  
	            }  
	            rs.append("]");  
	            index++;  
	        }  
	        return rs.toString();  
	    }  

}
