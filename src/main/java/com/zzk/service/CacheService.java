package com.zzk.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zzk.dao.DictMapper;
import com.zzk.entity.Dict;

@Component
public class CacheService {
	@Autowired
	private DictMapper dictMapper;
	
	private static List<Dict> dictList;
	private static CacheService cacheService;
	
	@PostConstruct
	private void init(){
		cacheService = this;
		cacheService.dictMapper = this.dictMapper;
		dictList = cacheService.dictMapper.findAllDict();
		System.out.println("缓存查询成功");
	}
	
	public static String getLabel(String type,String code) {
		for(Dict dict:dictList){
			if(type.equals(dict.getType())&&code.equals(dict.getValue())){
				return dict.getLabel();
			}
		}
		return "";
	}
	
	public static String getValue(String type,String name) {
		for(Dict dict:dictList){
			if(type.equals(dict.getType())&&name.equals(dict.getLabel())){
				return dict.getValue();
			}
		}
		return "";
	}
	
	public static String getShotName(String type,String code){
		for(Dict dict:dictList){
			if(type.equals(dict.getType())&&code.equals(dict.getValue())){
				return dict.getDescription();
			}
		}
		return "";
	}
	
	public static List<Dict> getAllDict() {
		return dictList;
	}
	
	public static void update(){
		dictList = cacheService.dictMapper.findAllDict();
	}
}
