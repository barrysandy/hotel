package com.zzk.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zzk.util.Result;
import com.zzk.entity.TMpClientUser;



/**
 * 信誉记录表
 *
 * @author lishiqiang
 * @date 2017-3-15
 * modify history
 */
public interface TMpClientUserService {

    /**
     * 详情
     *
     * @author lishiqiang
     * @date 2017-3-15
     * modify history
     */
    TMpClientUser selectByPrimaryKey(String id);

    
    /**
     * 更新数据
     *
     * @param bean
     * @return
     * @author lishiqiang
     * @date 2017-3-15
     * modify history
     */
    public int update(TMpClientUser bean);

    /**
     * 新增
     *
     * @param bean
     * @return
     * @author lishiqiang
     * @param json 
     * @date 2017-3-16
     * modify history
     */
    public int insert(TMpClientUser bean, String token,String pd);

    /**
     * 删除
     *
     * @param id
     * @return
     * @author lishiqiang
     * @date 2017-3-16
     * modify history
     */
    public int delete(String id);


    int checkUserByPassword(TMpClientUser user, String token, Result<Object> result) throws NoSuchAlgorithmException;

    int validate(String token);

    int checkUserInfo(TMpClientUser user, Result<Object> result);

    String getUserInfo(String token);
    
    void cecheUser(String token ,JSONObject json,TMpClientUser user);
    
    /**
     * @des : 对外接口,用户登录
     * @author zhou.zhengkun
     * @date 2017/03/28 0028 14:53
     */
    TMpClientUser login(String userName, String pass,String regSource);

    /**
     * @des : 判断是否为已存在的用户
     * @author zhou.zhengkun
     * @date 2017/03/28 0028 14:54
     */
    int isExistUser(TMpClientUser user);

    /**
     * @des : 判断是否是注册过的用户 -- 用于忘记密码
     * @author zhou.zhengkun
     * @date 2017/04/07 0007 10:35
     */
    int isRegisterUser(String phoneNume);

    /**
     * @des : 用户注册
     * @author zhou.zhengkun
     * @date 2017/03/28 0028 15:21
     */
    int registUser(TMpClientUser user);

    /**
     * <p>description:</p>
     *
     * @param
     * @return int
     * @author Wen Yugang
     * @date 2017-3-30下午9:37:51
     */
    int checkPhone(String phone);

    /**
     * @des : 忘记密码 - 重置密码
     * @author zhou.zhengkun
     * @date 2017/04/07 0007 11:11
     */
    TMpClientUser resetPwd(TMpClientUser vo);

	/**
	 * @Description:  
	 * @author sty
	 */
	TMpClientUser getUserInfoById(String id);

	/**
	 * @Description:  
	 * @author sty
	 */
	int updateByPrimaryKeySelective(TMpClientUser bean);

	/**
	 * @Description:  
	 * @author sty
	 */
	Result updatePassword(TMpClientUser bean, String verificationCode);

	/**
	 * @Description:  
	 * @author sty
	 */
	Result bindPhone(TMpClientUser bean);

    /**
     * @des : 修改密码
     * @param
     * @return
     * @author zhou.zhengkun
     * @date 2017/04/12 0012 11:50
     */
    String changePwd(String id, String pwd, String newPwd);


    String getAllUserInfoById(String userId,int userType);


    String getPraiseUsersById(String userId);

    String getVisitorListById(int psize,int offset,String userId);


    TMpClientUser oldLogin(String userName, String pass );
    String getUserIfo(HttpServletRequest req);
	
    TMpClientUser selectByPhoneNum(String phoneNum);

    TMpClientUser selectByOpenId(String openId);

    String getUserInfoByPhoneNum(String phoneNum);

    String getUserOrderList(String phoneNum,Integer pageNumber,Integer pageSize);

    String getOrderDetail(String orderId);

    String getUserComment(String phoneNum,String destId,Integer pageNumber,Integer pageSize);

    String getUserFocusOrFans(String phoneNum,String userType,Integer startRow,Integer pageSize);

   

    String uploadUserLog(String userId,String userLog);

    String validateNickName(String nickName);

    String changeNickName(String userId,String nickName);


    String changeLoginState(String userId,String loginState);

	Result updatePasswordByOldPwd(TMpClientUser bean, String oldPwd);

}
