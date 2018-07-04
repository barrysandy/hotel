package com.zzk.service;

import java.util.*;

import com.zzk.entity.ProductBaseInfo;
import com.zzk.util.MethodAnnotation;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品基本信息表
 *
 * @author: Kun
 * @date: 2018-03-06 14:32
 */
public interface ProductBaseInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    List<ProductBaseInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商品基本信息表实体类
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    ProductBaseInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int update(ProductBaseInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:32
     */
    int insert(ProductBaseInfo bean);

    /**
     * 删除
     *
     * @param productId 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:32
     */
    int delete(String productId);

    /**
     * 回收站删除 - 物理删除
     * @param productId
     * @return int
     * @author kun
     * @date 12:02 2018/3/29
     */
    int physicalDeleteProduct(String productId);

    /**
     * 商家发布商品-基本信息-商品表保存
     * @param productId 商品ID
     * @param baseInfo json参数
     * @author kun
     * @date 10:27 2018/3/8
     */
    void saveProductPublishBaseInfo(String baseInfo,String productId);

    /**
     * 发布商品-基本信息-修改商品表信息
     * @param baseInfo json参数
     * @author kun
     * @date 14:47 2018/3/8
     */
    void updateProductPublishBaseInfo(String baseInfo);

    /**
     * 查询已删除商品
     * @param sellerId 商家ID
     * @return list
     * @author kun
     * @date 19:16 2018/3/13
     */
    List<Map<String, Object>> listDeleteProduct(String sellerId);

    /**
     * C端接口,商品LIST
     *
     * @param map 筛选条件
     * @return list
     * @author kun
     * @date 17:56 2018/3/14
     */
    List<Map<String, Object>> listProductForUser(Map<String, Object> map);

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
     * 回收站 - 还原删除的商品
     * @param productId 商品Id
     * @return
     * @author kun
     * @date 11:58 2018/3/16
     */
    int reductionProduct(String productId);

    /**
     * B端-查询商品列表
     * @param map 条件MAP
     * @return list
     * @author kun
     * @date 14:11 2018/3/17
     */
    List<Map<String,Object>> listProductForSeller(Map<String,Object> map);

    /**
     * B端展品列表 上方搜索条件接口
     * @return list
     * @author kun
     * @date 10:38 2018/3/19
     */
    Map<String,Object> getProductListSearchParam();

    /**
     * 通过商品ID删除旧的行程安排
     * @param productId
     * @return int
     * @author kun
     * @date 10:57 2018/3/30
     */
    int deleteOldSchedu(String productId);

    /**
     * 修改商品上下架状态
     * @param productId 商品ID
     * @param state 1=上架 2=下架
     * @return int
     * @author kun
     * @date 15:56 2018/4/2
     */
    int changeShelfState(String productId,Integer state);

    /**
     * 发团管理 - 选择产品 接口
     * @param sellerId 商家ID
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
}
