package com.zzk.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.math.NumberUtils;


/**
 * Map处理
* @author lishiqiang
* @date 2017-3-15
* modify history
 */
public class MapUtils {
	private static final DecimalFormat format = new DecimalFormat("#.##");
	/**
	 * 格式化为0.00
	 */
	private static final DecimalFormat formatter_0_00 = new DecimalFormat(
			"#.##");
	/**
	 * 格式化为整数
	 */
	private static final DecimalFormat formatter_0 = new DecimalFormat("#");
	/**
	 * 显示为整数
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getInteger(Map dataMap, String key, String defaultVal) {
		Object val = dataMap.get(key);
		if (val == null) {
			return defaultVal;
		}
		if (val instanceof Number) {
			Number n = (Number) val;
			return formatter_0.format(n.doubleValue());
		} else {
			String str = val.toString();
			if (StringUtils.isBlank(str) || NumberUtils.isNumber(str) == false) {
				return defaultVal;
			}
			double d = NumberUtils.toDouble(str, 0);
			return formatter_0.format(d);
		}
	}

	/**
	 * 显示为整数
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getInteger(Map dataMap, String key) {
		return getInteger(dataMap, key, "0");
	}

	

	
	
	/**
	 * 显示为两位小数
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getNumeric(Map dataMap,String key, String defaultVal) {
		if(dataMap==null){
			return defaultVal;
		}
		Object val = dataMap.get(key);
		if (val == null) {
			return defaultVal;
		}
		if (val instanceof Number) {
			Number n = (Number) val;
			return formatter_0_00.format(((Number) val).doubleValue());
		} else {
			String str = val.toString();
			if (StringUtils.isBlank(str) || NumberUtils.isNumber(str) == false) {
				return defaultVal;
			}
			double d = NumberUtils.toDouble(str, 0);
			return formatter_0_00.format(d);
		}
	}

	/**
	 * 显示为两位小数
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static String getNumeric(Map dataMap,String key) {
		return getNumeric(dataMap,key, "0.00");
	}
	public static String getString(Map map, String key, String def) {
		if (map == null) {
			return def;
		}
		Object val = map.get(key);
		if (val == null) {
			return def;
		}
		return val.toString();
	}

	public static String getString(Map map, String key) {
		return getString(map, key, "");
	}

	public static String getIntString(Map map, String key, String def) {
		String val = getString(map, key, def);
		int num = NumberUtils.toInt(val);
		return String.valueOf(num);
	}
	public static int getInt(Map map, String key) {
		String val = getString(map, key, "0");
		int num = NumberUtils.toInt(val);
		return num;
	}

	public static String getNumberString(Map map, String key, String def) {
		String val = getString(map, key, def);
		double num = NumberUtils.toDouble(val);
		return String.valueOf(num);
	}

	/**
	 * 获取格式化的数值
	 * 
	 * @param map
	 * @param key
	 * @param def
	 * @return
	 */
	public static String getNumber2(Map map, String key, String def) {
		String val = getString(map, key, def);
		double num = NumberUtils.toDouble(val);
		return format.format(num);
	}

	/**
	 * 获取格式化的数值
	 * 
	 * @param map
	 * @param key
	 * @param def
	 * @return
	 */
	public static String getNumber2(Map map, String key) {
		String val = getString(map, key, "0");
		double num = NumberUtils.toDouble(val);
		return format.format(num);
	}
	
	/**
	 * 替换map中为null值的数据
	 * @param map
	 * @param replaceStr
	 */
	public static void clearMapNullVal(Map map,String replaceStr){
		if(map==null){
			return;
		}
		Set<String> keySet = map.keySet();
		for(String s:keySet){
			map.put(s, MapUtils.getString(map, s,""));
		}
	}

