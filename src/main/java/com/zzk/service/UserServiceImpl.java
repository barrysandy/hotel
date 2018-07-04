package com.zzk.service;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.zzk.common.Constact;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zzk.dao.BusinessInfoMapper;
import com.zzk.dao.UserMapper;
import com.zzk.entity.BusinessInfo;
import com.zzk.entity.Hotel;
import com.zzk.entity.User;
import com.zzk.util.JsonUtils;
import com.zzk.util.PasswordEncoder;
import com.zzk.util.RedisUtil;
import com.zzk.util.RedisUtils;
import com.zzk.util.Result;
import com.zzk.vo.BusinessAllInfoVo;

/**
 * 用户表
* @author: wangpeng
* @date: 2018-03-12 10:21
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private RedisUtils redisUtils;
	@Resource
	private UserMapper userMapper;
	@Resource
	private RedisUtil redisUtil;
	@Resource
	private BusinessInfoMapper businessInfoMapper;
	@Resource
	private HotelService hotelService;
    @Value("${user.password.salt}")
    private String salt;
    private long expireTime = 3600000;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<User> selectByPage(Map map) {
		return userMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return userMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public User selectByPrimaryKey(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(User bean) {
		return userMapper.updateByPrimaryKeySelective(bean);
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
    public int insert(User bean, String token) {
        Date now = new Date();
        bean.setRegTime(now);
        int inserted = userMapper.insert(bean);
        if (inserted > 0 ) {
            return 1;
        } else {
            return -1;
        }
    }
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		User bean = userMapper.selectByPrimaryKey(id);
		
		return userMapper.updateByPrimaryKey(bean);
	}
	/**
	 * 检查用户是否存在
	 */
	@Override
	public boolean checkUser(String phoneNum) {
		int resultCode = userMapper.checkUser(phoneNum);
		if(resultCode>0){
			return true;
		}
			return false;
	}
	/**
	 * 商家注册
	 */
	@Override
	@Transactional
	public Result<Object> insertBusinessUser(User user,BusinessInfo businessInfo) {
		
		int resultCode = userMapper.insert(user);
		businessInfoMapper.insert(businessInfo);
		if(resultCode>0){
			User dbUser = selectByPhoneNum(user.getPhoneNum());
			if(dbUser != null){
				String token = UUID.randomUUID().toString();
				redisUtil.cacheValue(token, JSON.toJSONString(dbUser), expireTime);
				businessInfo.setToken(token);
				return new Result<Object>(1, "success", "注册成功", businessInfo);
			}else{
				return new Result<Object>(0, "error", "获取数据失败", null);
			}
		}
		return new Result<Object>(0, "error", "注册失败", null);
	}
	
	
	/**
	 * 通过手机查询
	 */
	@Override
	public User selectByPhoneNum(String phoneNum) {
		return userMapper.selectByPhoneNum(phoneNum);
	}
	/**
	 * B端线路登录
	 */
	@Override
	public Result checkBusinessUserByPassword(User user, String token, HttpSession session) throws NoSuchAlgorithmException {

        User dbUser = userMapper.selectByPhoneNum(user.getPhoneNum());
        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
        String encode = encoderMd5.encode(user.getPassword());
        if (dbUser != null && dbUser.getPassword() != null && encoderMd5.isPasswordValid(encode, dbUser.getPassword())) {
        	if(dbUser.getUserType()!=2&& StringUtils.isBlank(dbUser.getMerchatId())){
        		return new Result<User>(0, "error", "不是供应商管理用户", null);
        	}
        	BusinessInfo businessInfo = businessInfoMapper.selectByPrimaryKey(dbUser.getMerchatId());
        	if(businessInfo==null){
        		Hotel hotel = hotelService.selectByPrimaryKey(dbUser.getMerchatId());
        		if (hotel==null){
        			return new Result<User>(0, "error", "不是供应商管理用户", null);
        		}
        		BusinessInfo bean = hotelService.hotel2Business(hotel);
        		bean.setType(1);
        		bean.setStatus(1);
        		bean.setBusinStatus(4);
        		businessInfoMapper.insert(bean);
        	}
        	BusinessAllInfoVo allInfoVo = businessInfoMapper.selectAllInfoByPrimaryKey(dbUser.getMerchatId());
        	System.err.println(JSON.toJSON(allInfoVo));
        	
        		redisUtil.cacheValue(token, JSON.toJSONString(dbUser), expireTime);
        		String a = redisUtil.getValue(token);
        		dbUser.setLastLoginTime(new Date());
                userMapper.updateLastLoginTime(dbUser);
                allInfoVo.setToken(token);
                session.setAttribute(Constact.IS_AUTHENTICATION,true);
                return new Result(1, "success", "登录成功", allInfoVo);
        }
		return new Result<User>(0, "error", "用户名或密码错误", null);
	}
	
	/**
	 * 根据用户获取线路商家信息
	 */
	@Override
	public Result getMeichantInfo(User dbUser, HttpServletRequest request) {
			BusinessInfo businessInfo = businessInfoMapper.selectByPrimaryKey(dbUser.getMerchatId());
        	if(businessInfo==null){
        		Hotel hotel = hotelService.selectByPrimaryKey(dbUser.getMerchatId());
        		if (hotel==null){
        			return new Result<User>(0, "error", "不是供应商管理用户", null);
        		}
        		BusinessInfo bean = hotelService.hotel2Business(hotel);
        		bean.setType(1);
        		bean.setStatus(1);
        		bean.setBusinStatus(4);
        		businessInfoMapper.insert(bean);
        	}
        	BusinessAllInfoVo allInfoVo = businessInfoMapper.selectAllInfoByPrimaryKey(dbUser.getMerchatId());
        	String token = UUID.randomUUID().toString();
    		redisUtil.cacheValue(token, JSON.toJSONString(dbUser), expireTime);
    		dbUser.setLastLoginTime(new Date());
            userMapper.updateLastLoginTime(dbUser);
            allInfoVo.setToken(token);
			request.getSession().setAttribute(Constact.IS_AUTHENTICATION,true);
            return new Result(1, "success", "登录成功", allInfoVo);
     }
	
	/**
	 * 用户登录
	 */
	@Override
	public Result checkUserByPassword(User user, String token, HttpServletRequest request) throws NoSuchAlgorithmException {

        User dbUser = userMapper.selectByPhoneNum(user.getPhoneNum());
        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
        String encode = encoderMd5.encode(user.getPassword());
        if (dbUser != null && dbUser.getPassword() != null && encoderMd5.isPasswordValid(encode, dbUser.getPassword())) {
			dbUser.setLastLoginTime(new Date());
			userMapper.updateLastLoginTime(dbUser);
			request.getSession().setAttribute(Constact.IS_AUTHENTICATION,true);
        	return new Result<User>(1, "success", "登陆成功", dbUser);
        }
		return new Result<User>(0, "error", "用户名或密码错误", null);
	}
	/**
	 * 旧密码修改密码
	 */
	@Override
	public String updatePasswordByOldPassword(User user, String newPassword) {
		User dbUser = userMapper.selectByPrimaryKey(user.getId());
		if(dbUser == null){
			return JsonUtils.lineJsonData(0, "error", "用户不存在", null);
		}
		PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
		String encode = encoderMd5.encode(user.getPassword());
		boolean flag = encoderMd5.isPasswordValid(encode, dbUser.getPassword());
		if(flag){
			User newUser = new User();
			String code = encoderMd5.encode(newPassword);
			newUser.setId(dbUser.getId());
			newUser.setUpdateTime(new Date());
			newUser.setPassword(code);
			int resultCode= userMapper.updateByPrimaryKeySelective(newUser);
			if(resultCode>0){
				return JsonUtils.lineJsonData(1, "success", "修改成功", null);
			}
			return JsonUtils.lineJsonData(0, "error", "内部错误", null);
		}
		return JsonUtils.lineJsonData(0, "error", "旧密码错误", null);
	}
	/**
	 * 验证码修改密码
	 */
	@Override
	public String updatePasswordByCode(User user, String validateCode) {
		String recordCode = redisUtils.getDataByKey(user.getPhoneNum());
		if(!StringUtils.equalsIgnoreCase(validateCode, recordCode)){
			return JsonUtils.lineJsonData(0, "error", "验证码错误", null);
		}
		PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
		String encode = encoderMd5.encode(user.getPassword());
		User dbUser = userMapper.selectByPrimaryKey(user.getId());
		if(dbUser != null){
			User newUser = new User();
			newUser.setId(user.getId());
			newUser.setPassword(encode);
			newUser.setUpdateTime(new Date());
			int resultCode= userMapper.updateByPrimaryKeySelective(newUser);
			if(resultCode>0){
				return JsonUtils.lineJsonData(1, "success", "修改成功", null);
			}
			return JsonUtils.lineJsonData(0, "error", "修改失败内部错误", null);
		}
		
		return JsonUtils.lineJsonData(0, "error", "用户不存在", null);
	}

	@Override
	public String updateHeadImgById(String id, String headImg) {
		User dbUser = userMapper.selectByPrimaryKey(id);
		if(dbUser==null){
			return JsonUtils.lineJsonData(0, "error", "用户不存在", null);
		}
		User user = new User();
		user.setId(id);
		user.setHeadImg(headImg);
		user.setUpdateTime(new Date());
		int resultCode =	userMapper.updateByPrimaryKeySelective(user);
		if(resultCode>0){
			return JsonUtils.lineJsonData(1, "success", "修改成功", null);
		}
		return JsonUtils.lineJsonData(0, "error", "修改失败", null);
	}
	
	/**
     * 更新密码
     */
    @Override
    public Result updatePassword(User bean, String verificationCode) {

        Result result = new Result();
        User user = userMapper.selectByPhoneNum(bean.getPhoneNum());
        if(user==null){
        	result.setState(0);
            result.setMsg("error");
            result.setMessage("未注册用户");
            return result;
        }
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
            int code = userMapper.updateByPrimaryKeySelective(user);
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
	  * 判断是否已注册
	  * @param user
	  * @return
	  * @author husshuwen
	  * @date： 2018年3月12日 下午8:07:50
	  */
    @Override
    public int checkUserInfo(User user, Result<Object> result) {
        if (user.getNickname() != null) {
            User recordUser = userMapper.selectByNickName(user.getNickname());
            if (recordUser != null) {
            	result.setMsg("error");
                result.setMessage("用户名称已存在");
                result.setState(0);
                return -1;
            }
        }

        if (user.getEmail() != null) {
            User recordUser = userMapper.selectByEmail(user.getEmail());
            if (recordUser != null) {
            	result.setMsg("error");
                result.setMessage("电子邮件已存在");
                result.setState(0);
                return -1;
            }
        }

        if (user.getPhoneNum() != null) {
            User recordUser = userMapper.selectByPhoneNum(user.getPhoneNum());
            if (recordUser != null) {
            	result.setMsg("error");
                result.setMessage("电话号码已存在");
                result.setState(0);
                return -1;
            }
        }
        return 1;
    }
    
    @Override
	public Result<User> insertUser(User user) {
		int resultCode = userMapper.insert(user);
		if(resultCode>0){
			User dbUser = selectByPhoneNum(user.getPhoneNum());
			if(dbUser != null){
				return new Result<User>(1, "success", "注册成功", dbUser);
			}else{
				return new Result<User>(0, "error", "获取数据失败", null);
			}
		}
		return new Result<User>(0, "error", "注册失败", null);
	}

	@Override
    public User selectByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }
}
