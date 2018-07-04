package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

/**
 * 所属类别:实体类 <br/> 
 * 用途: 用户信息表的实体类<br/>
 * @author: Kun
 * @date: 2018-04-03 10:26
 */
public class LineUser{

	/**
	 *
	 */
	private String id;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id== null ? null : id.trim();
	}


	/**
	 * 部门ID
	 */
	private String departmentId;
	public String getDepartmentId(){
		return departmentId;
	}
	public void setDepartmentId(String departmentId){
		this.departmentId=departmentId== null ? null : departmentId.trim();
	}


	/**
	 * 角色ID
	 */
	private String roleId;
	public String getRoleId(){
		return roleId;
	}
	public void setRoleId(String roleId){
		this.roleId=roleId== null ? null : roleId.trim();
	}


	/**
	 * 头衔
	 */
	private String honor;
	public String getHonor(){
		return honor;
	}
	public void setHonor(String honor){
		this.honor=honor== null ? null : honor.trim();
	}


	/**
	 * 密码
	 */
	private String password;
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password=password== null ? null : password.trim();
	}


	/**
	 * 头像
	 */
	private String headImg;
	public String getHeadImg(){
		return headImg;
	}
	public void setHeadImg(String headImg){
		this.headImg=headImg== null ? null : headImg.trim();
	}


	/**
	 * 账号
	 */
	private String account;
	public String getAccount(){
		return account;
	}
	public void setAccount(String account){
		this.account=account== null ? null : account.trim();
	}


	/**
	 * 昵称
	 */
	private String nickname;
	public String getNickname(){
		return nickname;
	}
	public void setNickname(String nickname){
		this.nickname=nickname== null ? null : nickname.trim();
	}


	/**
	 * 手机
	 */
	private String phoneNum;
	public String getPhoneNum(){
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum){
		this.phoneNum=phoneNum== null ? null : phoneNum.trim();
	}


	/**
	 * 邮箱
	 */
	private String email;
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email== null ? null : email.trim();
	}


	/**
	 * 用户等级
	 */
	private String level;
	public String getLevel(){
		return level;
	}
	public void setLevel(String level){
		this.level=level== null ? null : level.trim();
	}


	/**
	 * 1:游客;2:电商;3:达西
	 */
	private int userType;
	public int getUserType(){
		return userType;
	}
	public void setUserType(int userType){
		this.userType=userType;
	}


	/**
	 * 性别:0保密，1男，2女
	 */
	private int gender;
	public int getGender(){
		return gender;
	}
	public void setGender(int gender){
		this.gender=gender;
	}


	/**
	 * 积分
	 */
	private int integral;
	public int getIntegral(){
		return integral;
	}
	public void setIntegral(int integral){
		this.integral=integral;
	}


	/**
	 * 用户设置附件空间的最大限制
	 */
	private int spaceMaxLimit;
	public int getSpaceMaxLimit(){
		return spaceMaxLimit;
	}
	public void setSpaceMaxLimit(int spaceMaxLimit){
		this.spaceMaxLimit=spaceMaxLimit;
	}


	/**
	 * 活跃度
	 */
	private String active;
	public String getActive(){
		return active;
	}
	public void setActive(String active){
		this.active=active== null ? null : active.trim();
	}


	/**
	 * 个人标签
	 */
	private String personalLabel;
	public String getPersonalLabel(){
		return personalLabel;
	}
	public void setPersonalLabel(String personalLabel){
		this.personalLabel=personalLabel== null ? null : personalLabel.trim();
	}


	/**
	 * 是否达人:0否，1是
	 */
	private boolean isTalent;
	public boolean getIsTalent(){
		return isTalent;
	}
	public void setIsTalent(boolean isTalent){
		this.isTalent=isTalent;
	}


	/**
	 *
	 */
	private boolean isSilent;
	public boolean getIsSilent(){
		return isSilent;
	}
	public void setIsSilent(boolean isSilent){
		this.isSilent=isSilent;
	}


	/**
	 * 荣誉点
	 */
	private int honorPoint;
	public int getHonorPoint(){
		return honorPoint;
	}
	public void setHonorPoint(int honorPoint){
		this.honorPoint=honorPoint;
	}


	/**
	 * 冻结/解冻状态：0正常、1冻结
	 */
	private int freezeThaw;
	public int getFreezeThaw(){
		return freezeThaw;
	}
	public void setFreezeThaw(int freezeThaw){
		this.freezeThaw=freezeThaw;
	}


	/**
	 * 冻结原因
	 */
	private String freezingCause;
	public String getFreezingCause(){
		return freezingCause;
	}
	public void setFreezingCause(String freezingCause){
		this.freezingCause=freezingCause== null ? null : freezingCause.trim();
	}


	/**
	 * 冻结天数
	 */
	private int freezingDays;
	public int getFreezingDays(){
		return freezingDays;
	}
	public void setFreezingDays(int freezingDays){
		this.freezingDays=freezingDays;
	}


	/**
	 * 冻结原因备注
	 */
	private String remarks;
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks=remarks== null ? null : remarks.trim();
	}


	/**
	 * 经度
	 */
	private BigDecimal longitude;
	public BigDecimal getLongitude(){
		return longitude;
	}
	public void setLongitude(BigDecimal longitude){
		this.longitude=longitude;
	}


	/**
	 * 纬度
	 */
	private BigDecimal latitude;
	public BigDecimal getLatitude(){
		return latitude;
	}
	public void setLatitude(BigDecimal latitude){
		this.latitude=latitude;
	}


	/**
	 *
	 */
	private String countyId;
	public String getCountyId(){
		return countyId;
	}
	public void setCountyId(String countyId){
		this.countyId=countyId== null ? null : countyId.trim();
	}


	/**
	 * 1 达西APP；2 社交APP；3 电商网站PC；4 站点驿站 ;5 商家APP
	 */
	private int regSource;
	public int getRegSource(){
		return regSource;
	}
	public void setRegSource(int regSource){
		this.regSource=regSource;
	}


	/**
	 * 商家ID
	 */
	private String merchatId;
	public String getMerchatId(){
		return merchatId;
	}
	public void setMerchatId(String merchatId){
		this.merchatId=merchatId== null ? null : merchatId.trim();
	}


	/**
	 * 注册时间
	 */
	private Date regTime;
	public Date getRegTime(){
		return regTime;
	}
	public void setRegTime(Date regTime){
		this.regTime=regTime;
	}


	/**
	 *
	 */
	private Date lastLoginTime;
	public Date getLastLoginTime(){
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime=lastLoginTime;
	}


	/**
	 * 1 达西APP；2 社交APP；3 电商网站PC；4 站点驿站
	 */
	private String signature;
	public String getSignature(){
		return signature;
	}
	public void setSignature(String signature){
		this.signature=signature== null ? null : signature.trim();
	}


	/**
	 *
	 */
	private int status;
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}


	/**
	 *
	 */
	private Date updateTime;
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}


	/**
	 *
	 */
	private Date createTime;
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	/**
	 * 姓名
	 */
	private String pname;
	public String getPname(){
		return pname;
	}
	public void setPname(String pname){
		this.pname=pname== null ? null : pname.trim();
	}


	/**
	 * 是否水军0否1是
	 */
	private int isNavy;
	public int getIsNavy(){
		return isNavy;
	}
	public void setIsNavy(int isNavy){
		this.isNavy=isNavy;
	}


	/**
	 * 生日
	 */
	private Date birthday;
	public Date getBirthday(){
		return birthday;
	}
	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}


	/**
	 * 冻结日期
	 */
	private Date freezingDate;
	public Date getFreezingDate(){
		return freezingDate;
	}
	public void setFreezingDate(Date freezingDate){
		this.freezingDate=freezingDate;
	}


	/**
	 *
	 */
	private int releases;
	public int getReleases(){
		return releases;
	}
	public void setReleases(int releases){
		this.releases=releases;
	}


	/**
	 * 个人简介
	 */
	private String profile;
	public String getProfile(){
		return profile;
	}
	public void setProfile(String profile){
		this.profile=profile== null ? null : profile.trim();
	}


	/**
	 *
	 */
	private String age;
	public String getAge(){
		return age;
	}
	public void setAge(String age){
		this.age=age== null ? null : age.trim();
	}


	/**
	 * 身高
	 */
	private float height;
	public float getHeight(){
		return height;
	}
	public void setHeight(float height){
		this.height=height;
	}


	/**
	 * 故乡
	 */
	private String homeTown;
	public String getHomeTown(){
		return homeTown;
	}
	public void setHomeTown(String homeTown){
		this.homeTown=homeTown== null ? null : homeTown.trim();
	}


	/**
	 * 用户签名
	 */
	private String userSingature;
	public String getUserSingature(){
		return userSingature;
	}
	public void setUserSingature(String userSingature){
		this.userSingature=userSingature== null ? null : userSingature.trim();
	}


	/**
	 * 省
	 */
	private String province;
	public String getProvince(){
		return province;
	}
	public void setProvince(String province){
		this.province=province== null ? null : province.trim();
	}


	/**
	 * 市
	 */
	private String city;
	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city=city== null ? null : city.trim();
	}


	/**
	 * 区县
	 */
	private String county;
	public String getCounty(){
		return county;
	}
	public void setCounty(String county){
		this.county=county== null ? null : county.trim();
	}


	/**
	 *
	 */
	private String pcc;
	public String getPcc(){
		return pcc;
	}
	public void setPcc(String pcc){
		this.pcc=pcc== null ? null : pcc.trim();
	}


	/**
	 *
	 */
	private String openId;
	public String getOpenId(){
		return openId;
	}
	public void setOpenId(String openId){
		this.openId=openId== null ? null : openId.trim();
	}


	/**
	 * qq
	 */
	private String qq;
	public String getQq(){
		return qq;
	}
	public void setQq(String qq){
		this.qq=qq== null ? null : qq.trim();
	}


	/**
	 * 支付密码
	 */
	private String payPassword;
	public String getPayPassword(){
		return payPassword;
	}
	public void setPayPassword(String payPassword){
		this.payPassword=payPassword== null ? null : payPassword.trim();
	}


	/**
	 * 是否开启话题新消息提醒 0:关闭 1：开启
	 */
	private int isTopicCall;
	public int getIsTopicCall(){
		return isTopicCall;
	}
	public void setIsTopicCall(int isTopicCall){
		this.isTopicCall=isTopicCall;
	}


	/**
	 *
	 */
	private String loginState;
	public String getLoginState(){
		return loginState;
	}
	public void setLoginState(String loginState){
		this.loginState=loginState== null ? null : loginState.trim();
	}


	/**
	 * 身份证号码
	 */
	private String idCard;
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		this.idCard=idCard== null ? null : idCard.trim();
	}


	/**
	 * 身份证照片地址
	 */
	private String idCardImg;
	public String getIdCardImg(){
		return idCardImg;
	}
	public void setIdCardImg(String idCardImg){
		this.idCardImg=idCardImg== null ? null : idCardImg.trim();
	}


	/**
	 * 是否已认证101未认证 201认证中 301已认证
	 */
	private int isAuth;
	public int getIsAuth(){
		return isAuth;
	}
	public void setIsAuth(int isAuth){
		this.isAuth=isAuth;
	}


	/**
	 * 是否新手
	 */
	private int isRookie;
	public int getIsRookie(){
		return isRookie;
	}
	public void setIsRookie(int isRookie){
		this.isRookie=isRookie;
	}

}