	/**
	 * 获取百分比
	 * 
	 * @param map
	 * @param target
	 * @param keys
	 * @return
	 */
	public static String getPercent(Map map, String target, String keys) {
		String key[] = keys.split(",");
		BigDecimal total = new BigDecimal(0);
		for (String s : key) {
			Object val = map.get(s);
			String num = String.valueOf(val);
			if (NumberUtils.isNumber(num)) {
				total = total.add(new BigDecimal(num));
			}
		}
		Object val = map.get(target);
		String num = String.valueOf(val);
		if (NumberUtils.isNumber(num)) {
			BigDecimal t = new BigDecimal(num);
			if (total.compareTo(new BigDecimal("0")) == 0) {
				return "-";
			}
			BigDecimal bi = t.multiply(new BigDecimal("100")).divide(total,
					MathContext.DECIMAL32);
			return format.format(bi) + "%";
		}
		return "-";
	}

	/**
	 * 获取百分比
	 * 
	 * @param map
	 * @param target
	 * @param keys
	 * @return
	 */
	public static String getPercent(Map map1, String target, Map map2,
			String keys) {
		if (map1 == null || map2 == null)
			return "——";
		String key[] = keys.split(",");
		BigDecimal total = new BigDecimal(0);
		for (String s : key) {
			Object val = map2.get(s);
			String num = String.valueOf(val);
			if (NumberUtils.isNumber(num)) {
				total = total.add(new BigDecimal(num));
			}
		}
		Object val = map1.get(target);
		String num = String.valueOf(val);
		if (NumberUtils.isNumber(num)) {
			BigDecimal t = new BigDecimal(num);
			if (total.compareTo(new BigDecimal("0")) == 0) {
				return "——";
			}
			BigDecimal bi = t.multiply(new BigDecimal("100")).divide(total,
					MathContext.DECIMAL32);
			return format.format(bi) + "%";
		}
		return "——";
	}

	/**
	 * 获取增长率百分比
	 * 
	 * @param map
	 * @param target
	 * @param keys
	 * @return
	 */
	public static String getIncreasePercent(Map map, String current, String last) {
		if (map == null)
			return "——";
		Object val = map.get(last);
		String num = String.valueOf(val);
		if (NumberUtils.isNumber(num)) {
			BigDecimal lastDecimal = new BigDecimal(num);
			if (lastDecimal.intValue() == 0 || lastDecimal.doubleValue() < 0) {
				return "——";
			}
			val = map.get(current);
			num = String.valueOf(val);
			if (NumberUtils.isNumber(num)) {
				BigDecimal currentDecimal = new BigDecimal(num);
				BigDecimal percent = currentDecimal.subtract(lastDecimal)
						.multiply(new BigDecimal("100")).divide(lastDecimal,
								MathContext.DECIMAL32);
				String s = format.format(percent);
				return s + "%";
			}
		}
		return "——";
	}

	/**
	 * 获取增长率百分比
	 * 
	 * @param map
	 * @param target
	 * @param keys
	 * @return
	 */
	public static String getIncreasePercent2(Map map, String current,
			String last) {
		if (map == null)
			return "0.00";
		Object val = map.get(last);
		String num = String.valueOf(val);
		if (NumberUtils.isNumber(num)) {
			BigDecimal lastDecimal = new BigDecimal(num);
			if (lastDecimal.intValue() == 0) {
				return "0.00";
			}
			val = map.get(current);
			num = String.valueOf(val);
			if (NumberUtils.isNumber(num)) {
				BigDecimal currentDecimal = new BigDecimal(num);
				BigDecimal percent = currentDecimal.subtract(lastDecimal)
						.multiply(new BigDecimal("100")).divide(lastDecimal,
								MathContext.DECIMAL32);
				String s = format.format(percent);
				return s;
			}
		}
		return "0.00";
	}

	/**
	 * 获取增长率百分比
	 * 
	 * @param map
	 * @param target
	 * @param keys
	 * @return
	 */
	public static String getIncreasePercent(Map map1, String current, Map map2,
			String last) {
		if (map1 == null || map2 == null)
			return "——";
		Object val1 = map1.get(current);
		Object val2 = map2.get(last);
		if (val1 == null || val2 == null) {
			return "——";
		}
		String num1 = val1.toString(), num2 = val2.toString();
		if (NumberUtils.isNumber(num1) && NumberUtils.isNumber(num2)) {
			BigDecimal lastDecimal = new BigDecimal(num2);
			if (lastDecimal.intValue() == 0 || lastDecimal.doubleValue() < 0) {
				return "——";
			}
			if (NumberUtils.isNumber(num1)) {
				BigDecimal currentDecimal = new BigDecimal(num1);
				BigDecimal percent = currentDecimal.subtract(lastDecimal)
						.multiply(new BigDecimal("100")).divide(lastDecimal,
								MathContext.DECIMAL32);
				String s = format.format(percent);
				return s + "%";
			}
		}
		return "——";
	}

