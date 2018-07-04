/**   
* @Description:  
* @author sty   
* @date 2017年3月20日 下午7:36:02 
* @version V1.0   
*/
package com.zzk.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.deletebyquery.*;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.util.StringUtils;
import com.zzk.entity.Dict;
import com.zzk.entity.Hotel;
import com.zzk.entity.HotelGoods;
import com.zzk.entity.HotelServe;
import com.zzk.entity.PriceRule;
import com.zzk.service.CreateIndexService;
import com.zzk.service.DictService;
import com.zzk.service.HotelGoodsService;
import com.zzk.service.HotelServeService;
import com.zzk.service.HotelService;
import com.zzk.service.PriceRuleService;

@Service("createIndexService")
public class CreateIndexServiceImpl implements CreateIndexService{

	@Resource
	private EsUtils esUtils;

	@Value("#{100}")
	public int size;
	
	@Resource
	private HotelService hotelService;
	
	@Resource
	private HotelGoodsService hotelGoodsService;
	
	@Resource
	private PriceRuleService priceRuleService;
	
	@Resource
	private HotelServeService hotelServeService;
	
	@Autowired
	private DictService dictService;
	
	private static List<Dict> dictList;
	
	@Override
	public void initSearchEngine() throws Exception{
		Client client =  esUtils.getEsClient();
		//首先创建index
		CreateIndexResponse createIndexResponse = client.admin().indices()
			.prepareCreate(esUtils.getINDEX_NAME()).execute().actionGet();
		
		System.out.println("createIndexResponse="+createIndexResponse.isAcknowledged());
		
		//创建商品Mapping
		PutMappingRequestBuilder mappingRequest = client.admin().indices().preparePutMapping(esUtils.getINDEX_NAME())
				.setType(esUtils.getTYPE_HOTELS()).setSource(createHotelModelMapping());
		
		PutMappingResponse putMappingResponse = mappingRequest.execute().actionGet();
		System.out.println("putMappingResponse="+putMappingResponse.isAcknowledged());
		
		
		
	}

