/*package com.zzk.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zzk.util.FastDFSClientWrapper;
import com.zzk.util.Result;

import net.sf.json.JSONObject;

*//**
 * <p>description:电商网站文件上传控制器</p>
 * name UploadController
 * @author Wen Yugang
 * @date 2017-4-10上午10:31:01
 *//*
@RestController
@RequestMapping(value = "/upload")
public class UploadController {
	
    *//**
     * <p>description：修改图片信息</p>
     *
     * @Author Tarriance
     * @Date 2017/4/10,11:01
     *//*
	@Autowired
    private FastDFSClientWrapper dfsClient;;
	
    @RequestMapping(value = "/saveImg", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Map saveImg(@RequestParam MultipartFile headImgUp, HttpServletRequest request, HttpServletResponse response) {
        Map resMap = new HashMap();
        JSONObject jsonObject = new JSONObject();
        try {
            if (headImgUp != null && !headImgUp.isEmpty()) {
                String  fileId = dfsClient.uploadFile(headImgUp);
                resMap.put("fileId", fileId);
                return resMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resMap;
    }
	
	
    *//** 
     * 获取图片高度 
     * @param is  图片文件
     * @return 高度 
     *//*  
    public static int getImgHeight(InputStream is) {  
        BufferedImage src = null;  
        int ret = -1;  
        try {  
            src = javax.imageio.ImageIO.read(is);  
            ret = src.getHeight(null); // 得到源图高  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return ret;  
    }  
    
    
    
    @RequestMapping("/del")
    @ResponseBody
    public Result del(String url){
    	if(UploadUtils.removeFileById(url)){
    		return new Result(1,"success","删除成功");
    	}else{
    		return new Result(0,"error","删除失败");
    	}
    	
    }
    
    
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
}
*/