/**   
* @Description:  
* @author sty   
* @date 2017年3月21日 下午2:47:28 
* @version V1.0   
*/
package com.zzk.service;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zzk.util.DateUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.dao.HotelMapper;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.RoomType;
import com.zzk.entity.SwitchRule;
import com.zzk.service.RoomTypeService;
import com.zzk.service.SearchService;
import com.zzk.service.SwitchRuleService;

@Service("searchService")
public class SearchServiceImpl implements SearchService{
	
	@Resource
	private EsUtils esUtils;
	@Resource
	private HotelMapper hotelMapper;
	@Autowired
	private SwitchRuleService switchRuleService;
	@Autowired
	private RoomTypeService roomTypeService;


	@Override
	public int search(String type,Map<String,Object> parameters,String starLevel,Double lat,Double lon,int start,int size, Page page,Integer miniPrice,Integer maxPrice,String checkinDate,String leaveDate ) throws UnknownHostException{
	    Client client = esUtils.getEsClient();
	    //基础查询条件
		BoolQueryBuilder baseInfoQuery = QueryBuilders.boolQuery();
		
		//按参数查询
		if(parameters.size()>0){
			BoolQueryBuilder baseInfoShouldQuery = QueryBuilders.boolQuery();
			for (Map.Entry<String,Object> entry : parameters.entrySet()) {
				baseInfoShouldQuery.should(QueryBuilders.matchQuery(entry.getKey().toString(), entry.getValue()).operator(Operator.AND));
			}
			
			baseInfoQuery.must(baseInfoShouldQuery);
		}
		
		if(!StringUtils.isEmpty(starLevel)){
		baseInfoQuery.must(QueryBuilders.matchQuery("starLevel", starLevel).operator(Operator.AND));
		}
		/*if(miniPrice!=null&&maxPrice!=null){
			baseInfoQuery.must(QueryBuilders.rangeQuery("maxPrice").gte(maxPrice));
			baseInfoQuery.must(QueryBuilders.rangeQuery("miniPrice").lte(miniPrice));
		}*/
		System.err.println(JSON.toJSON(baseInfoQuery));
		//总条件
		BoolQueryBuilder query = QueryBuilders.boolQuery().must(baseInfoQuery);
		System.err.println(JSON.toJSON(query));
		/*//扩展属性条件
		BoolQueryBuilder attributeBaseQuery = QueryBuilders.boolQuery();
		if(attributes!=null&&attributes.size()>0){
			for (Map.Entry<String,Object> entry : attributes.entrySet()) {
				attributeBaseQuery.must(QueryBuilders.matchQuery("sellerGoodsSkuInfoList.attributes."+entry.getKey().toString(), entry.getValue()));
			}
			QueryBuilder attributeQuery = QueryBuilders.nestedQuery("sellerGoodsSkuInfoList.attributes", attributeBaseQuery);
			query.must(attributeQuery);
		}*/
		//搜索
		SearchRequestBuilder searchBuilder = client.prepareSearch(esUtils.getINDEX_NAME()).setTypes(type).setQuery(query).setFrom(start).setSize(size);
		//排序
		/*if(!StringUtils.isEmpty(sortFiled)&&sortType!=0){
			SortBuilder sort = SortBuilders.fieldSort(sortFiled);
			if(sortType==1){
				sort.order(SortOrder.ASC);
			}else{
				sort.order(SortOrder.DESC);
			}
			searchBuilder.addSort(sort);
		}*/
		GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort("location");
		sort.unit(DistanceUnit.MILES);
		sort.order(SortOrder.ASC);
		sort.point(lat, lon);
		//搜索
		searchBuilder.addSort(sort);
		SearchResponse searchResponse = searchBuilder.execute().actionGet();		
		SearchHits shs = searchResponse.getHits();
		
		List<Hotel> baseList = new ArrayList<Hotel>();
		for(SearchHit hit : shs){/*
			String distance = (String) hit.getSource().get("geoDistance");*/
			BigDecimal geoDis=new BigDecimal((double)hit.getSortValues()[0]).setScale(2,BigDecimal.ROUND_HALF_UP);
			Hotel hotel = JSON.parseObject(hit.getSourceAsString(), Hotel.class);
			hotel.setGoodsList(null);
			hotel.setDistance(geoDis);
			if(miniPrice!=null&&maxPrice!=null){
				String datePrice = hotel.getDatePrice();
				if(StringUtils.isNotBlank(datePrice)){
					double[] aMinMaxPrice = computeAveragePrice(datePrice,checkinDate,leaveDate);
					hotel.setMiniPrice(aMinMaxPrice[0]);
					hotel.setMaxPrice(aMinMaxPrice[1]);
					if(hotel.getMiniPrice()>0.001&&hotel.getMaxPrice()>0.001){
						if(!(hotel.getMiniPrice()>maxPrice||hotel.getMaxPrice()<miniPrice)){
							System.err.println(hotel.getName());
							System.err.println(aMinMaxPrice[0]);
							System.err.println(aMinMaxPrice[1]);
							baseList.add(hotel);
						}
					}
				}
			}else{
				baseList.add(hotel);
			}
		}
		/*Collections.sort(baseList,new Comparator<Hotel>(){
			public int compare(Hotel o1, Hotel o2) {  
	            if(Double.parseDouble(o1.getPrice()) > Double.parseDouble(o2.getPrice())){  
                    return 1;  
                }  
                if(Double.parseDouble(o1.getPrice()) == Double.parseDouble(o2.getPrice())){  
                    return 0;  
                }  
                return -1; 
            }
		});*/
		
		page.setList(baseList);
		page.setFullListSize((int)shs.getTotalHits());
		return 1;
	}