	public static double getIncreasePercent(String current1, String current2) {
		if (NumberUtils.isNumber(current1) && NumberUtils.isNumber(current2)
				&& Double.parseDouble(current2) != 0) {
			BigDecimal decimal1 = new BigDecimal(current1);
			BigDecimal decimal2 = new BigDecimal(current2);
			if (decimal2.intValue() == 0) {
				return 0;
			} else {
				BigDecimal percent = decimal1.subtract(decimal2).multiply(
						new BigDecimal("100")).divide(decimal2,
						MathContext.DECIMAL32);
				return percent.doubleValue();
			}
		}
		return 0;
	}

	public static String getIncreasePercent2(String current1, String current2) {
		if (NumberUtils.isNumber(current1) && NumberUtils.isNumber(current2)
				&& Double.parseDouble(current2) != 0) {
			BigDecimal decimal1 = new BigDecimal(current1);
			BigDecimal decimal2 = new BigDecimal(current2);
			if (decimal2.intValue() == 0 || decimal2.doubleValue() < 0) {
				return "——";
			} else {
				BigDecimal percent = decimal1.subtract(decimal2).multiply(
						new BigDecimal("100")).divide(decimal2,
						MathContext.DECIMAL32);
				return format.format(percent.doubleValue()) + "%";
			}
		}
		return "——";
	}

	public static void main(String[] args) {
		System.out.println(MapUtils.getIncreasePercent("23.5", "-230"));
	}

	/**
	 * 计算2个值的总和
	 * 
	 * @param current1
	 * @param current2
	 * @return
	 */
	public static String getTotal(String current1, String current2) {
		if(StringUtils.isBlank(current1)){
			current1 = "0";
		}
		if(StringUtils.isBlank(current2)){
			current2 = "0";
		}
		if (NumberUtils.isNumber(current1) && NumberUtils.isNumber(current2)) {
			BigDecimal decimal1 = new BigDecimal(current1);
			BigDecimal decimal2 = new BigDecimal(current2);
			BigDecimal total = decimal1.add(decimal2);
			return total.toString();
		}
		return "0.00";
	}

	/**
	 * 计算2个值的相除
	 * 
	 * @param current1
	 * @param current2
	 * @return
	 */
	public static String getDivisor(String current1, String current2) {
		if (NumberUtils.isNumber(current1) && NumberUtils.isNumber(current2)
				&& Double.parseDouble(current2) != 0) {
			BigDecimal decimal1 = new BigDecimal(current1);
			BigDecimal decimal2 = new BigDecimal(current2);
			BigDecimal bi = decimal1.divide(decimal2, MathContext.DECIMAL32);
			return format.format(bi);
		}
		return "——";
	}

	/**
	 * 计算2个值的相乘
	 * 
	 * @param current1
	 * @param current2
	 * @return
	 */
	public static String getMultiply(String current1, String current2) {
		if (NumberUtils.isNumber(current1) && NumberUtils.isNumber(current2)) {
			BigDecimal decimal1 = new BigDecimal(current1);
			BigDecimal decimal2 = new BigDecimal(current2);
			BigDecimal bi = decimal1.multiply(decimal2, MathContext.DECIMAL32);
			return format.format(bi);
		}
		return "——";
	}

	/**
	 * 计算2个值的相除
	 * 
	 * @param current1
	 * @param current2
	 * @return
	 */
	public static String getDivisor(Map map, String key, String current2) {
		if (map == null || !NumberUtils.isNumber(current2) && Double.parseDouble(current2) == 0)
			return "——";
		BigDecimal decimal1 = new BigDecimal(0);
		Object val = map.get(key);
		String num = String.valueOf(val);
		if (NumberUtils.isNumber(num)) {
			decimal1 = decimal1.add(new BigDecimal(num));
		} else {
			return "——";
		}

		BigDecimal decimal2 = new BigDecimal(current2);
		BigDecimal bi = decimal1.divide(decimal2, MathContext.DECIMAL32);
		return format.format(bi);
	}

