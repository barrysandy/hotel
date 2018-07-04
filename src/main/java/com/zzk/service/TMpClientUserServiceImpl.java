package com.zzk.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.taobao.api.domain.OpenImUser;
//import com.taobao.api.domain.Userinfos;
//import com.taobao.api.request.OpenimTribeCreateRequest;
//import com.taobao.api.request.OpenimTribeExpelRequest;
//import com.taobao.api.request.OpenimTribeInviteRequest;
//import com.taobao.api.request.OpenimUsersAddRequest;
//import com.taobao.api.request.OpenimUsersDeleteRequest;
//import com.taobao.api.request.OpenimUsersGetRequest;
//import com.taobao.api.request.OpenimUsersUpdateRequest;
//import com.taobao.api.response.OpenimTribeCreateResponse;
//import com.taobao.api.response.OpenimTribeExpelResponse;
//import com.taobao.api.response.OpenimTribeInviteResponse;
//import com.taobao.api.response.OpenimUsersAddResponse;
//import com.taobao.api.response.OpenimUsersDeleteResponse;
//import com.taobao.api.response.OpenimUsersGetResponse;
//import com.taobao.api.response.OpenimUsersUpdateResponse;
import com.zzk.util.Result;
import com.zzk.common.CommonConstants;
import com.zzk.common.log.LogTypeEnum;
import com.zzk.util.JsonUtil;
import com.zzk.util.MapUtils;
import com.zzk.util.PasswordEncoder;
import com.zzk.util.StringUtils;
import com.zzk.dao.TMpClientUserMapper;
import com.zzk.util.RedisUtils;
import com.zzk.util.RedisUtil;
import com.zzk.entity.TMpClientUser;

/**
 * 信誉记录表
 *
 * @author lishiqiang
 * @date 2017-3-15
 * modify history
 */
@Service("tMpClientUserService")
public class TMpClientUserServiceImpl implements TMpClientUserService {

    @Resource
    private TMpClientUserMapper tMpClientUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisUtils redisUtils;


    @Value("${user.token.expireTime}")
    private long expireTime;

    @Value("${user.password.salt}")
    private String salt;

   

   

