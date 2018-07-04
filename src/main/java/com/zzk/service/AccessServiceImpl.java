package com.zzk.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.zshlpay.common.util.DateUtil;

import com.zzk.dao.AccessMapper;
import com.zzk.entity.Access;
import com.zzk.service.AccessService;
import com.zzk.util.DateUtils;

@Service("accessService")
public class AccessServiceImpl implements AccessService {
	@Resource 
	private AccessMapper accessMapper;

	@Override
	public int insertSelective(Access record) {
		return accessMapper.insertSelective(record);
	}
	@Override
	public List selectTotalNumber(Map map) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		List numList = new ArrayList();
		List<Map<String,Object>>  listMaps=  accessMapper.selectTotalNumber(map);
		List<String> dateList = new ArrayList<String>();
		String date = "";
		String startDate = DateUtils.getAfterDay(sdf.format(currentTime), -6, "yyyy-MM-dd");
		for(int i=0;i<=6;i++){
			date = DateUtils.getAfterDay(startDate, i, "yyyy-MM-dd");
			dateList.add(date);
		}
		loop:for(String d:dateList){
			for(Map<String,Object> m :listMaps){
				if(d.equals(m.get("date"))){
					numList.add( m.get("amount"));
					continue loop;
				}
			}
			numList.add(0);
		}
		return numList;
	}
	@Override
	public Access selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return accessMapper.selectByPrimaryKey(id);

	}
	
	

	

}
