package com.zzk.entity;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.time.DateFormatUtils;




/**
 * 所属类别:实体类 <br/>
 * 用途: 信誉记录表的实体类<br/>
 * Author:<br/>
 */
public class TMpClientUser {
    /**
     *
     */
    private String id;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    private String imPassword;

    public String getImPassword() {
        return "d35bb804b879047c5bd31318824c8c91";
    }


    public void setImPassword(String imPassword) {
        this.imPassword = imPassword == null ? null : imPassword.trim();
    }

    /**
     * 头衔
     */
    private String honor;

    public String getHonor() {
        return honor;
    }


    public void setHonor(String honor) {
        this.honor = honor == null ? null : honor.trim();
    }

    /**
     * 密码
     */
    private String password;

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 头像
     */
    private String headImg;

    public String getHeadImg() {
        return headImg;
    }


    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    /**
     * 账号
     */
    private String account;

    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 昵称
     */
    private String nickname;

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 手机
     */
    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }


    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    /**
     * 邮箱
     */
    private String email;

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    private String age;
    
    public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}
	/**
     * 用户等级
     */
    private String level;

    public String getLevel() {
        return level;
    }


    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     *
     */
    private int userType;

    public int getUserType() {
        return userType;
    }


    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * 性别:0不显示，1男，2女
     */
    private int gender;

    public int getGender() {
        return gender;
    }


    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * 积分
     */
    private int integral;

    public int getIntegral() {
        return integral;
    }


    public void setIntegral(int integral) {
        this.integral = integral;
    }

    /**
     * 用户设置附件空间的最大限制
     */
    private int spaceMaxLimit;

    public int getSpaceMaxLimit() {
        return spaceMaxLimit;
    }


    public void setSpaceMaxLimit(int spaceMaxLimit) {
        this.spaceMaxLimit = spaceMaxLimit;
    }

    /**
     * 活跃度
     */
    private String active;

    public String getActive() {
        return active;
    }


    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    /**
     * 个人标签
     */
    private String personalLabel;

    public String getPersonalLabel() {
        return personalLabel;
    }


    public void setPersonalLabel(String personalLabel) {
        this.personalLabel = personalLabel == null ? null : personalLabel.trim();
    }

    /**
     * 是否达人:0否，1是
     */
    private boolean isTalent;

    public boolean getIsTalent() {
        return isTalent;
    }


    public void setIsTalent(boolean isTalent) {
        this.isTalent = isTalent;
    }

    /**
     *
     */
    private boolean isSilent;

    public boolean getIsSilent() {
        return isSilent;
    }


    public void setIsSilent(boolean isSilent) {
        this.isSilent = isSilent;
    }

    /**
     * 荣誉点
     */
    private int honorPoint;

    public int getHonorPoint() {
        return honorPoint;
    }


    public void setHonorPoint(int honorPoint) {
        this.honorPoint = honorPoint;
    }

    /**
     * 冻结/解冻状态：0正常、1冻结
     */
    private int freezeThaw;

    public int getFreezeThaw() {
        return freezeThaw;
    }


    public void setFreezeThaw(int freezeThaw) {
        this.freezeThaw = freezeThaw;
    }

    /**
     * 冻结原因
     */
    private String freezingCause;

    public String getFreezingCause() {
        return freezingCause;
    }


    public void setFreezingCause(String freezingCause) {
        this.freezingCause = freezingCause == null ? null : freezingCause.trim();
    }

    /**
     * 冻结天数
     */
    private int freezingDays;

    public int getFreezingDays() {
        return freezingDays;
    }


    public void setFreezingDays(int freezingDays) {
        this.freezingDays = freezingDays;
    }

    /**
     * 冻结原因备注
     */
    private String remarks;

    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     *
     */
    private BigDecimal longitude;

    public BigDecimal getLongitude() {
        return longitude;
    }


    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     *
     */
    private BigDecimal latitude;

    public BigDecimal getLatitude() {
        return latitude;
    }


    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     *
     */
    private String countyId;

    public String getCountyId() {
        return countyId;
    }


    public void setCountyId(String countyId) {
        this.countyId = countyId == null ? null : countyId.trim();
    }

    /**
     * 1 达西APP；2 社交APP；3 电商网站PC；4 站点驿站 5商家APP
     */
    private int regSource;

    public int getRegSource() {
        return regSource;
    }


    public void setRegSource(int regSource) {
        this.regSource = regSource;
    }

    /**
     *
     */
    private String merchatId;

    public String getMerchatId() {
        return merchatId;
    }


    public void setMerchatId(String merchatId) {
        this.merchatId = merchatId == null ? null : merchatId.trim();
    }

    /**
     * 注册时间
     */
    private Date regTime;

    public Date getRegTime() {
        return regTime;
    }


    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     *
     */
    private Date lastLoginTime;

    public Date getLastLoginTime() {
        return lastLoginTime;
    }


    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 1 达西APP；2 社交APP；3 电商网站PC；4 站点驿站
     */
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    /**
     * 创建日期
     */
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改日期
     */
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 状态(标识是否删除)
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    /**
     * 个人简介
     */
    private String profile;

    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }


    /**
     * @param profile the profile to set
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * 身高
     */
    private Float height;

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    /**
     * 故乡
     */
    private String hometown;

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    /**
     * 生日
     *
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private Date birthday;

    /**
     * 用户签名
     */
    private String userSignature;

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    //    附加字段在此添加
    /**
     * 关注数
     */
    private Integer followNum;

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    /**
     * 粉丝数
     */
    private Integer fansNum;

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    /**
     * 来访数
     */
    private Integer visitNum;

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    /**
     * 消息数
     */
    private Integer msgNum;

    public Integer getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(Integer msgNum) {
        this.msgNum = msgNum;
    }

    /**
     * 点赞数
     */
    private Integer praiseNum;

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    /**
     * 登录状态
     */
    private String loginState;

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    /**
     * openId
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    /**
     * 身份证号码
     */
    private String idCard;
    public String getIdCard() {
    	return idCard;
    }
    
    
    public void setIdCard(String idCard) {
    	this.idCard = idCard;
    }
    /**
     * 身份证IMG
     */
    private String idCardIMGPath;
    public String getIdCardIMGPath() {
    	return idCardIMGPath;
    }
    
    
    public void setIdCardIMGPath(String idCardIMGPath) {
    	this.idCardIMGPath = idCardIMGPath;
    }
    /**
     * 是否通过认证
     */
    private Integer isAuth;
	public Integer getIsAuth() {
		return isAuth;
	}


	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}
	/**
     * 是否菜鸟
     */
    private Integer isRookie;
	public Integer getIsRookie() {
		return isRookie;
	}
	public void setIsRookie(Integer isRookie) {
		this.isRookie = isRookie;
	}
 


}