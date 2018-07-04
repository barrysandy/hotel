package com.zzk.dao;

import java.util.*;

import com.zzk.entity.User;
public interface UserMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 10:21
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    int insert(User record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    int insertSelective(User record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return User 实体类
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    User selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    int updateByPrimaryKeySelective(User record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    int updateByPrimaryKey(User record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    List<User> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-12 10:21
	 */
    int selectCount(Map record);
    /**
     * 查询用户是否存在
     * @param phoneNum
     * @author John
     * @date： 2018年3月12日 上午10:35:38
     */
    int checkUser(String phoneNum);
    /**
     * 通过手机查询用户
     * @param phoneNum
     * @return
     * @author John
     * @date： 2018年3月12日 上午10:52:20
     */
    User selectByPhoneNum(String phoneNum);
    /**
     * 更新最后一次登录时间
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:31:56
     */
    int updateLastLoginTime(User user);
    /**
     * 根据email查账户
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:31:56
     */
    User selectByEmail(String email);
    /**
     * 根据昵称查账户
     * @param user
     * @return
     * @author John
     * @date： 2018年3月12日 上午11:31:56
     */
    User selectByNickName(String nickName);
    /**
	  * 根据openId查找用户
	  * @param id
	  * @return
	  * @author hua
	  * @date： 2018年3月12日 下午8:07:50
	  */
    User selectByOpenId(String openId);
}