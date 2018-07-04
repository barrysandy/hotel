package com.zzk.service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.zzk.dao.ProductAttributeInfoMapper;
import com.zzk.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.zzk.dao.SellerChooseAttributeInfoMapper;
import com.zzk.entity.SellerChooseAttributeInfo;

/**
 * 商家选择的扩展属性信息表
 *
 * @author: Kun
 * @date: 2018-03-07 16:05
 */
@Service("sellerChooseAttributeInfoService")
public class SellerChooseAttributeInfoServiceImpl implements SellerChooseAttributeInfoService {

    @Resource
    private SellerChooseAttributeInfoMapper sellerChooseAttributeInfoMapper;
    @Resource
    private ProductAttributeInfoMapper productAttributeInfoMapper;
    private static Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
    /**
     * 分页查询
     */
    @Override
    public List<SellerChooseAttributeInfo> selectByPage(Map map) {
        return sellerChooseAttributeInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return sellerChooseAttributeInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public SellerChooseAttributeInfo selectByPrimaryKey(String id) {
        return sellerChooseAttributeInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(SellerChooseAttributeInfo bean) {
        return sellerChooseAttributeInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(SellerChooseAttributeInfo bean) {
        return sellerChooseAttributeInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        SellerChooseAttributeInfo bean = sellerChooseAttributeInfoMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return sellerChooseAttributeInfoMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<Map<String, Object>> listChooseProductAttrByProductId(String productId) {
        return sellerChooseAttributeInfoMapper.listChooseProductAttrByProductId(productId);
    }

    @Override
    public void saveProductPublishSellerAttr(String baseInfo,String productId) {

        List<SellerChooseAttributeInfo> sqlList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        JSONArray theme = jsonObject.getJSONArray("theme");
        JSONArray otherAttribute = jsonObject.getJSONArray("otherAttribute");
        JSONArray featuresLabel = jsonObject.getJSONArray("featuresLabel");

        //查询名字为主题的扩展属性
        Map<String,Object> themeMap = productAttributeInfoMapper.getAttributeByName("主题");
        if (themeMap != null){
            JSONArray themeArray = JSONArray.fromObject(themeMap.get("CONTENT"));
            for (int i = 0 ; i<theme.size() ; i++){
                String checkedTheme = theme.getString(i);
                for (int j = 0 ; j<themeArray.size() ; j++){
                    JSONObject themeItem = themeArray.getJSONObject(j);
                    String themeItemName = themeItem.getString("name");
                    if (StringUtils.isEquals(checkedTheme,themeItemName)){
                        themeItem.put("checked",true);
                    }
                }
            }
            themeMap.put("CONTENT",themeArray);
        }
        // 组装实体类
        SellerChooseAttributeInfo themeAttr = new SellerChooseAttributeInfo();
        themeAttr.setId(StringUtils.getUUID());
        themeAttr.setProductId(productId);
        themeAttr.setAttrId("61ec899abbb44e498b16bb4e88ceb2eb");
        themeAttr.setAttrType("1");
        themeAttr.setName("主题");
        themeAttr.setType(3);
        themeAttr.setContent(themeMap.get("CONTENT").toString());
        themeAttr.setStatus(1);
        themeAttr.setCreateTime(new Date());
        sqlList.add(themeAttr);

        //查询名字为线路标签(特色)的扩展属性
        Map<String,Object> featuresMap = productAttributeInfoMapper.getAttributeByName("线路标签(特色)");
        if (featuresMap != null){
            JSONArray featuresArray = JSONArray.fromObject(featuresMap.get("CONTENT"));
            for (int i = 0 ; i < featuresLabel.size() ; i++){
                String checkedLabel = featuresLabel.getString(i);
                for (int j = 0 ; j < featuresArray.size() ; j++){
                    JSONObject labelItem = featuresArray.getJSONObject(j);
                    String labelItemName = labelItem.getString("name");
                    if (StringUtils.isEquals(checkedLabel,labelItemName)){
                        labelItem.put("checked",true);
                    }
                }
            }
            featuresMap.put("CONTENT",featuresArray);
        }
        // 组装实体类
        SellerChooseAttributeInfo labelAttr = new SellerChooseAttributeInfo();
        labelAttr.setId(StringUtils.getUUID());
        labelAttr.setProductId(productId);
        labelAttr.setAttrId("123daf133c6547ce80f96b526f347a20");
        labelAttr.setAttrType("1");
        labelAttr.setName("线路标签(特色)");
        labelAttr.setType(3);
        labelAttr.setContent(featuresMap.get("CONTENT").toString());
        labelAttr.setStatus(1);
        labelAttr.setCreateTime(new Date());
        sqlList.add(labelAttr);

        JSONObject otherAttrObject ;
        for (int i = 0 ;i < otherAttribute.size();i++){
            SellerChooseAttributeInfo attributeInfo = new SellerChooseAttributeInfo();
            otherAttrObject = otherAttribute.getJSONObject(i);
            String name = otherAttrObject.optString("NAME");
            String content = otherAttrObject.optString("CONTENT");
            attributeInfo.setName(name);
            /*这里将/n转换陈html标签*/
            if (StringUtils.isNotBlank(content)) {
                Matcher m = CRLF.matcher(content);
                if (m.find()){
                    content = m.replaceAll("<br/>");
                }
            }
            attributeInfo.setId(StringUtils.getUUID());
            attributeInfo.setProductId(productId);
            attributeInfo.setAttrId(otherAttrObject.optString("ID"));
            attributeInfo.setType(otherAttrObject.optInt("TYPE"));
            attributeInfo.setValue(content);
            attributeInfo.setContent(content);
            if (StringUtils.equals(name,"服务/咨询电话")){
                attributeInfo.setValue(jsonObject.optString("phoneNumber"));
            } else if (StringUtils.equals(name,"线路特色(详情)")) {
                attributeInfo.setValue(jsonObject.optString("editorContent"));
            }
            attributeInfo.setAttrType(otherAttrObject.optString("ATTR_TYPE"));
            attributeInfo.setStatus(1);
            attributeInfo.setCreateTime(new Date());
            if (!StringUtils.isEquals(name,"线路标签(特色)") && !StringUtils.isEquals(name,"主题")){
                sqlList.add(attributeInfo);
            }
        }

        //处理富文本编辑器
//        String editorContent = jsonObject.optString("editorContent");
//        SellerChooseAttributeInfo attributeInfo = new SellerChooseAttributeInfo();
//        attributeInfo.setName("线路特色(详情)");
//        attributeInfo.setId(StringUtils.getUUID());
//        attributeInfo.setProductId(productId);
//        attributeInfo.setAttrId("jij2981dh193hdincq89hd193hd19jd1x9");
//        attributeInfo.setType(4);
//        attributeInfo.setValue(editorContent);
//        // attrType: 1=商品扩展 2=价格扩展
//        attributeInfo.setAttrType("1");
//        attributeInfo.setStatus(1);
//        attributeInfo.setCreateTime(new Date());
//        attributeInfo.setUpdateTime(new Date());
//
//        sqlList.add(attributeInfo);

        sellerChooseAttributeInfoMapper.insertBatch(sqlList);

    }

    @Override
    public void updateProductPublishSellerAttr(String baseInfo) {

        JSONObject jsonObject = JSONObject.fromObject(baseInfo);
        String productId = jsonObject.optString("productId");
        /*删除旧的扩展属性*/
        sellerChooseAttributeInfoMapper.deleteByProductId(productId);
        saveProductPublishSellerAttr(baseInfo,productId);
    }

}
