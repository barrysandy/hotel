package com.zzk.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Kun on 2017/04/25 0025.
 */
public class JsonUtils {

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

    /**
     * 转JSON（线路接口开发）
     * @param state 返回状态码：0：失败，1：成功
     * @param msg 成功-失败信息 success error
     * @param message 自定义消息 eg：请求超时。。。。。。。
     * @param object 返回的数据体。
     * @return String
     * @author kun
     * @date 2018-03-07
     */
    public static String lineJsonData(int state, String msg, String message , Object object){
        JSONObject obj = new JSONObject();
        obj.put("state", state);
        obj.put("msg", msg);
        obj.put("message", message);
        if (object instanceof List) {
            object = toJsonArray((List) object);
        } else if (object instanceof Map) {
            object = toJsonObject((Map) object);
        } else if (object == null) {
            object = "";
        } else {
//            object = object.toString();
        }
        obj.put("data", object);
        String result = obj.toString();
        return result;
    }

    /**
     * @param state 返回状态码：0：失败，1：成功
     * @param msg 成功-失败信息 success error
     * @param message 自定义消息 eg：请求超时。。。。。。。
     * @param object 返回的数据体。
     * @param pageIndex 第几页
     * @param pageSize 每页几条数据
     * @param totalNum 一共多少条数据
     * @param pageCount 一共多少页
     * @return String
     * @author kun
     * @date 11:15 2018/3/7
     */
    public static String lineJsonDataForPage(int state, String msg, String message ,int pageIndex,
                                             int pageSize,int totalNum,int pageCount, Object object){
        JSONObject obj = new JSONObject();
        obj.put("state", state);
        obj.put("msg", msg);
        obj.put("message", message);
        obj.put("pageIndex", pageIndex);
        obj.put("pageSize", pageSize);
        obj.put("totalNum", totalNum);
        obj.put("pageCount", pageCount);
        if (object instanceof List) {
            object = toJsonArray((List) object);
        } else if (object instanceof Map) {
            object = toJsonObject((Map) object);
        } else if (object == null) {
            object = "";
        } else {
            object = object.toString();
        }
        obj.put("data", object);
        String result = obj.toString();
        return result;

    }

    public static JSONObject toJsonObject(Map map) {
        JsonConfig config = new JsonConfig();
        JSONObject resJson = JSONObject.fromObject(map, config);
        return resJson;
    }

    public static JSONArray toJsonArray(List list) {
        JsonConfig config = new JsonConfig();
        JSONArray resJson = JSONArray.fromObject(list, config);
        return resJson;
    }


    /**
     * 转json
     */
    public static String turnJsonString(Object o) {
        JSONObject obj = new JSONObject();
        obj.put("json", o);
        String result = obj.toString();
        return result;
    }

}
