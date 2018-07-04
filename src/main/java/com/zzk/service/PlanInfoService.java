package com.zzk.service;

import java.util.*;

import com.zzk.entity.PlanInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 行程安排总表
 *
 * @author: Kun
 * @date: 2018-03-06 11:09
 */
public interface PlanInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 11:09
     */
    List<PlanInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 行程安排总表实体类
     * @author: Kun
     * @date: 2018-03-06 11:09
     */
    PlanInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 11:09
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:09
     */
    int update(PlanInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:09
     */
    int insert(PlanInfo bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 11:09
     */
    int delete(String id);


    /**
     * 商品发布-基本信息-保存行程总安排
     *
     * @param baseInfo  json参数
     * @param productId 商品ID
     * @return planId
     * @author kun
     * @date 11:30 2018/3/8
     */
    String saveProductPublishPlanInfo(String baseInfo, String productId);


    /**
     * 商品发布-基本信息-更新行程总安排
     *
     * @param baseInfo json参数
     * @author kun
     * @date 11:30 2018/3/8
     */
    void updateProductPublishPlanInfo(String baseInfo);


    /**
     * 根据商品ID 查询详细模式的发团总行程安排
     *
     * @param productId
     * @return planInfo
     * @author kun
     * @date 15:46 2018/3/8
     */
    PlanInfo getDetailModePlanInfo(String productId);

    /**
     * 根据商品ID 查询简介模式的总行程安排
     *
     * @param productId
     * @return planInfo
     * @author kun
     * @date 15:46 2018/3/8
     */
    PlanInfo getConciseModePlanInfo(String productId);

    /**
     * 商品发布-行程安排-简介模式行程保存
     *
     * @param productId 商品ID
     * @param productId 途径目的地
     * @param productId 内容
     * @author kun
     * @date 11:27 2018/3/9
     */
    void saveConciseModePlanInfo(String productId, JSONArray destination, String content);

    /**
     * 商品发布-行程安排-详细模式行程保存
     *
     * @param productId 商品ID
     * @param list      单天行程List
     * @author kun
     * @date 14:45 2018/3/9
     */
    void saveDetailModePlanInfo(List<Map<String, Object>> list, String productId);
}
