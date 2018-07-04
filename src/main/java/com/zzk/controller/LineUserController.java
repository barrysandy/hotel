package com.zzk.controller;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.StringUtils;
import com.zzk.entity.LineUser;
import com.zzk.service.LineUserService;

/**
 * 用户信息表
 *
 * @name: LineUserController
 * @author: Kun
 * @date: 2018-04-03 10:13
 */
@Controller
@RequestMapping(value = "/lineUser")
public class LineUserController extends BaseController {

    @Resource
    private LineUserService lineUserService;


    /************************************* B端设置接口 **********************************/

    /**
     * 设置首页 - 账户信息 - 获取当前商家信息
     * @param sellerId
     * @return result
     * @author kun
     * @date 10:07 2018/4/3
     */
    @RequestMapping("getSellerInfo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result getSellerInfo(String sellerId){
        try {
            if (StringUtils.isBlank(sellerId)){
                return new Result(0,"fail","商家ID不能为");
            } else {
                Map<String,Object> resultMap = lineUserService.getSellerInfo(sellerId);
                return new Result(1,"success","查询到商家信息",resultMap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0,"fail","内部异常",e);
        }
    }

    /**
     * 修改用户头像接口
     * @param headImg 头像Url
     * @return result
     * @author kun
     * @date 10:47 2018/4/3
     */
    @RequestMapping("changeUserHeadImg")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result changeUserHeadImg(String headImg,String sellerId){
        try {
            if (StringUtils.isBlank(headImg) || StringUtils.isBlank(sellerId)){
                return new Result(0,"fail","参数错误");
            } else {
                int result = lineUserService.changeUserHeadImg(sellerId,headImg);
                if (result > 0){
                    return new Result(1,"success","修改成功");
                }else {
                    return new Result(0,"fail","未知错误导致修改失败");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(0,"fail","内部异常",e);
        }
    }

    /**
     * 设置 - 修改密码 - 使用密码验证修改密码
     * @param sellerId 商家ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return result
     * @author kun
     * @date 11:16 2018/4/4
     */
    @RequestMapping("changeByOldPwd")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Result changeByOldPwd(String sellerId,String oldPassword,String newPassword) {
        try {
            if (StringUtils.isBlank(sellerId) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
                return new Result(0,"fail","参数错误!");
            } else {
                return lineUserService.changeByOldPwd(sellerId,oldPassword,newPassword);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(0,"fail","内部异常",e);
        }
    }

}
