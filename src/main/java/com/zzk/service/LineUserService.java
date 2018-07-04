package com.zzk.service;

import java.util.*;

import com.zzk.entity.LineUser;
import com.zzk.util.Result;

/**
 * 用户信息表
 *
 * @author: Kun
 * @date: 2018-04-03 10:13
 */
public interface LineUserService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    List<LineUser> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 用户信息表实体类
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    LineUser selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int update(LineUser bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int insert(LineUser bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 10:13
     */
    int delete(String id);

    /**
     * 设置首页 - 账户信息 - 获取当前商家信息
     * @param sellerId 商家ID
     * @return map
     * @author kun
     * @date 10:28 2018/4/3
     */
    Map<String,Object> getSellerInfo(String sellerId);

    /**
     * 设置首页 - 账户信息 - 修改头像
     * @param sellerId 商家ID
     * @param headImg 头像URL
     * @return int
     * @author kun
     * @date 10:53 2018/4/3
     */
    int changeUserHeadImg(String sellerId,String headImg);

    /**
     * 通过密码验证的方式修改密码
     * @param sellerId 商家ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return result
     * @author kun
     * @date 11:27 2018/4/4
     */
    Result changeByOldPwd(String sellerId, String oldPassword, String newPassword);
}
