package com.zzk.dao;

import com.zzk.entity.Hotel;

import java.util.List;
import java.util.Map;


public interface HotelMapper {
    int deleteByPrimaryKey(String id);

    int insert(Hotel record);

    int insertSelective(Hotel record);

    Hotel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Hotel record);

    int updateByPrimaryKey(Hotel record);
    
    List<Hotel> selectByPage(Map map);
	
    int selectCount(Map record);
    
    Hotel selectByName(String name);
    
    List<Hotel> selectList();
    
   String selectCity(String cityId);
   
   String selectProvince(String provinceId);
   
   String selectArea(String areaId);
   
   List<Map<String,Object>> getProvinceList();
   
   List<Map<String,Object>> getCityList(String provinceId);
   
   List<Map<String,Object>> getAreaList(String cityId);
   
   List<Hotel> checkOwnerId(String id);
   
   List<Hotel> selectHotels(Map map);
   
   String selectById(String id);
}