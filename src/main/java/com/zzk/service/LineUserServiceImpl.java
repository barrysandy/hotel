package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.util.PasswordEncoder;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.zzk.dao.LineUserMapper;
import com.zzk.entity.LineUser;

/**
 * 用户信息表
 *
 * @author: Kun
 * @date: 2018-04-03 10:13
 */
@Service("lineUserService")
public class LineUserServiceImpl implements LineUserService {

    @Resource
    private LineUserMapper lineUserMapper;
    @Value("${user.password.salt}")
    private String salt;

    /**
     * 分页查询
     */
    @Override
    public List<LineUser> selectByPage(Map map) {
        return lineUserMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return lineUserMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public LineUser selectByPrimaryKey(String id) {
        return lineUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(LineUser bean) {
        return lineUserMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(LineUser bean) {
        return lineUserMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        LineUser bean = lineUserMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return lineUserMapper.updateByPrimaryKey(bean);
    }

    @Override
    public Map<String, Object> getSellerInfo(String sellerId) {
        return lineUserMapper.getSellerInfo(sellerId);
    }

    @Override
    public int changeUserHeadImg(String sellerId, String headImg) {
        LineUser user = lineUserMapper.getUserInfoBySellerId(sellerId);
        if (user == null){
            return 0;
        } else {
            user.setHeadImg(headImg);
            user.setUpdateTime(new Date());
            return lineUserMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public Result changeByOldPwd(String sellerId, String oldPassword, String newPassword) {
        LineUser user = lineUserMapper.getUserInfoBySellerId(sellerId);
        if (user == null){
            return new Result(0,"fail","此用户数据有误!");
        } else {
            String passwordFromDb =  user.getPassword();
            PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
            String passwordFromPage = encoderMd5.encode(oldPassword);
            if (StringUtils.isEquals(passwordFromDb,passwordFromPage)) {
                user.setPassword(encoderMd5.encode(newPassword));
                user.setUpdateTime(new Date());
                lineUserMapper.updateByPrimaryKeySelective(user);
                return new Result(1,"success","修改密码成功!");
            } else {
                return new Result(0,"fail","密码不正确!");
            }
        }
    }

}
