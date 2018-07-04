package com.zzk.service;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.zzk.entity.BusinessInfo;
import com.zzk.entity.User;
import com.zzk.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户表
 * @author: wangpeng
 * @date: 2018-03-12 10:21
 */
public interface UserService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
	List<User> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 用户表实体类
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
	User selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
	 int update(User bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
	 int insert(User bean, String token);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 10:21
	 */
	 int delete(String id);
	 /**
	  * 检查用户是否存在
	  * @param phoneNum
	  * @return
	  * @author John
	  * @date： 2018年3月12日 上午10:34:28
	  */
	 boolean checkUser(String phoneNum);
	 /**
	  * 线路商家注册
	  * @param user
	  * @return
	  * @author John
	  * @date： 2018年3月12日 上午10:53:43
	  */
	 Result<Object> insertBusinessUser(User user,BusinessInfo businessInfo);
	 
	 /**
	  * 新商家注册
	  * @param user
	  * @return
	  * @author John
	  * @date： 2018年3月12日 上午10:53:43
	  */
	 Result<User> insertUser(User user);
	 
	 /**
	  * 通过手机查询
	  * @param phoneNum
	  * @return
	  * @author John
	  * @date： 2018年3月12日 上午10:54:12
	  */
	 User selectByPhoneNum(String phoneNum);
	 /**
	  * 登录检查密码线路B端
	  * @param user
	  * @param token
	  * @param session
      * @return
	  * @throws NoSuchAlgorithmException
	  * @author John
	  * @date： 2018年3月12日 上午11:15:02
	  */
	 Result checkBusinessUserByPassword(User user, String token, HttpSession session) throws NoSuchAlgorithmException;
	 
	 /**
	  * 获取商家信息(线路B)
	  * @param user
	  * @param request
      * @return
	  * @author hua
	  * @date： 2018年3月12日 上午11:15:02
	  */
	 Result getMeichantInfo(User user, HttpServletRequest request);
	 /**
	  * 登录检查密码
	  * @param user
	  * @param token
	  * @param request
      * @return
	  * @throws NoSuchAlgorithmException
	  * @author John
	  * @date： 2018年3月12日 上午11:15:02
	  */
	 Result checkUserByPassword(User user, String token, HttpServletRequest request) throws NoSuchAlgorithmException;
	 /**
	  * 通过旧密码更改密码
	  * @param user
	  * @param newPassword
	  * @return
	  * @author John
	  * @date： 2018年3月12日 下午1:42:33
	  */
	 String  updatePasswordByOldPassword(User user,String newPassword);
	 /**
	  * 通过验证码修改密码
	  * @param user
	  * @param validateCode
	  * @return
	  * @author John
	  * @date： 2018年3月12日 下午2:10:40
	  */
	 String updatePasswordByCode(User user,String validateCode);
	 /**
	  * 通过ID修改头像
	  * @param id
	  * @param headImg
	  * @return
	  * @author John
	  * @date： 2018年3月12日 下午8:07:50
	  */
	 String updateHeadImgById(String id,String headImg);
	 /**
		 * @Description:  
		 * @author sty
		 */
	Result updatePassword(User bean, String verificationCode);
	/**
	  * 判断是否已注册
	  * @param user
	  * @return
	  * @author John
	  * @date： 2018年3月12日 下午8:07:50
	  */
	int checkUserInfo(User user, Result<Object> result);
	
	/**
	  * 根据openId查找用户
	  * @param OpenId
	  * @return
	  * @author hua
	  * @date： 2018年3月12日 下午8:07:50
	  */
	User selectByOpenId(String OpenId);
	
}
