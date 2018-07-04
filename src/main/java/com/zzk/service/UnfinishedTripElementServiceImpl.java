package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.zzk.dao.TripElementMapper;
import com.zzk.dao.UnfinishedTripElementMapper;
import com.zzk.entity.TripElement;
import com.zzk.entity.UnfinishedTripElement;
import com.zzk.util.StringUtils;

/**
 * 待完善旅游要素
* @author: huashuwen
* @date: 2018-03-10 11:02
 */
@Service("unfinishedTripElementService")
public class UnfinishedTripElementServiceImpl implements UnfinishedTripElementService {
	
	@Resource
	private TripElementMapper tripElementMapper;
	@Resource
	private UnfinishedTripElementMapper unfinishedTripElementMapper;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<UnfinishedTripElement> selectByPage(Map map) {
		return unfinishedTripElementMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return unfinishedTripElementMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public UnfinishedTripElement selectByPrimaryKey(String id) {
		return unfinishedTripElementMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(UnfinishedTripElement bean) {
		return unfinishedTripElementMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(UnfinishedTripElement bean) {
		return unfinishedTripElementMapper.insertSelective(bean);
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		UnfinishedTripElement bean = unfinishedTripElementMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return unfinishedTripElementMapper.updateByPrimaryKey(bean);
	}
	
	@Override
	public int batchInsert(List<String> nameList) {
		String shopName = "供应商D";
		int relationStatus = 0;
		int unRelationStatus = 1;
		//要素
		List<String> elementNameList = tripElementMapper.selectAll();
		//待完善旅游要素
		List<String> relationNameList = unfinishedTripElementMapper.selectByStatus(relationStatus);
		//未关联旅游要素
		List<UnfinishedTripElement> unRelationNameList = unfinishedTripElementMapper.selectObjByStatus(unRelationStatus);
		//新增集合
		List<UnfinishedTripElement> insertList = new ArrayList<UnfinishedTripElement>();
		//更新集合
		List<UnfinishedTripElement> updateList = new ArrayList<UnfinishedTripElement>();
		elementNameList.addAll(relationNameList);
		//需要处理的要素
		nameList.removeAll(elementNameList);
		List<String> removeList = new ArrayList<String>();
		String source="";
		//添加更新数据
		for(String name:nameList){
			for(UnfinishedTripElement bean :unRelationNameList){
				if(name.equals(bean.getName())){
					bean.setFrequency(bean.getFrequency()+1);
					if(bean.getSource().indexOf(shopName)<0){
						source = StringUtils.isEmpty(bean.getSource())?shopName:bean.getSource()+","+shopName;
						bean.setSource(source);
					}
					removeList.add(name);
					updateList.add(bean);
					continue;
				}
			}
		}
		nameList.removeAll(removeList);
		//添加新增数据
		for(String name :nameList){
			UnfinishedTripElement bean = new UnfinishedTripElement();
			bean.setId(UUID.randomUUID().toString());
			bean.setCreateTime(new Date());
			bean.setFrequency(1);
			bean.setName(name);
			bean.setStatus(unRelationStatus);
			bean.setSource(shopName);
			insertList.add(bean);
		}
		if(insertList.size()>0){
			unfinishedTripElementMapper.batchInsert(insertList);
		}
		if(updateList.size()>0){
			unfinishedTripElementMapper.batchUpdate(updateList);
		}
		return 0;
	}

	@Override
	@Transactional
	public int reletion(String id, String tripElementId) {
		int relationStatus = 0;
		UnfinishedTripElement rule = unfinishedTripElementMapper.selectByPrimaryKey(id);
		TripElement bean=  tripElementMapper.selectByPrimaryKey(tripElementId);
		rule.setTripElementId(tripElementId);
		rule.setStatus(relationStatus);
		unfinishedTripElementMapper.updateByPrimaryKey(rule);
		String otherName = bean.getOtherName();
		if(StringUtils.isEmpty(otherName)){
			bean.setOtherName("/"+rule.getName()+"/");
		}else{
			bean.setOtherName(otherName+",/"+rule.getName()+"/");
		}
		bean.setUpdateTime(new Date());
		int result = tripElementMapper.updateByPrimaryKey(bean);
		return result;
	}
	
}