	/**
	 * 计算2个值的相除
	 * 
	 * @param map
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static String getDivisor3(Map map, String key1, String key2) {
		if (map == null)
			return "——";
		BigDecimal decimal1 = new BigDecimal(0);
		BigDecimal decimal2 = new BigDecimal(0);
		Object val1 = map.get(key1);
		Object val2 = map.get(key2);
		String num1 = String.valueOf(val1);
		String num2 = String.valueOf(val2);
		if (NumberUtils.isNumber(num1) && NumberUtils.isNumber(num2)&& Double.parseDouble(num2) != 0) {
			decimal1 = decimal1.add(new BigDecimal(num1));
			decimal2 = decimal2.add(new BigDecimal(num2));
		} else {
			return "——";
		}

		BigDecimal bi = decimal1.divide(decimal2, MathContext.DECIMAL32);
		return format.format(bi);
	}

	public static String getDivisor2(Map map, String keys, String current2) {
		if (map == null || !NumberUtils.isNumber(current2)&& Double.parseDouble(current2) == 0)
			return "——";
		String key[] = keys.split(",");
		BigDecimal decimal1 = new BigDecimal(0);
		for (String s : key) {
			Object val = map.get(s);
			String num = String.valueOf(val);
			if (NumberUtils.isNumber(num)) {
				decimal1 = decimal1.add(new BigDecimal(num));
			}
		}

		BigDecimal decimal2 = new BigDecimal(current2);
		BigDecimal bi = decimal1.divide(decimal2, MathContext.DECIMAL32);
		return format.format(bi);
	}

	/**
	 * Map的值相减
	 * 
	 * @param map1
	 * @param key1
	 * @param map2
	 * @param key2
	 * @return
	 */
	public static String substract(Map map1, String key1, Map map2, String key2) {
		Object val1 = map1.get(key1), val2 = map2.get(key2);
		BigDecimal bi1 = new BigDecimal("0"), bi2 = new BigDecimal("0");
		if (val1 != null) {
			String s1 = val1.toString();
			if (NumberUtils.isNumber(s1)) {
				bi1 = new BigDecimal(s1);
			}
		}
		if (val2 != null) {
			String s2 = val2.toString();
			if (NumberUtils.isNumber(s2)) {
				bi2 = new BigDecimal(s2);
			}
		}
		return bi1.subtract(bi2).toString();
	}

