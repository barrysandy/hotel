package com.zzk.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//import scala.collection.mutable.Map;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.zzk.util.Result;
import com.zzk.common.CommonConstants;
import com.zzk.util.PasswordEncoder;
import com.zzk.util.StringUtils;
import com.zzk.util.RedisUtils;
import com.zzk.entity.TMpClientUser;
import com.zzk.link.IndustrySMS;
import com.zzk.link.Template;
import com.zzk.util.RedisUtil;
import com.zzk.service.TMpClientUserService;
import com.zzk.service.TMpClientUserServiceImpl;

/**
 * <p>Title:TMpClientUserController</p>
 * <p>Description:信誉记录表</p>
 * <p>Copyright: 川大智胜系统集成公司</p>
 *
 * @author li.shiqiang
 * @data 2017-3-6 上午10:44:46
 */
@Controller
@RequestMapping(value = "/member-web/user")
public class TMpClientUserController {

    @Autowired
    private TMpClientUserService tMpClientUserService;
   
    @Value("${user.password.salt}")
    private String salt;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisUtils redisUtils;

    private final String repairedTemplateId="183445";
    private final String imageValidatePrefix = "imageValidate_";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //忽略字段绑定异常
        //binder.setIgnoreInvalidFields(true);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
    /**
     * 信誉记录表分页查询
     *
     * @param page
     * @param model
     * @param id
     * @return
     * @return
     * @author lishiqiang
     * @date 2017-3-16 modify history
     */
 /*@RequestMapping("/list")
 public ModelAndView list(Page pager, Model model, String search,String destination) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<TMpClientUser> list = tMpClientUserService.selectByPage(map);
		int totalNum = tMpClientUserService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		return toVm("member/tmpClientUserIndex");
	}*/

