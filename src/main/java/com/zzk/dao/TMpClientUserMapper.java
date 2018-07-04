package com.zzk.dao;

import com.zzk.entity.TMpClientUser;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



public interface TMpClientUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(TMpClientUser record);

    int insertSelective(TMpClientUser record);

    TMpClientUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TMpClientUser record);

    int updateByPrimaryKey(TMpClientUser record);
    
    List<TMpClientUser> selectByPage(Map map);
	
    int selectCount(Map record);
    
    TMpClientUser selectByPhoneNum(String phoneNum);
    
    TMpClientUser selectByEmail(String email);
    
    TMpClientUser selectByNickName(String nickName);

    TMpClientUser findLoginUser(Map record);

	/**
	 * <p>description:注册验证手机号是否已经存在</p>
	 * @param 
	 * @return int
	 * @author Wen Yugang
	 * @date 2017-3-30下午9:38:42
	 */
	int checkPhone(String phone);

    int resetPwd (TMpClientUser vo);

    int changePwd(TMpClientUser userVo);

    List<TMpClientUser> getNearUser(Map map);

    TMpClientUser getAllUserInfoById(String userId);

    List<TMpClientUser> getPraiseUsersById(String userId);

    List<TMpClientUser> getVisitorListById(Map map);

    Integer getVisitorNum(@Param("userId") String userId);

    List<Map<String,Object>> regulatoryAppLogin(Map map);
    List<TMpClientUser> getUserIfo(@Param("ids")String[] ids);
	/**
	 * 创建im群
	 * @return
	 */
	 int CreateIms(Map<String,String> map) ;


	TMpClientUser fingUserByAccount(Map map);
	Map<String, Object> selectByPhoneNumMap(String phoneNum);

    /**
     * 根据主键列表查询用户
     * @param map
     * @return
     */
    List<TMpClientUser> getUsersByIds(Map map);

    TMpClientUser selectByOpenId(String openId);

    List<Map<String,Object>> getUserInfoByPhoneNum(String phoneNum);

    String getUserBalance(String userId);

    List<Map<String,Object>> getUserOrderListOrDetail(Map map);

    String countUserOrderListOrDetail(Map map);

    List<Map<String,Object>> getUserComment(Map map);

    List<Map<String,Object>> getUserFocus(Map map);

    List<Map<String,Object>> getUserFans(Map map);

    /***
     * 根据用户Id获取邀约，想去，足迹，来访，粉丝，关注 统计总数 接口
     * @param userId
     * @return
     */

    int validateNickName(String nickName);

    int focus(Map map);

    String checkIsAlreadyFocus(Map map);

    int changeLoginState(Map map);

    void batchConcern(String userId);
    
    int updateLastLoginTime(TMpClientUser vo);
}