package com.zzk.dao;

import java.util.*;

import com.zzk.entity.LineUser;

public interface LineUserMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 10:13
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int insert(LineUser record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int insertSelective(LineUser record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return LineUser 实体类
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    LineUser selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int updateByPrimaryKeySelective(LineUser record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int updateByPrimaryKey(LineUser record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    List<LineUser> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-04-03 10:13
     */
    int selectCount(Map record);

    /**
     * 设置首页 - 账户信息 - 获取当前商家信息
     * @param sellerId 商家ID
     * @return map
     * @author kun
     * @date 10:28 2018/4/3
     */
    Map<String,Object> getSellerInfo(String sellerId);

    /**
     * 通过商家ID查询用户信息
     * @param sellerId
     * @return userInfo
     * @author kun
     * @date 11:00 2018/4/3
     */
    LineUser getUserInfoBySellerId(String sellerId);
}