	/**
	 * Map的值相减
	 * 
	 * @param map1
	 * @param map2
	 * @param ignore
	 * @return
	 */
	public static Map substract(Map map1, Map map2, String ignore) {
		Map result = new HashMap();
		Iterator iter = map1.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (StringUtils.contains(ignore, key))
				continue;
			Object val1 = map1.get(key), val2 = map2.get(key);
			BigDecimal bi1 = new BigDecimal("0"), bi2 = new BigDecimal("0");
			if (val1 != null) {
				String s1 = val1.toString();
				if (NumberUtils.isNumber(s1)) {
					bi1 = new BigDecimal(s1);
				}
			}
			if (val2 != null) {
				String s2 = val2.toString();
				if (NumberUtils.isNumber(s2)) {
					bi2 = new BigDecimal(s2);
				}
			}
			result.put(key, bi1.subtract(bi2).toString());
		}
		return result;
	}

	/**
	 * Map内的数值相加
	 * 
	 * @param list
	 * @param ignore
	 * @return
	 */
	public static Map addAll(List list, String ignore) {
		Map result = new HashMap();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Map map = (Map) list.get(i);
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				if (StringUtils.contains(ignore, key))
					continue;
				BigDecimal total = (BigDecimal) result.get(key);
				if (total == null) {
					total = new BigDecimal("0");
				}
				String val = MapUtils.getNumber2(map, key, "0");
				if (NumberUtils.isNumber(val)) {
					total = total.add(new BigDecimal(val));
				}
				result.put(key, total);
			}
		}
		return result;
	}

 
	
	public static String addAll(Map map) {
		BigDecimal bi = new BigDecimal("0");
		Iterator iter = map.values().iterator();
		while (iter.hasNext()) {
			Object val = iter.next();
			if (val == null) {
				continue;
			}
			String valString = val.toString();
			if (NumberUtils.isNumber(valString)) {
				bi = bi.add(new BigDecimal(valString));
			}
		}
		return bi.toString();
	}

	public static Map ignoreMap(Map map, String noignore) {
		Map result = new HashMap();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = StringUtil.getString(iter.next(), "");
			String value = StringUtil.getString(map.get(key), "");
			if (StringUtils.contains(noignore, key)) {
				result.put(key, value);
			} else {
				result.put(key, "——");
			}
		}
		return result;
	}

	/**
	 * 比较相同结构Map里面的数据增长率
	 * 
	 * @param map1
	 * @param map2
	 * @param percent
	 * @param noignore
	 * @param staHtml
	 * @param endHtml
	 * @return
	 */
	public static Map comparPercentMap(Map map1, Map map2, double percent,
			String noignore, String staHtml, String endHtml) {
		Map result = new HashMap();
		Iterator iter = map1.keySet().iterator();
		while (iter.hasNext()) {
			String key = StringUtil.getString(iter.next(), "");
			String value1 = StringUtil.getString(map1.get(key), "");
			String value2 = StringUtil.getString(map2.get(key), "");
			if (StringUtils.contains(noignore, key)
					|| !NumberUtils.isNumber(value1)
					|| !NumberUtils.isNumber(value2))
				result.put(key, value1);
			else {
				double tempPercent = MapUtils
						.getIncreasePercent(value1, value2);
				if (Math.abs(tempPercent) >= percent) {
					result.put(key, staHtml + value1 + endHtml);
				} else {
					result.put(key, value1);
				}
			}
		}
		return result;
	}
	
	/**
	 * 比较结构相同的两个map内的值有无差异
	 * 有差异返回true,无差异返回false
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static boolean comparMap(Map map1, Map map2) {
		Iterator iter = map1.keySet().iterator();
		while (iter.hasNext()) {
			String key = StringUtil.getString(iter.next(), "");
			String value1 = StringUtil.getString(map1.get(key), "");
			String value2 = StringUtil.getString(map2.get(key), "");
			if(!StringUtils.equals(value1, value2)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 比较结构相同的两个map内的值差异
	 * 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static List<String> comparDifMap(Map map1, Map map2) {
		List<String> result = new ArrayList<String>();
		Iterator iter = map1.keySet().iterator();
		while (iter.hasNext()) {
			String key = StringUtil.getString(iter.next(), "");
			String value1 = StringUtil.getString(map1.get(key), "");
			String value2 = StringUtil.getString(map2.get(key), "");
			if(!StringUtils.equals(value1, value2)){
				result.add(key);
			}
		}
		Iterator iter2 = map2.keySet().iterator();
		while (iter2.hasNext()) {
			String key = StringUtil.getString(iter2.next(), "");
			String value2 = StringUtil.getString(map2.get(key), "");
			String value1 = StringUtil.getString(map1.get(key), "");
			if(!StringUtils.equals(value1, value2)){
				result.add(key);
			}
		}
		return result;
	}
	
	public static Map comparWarnMap(Map map, List list) {
		Map result = new HashMap();
		Iterator iter = map.keySet().iterator();
		int size = list.size();
		while (iter.hasNext()) {
			String key = StringUtil.getString(iter.next(), "");
			String value = StringUtil.getString(map.get(key), "");
			for (int i = 0; i < size; i++) {
				Map warnMap = (Map) list.get(i);
				String index_id = MapUtils.getString(warnMap, "index_id", "");
				String reason_content = MapUtils.getString(warnMap,
						"reason_content", "");
				String warn_display = MapUtils.getString(warnMap,
						"warn_display", "");
				if (index_id.toUpperCase().equals(key.toUpperCase())) {
					value = "<input class='reason_content' type='hidden' id='"
							+ index_id + "' value='" + reason_content
							+ "' /><font style='" + warn_display + "'>" + value
							+ "</font>";
				}
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 将相同结构list转化为相应map
	 * 
	 * @param list
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map listToMap(List list, String key, String value) {
		Map result = new HashMap();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Map map = (Map) list.get(i);
			Iterator iter = map.keySet().iterator();
			String newKey = "";
			String newValue = "";
			while (iter.hasNext()) {
				String iterkey = (String) iter.next();
				if (key.equals(iterkey)) {
					newKey = MapUtils.getString(map, iterkey, "");
				}
				if (value.equals(iterkey)) {
					newValue = MapUtils.getString(map, iterkey, "");
				}
			}
			result.put(newKey, newValue);
		}
		return result;
	}

	public static Map getMap(List list, String keys) {
		Map result = new HashMap();
		int size = list.size();
		if (size > 0) {
			Map map = (Map) list.get(0);
			Iterator iter = map.keySet().iterator();
			String newKey = "";
			String newValue = "";
			while (iter.hasNext()) {
				String iterkey = (String) iter.next();
				if (StringUtils.contains(keys, iterkey)) {
					newKey = iterkey;
					newValue = MapUtils.getString(map, iterkey, "");
				}
			}
			result.put(newKey, newValue);
		}
		return result;
	}

	 
	/**
	 * MAP中的项目值相加
	 * @param map
	 * @param keys
	 * @return
	 */
	public static String add(Map map,String[] keys){
		double total=0;
		for(int i=0;i<keys.length;i++){
			total+=MapUtils.getDouble(map, keys[i]);
		}
		return format.format(total);
	}

	public static double getDouble(Map map, String key) {
		String string = MapUtils.getString(map, key);
		return NumberUtils.toDouble(string, 0);
	}
	
	public static Map list2Map(List list,String key){
		int size=list.size();
		Map result=new HashMap();
		for(int i=0;i<size;i++){
			Map map=(Map)list.get(i);
			Object mapKey=(Object)map.get(key);
			result.put(mapKey, map);
		}
		return result;
	}
	
	public static List keyToLowerCase(List list){
		List result_list = new ArrayList();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Map result_map = new HashMap();
			Map map = (Map) list.get(i);
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				result_map.put(key.toLowerCase(),map.get(key));
			}
			result_list.add(result_map);
		}
		return result_list;
	}
	
	public static long addInt(Map map,String keys){
		if(map==null||keys==null){
			return 0;
		}
		long total=0;
		String tempArr[]=keys.split(",");
		for(int i=0;i<tempArr.length;i++){
			total+=MapUtils.getInt(map, tempArr[i]);
		}
		return total;
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {
		if(obj == null){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

	public static long addInts(List list,String keys){
		if(list==null||keys==null){
			return 0;
		}
		String tempArr[]=keys.split(",");
		long total=0;
		for(int j=0;j<list.size();j++){
			Map map=(Map)list.get(j);
			for(int i=0;i<tempArr.length;i++){
				total+=MapUtils.getInt(map, tempArr[i]);
			}
		}
		return total;
	}
	/**
	 * 根据map键字段，获取list
	 * @param maplist
	 * @param key
	 * @return
	 */
	public static List<String> getListColumn(List maplist,String key ){
		if(maplist==null||key==null){
			return null;
		}
		List<String> list=new ArrayList();
		for(int i=0;i<maplist.size();i++){
			Map map=(Map) maplist.get(i);
			String value=getString(map, key,"0.00");
			list.add(value);
		}
		return list;
	}
	public static List<Map<String,Object>> convertListasCol(List<Map<String,Object>> maplist,List<Object> list,String key){
		if(maplist==null){
			return null;
		}
		if(list==null){
			return maplist;
		}
		if(maplist.size()!=list.size()){//行转列长度需要一样
			return null;
		}
		for(int i=0;i<maplist.size();i++){
			Map map=maplist.get(i);
			Object value=list.get(i);
			map.put(key, value);
		}
		return maplist;
	}
}
