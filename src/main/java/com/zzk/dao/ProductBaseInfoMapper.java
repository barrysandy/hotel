package com.zzk.dao;

import java.util.*;

import com.zzk.entity.ProductBaseInfo;

public interface ProductBaseInfoMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:32
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int insert(ProductBaseInfo record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int insertSelective(ProductBaseInfo record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return ProductBaseInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    ProductBaseInfo selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int updateByPrimaryKeySelective(ProductBaseInfo record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int updateByPrimaryKey(ProductBaseInfo record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    List<ProductBaseInfo> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int selectCount(Map record);

    /**
     * 通过单品ID查询出对于的商品图片及名称
     *
     * @param skuId
     * @return
     * @author John
     * @date： 2018年3月13日 下午7:12:35
     */
    Map<String, Object> getProductNameImg(String skuId);

    /**
     * C端接口,商品LIST
     *
     * @param map
     * @return list
     * @author kun
     * @date 17:56 2018/3/14
     */
    List<Map<String, Object>> listProductForUser(Map map);

    /**
     * C端接口商品详情
     *
     * @param productId 商品ID
     * @return map
     * @author kun
     * @date 19:37 2018/3/14
     */
    Map<String, Object> getProductDetail(String productId);

    /**
     * 查询已删除商品
     * @param sellerId 商家ID
     * @return list
     * @author kun
     * @date 19:16 2018/3/13
     */
    List<Map<String,Object>> listDeleteProduct(String sellerId);

    /**
     * B端-查询商品列表
     * @param map 条件MAP
     * @return list
     * @author kun
     * @date 14:11 2018/3/17
     */
    List<Map<String,Object>> listProductForSeller(Map<String,Object> map);

    /**
     * 回收站删除 - 物理删除
     * @param productId
     * @return int
     * @author kun
     * @date 12:02 2018/3/29
     */
    int physicalDeleteProduct(String productId);

    /**
     * 发团管理 - 选择产品 接口
     * @param sellerId
     * @return result
     * @author kun
     * @date 15:09 2018/4/10
     */
    List<Map<String,Object>> listProductBySellerId(String sellerId);

    /**
     * 通过商家ID查询商家的联系电话
     * @param sellerId
     * @return
     * @author kun
     * @date 15:34 2018/5/9
     */
    String getSellerPhone(String sellerId);

    /**
     * 查询当前商品剩余的最大库存
     * @param
     * @return
     * @author kun
     * @date 16:12 2018/5/23
     */
    Integer getRemainingNumber(String productId);
}