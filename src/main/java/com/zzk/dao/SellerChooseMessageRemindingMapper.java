package com.zzk.dao;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.zzk.entity.SellerChooseMessageReminding;

public interface SellerChooseMessageRemindingMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 14:50
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int insert(SellerChooseMessageReminding record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int insertSelective(SellerChooseMessageReminding record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return SellerChooseMessageReminding 实体类
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    SellerChooseMessageReminding selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int updateByPrimaryKeySelective(SellerChooseMessageReminding record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int updateByPrimaryKey(SellerChooseMessageReminding record);

    /**
     * 分页查询
     *
     * @param sellerId 商家ID
     * @return List
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    List<SellerChooseMessageReminding> selectByPage(String sellerId);
    /**
     * 根据商户ID和内容名称查询
     * @param sellerId
     * @param contentName
     * @return
     * @author John
     * @date： 2018年4月30日 下午9:09:13
     */
    SellerChooseMessageReminding selectBySellerIdAndMessaegName(@Param("sellerId") String sellerId,@Param("messageName")String messaegName);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-04-03 14:50
     */
    int selectCount(Map record);

    /**
     * 通过SellerId查询商家选择的消息提醒
     * @param sellerId 商家ID
     * @return list
     * @author kun
     * @date 16:36 2018/4/3
     */
    List<Map<String,Object>> listChooseMessageBySellerId(String sellerId);

    /**
     * 通过sellerId删除旧的选择
     * @param sellerId
     * @return int
     * @author kun
     * @date 15:20 2018/4/8
     */
    int deleteOldBySellerId(String sellerId);

    /**
     * 批量插入
     * @param list 实体类list
     * @return int
     * @author kun
     * @date 16:50 2018/4/8
     */
    int insertBatch(List<SellerChooseMessageReminding> list);
}