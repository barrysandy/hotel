package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zzk.entity.ProductTypeInfo;
import com.zzk.util.MethodAnnotation;
import com.zzk.util.StringUtils;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.zzk.dao.ProductTypeInfoMapper;

/**
 * 商品分类信息表
 *
 * @author: Kun
 * @date: 2018-03-06 14:35
 */
@Service("productTypeInfoService")
public class ProductTypeInfoServiceImpl implements ProductTypeInfoService {

    @Resource
    private ProductTypeInfoMapper productTypeInfoMapper;

    /**
     * 分页查询
     */
    @Override
    public List<ProductTypeInfo> selectByPage(Map map) {
        return productTypeInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return productTypeInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public ProductTypeInfo selectByPrimaryKey(String id) {
        return productTypeInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProductTypeInfo bean) {
        return productTypeInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(ProductTypeInfo bean) {
        return productTypeInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        ProductTypeInfo bean = productTypeInfoMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return productTypeInfoMapper.updateByPrimaryKey(bean);
    }

    /**
     * 通过商品ID，查询出商家选择的类型
     *
     * @param productId 商品ID
     * @return ProductTypeInfo bean
     * @author kun
     * @date 15:23 2018/3/7
     */
    @Override
    public ProductTypeInfo getByProductId(String productId) {
        return productTypeInfoMapper.getByProductId(productId);
    }

    @Override
    public void saveProductPublishSellerChooseType(String baseInfo,String productId) {

        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String sellerChooseTypeId = jsonObject.optString("typeId");
        String typeName = jsonObject.optString("typeName");
        ProductTypeInfo productTypeInfo = new ProductTypeInfo();
        productTypeInfo.setId(StringUtils.getUUID());
        productTypeInfo.setName(typeName);
        productTypeInfo.setParentId(sellerChooseTypeId);
        productTypeInfo.setProductId(productId);
        productTypeInfo.setStatus(1);
        productTypeInfo.setCreateTime(new Date());
        productTypeInfoMapper.insertSelective(productTypeInfo);

    }

    @Override
    public void updateProductPublishSellerChooseType(String baseInfo) {
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String sellerChooseTypeId = jsonObject.optString("typeId");
        String typeName = jsonObject.optString("typeName");
        String productId = jsonObject.optString("productId");
        ProductTypeInfo productTypeInfo = productTypeInfoMapper.getByProductId(productId);
        if (productTypeInfo != null) {
            productTypeInfo.setUpdateTime(new Date());
            productTypeInfo.setParentId(sellerChooseTypeId);
            productTypeInfo.setName(typeName);
            productTypeInfoMapper.updateByPrimaryKeySelective(productTypeInfo);
        }
    }

}
