package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;

import com.zzk.util.MethodAnnotation;
import com.zzk.util.StringUtils;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.zzk.dao.ProductImagesMapper;
import com.zzk.entity.ProductImages;

/**
 * 商品图片表
 *
 * @author: Kun
 * @date: 2018-03-06 14:26
 */
@Service("productImagesService")
public class ProductImagesServiceImpl implements ProductImagesService {

    @Resource
    private ProductImagesMapper productImagesMapper;
    @Resource
    private OperateLogService operateLogService;
    /**
     * 分页查询
     */
    @Override
    public List<ProductImages> selectByPage(Map map) {
        return productImagesMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return productImagesMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public ProductImages selectByPrimaryKey(String id) {
        return productImagesMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProductImages bean) {
        return productImagesMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(ProductImages bean) {
        return productImagesMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        ProductImages bean = productImagesMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return productImagesMapper.updateByPrimaryKey(bean);
    }

    @Override
    public void saveProductPublishProductImg(String baseInfo,String productId) {
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String coverUrl = jsonObject.optString("coverUrl");
        String imgList = jsonObject.optString("imgList");
	    /*添加封面*/
        ProductImages productImages = new ProductImages();
        productImages.setId(StringUtils.getUUID());
        productImages.setProductId(productId);
        productImages.setUrl(coverUrl);
        productImages.setStatus(1);
		/*1=普通图片 2=封面图片 3=大图*/
        productImages.setType(2);
        productImages.setCreateTime(new Date());
        productImagesMapper.insertSelective(productImages);

		/*添加普通图片*/
        List<ProductImages> list = new ArrayList<>();
        if (StringUtils.isNotBlank(imgList)) {
            imgList = imgList.replace("，", ",");
            String[] args = imgList.split(",");
            for (int i = 0; i < args.length; i++) {
                ProductImages bean = new ProductImages();
                bean.setId(StringUtils.getUUID());
                bean.setProductId(productId);
                bean.setUrl(args[i]);
                bean.setStatus(1);
                /*1=普通图片 2=封面图片 3=大图*/
                bean.setType(1);
                bean.setCreateTime(new Date());
                list.add(bean);
            }
            productImagesMapper.insertBatch(list);
        }
        operateLogService.recordLog("更新图片", productId, 2);

    }

    @Override
    public void updateProductPublishProductImg(String baseInfo) {
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String productId = jsonObject.optString("productId");
	    /*删除以前的图片*/
        productImagesMapper.deleteOldProductPublishImg(productId);
        saveProductPublishProductImg(baseInfo,productId);
    }

    @Override
    public List<ProductImages> listImageByProductId(String productId) {
        return productImagesMapper.listImageByProductId(productId);
    }

}