    /**
     * 主键查询
     */
    @Override
    public TMpClientUser selectByPrimaryKey(String id) {
        return tMpClientUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(TMpClientUser bean) {
        return tMpClientUserMapper.updateByPrimaryKey(bean);
    }

    /**
     * 更新
     */
    @Override
    public int updateByPrimaryKeySelective(TMpClientUser bean) {
        return tMpClientUserMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 更新密码
     */
    @Override
    public Result updatePassword(TMpClientUser bean, String verificationCode) {

        Result result = new Result();
        TMpClientUser user = tMpClientUserMapper.selectByPhoneNum(bean.getPhoneNum());
        String recordCode = redisUtils.getDataByKey(user.getPhoneNum());

        if (StringUtils.isEmpty(recordCode)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("无效验证码");
            return result;
        }
        if (!recordCode.equals(verificationCode)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("验证码错误");
            return result;
        } else {
            Map map = new HashMap();
            PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
            String encode = encoderMd5.encode(bean.getPassword());
//            bean.setPassword(encode);
            user.setPassword(encode);
            user.setUpdateTime(new Date());
            int code = tMpClientUserMapper.updateByPrimaryKeySelective(user);
            if (code > 0) {
                result.setState(1);
                result.setMsg("success");
                result.setMessage("密码修改成功");
                redisUtils.removeBykey(bean.getPhoneNum());
            } else {
                result.setState(0);
                result.setMsg("error");
                result.setMessage("内部错误");
            }
        }

        return result;
    }
    
    /**
     * 通过旧密码更新密码
     */
    @Override
    public Result updatePasswordByOldPwd(TMpClientUser bean, String oldPwd) {

        Result result = new Result();
        TMpClientUser user = tMpClientUserMapper.selectByPhoneNum(bean.getPhoneNum());
        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
        if (StringUtils.isEmpty(oldPwd)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("旧密码不能为空");
            return result;
        }
        String encode1 = encoderMd5.encode(oldPwd);
        if (!user.getPassword().equals(encode1)) {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("密码错误");
            return result;
        } else {
            Map map = new HashMap();
            
            String encode = encoderMd5.encode(bean.getPassword());
//            bean.setPassword(encode);
            user.setPassword(encode);
            user.setUpdateTime(new Date());
            int code = tMpClientUserMapper.updateByPrimaryKeySelective(user);
            if (code > 0) {
                result.setState(1);
                result.setMsg("success");
                result.setMessage("密码修改成功");
                redisUtils.removeBykey(bean.getPhoneNum());
            } else {
                result.setState(0);
                result.setMsg("error");
                result.setMessage("内部错误");
            }
        }

        return result;
    }
    
    

    /**
     * 更新密码
     */
    @Override
    public Result bindPhone(TMpClientUser bean) {

        Result result = new Result();

        int code = tMpClientUserMapper.updateByPrimaryKeySelective(bean);

        if (code > 0) {
            result.setState(1);
            result.setMsg("success");
            result.setMessage("绑定手机成功");
            redisUtils.removeBykey(bean.getPhoneNum());
        } else {
            result.setState(0);
            result.setMsg("error");
            result.setMessage("内部错误");
        }

        return result;
    }

    @Override
    public String changePwd(String id, String pwd, String newPwd) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(pwd) || StringUtils.isBlank(newPwd)) {
            return JsonUtil.turnJson(false, "用户ID,密码或者新密码不能为空", null);
        }
        TMpClientUser userVo = tMpClientUserMapper.selectByPrimaryKey(id);
        if (userVo == null) {
            return JsonUtil.turnJson(false, "用户尚未注册!", null);
        }
        PasswordEncoder encoderMd5 = new PasswordEncoder("wisesoft", "MD5");
        String encode = encoderMd5.encode(pwd);//将传进来的密码进行MD5加密
        if (!StringUtils.equals(encode, userVo.getPassword())) {
            //判断注册的密码和传进来的密码是否一致
            return JsonUtil.turnJson(false, "旧密码和注册密码不一致", null);
        }
        String newEncode = encoderMd5.encode(newPwd);//将传进来的密码进行MD5加密
        userVo.setPassword(newEncode);
        userVo.setUpdateTime(new Date());
       int isSuccess = tMpClientUserMapper.changePwd(userVo);
        if (isSuccess > 0) {
            return JsonUtil.turnJson(true, "操作成功!", null);
        } else {
            return JsonUtil.turnJson(false, "操作失败!", null);
        }
    }

    /**
     * 新增
     *
     * @param bean
     * @return
     * @author lishiqiang
     * @date 2017-3-16
     * modify history
     */
    @Override
    public int insert(TMpClientUser bean, String token, String pd) {
        Date now = new Date();
        bean.setRegTime(now);
        //bean.setLastLoginTime(now);
        bean.setCreateTime(now);
        int inserted = tMpClientUserMapper.insert(bean);
        boolean cached = redisUtil.cacheValue(token, JSON.toJSONString(bean), 360000);
        //boolean cached = redisUtils.setDataByKey(token, JSON.toJSONString(bean), 360000);
        System.out.println(" inserted:" + inserted + " cached:" + cached);
        if (inserted > 0 && cached) {
            //会员成功注册立即获得5个脚印
            
            return 1;

        } else {
            return -1;
        }
    }

