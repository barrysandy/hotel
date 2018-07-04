package com.zzk.service;

import java.util.*;

import com.zzk.entity.SellerChooseMessageReminding;

/**
 * 商家选择消息提醒表
 *
 * @author: Kun
 * @date: 2018-04-03 14:50
 */
public interface SellerChooseMessageRemindingService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    List<SellerChooseMessageReminding> selectByPage(String sellerId);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商家选择消息提醒表实体类
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    SellerChooseMessageReminding selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int update(SellerChooseMessageReminding bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int insert(SellerChooseMessageReminding bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 14:50
     */
    int delete(String id);

    /**
     * 通过SellerId查询商家选择的消息提醒
     * @param sellerId 商家ID
     * @return list
     * @author kun
     * @date 16:36 2018/4/3
     */
    List<Map<String,Object>> listChooseMessageBySellerId(String sellerId);
}