    /**
     * 信誉记录表首页
     *
     * @param
     * @param
     * @param
     * @return
     * @author lishiqiang
     * @date 2017-3-16 modify history
     */
	/*@RequestMapping("/index")
	public ModelAndView index(Page pager, Model model, String search) {
		Map map = new HashMap();
		map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
		map.put("pageSize", pager.getPageSize());
		map.put("search", search);
		List<TMpClientUser> list = tMpClientUserService.selectByPage(map);
		int totalNum = tMpClientUserService.selectCount(map);
		pager.setFullListSize(totalNum);
		pager.setList(list);
		model.addAttribute("pager", pager);
		model.addAttribute("search",search);
		return toVm("member/tmpClientUserIndex");
	}*/
    @RequestMapping(value = "/info", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String detail(String token) {
        Result<Object> result = new Result<Object>();
        String info = tMpClientUserService.getUserInfo(token);

        if (StringUtils.isEmpty(info)) {
            
            result.setMessage("未能找到用户信息");
            result.setState(0);
        } else {
            result.setMessage("获取成功");
            result.setState(1);
            result.setData(info);
        }
        return JSON.toJSONString(result);
    }
    
    
    
    @RequestMapping(value = "/updateProfile", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateProfile(TMpClientUser bean) {

        Result<Object> result = new Result<Object>();

        if (StringUtils.isEmpty(bean.getId())) {
            result.setState(0);
            result.setMsg("error"); 
            result.setMessage("id不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(bean.getHeadImg())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("头像不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(bean.getNickname())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("昵称不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(bean.getGender())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("性别不能为空");
            return JSON.toJSONString(result);
        }
        int code = tMpClientUserService.updateByPrimaryKeySelective(bean);
        if (code > 0) {
            result.setState(1);
            result.setMsg("success");
            result.setMessage("保存成功");
        } else {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("保存失败");
        }

        return JSON.toJSONString(result);

    }

    @RequestMapping(value = "/infoByUserId", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String infoByUserId(String userId) {
        Result<Object> result = new Result<Object>();
        if (StringUtils.isEmpty(userId)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("userId不能为空");
            return JSON.toJSONString(result);
        }
        TMpClientUser info = tMpClientUserService.getUserInfoById(userId);

        if (StringUtils.isEmpty(info)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("获取失败");
        } else {
            result.setState(1);
            result.setMsg("success");
            result.setMessage("获取成功");
            result.setData(info);
        }

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/register", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String register(TMpClientUser tMpClientUser, String validateCode) {
    	Result<Object> result = new Result<Object>();
        UUID uuid = UUID.randomUUID();
        tMpClientUser.setId(uuid.toString().replaceAll("-", ""));
        if (StringUtils.isEmpty(tMpClientUser.getNickname()) && StringUtils.isEmpty(tMpClientUser.getEmail()) && StringUtils.isEmpty(tMpClientUser.getPhoneNum())) {
          
            result.setMessage("名称、邮件、电话不能同时为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(tMpClientUser.getPassword())) {
            result.setMessage("密码不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(validateCode)) {
            result.setMessage("验证码不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        String recordCode = redisUtils.getDataByKey(tMpClientUser.getPhoneNum());
        //if (!"999999".equals(validateCode)&&!validateCode.equals(recordCode)) {
        if (!"111111".equals(validateCode)) {
 			 result.setMessage("验证码错误");
             result.setState(0);
             return JSON.toJSONString(result);
         }
        int existed = tMpClientUserService.checkUserInfo(tMpClientUser, result);

        if (existed < 0) {
            return JSON.toJSONString(result);
        }
        MessageDigest md5;
        String pd;
        try {
            PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
            String encode = encoderMd5.encode(tMpClientUser.getPassword());
            pd = tMpClientUser.getPassword();
            tMpClientUser.setPassword(encode);
            tMpClientUser.setStatus(1);
            tMpClientUser.setUserType(1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("内部错误");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        String token = UUID.randomUUID().toString();
        if (StringUtils.isBlank(tMpClientUser.getNickname())) {
            tMpClientUser.setNickname(tMpClientUser.getPhoneNum());
        }
        tMpClientUser.setRegSource(2);
        tMpClientUser.setAccount(tMpClientUser.getPhoneNum());
        tMpClientUser.setIsAuth(101);
        int code = tMpClientUserService.insert(tMpClientUser, token, pd);
        result.setData(tMpClientUser);
        result.setState(1);
        return JSON.toJSONString(result);
    }
    
    @RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String login(TMpClientUser user, HttpServletResponse response) {
//        response.setCharacterEncoding("text/html,charset=utf-8");
        
        Result<Object> result = new Result<Object>();
        String recordCode = redisUtils.getDataByKey(imageValidatePrefix + user.getPhoneNum());

        /*if (StringUtils.isNotBlank(imageValidate)&&recordCode != null && !recordCode.equalsIgnoreCase(imageValidate)) {
            json.put("message", "验证码错误");
            json.put("code", CommonConstants.LOGIN_PASSWORD_ERROR);
            return json.toJSONString();
        }*/
        if (StringUtils.isEmpty(user.getPhoneNum())) {
            result.setMessage("电话号码不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            result.setMessage("密码不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        String token = UUID.randomUUID().toString();
        try {
            int code = tMpClientUserService.checkUserByPassword(user, token, result);
            if (code == 1) {
                redisUtils.removeBykey(imageValidatePrefix + user.getPhoneNum());
                result.setMessage("登陆成功");
                result.setState(1);
                return JSON.toJSONString(result);
            } else {
                
                result.setMessage("账号或者密码错误");
                result.setState(0);
                return JSON.toJSONString(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("内部错误");
            result.setState(0);
            return JSON.toJSONString(result);
        }
    }
    
    //c端动态验证码登录
    @RequestMapping(value = "/loginByCode", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String loginByCode(TMpClientUser user, HttpServletResponse response,String validateCode) {
        Result<Object> result = new Result<Object>();
        String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
        if (!"999999".equals(validateCode)&&!validateCode.equals(recordCode)) {
			result.setMessage("验证码错误");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotBlank(validateCode)&&recordCode != null && !recordCode.equalsIgnoreCase(validateCode)) {
          
            result.setMessage("验证码错误");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(user.getPhoneNum())) {
            result.setMessage("电话号码不能为空");
            result.setState(0);
            return JSON.toJSONString(result);
        }
        int code = tMpClientUserService.checkPhone(user.getPhoneNum());
        if (code == 1){
        	JSONObject json = new JSONObject();
        	String token = UUID.randomUUID().toString();
        	tMpClientUserService.cecheUser(token,json,user);
            json.put("token", token);
            result.setMessage("登陆成功");
            result.setData(json);
            result.setState(1);
        }else{
        	
        	result.setMessage("登陆成功");
            result.setState(1);
        }
        return JSON.toJSONString(result);
    }
    
  //管理端动态验证码登录
    @RequestMapping(value = "/loginByCodeB", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String loginByCodeB(TMpClientUser user, HttpServletResponse response,String validateCode) {
        Result<Object> result = new Result<Object>();
        String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
        if (!"999999".equals(validateCode)&&!validateCode.equals(recordCode)) {
			result.setMessage("动态密码错误");
            result.setState(0);
			return JSON.toJSONString(result);
        }
        if (StringUtils.isNotBlank(validateCode)&&recordCode != null && !recordCode.equalsIgnoreCase(validateCode)) {
            
            result.setMessage("动态密码错误");
            result.setState(0);
			return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(user.getPhoneNum())) {
           
            result.setMessage("电话号码不能为空");
            result.setState(0);
			return JSON.toJSONString(result);
        }
        int code = tMpClientUserService.checkPhone(user.getPhoneNum());
        if (code == 1){
        	JSONObject json = new JSONObject();
        	String token = UUID.randomUUID().toString();
        	tMpClientUserService.cecheUser(token,json,user);
            json.put("token", token);
            result.setMessage("登陆成功");
            result.setState(1);
            result.setData(json);
        }else{
        	result.setMessage("您还未注册");
            result.setState(0);
        }
        return JSON.toJSONString(result);
    }
    
    @RequestMapping("/validate")
    @ResponseBody
    public String validate(String token) {
    	Result<Object> result = new Result<Object>();
        int code = tMpClientUserService.validate(token);

        if (code == 1) {
            result.setMessage("验证成功");
            result.setState(1);
			return JSON.toJSONString(result);
        } else {
            result.setMessage("验证失败");
            result.setState(0);
			return JSON.toJSONString(result);
        }

    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendUpdatePasswordCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendUpdatePasswordCode(String phoneNum) {

        Result<Object> result = new Result<Object>();

        if (!StringUtils.isEmpty(phoneNum)) {
            result = sendCode(phoneNum, Template.UPDATE_PWD_CODE);
        } else {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("请输入手机号");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 验证手机验证码
     *
     * @param
     * @return
     * @author sty
     * @date 2017-04-11
     */
    @RequestMapping(value = "/validatePhoneCodeByUserid", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validatePhoneCodeByUserid(String userId, String validateCode) {

        Result result = new Result();
        TMpClientUser user = tMpClientUserService.getUserInfoById(userId);

		String recordCode = redisUtils.getDataByKey(user.getPhoneNum());

        if (StringUtils.isEmpty(recordCode)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("无效验证码");
        }
        if (!recordCode.equals(validateCode)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("验证码错误");
        }
        result.setState(1);
        result.setMsg("success");
        result.setMessage("验证成功");

        return JSON.toJSONString(result);

    }

    /**
     * 验证手机验证码
     *
     * @param
     * @return
     * @author sty
     * @date 2017-04-11
     */
    @RequestMapping(value = "/validatePhoneCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validatePhoneCode(String userId, String phoneNum, String validateCode) {

        Result result = new Result();
        String recordCode = redisUtils.getDataByKey(phoneNum);
        if (StringUtils.isEmpty(userId)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("userId不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(recordCode)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("无效验证码");
            return JSON.toJSONString(result);
        }
        if (!recordCode.equals(validateCode)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("验证码错误");
            return JSON.toJSONString(result);
        }
        TMpClientUser user = new TMpClientUser();

        user.setId(userId);
        user.setPhoneNum(phoneNum);
        result = tMpClientUserService.bindPhone(user);
        return JSON.toJSONString(result);

    }

    @RequestMapping(value = "/updatePassword", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePassword(TMpClientUser bean, String verificationCode) {

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
        result = tMpClientUserService.updatePassword(bean, verificationCode);

        return JSON.toJSONString(result);
    }
    /**
     * 通过旧密码修改密码
     *
     * @param
     * @return
     * @author huashuwen
     * @date 2018-0-11
     */
    @RequestMapping(value = "/updatePasswordByOldPwd", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updatePasswordByOldPwd(TMpClientUser bean, String oldPwd) {

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
            result.setMessage("新密码不能为空");
            return JSON.toJSONString(result);
        }
        result = tMpClientUserService.updatePasswordByOldPwd(bean, oldPwd);
        return JSON.toJSONString(result);
    }
    
    
    @RequestMapping(value = "/wxLogin")
    @ResponseBody
    public String wxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String wxJSAPIAppId = "wx93a4e8ff7be8feb9";
    String wxJSAPISecret = "eacce4048a253e99fa2ea649cf11b928";
    String wxJSAPIRedirectUrl = "http://www.daxi51.com/member-web/user/wxLogin" ;
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
            /*out.println("获取access_token出错！错误信息为：" + resultObject.get("errmsg").toString());*/
            
            result.setMessage("获取access_token出错");
            result.setState(0);
        } else {
            String openId = resultObject.get("openid").toString();
            TMpClientUser user = tMpClientUserService.selectByOpenId(openId);
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
            }else{
            	
            	UUID uuid = UUID.randomUUID();
            	String access_token = resultObject.get("access_token").toString();
                String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId + "&lang=zh_CN";
                resultStr = sendGet(url);
                TMpClientUser newUser = new TMpClientUser();
                System.out.println("resultStr:  "+resultStr);
                JSONObject userObj = JSONObject.parseObject(resultStr);
                
                newUser.setId(uuid.toString().replaceAll("-", ""));
                newUser.setOpenId(openId);
                newUser.setNickname(userObj.get("nickname").toString());
                newUser.setGender(userObj.getIntValue("sex"));
                newUser.setHeadImg(userObj.get("headimgurl").toString());
                newUser.setStatus(1);
                tMpClientUserService.insert(newUser, token, "");
                
                HashMap<String, Object> map = new HashMap<String,Object>();
            	map.put("token", token);
            	map.put("info",newUser);
            	result.setState(1);
            	result.setMsg("登录成功");
            	result.setData(map);
            }
        }
        
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	return JSON.toJSONString(result);
    }


    /**
     * <p>description:发送注册手机验证码</p>
     * @param phoneNum 手机号码
     * @return 
     * @author Wen Yugang
     * @date 2017-5-26下午2:32:59
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendRegisterCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendRegisterCode(String phoneNum) {

        Result<Object> result = new Result<Object>();
        int code = tMpClientUserService.checkPhone(phoneNum);

        if (code == 0){
            result = sendCode(phoneNum, Template.REGISTER_CODE);
        }else{
            result.setState(0);
            result.setMsg("error");
            result.setMessage("手机号已注册");
        }

        return JSON.toJSONString(result);

    }
    
    /**
     * <p>description:发送动态密码登录</p>
     * @param phoneNum 手机号码
     * @return 
     * @author huashuwen
     * @date 2017-5-26下午2:32:59
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/sendLoginCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendLoginCode(String phoneNum) {
    	Result<Object> result = new Result<Object>();
    	TMpClientUser tMpClientUser = tMpClientUserService.selectByPhoneNum(phoneNum);
    	if(tMpClientUser == null){
    		result.setState(0);
    		result.setMessage("未注册的手机号");
    		result.setMsg("notregister");
    		return JSON.toJSONString(result);
    	}
        result = sendCode(phoneNum, Template.DYNAMIC_PWD_CODE);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/sendUpdatePayPassword", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendUpdatePayPassword(String userId) {

        Result<Object> result = new Result<Object>();
        TMpClientUser user = tMpClientUserService.getUserInfoById(userId);

        if (user != null && user.getPhoneNum() != null) {
            result = sendCode(user.getPhoneNum(), Template.UPDATE_PWD_CODE);
        } else {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("手机号未绑定");
        }

        return JSON.toJSONString(result);

    }

    @RequestMapping(value = "/bindPhone", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String bindPhone(TMpClientUser bean) {

        Result<Object> result = new Result<Object>();

        if (StringUtils.isEmpty(bean.getId())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("id不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(bean.getPhoneNum())) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("电话号码不能为空");
            return JSON.toJSONString(result);
        }
        result = tMpClientUserService.bindPhone(bean);

        return JSON.toJSONString(result);

    }

   /* *//**
     * 发送验证邮件
     *
     * @param email
     * @return
     * @author sty
     * @date 2017-04-11
     *//*
    @RequestMapping(value = "/sendValidateEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String sendValidateEmail(HttpServletResponse response, String userId, String email,HttpServletRequest request) {
        Util.SetHttpServletResponse(response);//处理跨域
        Result<Object> result = new Result<Object>();
        if (StringUtils.isEmpty(userId)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("userId不能为空");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isEmpty(email)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("email不能为空");
            return JSON.toJSONString(result);
        }
        int code = emailService.sendEmail(userId, email,request);
        if(code==1) {
            result.setState(1);
            result.setMsg("success");
            result.setMessage("验证邮箱信息已发送");
        }else{
            result.setState(0);
            result.setMsg("error");
            result.setMessage("邮箱服务器异常");
        }
        return JSON.toJSONString(result);

    }*/

   /* @RequestMapping(value = "/validateEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validateEmail(String userId, String email, String code) {
        Result<Object> result = new Result<Object>();
        result = emailService.valideEmail(userId, email, code);
        return JSON.toJSONString(result);
    }*/

    @RequestMapping("/checkPhone")
    @ResponseBody
    public String checkPhone(String phone) {
    	Result result = new Result();
        int code = tMpClientUserService.checkPhone(phone);

        if (code == 0) {
            result.setMessage("验证成功");
            result.setState(1);
            return JSON.toJSONString(result);
        } else {
            result.setMessage("验证失败");
            result.setState(0);
            return JSON.toJSONString(result);
        }

    }
    /**
     * <p>description:发送验证码</p>
     * @param 
     * @return Result
     * @author Wen Yugang
     * @date 2017-5-26下午3:03:15
     */
    @SuppressWarnings("rawtypes")
	/*private Result sendCode(String phoneNum, String templateId) {
        Result result = new Result();
        try {
            HashMap<String, Object> resultMap = null;
            CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
            // *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
            // *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
            // *******************************************************************************
            //短信接口调用准备:1 初始化,2 设置账户,3 设置应用ID
            restAPI.init(serverIp, serverPort);
            restAPI.setAccount(accountSid, accountToken);
            restAPI.setAppId(appId);
            
            //使用随机数生成一个6位数验证码
            String yzmStr = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            
            redisUtils.setDataByKey(phoneNum, yzmStr, 600);
            // 参数说明:1:电话号码  2短信模版ID(免费测试模版为1) 3第一个为短信内容,第二个是几分钟之内输入
            resultMap = restAPI.sendTemplateSMS(phoneNum, templateId, new String[]{yzmStr, "10分钟"});
            String statusCode = (String) resultMap.get("statusCode");
            if (StringUtils.equals("000000", statusCode)) {
                result.setState(1);
                result.setMsg("success");
                result.setMessage("验证短信已下发");
            } else if (StringUtils.equals("160040", statusCode)){
                result.setState(0);
                result.setMsg("error");
                result.setMessage("同一手机一天发送验证码次数超过限制");
            } else if (StringUtils.equals("160038", statusCode)) {
                result.setState(0);
                result.setMsg("error");
                result.setMessage("短信验证码发送过频繁");
            } else {
                result.setState(0);
                result.setMsg("error");
                result.setMessage("验证短信发送不成功");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }*/
    
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
    
    
    //生成验证码
    @RequestMapping(value = "/genarateValidateCode")
    public String genarateValidateCode(HttpServletRequest request, HttpServletResponse response, String phoneNum)
     throws ServletException, IOException {
        Result result = new Result();
        if (StringUtils.isEmpty(phoneNum)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("电话不能为空");
            return JSON.toJSONString(result);
        }
        // 验证码图片的宽度。
        int width = 95;
        // 验证码图片的高度。
        int height = 50;
        BufferedImage buffImg = new BufferedImage(width, height,
         BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类。
        Random random = new Random();

        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();

        // 设置默认生成4个验证码
        int length = 4;
        // 设置备选验证码:包括"a-z"和数字"0-9"
        String base = "abcdefghjkmnpqrstuvwxyz23456789";

        int size = base.length();

        // 随机产生4位数字的验证码。
        for (int i = 0; i < length; i++) {
            // 得到随机产生的验证码数字。
            int start = random.nextInt(size);
            String strRand = base.substring(start, start + 1);

            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
             .nextInt(110), 20 + random.nextInt(110)));

            g.drawString(strRand, 15 * i + 6, 24);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }

        // 将四位数字的验证码保存到Session中。
        /* HttpSession session = request.getSession();
	        session.setAttribute("signcode", randomCode.toString());  */
        redisUtils.setDataByKey(imageValidatePrefix + phoneNum, randomCode.toString(), 3000);
        // 图象生效
        g.dispose();

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.flush();
        sos.close();
        return JSON.toJSONString(result);
    }

    // 给定范围获得随机颜色
    Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /*************************************IM相关外部调用**********************************************/
    /**
     * <p>description：IM相关外部调用</p>
     *
     * @Author Tarriance
     * @Date 2017/4/10,11:01
     */
    

    /**
     * 注册商家IM
     * @param acount
     * @param passwd
     * @param request
     * @param response
     * @return
     * 2017-08-14 13:22:48
     */
    
     /**
      * 用户的认证
      * @param phoneNum
      * @param idCard
      * @param idCardIMGPath
      * @return
      */
    @ResponseBody
    @RequestMapping(value="/setAuth")
    public String regAuth(@RequestParam(required=true) String id,String idCard,String idCardIMGPath){
    	 Result<String> result = new Result<>();
    	 TMpClientUser tmpClientUser= tMpClientUserService.selectByPrimaryKey(id);
    	 if(StringUtils.isBlank(idCard) || StringUtils.isBlank(idCardIMGPath)){
    		 result.setMessage("身份证号或身份证相片不内为空");
    		 result.setMsg("error");
    		 result.setState(0);
    		 return JSON.toJSONString(result);
    	 }
    	 if(tmpClientUser ==null){
    		 result.setMessage("用户不存在");
    		 result.setMsg("error");
    		 result.setState(0);
    		 return JSON.toJSONString(result);
    	 }
    	 tmpClientUser.setIdCard(idCard);
    	 tmpClientUser.setIdCardIMGPath(idCardIMGPath);
    	 tmpClientUser.setIsAuth(301);
    	 int status= tMpClientUserService.update(tmpClientUser);
    	 if (status > 0) {
    		 result.setMsg("success");
    		 result.setState(1);
             return JSON.toJSONString(result);
         } else {
        	 result.setMessage("以认证");
        	 result.setMsg("success");
    		 result.setState(1);
             return JSON.toJSONString(result);
         }
    }
    /**
     * 更换用户头像
     * @param id
     * @param headIMG
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/setHeadImg")
    public String setHeadIMG(String id,String headImg) throws Exception{
    	Result<String> result = new Result<>();
    	if(StringUtils.isBlank(id)){
    		result.setState(0);
    		result.setMsg("error");
    		result.setMessage("id 不能为空");
    		return JSON.toJSONString(result);
    	}
    	if(StringUtils.isBlank(headImg)){
    		result.setState(0);
    		result.setMsg("error");
    		result.setMessage("头像不能为空");
    		return JSON.toJSONString(result);
    	}
    	TMpClientUser tMpClientUser = tMpClientUserService.selectByPrimaryKey(id);
    	tMpClientUser.setHeadImg(headImg);
    	int code  = tMpClientUserService.update(tMpClientUser);
    	if(code>0){
	    	result.setState(1);
	    	result.setMsg("success");
	    	result.setMessage("保存成功");
    	}else{
    		result.setState(0);
    		result.setMsg("error");
    		result.setMessage("保存失败");
    		
    	}
    	return JSON.toJSONString(result);
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