    @Override
    public int isRegisterUser(String phoneNum) {
        TMpClientUser vo = tMpClientUserMapper.selectByPhoneNum(phoneNum);
        if (vo != null) {
            Integer status = vo.getStatus();
            if (status == 1) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public int registUser(TMpClientUser user) {
//    	String nick=user.getNickname();
//      	Date date=new Date();
//    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	   String str = format.format(date);
//    	   str= str.replace(" ", "");
//    	   str= str.replace("-", "");
//    	   str= str.replace(":", "");
//    		if(null==nick){
//				nick="游客_"+str;
//				user.setNickname(nick);
//			}
        //会员成功注册立即获得5个脚印
       /* try {
            memberCreditService.addOrMinusScore(user.getId(), "register_first");
        }catch (Exception e){e.printStackTrace();}
        getIm(user);*/
        return tMpClientUserMapper.insertSelective(user);
    }

   
    
   

   

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     * @author lishiqiang
     * @date 2017-3-16
     * modify history
     */
    @Override
    public int delete(String id) {
        TMpClientUser bean = tMpClientUserMapper.selectByPrimaryKey(id);
        //bean.setStatus(-1);
        //bean.setS
        return tMpClientUserMapper.updateByPrimaryKey(bean);
    }

    public int checkUserByPassword(TMpClientUser user, String token, Result<Object> result) throws NoSuchAlgorithmException {

    	
        TMpClientUser recordUser = tMpClientUserMapper.selectByPhoneNum(user.getPhoneNum());
        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
        String encode = encoderMd5.encode(user.getPassword());
        if (recordUser != null && recordUser.getPassword() != null && encoderMd5.isPasswordValid(encode, recordUser.getPassword())) {
            redisUtil.cacheValue(token, JSON.toJSONString(recordUser), expireTime);
            recordUser.setId(recordUser.getId());
            /*json.put("info", JSON.toJSONString(recordUser));*/
            result.setData(recordUser);
            recordUser.setLastLoginTime(new Date());
            tMpClientUserMapper.updateLastLoginTime(recordUser);
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public TMpClientUser resetPwd(TMpClientUser vo) {
        int isRestPwdSuccess = tMpClientUserMapper.resetPwd(vo);
        if (isRestPwdSuccess > 0) {
            TMpClientUser resultVo = tMpClientUserMapper.selectByPhoneNum(vo.getPhoneNum());
            //updateIm(vo);
            return resultVo;
        } else {
            return null;
        }
    }

    public int checkUserInfo(TMpClientUser user, Result<Object> result) {


        if (user.getNickname() != null) {
            TMpClientUser recordUser = tMpClientUserMapper.selectByNickName(user.getNickname());
            if (recordUser != null) {
                result.setMessage("用户名称已存在");
                result.setState(0);
                return -1;
            }
        }

        if (user.getEmail() != null) {
            TMpClientUser recordUser = tMpClientUserMapper.selectByEmail(user.getEmail());
            if (recordUser != null) {
                
                result.setMessage("电子邮件已存在");
                result.setState(0);
                return -1;
            }
        }

        if (user.getPhoneNum() != null) {
            TMpClientUser recordUser = tMpClientUserMapper.selectByPhoneNum(user.getPhoneNum());
            if (recordUser != null) {
                result.setMessage("电话号码已存在");
                result.setState(0);
                return -1;
            }
        }

        return 1;

    }

    @Override
    public int validate(String token) {
        boolean result = redisUtil.containsValueKey(token);
        if (result) {
            redisUtil.updateExpire(token, expireTime);
            return 1;
        } else {
            return -1;
        }
    }

    public String getUserInfo(String token) {
        return redisUtil.getValue(token);
    }

    @Override
    public TMpClientUser getUserInfoById(String id) {
        return tMpClientUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public TMpClientUser login(String userName, String pass, String regSource) {
        Map record = new HashMap();
        record.put("userName", userName);
        record.put("pass", pass);
        record.put("regSource", regSource);
        return tMpClientUserMapper.findLoginUser(record);
    }

    @Override
    public int isExistUser(TMpClientUser user) {
        if (user.getNickname() != null) {
            TMpClientUser recordUser = tMpClientUserMapper.selectByNickName(user.getNickname());
            if (recordUser != null) {
                return -1;
            }
        }
        if (user.getEmail() != null) {
            TMpClientUser recordUser = tMpClientUserMapper.selectByEmail(user.getEmail());
            if (recordUser != null) {
                return -1;
            }
        }
        if (user.getPhoneNum() != null) {
            TMpClientUser recordUser = tMpClientUserMapper.selectByPhoneNum(user.getPhoneNum());
            if (recordUser != null) {
                /*Integer status = recordUser.getStatus(); //如果是被删除的用户 仍然可以进行注册
                if (status==1){
                    return -1;
                }*/
                return -1;
            }
        }
        return 1;
    }


    @Override
    public int checkPhone(String phone) {
        return tMpClientUserMapper.checkPhone(phone);
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
   

    @Override
    public String getAllUserInfoById(String userId,int userType) {
        String result = JsonUtil.turnJson(false, "获取用户信息失败", net.sf.json.JSONObject.fromObject("{}"));
        try {
            TMpClientUser tMpClientUser = tMpClientUserMapper.getAllUserInfoById(userId);
            
                result = JsonUtil.turnJson(true, "获取用户信息成功",net.sf.json.JSONObject.fromObject(tMpClientUser));
            
            LogTypeEnum.DEFAULT.debug("userApp/getAllUserInfoById,获取用户信息成功", result);
           // LogTypeEnum.DEFAULT.info("userApp/getAllUserInfoById,获取用户信息成功", result);
        } catch (Exception e) {
            LogTypeEnum.DEFAULT.error("userApp/getAllUserInfoById,获取用户信息失败", e.getMessage());
        }
        return result;
    }

  

    @Override
    public String getPraiseUsersById(String userId) {
        String result = JsonUtil.turnJson(false, "获取点赞列表失败", null);
        try {
            List<TMpClientUser> tMpClientUsers = tMpClientUserMapper.getPraiseUsersById(userId);
            result = JsonUtil.turnJson(true, "获取点赞列表成功", tMpClientUsers);
            LogTypeEnum.DEFAULT.debug("userApp/getPraiseUsersById,获取点赞列表成功", result);
            LogTypeEnum.DEFAULT.info("userApp/getPraiseUsersById,获取点赞列表成功", result);
        } catch (Exception e) {
            LogTypeEnum.DEFAULT.error("userApp/getPraiseUsersById,获取点赞列表成功", e.getMessage());
        }
        return result;
    }

    @Override
    public String getVisitorListById(int psize, int offset, String userId) {
        String result = JsonUtil.turnJson(false, "获取访问列表失败", null);
        Map map = new HashMap();
        map.put("startRow", (offset - 1) * psize);
        map.put("pageSize", psize);
        map.put("userId", userId);
        try {
            List<TMpClientUser> tMpClientUsers = tMpClientUserMapper.getVisitorListById(map);
            int sum = tMpClientUserMapper.getVisitorNum(userId);
            int total = sum / psize + 1;
            if (sum % psize == 0) {
                total--;
            }


            HashMap resultMap = new HashMap();
            resultMap.put("total", total);
            result = JsonUtil.turnJson(true, "获取访问列表成功", resultMap);
            LogTypeEnum.DEFAULT.debug("userApp/getVisitorListById,获取访问列表成功", resultMap);
            LogTypeEnum.DEFAULT.info("userApp/getVisitorListById,获取访问列表成功", resultMap);
        } catch (Exception e) {
            LogTypeEnum.DEFAULT.error("userApp/getVisitorListById,获取访问列表成功", e.getMessage());
        }
        return result;
    }

  

    @Override
    public TMpClientUser oldLogin(String userName, String pass) {
        Map record = new HashMap();
        record.put("userName", userName);
        record.put("pass", pass);
        return tMpClientUserMapper.findLoginUser(record);
    }

    

   

    
    

    @Override
    public TMpClientUser selectByPhoneNum(String phoneNum) {
        return  tMpClientUserMapper.selectByPhoneNum(phoneNum);
    }
   

    @Override
    public TMpClientUser selectByOpenId(String openId) {
        return tMpClientUserMapper.selectByOpenId(openId);
    }

    @Override
    public String getUserInfoByPhoneNum(String phoneNum) {
        List<Map<String,Object>> userList = tMpClientUserMapper.getUserInfoByPhoneNum(phoneNum);
        if (CollectionUtils.isNotEmpty(userList)){
            net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
            for (Map user : userList){
                String userId = MapUtils.getString(user,"ID");//用户Id
                String balance = tMpClientUserMapper.getUserBalance(userId);//余额
                if (StringUtils.isBlank(balance)){
                    balance = "0.00";
                }
                String headImg = MapUtils.getString(user,"HEAD_IMG");//头像
                String nickName = MapUtils.getString(user,"NICKNAME");//昵称
                String gender = MapUtils.getString(user,"GENDER");//性别 1:男,2:女
                String focusCount = MapUtils.getString(user,"focusCount");//关注数
                String fansCount = MapUtils.getString(user,"fansCount");//粉丝数
                String lat = MapUtils.getString(user,"LATITUDE");//纬度
                String lon = MapUtils.getString(user,"LONGITUDE");//经度
                if (StringUtils.isBlank(focusCount)){
                    focusCount = "0";
                }
                if (StringUtils.isBlank(fansCount)){
                    fansCount = "0";
                }
                jsonObject.put("headImg",headImg);
                jsonObject.put("nickName",nickName);
                jsonObject.put("gender",gender);
                jsonObject.put("focusCount",focusCount);
                jsonObject.put("fansCount",fansCount);
                jsonObject.put("balance",balance);
                jsonObject.put("lat",lat);
                jsonObject.put("lon",lon);
            }
            return JsonUtil.turnJson(true,"查询到用户信息",jsonObject);
        }else{
            return JsonUtil.turnJson(false,"未查询到用户信息",null);
        }
    }

    @Override
    public String getUserOrderList(String phoneNum,Integer pageNumber,Integer pageSize) {
        Integer startRow = (pageNumber-1)*pageSize;
        Map map = new HashMap();
        map.put("startRow",startRow);
        map.put("pageSize",pageSize);
        map.put("phoneNum",phoneNum);
        List<Map<String,Object>> orderList = tMpClientUserMapper.getUserOrderListOrDetail(map);
        String totalPage =  tMpClientUserMapper.countUserOrderListOrDetail(map);//总数
        if (CollectionUtils.isNotEmpty(orderList)){
            JSONArray jsonArray = new JSONArray();
            for (Map order : orderList){
                JSONObject jsonObject = new JSONObject();
                String userId = MapUtils.getString(order,"BUYER_ID");
                String orderState = MapUtils.getString(order,"ORDER_STATE");
                String orderId = MapUtils.getString(order,"ORDER_ID");
                String goodsName = MapUtils.getString(order,"NAME");
                String number = MapUtils.getString(order,"number");
                String orderDate = MapUtils.getString(order,"ORDER_TIME");
                String regCode = MapUtils.getString(order,"REG_CODE");
                String price = MapUtils.getString(order,"ORDER_MONEY");
                jsonObject.put("userId",userId);
                jsonObject.put("price",price);
                jsonObject.put("productType",regCode);
                jsonObject.put("orderState",orderState);
                jsonObject.put("orderId",orderId);
                jsonObject.put("goodsName",goodsName);
                jsonObject.put("number",number);
                jsonObject.put("orderDate",orderDate);
                jsonObject.put("totalCount",totalPage);
                jsonArray.add(jsonObject);
            }
            return JsonUtil.turnJson(true,"查询到订单列表",jsonArray);
        }else {
            return JsonUtil.turnJson(true,"该用户尚无任何订单",null);
        }
    }

    @Override
    public String getOrderDetail(String orderId) {
        Map map = new HashMap();
        map.put("orderId",orderId);
        List<Map<String,Object>> orderList = tMpClientUserMapper.getUserOrderListOrDetail(map);
        if (CollectionUtils.isNotEmpty(orderList)){
            net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
            for (Map order : orderList){
                String orderState = MapUtils.getString(order,"ORDER_STATE");
                String goodsName = MapUtils.getString(order,"NAME");
                String number = MapUtils.getString(order,"number");
                String price = MapUtils.getString(order,"price_real");
                String orderMoney = MapUtils.getString(order,"ORDER_MONEY");
                String transportPrice = MapUtils.getString(order,"TRANSPORT_FEE");
                String actualPay = MapUtils.getString(order,"ACTUAL_MONEY");
                String orderDate = MapUtils.getString(order,"ORDER_TIME");
                String contactUser = MapUtils.getString(order,"NICKNAME");
                String contactPhone = MapUtils.getString(order,"PHONE_NUM");
                jsonObject.put("orderState",orderState);
                jsonObject.put("goodsName",goodsName);
                jsonObject.put("number",number);
                jsonObject.put("price",price);
                jsonObject.put("orderMoney",orderMoney);
                jsonObject.put("transportPrice",transportPrice);
                jsonObject.put("actualPay",actualPay);
                jsonObject.put("orderDate",orderDate);
                jsonObject.put("contactUser",contactUser);
                jsonObject.put("contactPhone",contactPhone);
            }
            return JsonUtil.turnJson(true,"查询到订单详情",jsonObject);
        }else{
            return JsonUtil.turnJson(false,"为查询到订单详情",null);
        }
    }

    @Override
    public String getUserComment(String phoneNum, String destId, Integer pageNumber, Integer pageSize) {
        Map map = new HashMap();
        map.put("phoneNum",phoneNum);
        map.put("destId",destId);
        map.put("pageSize",pageSize);
        Integer startRow = (pageNumber-1)*pageSize;
        map.put("startRow",startRow);
        List<Map<String,Object>> commentList = tMpClientUserMapper.getUserComment(map);
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(commentList)){
            for (Map comment : commentList){
                net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
                String content = MapUtils.getString(comment,"CONTENT");
                String commentDate = MapUtils.getString(comment,"CREATE_TIME");
                jsonObject.put("comment",content);
                jsonObject.put("commentDate",commentDate);
                jsonArray.add(jsonObject);
            }
            return JsonUtil.turnJson(true,"获取到评论列表",jsonArray);
        }else{
            return JsonUtil.turnJson(false,"未查询到评论列表",null);
        }
    }
 
    @Override
    public String getUserFocusOrFans(String phoneNum, String userType, Integer startRow,Integer pageSize) {
        TMpClientUser user = tMpClientUserMapper.selectByPhoneNum(phoneNum);
        Map map = new HashMap();
        if (user!=null){
            String userId = user.getId();
            map.put("userId",userId);
        }
        map.put("userType",userType);
        map.put("startRow",startRow);
        map.put("pageSize",pageSize);
        List<Map<String,Object>> userFocusOrFans = null;
        if (StringUtils.equals(userType,"focus")){
            userFocusOrFans = tMpClientUserMapper.getUserFocus(map);
        }else{
            userFocusOrFans =   tMpClientUserMapper.getUserFans(map);
        }
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isNotEmpty(userFocusOrFans)){
            for (Map focusOrFans : userFocusOrFans ){
                net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
                String headImg = MapUtils.getString(focusOrFans,"HEAD_IMG");
                String nickName = MapUtils.getString(focusOrFans,"NICKNAME");
                String gender = MapUtils.getString(focusOrFans,"GENDER");
                String lon = MapUtils.getString(focusOrFans,"LONGITUDE");
                String lat = MapUtils.getString(focusOrFans,"LATITUDE");
                String account = MapUtils.getString(focusOrFans,"ACCOUNT");
                jsonObject.put("phone",account);
                jsonObject.put("headImg",headImg);
                jsonObject.put("nickName",nickName);
                jsonObject.put("gender",gender);
                jsonObject.put("lon",lon);
                jsonObject.put("lat",lat);
                jsonArray.add(jsonObject);
            }
            return JsonUtil.turnJson(true,"获取到列表",jsonArray);
        }else{
            return JsonUtil.turnJson(true,"尚无关注或粉丝",null);
        }
    }

   
    

    @Override
    public String uploadUserLog(String userId, String userLog) {
        TMpClientUser user = new TMpClientUser();
        user.setId(userId);
        user.setHeadImg(userLog);
        int isSuccess = tMpClientUserMapper.updateByPrimaryKeySelective(user);
        if (isSuccess>0){
            return JsonUtil.turnJson(true,"上传用户头像成功!",null);
        }else{
            return JsonUtil.turnJson(false,"上传用户头像失败!",null);
        }
    }

    @Override
    public String validateNickName(String nickName) {
        int isExist = tMpClientUserMapper.validateNickName(nickName);
        if (isExist>0){
            return JsonUtil.turnJson(false,"用户名已经存在!",nickName);
        }
        return JsonUtil.turnJson(true,"用户名可用",nickName);
    }

    @Override
    public String changeNickName(String userId, String nickName) {
        TMpClientUser user = new TMpClientUser();
        user.setId(userId);
        user.setNickname(nickName);
        int isSuccess = tMpClientUserMapper.updateByPrimaryKeySelective(user);
        if (isSuccess>0){
            return JsonUtil.turnJson(true,"修改成功!",null);
        }else{
            return JsonUtil.turnJson(false,"修改失败!",null);
        }
    }

    

    @Override
    public String changeLoginState(String userId, String loginState) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("loginState",loginState);
        Integer isSuccess = tMpClientUserMapper.changeLoginState(map);
        if (isSuccess>0){
            return JsonUtil.turnJson(true,"操作成功!",null);
        }else{
            return JsonUtil.turnJson(false,"网络异常,操作失败",null);
        }
    }

	@Override
	public void cecheUser(String token, JSONObject json, TMpClientUser user) {
		TMpClientUser recordUser = tMpClientUserMapper.selectByPhoneNum(user.getPhoneNum());
		 json.put("info", JSON.toJSONString(recordUser));
		tMpClientUserMapper.updateLastLoginTime(recordUser);
		redisUtil.cacheValue(token,JSON.toJSONString(recordUser),expireTime);
	}

	

	@Override
	public String getUserIfo(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return null;
	}
    

}
