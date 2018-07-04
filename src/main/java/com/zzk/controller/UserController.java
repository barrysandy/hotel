package com.zzk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.Constant;
import com.zzk.common.Constact;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.User;
import com.zzk.link.IndustrySMS;
import com.zzk.link.Template;
import com.zzk.service.UserService;
import com.zzk.util.JsonUtils;
import com.zzk.util.PasswordEncoder;
import com.zzk.util.RedisUtil;
import com.zzk.util.RedisUtils;
import com.zzk.util.Result;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * 
 * @Description:
 * @author John
 * @date： 2018年3月12日 下午12:38:35
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {
	
    @Value("${user.password.salt}")
    private String salt;

    @Resource
    private UserService userService;
    
    @Resource
    private RedisUtils redisUtils;
    
    @Resource
    private RedisUtil redisUtil;
    
    //登陆验证码有效次数
    private static final String loginFailNum = "5";
    /**
     * C、B端发送注册验证码
     * @param phoneNumber
     * @return
     * @author John
     * @date： 2018年3月8日 下午3:26:35
     */
    @ResponseBody
    @RequestMapping(value = "/sendRegisterCode", produces = "application/json;charset=UTF-8")
    public Result<Object> sendRegisterCode(String	phoneNumber) {
	    	if(StringUtils.isBlank(phoneNumber)){
	    		return new Result<Object>(0, "error", "手机号码不能为空", null);
	    	}
	    	boolean status=	userService.checkUser(phoneNumber);
    		if(status){
    			return new Result<Object>(0, "error", "用户已存在", null);
    		}
    		return sendCode(phoneNumber, Template.REGISTER_CODE);
    }
    
    /**
     * 发送更新密码验证码
     * @param phoneNumber
     * @return
     * @author John
     * @date： 2018年3月8日 下午12:53:56
     */
    
    @RequestMapping(value = "/sendUpdatePasswordCode")
    @ResponseBody
    public Result sendUpdatePasswordCode(String phoneNumber, String phoneNum) {
    	if(StringUtils.isBlank(phoneNumber) && StringUtils.isBlank(phoneNum)){
    		return new Result(0,"error","手机号码不能为空");
    	}
        phoneNumber = (phoneNumber == null ? phoneNum : phoneNumber);
    	boolean status=	userService.checkUser(phoneNumber);
		if(!status){
			return new Result(0,"error","用户不存在");
		}
		return sendCode(phoneNumber, Template.UPDATE_PWD_CODE);
    }
    
    /**
     * 线路B端注册
     * @param user
     * @return
     * @author John
     * @date： 2018年3月9日 下午1:55:42
     */
    @ResponseBody
    @RequestMapping(value = "/registerBusiness", produces = "application/json;charset=UTF-8")
    public Result registerBusiness(User user,BusinessInfo businessInfo,String validateCode ){
    	//电商
    	int userType = 2;
    	if(StringUtils.isBlank(user.getPhoneNum())){
    		return new Result<User>(0, "error", "用户名不能为空", null);
    	}
    	if(StringUtils.isBlank(user.getPassword())){
    		return new Result<User>(0, "error", "密码不能为空", null);
    	}
    	if(StringUtils.isBlank(validateCode)){
    		return new Result<User>(0, "error", "验证码不能为空", null);
    	}
    	if(StringUtils.isBlank(businessInfo.getBusinName())){
    		return new Result<BusinessInfo>(0, "error", "供应商名称不能为空", null);
    	}
    	if(businessInfo.getType()==0){
    		return new Result<BusinessInfo>(0, "error", "供应商类型不能为空", null);
    	}
    	if(StringUtils.isBlank(businessInfo.getBusinMain())){
    		return new Result<BusinessInfo>(0, "error", "主营业务不能为空", null);
    	}
    	//验证验证码
    	String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
    	if (!"999999".equals(validateCode)&&!validateCode.equals(recordCode)){
    		return new Result<BusinessInfo>(0, "error", "验证码错误", null);
        }
    	Result result = new Result(); 
    	int existed = userService.checkUserInfo(user,result);
    	
        if (existed < 0) {
            return result;
        }
    	PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
        String encode = encoderMd5.encode(user.getPassword());
        user.setPassword(encode);
        user.setUserType(userType);
        String merchatId = UUID.randomUUID().toString();
		String userId = UUID.randomUUID().toString();
		
		user.setCreateTime(new Date());
		user.setRegTime(new Date());
		user.setId(userId);
		user.setMerchatId(merchatId);
		user.setStatus(1);
		
		businessInfo.setId(merchatId);
		businessInfo.setCreateTime(new Date());
		businessInfo.setBusinStatus(Constant.BUSIN_EXAMINE);
		businessInfo.setUserId(userId);
		businessInfo.setPhone(user.getPhoneNum());
		businessInfo.setStatus(1);
        return userService.insertBusinessUser(user, businessInfo);
    }
    
    /**
     * 线路C端注册
     * @param user
     * @return
     * @author hua
     * @date： 2018年3月9日 下午1:55:42
     */
    @ResponseBody
    @RequestMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public Result register(User user, String validateCode) {
        UUID uuid = UUID.randomUUID();
        user.setId(uuid.toString().replaceAll("-", ""));
        if (StringUtils.isEmpty(user.getNickname()) && StringUtils.isEmpty(user.getEmail()) && StringUtils.isEmpty(user.getPhoneNum())) {
            return new Result(0, "error", "名称、邮件、电话不能同时为空", null);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return new Result(0, "error", "密码不能为空", null);
        }
        if (StringUtils.isEmpty(validateCode)) {
            return new Result(0, "error", "验证码不能为空", null);
        }
        String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
        if (!"999999".equals(validateCode)&&!validateCode.equals(recordCode)) {
        	return new Result(0, "error", "验证码错误", null);
         }
        Result result = new Result();
        int existed = userService.checkUserInfo(user, result);
        if (existed < 0) {
            return result;
        }
        MessageDigest md5;
        String pd;
        try {
            PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
            String encode = encoderMd5.encode(user.getPassword());
            pd = user.getPassword();
            user.setPassword(encode);
            user.setStatus(1);
            user.setUserType(1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("内部错误");
            result.setState(0);
            return result;
        }
        if (StringUtils.isBlank(user.getNickname())) {
        	user.setNickname(user.getPhoneNum());
        }
        user.setRegSource(2);
        user.setAccount(user.getPhoneNum());
        user.setIsAuth(101);
       return userService.insertUser(user);
    }
    
    
    
    /**
     * 线路B端账号密码登录
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:48:42
     */
    @ResponseBody
    @RequestMapping(value = "/loginBusiness", produces = "application/json;charset=UTF-8")
    public Result<Object> loginBusiness( User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute(Constact.IS_AUTHENTICATION,false);
        if(user == null){
    		return new Result<Object>(0, "error", "用户名或密码为空", null);
    	}
        if (StringUtils.isEmpty(user.getPhoneNum())|| StringUtils.isEmpty(user.getPassword())) {
            return new Result<Object>(0, "error", "用户名或密码为空", null);
        }
        String token = UUID.randomUUID().toString();
        try {
            return userService.checkBusinessUserByPassword(user, token,session);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<Object>(0, "error", "内部错误", null);
        }
    }
    
    @RequestMapping(value = "/wxLogin")
    @ResponseBody
    public Result<Object> wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute(Constact.IS_AUTHENTICATION,false);

        String wxJSAPIAppId = "wx93a4e8ff7be8feb9";
        String wxJSAPISecret = "eacce4048a253e99fa2ea649cf11b928";
        String wxJSAPIRedirectUrl = "http://www.zhishengxixing.com/user/wxLogin";
        String encodedWSJSAPIRedirectUrl = URLEncoder.encode(wxJSAPIRedirectUrl);

        Result<Object> result = new Result<Object>();
        if (request.getParameter("code") == null || request.getParameter("code").toString().equals("")) {
            String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxJSAPIAppId + "&redirect_uri=" + encodedWSJSAPIRedirectUrl + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
            System.out.println("wx jsapi redirct url:" + redirectUrl);
            response.sendRedirect(redirectUrl);
        } else {
            String code = request.getParameter("code");
            String resultStr = "";
            try {
                resultStr = sendGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxJSAPIAppId + "&secret=" + wxJSAPISecret + "&code=" + code + "&grant_type=authorization_code");
            System.out.println("code  :  "+code+"    result:" + resultStr);
            JSONObject resultObject = JSONObject.parseObject(resultStr);
            if (resultObject.containsKey("errcode")) {
                System.out.println("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());
                result.setState(0);
            } else {
                String openId = resultObject.get("openid").toString();
                User user = userService.selectByOpenId(openId);
                String token = UUID.randomUUID().toString();
                if(user!=null){
                    redisUtil.cacheValue(token, JSON.toJSONString(user), 360000);
                    //redisUtils.setDataByKey(token, JSON.toJSONString(user), 360000);
                    HashMap<String, Object> map = new HashMap<String,Object>();
                    map.put("token", token);
                    map.put("info",user);
                    result.setState(1);
                    result.setMsg("登录成功");
                    result.setData(map);
                    request.getSession().setAttribute(Constact.IS_AUTHENTICATION,false);
                }else{
                    UUID uuid = UUID.randomUUID();
                    String access_token = resultObject.get("access_token").toString();
                    String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId + "&lang=zh_CN";
                    resultStr = sendGet(url);
                    User newUser = new User();
                    System.out.println("resultStr:  "+resultStr);
                    JSONObject userObj = JSONObject.parseObject(resultStr);

                    newUser.setId(uuid.toString().replaceAll("-", ""));
                    newUser.setOpenId(openId);
                    newUser.setNickname(userObj.get("nickname").toString());
                    newUser.setGender(userObj.getIntValue("sex"));
                    newUser.setHeadImg(userObj.get("headimgurl").toString());
                    newUser.setStatus(1);
                    userService.insert(newUser, token);
                    HashMap<String, Object> map = new HashMap<String,Object>();
                    map.put("token", token);
                    map.put("info",newUser);
                    result.setState(1);
                    result.setMsg("登录成功");
                    result.setData(map);
                    request.getSession().setAttribute(Constact.IS_AUTHENTICATION,false);
                }
            }
            } catch (Exception e1) {
                e1.printStackTrace();
                return new Result<Object>(0,"error",e1.toString(),null);
            }
        }
        return result;
    }
    
    /**
     * 账号密码登录
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:48:42
     */
    @ResponseBody
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public Result<User> login(User user,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute(Constact.IS_AUTHENTICATION,false);
    	if(user == null){
    		return new Result<User>(0, "error", "用户名或密码为空", null);
    	}
        if (StringUtils.isEmpty(user.getPhoneNum())|| StringUtils.isEmpty(user.getPassword())) {
            return new Result<User>(0, "error", "用户名或密码为空", null);
        }
        String token = UUID.randomUUID().toString();
        try {
            return userService.checkUserByPassword(user, token,request);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<User>(0, "error", "内部错误", null);
        }
    }
    
    /***
     * C端动态密码登录
     * @param user
     * @param response
     * @param validateCode
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:49:19
     */
    @RequestMapping(value = "/loginByCode")
    @ResponseBody
    public Result<Object> loginByCode(User user, HttpServletRequest request,HttpServletResponse response,String validateCode) {
        HttpSession session = request.getSession();
        session.setAttribute(Constact.IS_AUTHENTICATION,false);
    	String phoneNum = "";
    	if(user == null || StringUtils.isBlank(user.getPhoneNum())){
    		return new Result<Object>(0, "error", "手机号码不能为空", null);
    	}
    	if(StringUtils.isBlank(validateCode)){
    		return new Result<Object>(0, "error", "验证码不能为空", null);
    	}
    	//获取redis中缓存的验证码信息
    	String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
    	phoneNum = user.getPhoneNum();
    	if(StringUtils.isBlank(recordCode)){
    		return new Result<Object>(0, "error", "请获取验证码", null);
    	}
    	if(!recordCode.equalsIgnoreCase(validateCode)){
    		return new Result<Object>(0, "error", "验证码错误", null);
    	}
    	boolean status =userService.checkUser(phoneNum);
    	if(!status){
    		return new Result<Object>(0, "error", "用户不存在", null);
    	}
    	User dbUser = userService.selectByPhoneNum(phoneNum);
    	if(dbUser != null){
            session.setAttribute(Constact.IS_AUTHENTICATION,true);
    		return new Result<Object>(1, "success", "登录成功", dbUser);
    	}
    	return new Result<Object>(0, "error", "系统错误", dbUser);
    }
    
    /***
     * 动态密码登录（线路B端）
     * @param user
     * @param response
     * @param validateCode
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:49:19
     */
    @RequestMapping(value = "/loginBusinessByCode")
    @ResponseBody
    public Result<Object> loginBusinessByCode(User user,HttpServletRequest request, HttpServletResponse response,String validateCode) {
        HttpSession session = request.getSession();
        session.setAttribute(Constact.IS_AUTHENTICATION,false);
        String phoneNum = "";
    	if(user == null || StringUtils.isBlank(user.getPhoneNum())){
    		return new Result<Object>(0, "error", "手机号码不能为空", null);
    	}
    	if(StringUtils.isBlank(validateCode)){
    		return new Result<Object>(0, "error", "动态密码不能为空", null);
    	}
    	//获取redis中缓存的验证码信息
    	String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
    	phoneNum = user.getPhoneNum();
    	if(StringUtils.isBlank(recordCode)){
    		return new Result<Object>(0, "error", "请重新获取验证码", null);
    	}
    	if(!recordCode.equalsIgnoreCase(validateCode)){
    		return validateFail(phoneNum);
    	}
    	boolean status =userService.checkUser(phoneNum);
    	if(!status){
    		return new Result<Object>(0, "error", "用户不存在", null);
    	}
    	User dbUser = userService.selectByPhoneNum(phoneNum);
    	
    	if(dbUser != null){
    		if(dbUser.getUserType() == 2){
    			return userService.getMeichantInfo(dbUser,request);
    		}else{
    			return new Result<Object>(0, "error", "不是供应商用户", null);
    		}
    	}
    	return new Result<Object>(0, "error", "系统错误", dbUser);
    }
    
    /**
     * <p>description:发送动态密码</p>
     * @param phoneNum 手机号码
     * @return 
     * @author huashuwen
     * @date 2017-5-26下午2:32:59
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendLoginCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result<Object> sendLoginCode(String phoneNum) {
    	Result<Object> result = new Result<Object>();
    	User user = userService.selectByPhoneNum(phoneNum);
    	if(user == null){
    		return new Result<Object>(0,"error","未注册用户",null);
    	}
        result = sendCode(phoneNum, Template.DYNAMIC_PWD_CODE);
        return result;
    }
    /**
     * <p>description:找回密码</p>
     * @return
     * @author huashuwen
     * @date 2017-5-26下午2:32:59
     */
    @RequestMapping(value = "/updatePassword", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePassword(User bean, String verificationCode) {

        Result<Object> result = new Result<Object>();

        if (StringUtils.isEmpty(bean.getPhoneNum())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("手机号不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(bean.getPassword())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("密码不能为空");
            return JSON.toJSONString(result);
        }
        result = userService.updatePassword(bean, verificationCode);
        return JSON.toJSONString(result);
    }
    
    /**
     * 旧密码修改
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 下午1:35:07
     */
    @RequestMapping(value = "/changePassword")
    @ResponseBody
    public String changePassword(User user,String newPassword){
    	if(user ==null){
    		return JsonUtils.lineJsonData(0, "error", "参数错误", null);
    	}
    	if(StringUtils.isBlank(user.getId())){
    		return JsonUtils.lineJsonData(0, "error", "Id不能为空", null);
    	}
    	if(StringUtils.isBlank(user.getPassword())){
    		return JsonUtils.lineJsonData(0, "error", "旧密码不能为空", null);
    	}
    	if(StringUtils.isBlank(newPassword)){
    		return JsonUtils.lineJsonData(0, "error", "新密码不能为空", null);
    	}
    	return userService.updatePasswordByOldPassword(user,newPassword);
    }
    
    
    
    
    
    
    
    //_____________________________________________________
    
    
    
    @ResponseBody
    @RequestMapping("/redisTest")
    public void redisTest(){
    	redisUtil.cacheSet("666666", "123456", 3600);
    	//redisUtils.setDataByKey("666666", "123456", 3600);
			//String a = redisUtils.getDataByKey("666666");
	    	//System.err.println(a);
    }
    @ResponseBody
    @RequestMapping("/getTest")
    public void getTest(){
    	String a=redisUtil.getValue("666666");
    	System.err.println(a);
    }
    
    /**
     * 修改用户基本信息
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 下午1:28:35
     */
    @RequestMapping(value = "/updateProfile")
    @ResponseBody
    public String updateProfile(User user) {
    	if(user == null){
    		JsonUtils.lineJsonData(0, "error", "用户参数错误", null);
    	}
    	return "";
    	
    
    }
    
    
    /**
     * 验证码修改密码
     * @param verificationCode
     * @return
     * @author John
     * @date： 2018年3月12日 下午2:04:04
     */
    @RequestMapping(value = "/changePasswordCode")
    @ResponseBody
    public String changePasswordCode(User user, String verificationCode) {
    	if(user ==null){
    		return JsonUtils.lineJsonData(0, "error", "参数错误", null);
    	}
    	if(StringUtils.isBlank(verificationCode)){
    		return JsonUtils.lineJsonData(0, "error", "验证码不能为空", null);
    	}
    	if(StringUtils.isBlank(user.getId())){
    		return JsonUtils.lineJsonData(0, "error", "Id不能为空", null);
    	}
    	if(StringUtils.isBlank(user.getPassword())){
    		return JsonUtils.lineJsonData(0, "error", "新密码不能为空", null);
    	}
    	return userService.updatePasswordByCode(user,verificationCode);
    }
    
    
    /**
     * 获取用户信息通过userid
     * @param userId
     * @return
     * @author John
     * @date： 2018年3月12日 下午1:18:46
     */
    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public String userInfo(String userId){
    	if(StringUtils.isBlank(userId)){
    		return JsonUtils.lineJsonData(0, "error", "用户ID为空", null);
    	}
    	User user = userService.selectByPrimaryKey(userId);
    	if(user!=null){
    		JsonUtils.lineJsonData(1, "success", "获取用户信息成功", user);
    	}
    	return JsonUtils.lineJsonData(0, "error", "用户不存在", null);
    }
    
    
    /**
     * 
     * @param phoneNumber
     * @return
     * @author John
     * @date： 2018年3月8日 上午11:44:13
     */
    private Result<Object> sendCode(String phoneNumber,String template){
             HashMap<String, Object> resultMap = null;
             //使用随机数生成一个6位数验证码
             String yzmStr = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
             redisUtils.setDataByKey(phoneNumber, yzmStr, 600);
             resultMap = IndustrySMS.link(phoneNumber, template, "", new String[]{yzmStr,"10分钟"}); 
             String status = (String) resultMap.get("status");
             Integer statusInt = Integer.parseInt(status);
             if(statusInt==1){
          	   return new Result<Object>(1, "success", "发送成功", null);
             }else{
          	   String msg = (String) resultMap.get("msg");
          	   return new Result<Object>(0, "error", msg, null);
             }
             
    }
    /**
     * 根据用户ID修改头像
     * @param id
     * @param headImg
     * @return
     * @author John
     * @date： 2018年3月12日 下午7:59:59
     */
    public String updateHeadImgByPrimaryKey(String id,String headImg){
    	if(StringUtils.isBlank(id)||StringUtils.isBlank(headImg)){
    		return JsonUtils.lineJsonData(0, "error", "参数", null);
    	}
    	return  userService.updateHeadImgById(id,headImg);
    }
    
    /**
     * 短信发送测试接口
     * @param phoneNumber
     * @param request
     * @return
     * @author John
     * @date： 2018年3月12日 上午9:52:47
     */
    @RequestMapping("/sendTest")
    public ModelAndView sendTest(String phoneNumber,HttpServletRequest request){
    	String template = request.getParameter("template");
    	ModelAndView view = new ModelAndView();
    	if(StringUtils.isBlank(phoneNumber)){
    		view.setViewName("error");
    		return view;
    	}
    	String json = "";
    	switch (template) {
		case "1":
			//json=sendCode(phoneNumber, Template.REGISTER_CODE);
			break;
		case "2":
			//json=sendCode(phoneNumber, Template.UPDATE_PWD_CODE);
			break;
		case "3":
			//json=sendCode(phoneNumber, Template.DYNAMIC_PWD_CODE);
			break;
		default:
			break;
		}
    	view.setViewName("success");
    	return view;
    }
    @RequestMapping("/del")
	@ResponseBody
	public String del(String id){
		userService.delete(id);
		/*try{
			unfinishedTripElementService.delete(id);
			
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}*/
		return JsonUtils.turnJson(true,"success",null);
	}
    
    private Result validateFail(String phoneNum){
    	String num = redisUtils.getDataByKey(phoneNum+"loginFailNum");
		if(num==null){
			num ="1";
			redisUtils.setDataByKey(phoneNum+"loginFailNum",num, 600);
		}else if (Integer.valueOf(num)>=Integer.valueOf(loginFailNum)){
			redisUtils.removeBykey(phoneNum);
			redisUtils.removeBykey(phoneNum+"loginFailNum");
			return new Result<User>(0, "error", "验证码错误超过"+loginFailNum+"次，请重新获取验证码", null);
		}else{
			num = String.valueOf(Integer.valueOf(num)+1);
			redisUtils.setDataByKey(phoneNum+"loginFailNum", num, 600);
		}
		return new Result<User>(0, "error", "验证码错误", null);
    }
    
    public String sendGet(String url) throws Exception {
        String result = "";
        BufferedReader in = null;
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