	@Override
	public int search1(Map<String, Object> parameters, String starLevel,
			Double lat, Double lon, int start, int size, Page page,
			Integer miniPrice, Integer maxPrice,Date checkinDate,Date leaveDate) throws Exception{
		parameters.put("starLevel", starLevel);
		parameters.put("miniPrice", miniPrice);
		parameters.put("maxPrice", maxPrice);
		parameters.put("lat", lat);
		parameters.put("lon", lon);
		parameters.put("start", start);
		parameters.put("size", size);
		parameters.put("startRow",start);
		parameters.put("pageSize",size);
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
		parameters.put("checkinDate", sdf.format(checkinDate));
		parameters.put("leaveDate", sdf.format(leaveDate));
		List<Hotel> baseList = hotelMapper.selectHotels(parameters);
		List<Hotel> removeList = new ArrayList<Hotel>();
		String date ="";
		int  flag = 0;
		List<String> dateList = new ArrayList<String>();
		List<String> dateListTemp = new ArrayList<String>();
		int isOpen = 0;
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek1=0;
		for(Hotel bean:baseList){
			System.err.println(JSON.toJSON(bean));
			if(bean.getMiniPrice()==null){
				removeList.add(bean);
			}
		}
		baseList.removeAll(removeList);
		System.err.println(removeList.size());
		//过滤全部关房的酒店（逻辑有问题）
		List<String> idList = new ArrayList<String>();
		for(Hotel bean:baseList){
			idList.add(bean.getId());
		}
		System.err.println(idList.size());
		List<SwitchRule> switchRuleList = switchRuleService.selectByHotelsId(idList, sdf.format(checkinDate),sdf.format(leaveDate));
		List<RoomType> roomTypeList = roomTypeService.selectByHotelsId(idList);
		int days=DateUtils.daysBetween(DateUtils.parseDate(sdf.format(checkinDate), DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD),DateUtils.parseDate(sdf.format(leaveDate), DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD));
		for(int i=0;i<days;i++){
			date = DateUtils.getAfterDay(sdf.format(checkinDate), i, DateUtils.NOSYMBOL_DATETIME_FORMAT_YMD);
			dateListTemp.add(date);
		}
		for(Hotel hotel :baseList){
			dateList.addAll(dateListTemp);
			Iterator<String> it = dateList.iterator(); 
			loop: while (it.hasNext()) { 
				 String date1  = it.next();
				 for(RoomType room:roomTypeList){
					 
					 if(hotel.getId().equals(room.getHotelId())){
						 //某酒店下所有房型的循环
						 flag = 0;
						 for(SwitchRule sr :switchRuleList){
							 isOpen =sr.getIsOpen();
							 if(room.getId().equals(sr.getRoomtypeId())){
								 //进了规则循环的标志
								 flag = 1; 
								 if(DateUtils.compareDate(sr.getStartTime(), sdf.parse(date1))<=0&&DateUtils.compareDate(sr.getEndTime(), sdf.parse(date1))>=0){
							    	 if(sr.getType()==2){
							    		 calendar.setTime(sdf.parse(date1));
							    		 dayOfWeek1 = calendar.get(7)-1;
										if(dayOfWeek1==0){
											dayOfWeek1=7;
										}
										if(String.valueOf(sr.getWeekStart()).indexOf(String.valueOf(dayOfWeek1))>=0){
											//如果不等于2，说明当天有房间是开的
											if(isOpen != 2){
												removeList.remove(hotel);
												continue loop;
											}
										}
							    	 }else{
							    		 removeList.remove(hotel);
							    		 continue loop;
									 }
							    }
							}
						}
						 
					 	if(flag==0){
					 		removeList.remove(hotel);
							continue loop; 
						 }else{
							 //进了规则循环也没跳出去的
							 removeList.add(hotel); 
							 //flag = 0;
						 }
					 } 	
				}
			}
		}
		baseList.removeAll(removeList);
		page.setList(baseList);
		
		return 1;
	}

	private double[] computeAveragePrice(String datePrice,String checkDate,String leaveDate){
		int start = datePrice.indexOf(checkDate);
		int end = datePrice.indexOf("//"+leaveDate);
		if(start==-1||end ==-1){
			return new double[]{0.0,0.0};
		}
		datePrice = datePrice.substring(start, end);
		String[] aDatePrice =datePrice.split("//");
		Double minAvg = 0.0;
		Double maxAvg = 0.0;
		for(String str :aDatePrice){
			double min = Double.valueOf(str.substring(str.indexOf(":")+1, str.indexOf(",")));
			double max = Double.valueOf(str.substring(str.indexOf(",")+1, str.length()));
			if(min<0.001||max<0.001){
				return new double[]{0.0,0.0};
			}
			minAvg += min;
			maxAvg += max;
		}
		minAvg = minAvg/aDatePrice.length;
		maxAvg = maxAvg/aDatePrice.length;
		minAvg = new BigDecimal(minAvg).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		maxAvg = new BigDecimal(maxAvg).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return new double[]{minAvg,maxAvg};
	};
}