	/**
	 * <p>description:酒店model 字段创建（酒店下 包含单品商品集合，（商品下包含价格规则List、库存规则List））</p>
	 * @param 
	 * @return XContentBuilder
	 * @date 2017-7-6上午11:08:57
	 */
	private XContentBuilder createHotelModelMapping() throws Exception{
		XContentBuilder mapping = XContentFactory.jsonBuilder()
			.startObject()
				.startObject(esUtils.getTYPE_HOTELS())
				
						.startObject("properties")
						.startObject("location")
							.field("type", "geo_point")
						.endObject()
						.startObject("goodsList")
								 .field("type", "nested")
								 .startObject("properties")
											.startObject("priceruleList")
													.field("type", "nested")
													.startObject("properties")
													.endObject()
											.endObject()
											
											.startObject("stockruleList")
													.field("type", "nested")
													.startObject("properties")
													.endObject()
											.endObject()
							 	 .endObject()	
						.endObject()
						
					.endObject()
					
				.endObject()
			.endObject();		
		return mapping;
	}
	
	
	@Override
	public int createAllHotelIndex() throws Exception {
        Client client = esUtils.getEsClient();
        //删除type products下所有数据
        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
        DeleteByQueryResponse response =  new DeleteByQueryRequestBuilder(client,   
                DeleteByQueryAction.INSTANCE).setIndices(esUtils.INDEX_NAME)
                .setTypes(esUtils.getTYPE_HOTELS())
                .setQuery(query)
                .execute()
                .actionGet();   
        response.getShardFailures();
        //查询商品数量
        int count = hotelService.selectCount(new HashMap<>());
        
        int number = count/size + 1;
        System.out.println("  count  : "+count+"   size:"+size+"   number:"+number);
        
        int result = 1;
        for(int i=0;i<number;i++){
        	int startRow = i*size;
        	Map map = new HashMap();
    		map.put("startRow", startRow);
    		//每次处理100条数据
    		map.put("pageSize", size);
    		List<Hotel> baseList = hotelService.selectByPage(map);
    		int code = processInsert(baseList,client);
    		if(code<0){
    			result = code;
    		}
        }
		return result;
	}
	
/*	public int createIndexByProductId(String productId) throws UnknownHostException{
		Client client = esUtils.getEsClient();
		//查询一条商品
		SellerProductsBaseInfo base = sellerProductsBaseInfoMapper.selectByPrimaryKey(productId);
		List<SellerProductsBaseInfo> baseList = new ArrayList<SellerProductsBaseInfo>();
		baseList.add(base);
		int code = processInsert(baseList,client);
		return code;
	}*/
	/**
	 * <p>description:保存商品数据</p>
	 * @param 
	 * @return int
	 * @throws IOException 
	 * @date 2017-7-6上午11:41:23
	 */
	private int processInsert(List<Hotel> baseList,Client client) throws IOException{
		//酒店ID
		List<String> BaseIds = new ArrayList<String>();
		//商品ID
		List<String> goodIds = new ArrayList<String>();
		//酒店map
		Map<String,Hotel> baseMap = new HashMap<String,Hotel>();
		//商品map
		Map<String,HotelGoods> goodmap = new HashMap<String,HotelGoods>();
		
		for(int m=0;m<baseList.size();m++){
			BaseIds.add(baseList.get(m).getId());
			baseMap.put(baseList.get(m).getId(), baseList.get(m));
		}
		if(BaseIds.size()>0){
			//组装产品属性
			//组装 酒店商品信息
			List<HotelGoods> goods = hotelGoodsService.selectHotelGoodsByHotelIds(BaseIds);
			for(int k=0;k<goods.size();k++){
				HotelGoods good = goods.get(k);
				goodmap.put(good.getId(), good);
				goodIds.add(good.getId());
			}
			
			//组装酒店服务
			List<HotelServe> serveList = hotelServeService.selectByHotelIds(BaseIds);
			
			
			for(int i=0;i<serveList.size();i++){
				List<String> icoList = new ArrayList<String>();
				List<String> serviceList = new ArrayList<String>();
				HotelServe hotelServe = serveList.get(i);
				
				String icos= hotelServe.getIcos();
		        if(StringUtils.isNotBlank(icos)){
		        	String[] aIcos= icos.split(",");
		        	for(String ico:aIcos){
		        		icoList.add(ico);
		        	}
		        }
		        hotelServe.setIcoList(icoList);
		        hotelServe.setAdslStr(CacheService.getLabel("adsl",String.valueOf(hotelServe.getAdsl())));
		        hotelServe.setAirConditionerStr(CacheService.getLabel("airConditioner",String.valueOf(hotelServe.getAirConditioner())));
		        hotelServe.setBathroomStr(CacheService.getLabel("bathroom",String.valueOf(hotelServe.getBathroom())));
		        hotelServe.setElevatorStr(CacheService.getLabel("elevator",String.valueOf(hotelServe.getElevator())));
		        hotelServe.setFreeDrinkingWaterStr(CacheService.getLabel("freeDrinkingWater",String.valueOf(hotelServe.getFreeDrinkingWater())));
		        hotelServe.setFreeWifiStr(CacheService.getLabel("freeWifi",String.valueOf(hotelServe.getFreeWifi())));
		        hotelServe.setHotWaterStr(CacheService.getLabel("hotWater",String.valueOf(hotelServe.getHotWater())));
		        hotelServe.setParkStr(CacheService.getLabel("park",String.valueOf(hotelServe.getPark())));
		        if(!StringUtils.isEmpty(hotelServe.getService())){
		        	String[] aService= hotelServe.getService().split(",");
		            for(String str:aService){
		            	str = CacheService.getLabel("hotelServe",str);
		            	serviceList.add(str);
		            }
		            hotelServe.setServiceList(serviceList);
		        }
		        
		        
				baseMap.get(hotelServe.getHotelId()).setHotelServe(hotelServe);
			}
			
			//组装价格规则
			List<PriceRule> rules = priceRuleService.selectRuleByGoodsList(goodIds);
			for(int k=0;k<rules.size();k++){
				PriceRule rule = rules.get(k);
				if(goodmap.get(rule.getGoodId()).getPriceruleList()==null){
					List<PriceRule> attList = new ArrayList<PriceRule>();
					attList.add(rule);
					goodmap.get(rule.getGoodId()).setPriceruleList(attList);
				}else{
					goodmap.get(rule.getGoodId()).getPriceruleList().add(rule);
				}
				
			}
			//组装商品进入酒店
			for (Map.Entry<String,HotelGoods> entry : goodmap.entrySet()) {
				HotelGoods good = entry.getValue();
				if(good.getHotelId()!=null&&baseMap.get(good.getHotelId()).getGoodsList()==null){
					List<HotelGoods> goodList = new ArrayList<HotelGoods>();
					goodList.add(good);
					baseMap.get(good.getHotelId()).setGoodsList(goodList);
				}else{
					baseMap.get(good.getHotelId()).getGoodsList().add(good);
				}
			}

			System.out.println(" baseMap:"+JSON.toJSONString(baseMap));
			//批量处理
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for (Map.Entry<String,Hotel> entry : baseMap.entrySet()) {  
				Hotel hotel = entry.getValue();
				if(hotel.getGoodsList()!=null&&hotel.getGoodsList().size()>0){
					String jsonString = JSON.toJSONString(hotel);
					//System.out.println(" "+jsonString);
					//装入商品数据
					JSONObject jsonObject  = (JSONObject) JSON.toJSON(hotel);
					JSONObject subNode = new JSONObject();
					subNode.put("lat", hotel.getLat());
					subNode.put("lon", hotel.getLon());
					jsonObject.put("location", subNode);
					bulkRequest.add(client.prepareIndex(esUtils.getINDEX_NAME(), esUtils.getTYPE_HOTELS(), hotel.getId()).setSource(jsonObject.toJSONString()));
				}
			}
			
			System.out.println(" *********************:"+bulkRequest.numberOfActions());
			if(bulkRequest.numberOfActions()>0){
				//批量处理
				BulkResponse bulkResponse = bulkRequest.get();
				if (bulkResponse.hasFailures()) {
				    // process failures by iterating through each bulk response item
					return -1;
				}
			}
		}
		return 1;
	}
	
	@Override
	public int createIndexByHotelId(String hotelId) throws Exception {
        Client client = esUtils.getEsClient();
        //查询商品数量
        int result = 1;
		List<Hotel> baseList = new ArrayList<Hotel>();
		baseList.add(hotelService.selectByPrimaryKey(hotelId));
		int code = processInsert(baseList,client);
		if(code<0){
			result = code;
		}
		return result;
	}
	
	

}
