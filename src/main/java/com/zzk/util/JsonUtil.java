package com.zzk.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.collections.map.ListOrderedMap;

public class JsonUtil {

	/**
	 * 
	 * json转换list. <br>
	 * 详细说明
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @return
	 * @return List<Map<String,Object>> list
	 * @throws @author slj
	 * @date 2013年12月24日 下午1:08:03
	 */
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * 
	 * json转换map. <br>
	 * 详细说明
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @return
	 * @return Map<String,Object> 集合
	 * @throws @author slj
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		ListOrderedMap map = new ListOrderedMap();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * 
	 * 通过HTTP获取JSON数据. <br>
	 * 通过HTTP获取JSON数据返回list
	 * 
	 * @param url
	 *            链接
	 * @return
	 * @return List<Map<String,Object>> list
	 * @throws @author slj
	 */
	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2List(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 通过HTTP获取JSON数据. <br>
	 * 通过HTTP获取JSON数据返回map
	 * 
	 * @param url
	 *            链接
	 * @return
	 * @return Map<String,Object> 集合
	 * @throws @author slj
	 */
	public static Map<String, Object> getMapByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2Map(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * map转换json. <br>
	 * 详细说明
	 * 
	 * @param map
	 *            集合
	 * @return
	 * @return String json字符串
	 * @throws @author slj
	 */
	public static String mapToJson(Map<String, String> map) {
		Set<String> keys = map.keySet();
		String key = "";
		String value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key);
			jsonBuffer.append(key + ":" + "\"" + value + "\"");
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}

	/**
	 * 
	 * map转换json. <br>
	 * 详细说明
	 * 
	 * @param map
	 *            集合
	 * @return
	 * @return String json字符串
	 * @throws @author slj
	 */
	public static String mapToJson2(Map<String, Object> map) {
		Set<String> keys = map.keySet();
		String key = "";
		String value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key).toString();
			jsonBuffer.append(key + ":" + "\"" + value + "\"");
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}

	public static JSONObject toJsonObject(Map map) {
		JsonConfig config = new JsonConfig();
		/*config.registerJsonValueProcessor(Timestamp.class, new TimestampProcessor("yyyy-MM-dd HH:mm:ss"));*/
		JSONObject resJson = JSONObject.fromObject(map, config);
		return resJson;
	}

	public static JSONArray toJsonArray(List list) {
		JsonConfig config = new JsonConfig();
		/*config.registerJsonValueProcessor(Timestamp.class, new TimestampProcessor("yyyy-MM-dd HH:mm:ss"));*/
		JSONArray resJson = JSONArray.fromObject(list, config);
		return resJson;
	}

	// /**
	// * 转json
	// *
	// * @param state
	// * @param msg
	// * @param lists
	// * @return
	// */
	// public static String turnJson(Boolean state, String msg, Object o) {
	// JSONObject obj = new JSONObject();
	// obj.put("state", state);
	// obj.put("msg", msg);
	// obj.put("result", o);
	// String result = obj.toString();
	// return result;
	// }

	/**
	 * 转json
	 * 
	 * @param state
	 * @param msg
	 * @return
	 */
	public static String turnJson(Boolean state, String msg, Object o) {
		JSONObject obj = new JSONObject();
		obj.put("state", state);
		obj.put("msg", msg);
		if (o instanceof List) {
			o = toJsonArray((List) o);
		} else if (o instanceof Map) {
			o = toJsonObject((Map) o);
		} else if (o == null) {
			o = "";
		} else {
			o = o.toString();
		}
		obj.put("result", o);
		String result = obj.toString();
		return result;
	}

	public static String turnJson(Boolean state, String msg,int totalPage, Object o) {
		JSONObject obj = new JSONObject();
		obj.put("state", state);
		obj.put("msg", msg);
		obj.put("total",totalPage);
		if (o instanceof List) {
			o = toJsonArray((List) o);
		} else if (o instanceof Map) {
			o = toJsonObject((Map) o);
		} else if (o == null) {
			o = "";
		} else {
			o = o.toString();
		}
		obj.put("result", o);
		String result = obj.toString();
		return result;
	}


	/**
	 * 转json
	 *
	 * @param state
	 * @param msg
	 * @return
	 */
	public static String turnToJson(Boolean state, String msg, Object o) {
		JSONObject obj = new JSONObject();
		obj.put("state", state);
		obj.put("msg", msg);
		if (o instanceof List) {
			o = toJsonArray((List) o);
		} else if (o instanceof Map) {
			o = toJsonObject((Map) o);
		} else if (o != null) {
			o = o.toString();
		} else {
			o = "";
		}
		obj.put("result", o);
		String result = obj.toString();
		return result;
	}

	/**
	 * 转json
	 * 
	 * @param model
	 * @return
	 */
/*	public static String turnJson(RequestReturnModel model) {
		JSONObject obj = new JSONObject();
		if (model.result != null) {
			obj = JSONObject.fromObject(model.result);
		}
		String result = turnJson(model.state, model.msg, obj);
		return result;
	}*/


	// test
	public static void main(String[] args) {
		String url = "http://...";
		List<Map<String, Object>> list = getListByUrl(url);
		System.out.println(list);
	}
	/**
	 * 转json
	 *
	 * @param state
	 * @param msg
	 * @param lists
	 * @return
	 */
	public static String turnJsonWithTotal(Boolean state, String msg,Integer total, Object o) {
		JSONObject obj = new JSONObject();
		obj.put("state", state);
		obj.put("total", total);
		obj.put("msg", msg);
		if(o instanceof List){
			o = toJsonArray((List)o);
		}else if(o instanceof Map){
			o = toJsonObject((Map)o);
		}else{
			o = o.toString();
		}
		obj.put("result", o);
		String result = obj.toString();
		return result;
	}

}