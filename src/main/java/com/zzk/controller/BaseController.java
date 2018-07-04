package com.zzk.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzk.controller.CustomDateEditor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Map;


/**
 * @author cby
 * @date 2016-9-26
 */
public class BaseController {
    private static final int CODE_FAIL = 0;
    private static final int CODE_OK = 1;
    private static final int ROLE_ERROR = 2;



    /**
     * action执行前会自动调用该方法
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void beforeInvoke(HttpServletRequest request, HttpServletResponse response) {

    }

    public HttpServletRequest getRequest() {
        return ControllerContext.getRequest();
    }

    public HttpServletResponse getResponse() {
        return ControllerContext.getResponse();
    }

    public ModelAndView toVm(String viewName) {
        return toVm(viewName, null);
    }

    @SuppressWarnings("unchecked")
    public ModelAndView toVm(String viewName, Object data) {
        ModelAndView mav = new ModelAndView(viewName);
        if (data != null) {
            if (data instanceof Map) {
                mav.addAllObjects((Map<String, ?>) data);
            } else {
                mav.addObject("data", data);
            }
        }
        return mav;
    }

    public String toJson(Object msg) {
        return toJson(null, msg);
    }

    public String toJsonSuccess(Object object) {
        return toJson(CODE_OK, object);
    }

    public String toJsonFail(Object object) {
        return toJson(CODE_FAIL, object);
    }

    public String toJsonRoleFail(Object object) {
        return toJson(ROLE_ERROR, object);
    }

    private String toJson(Integer code, Object msg) {
        getResponse().setContentType("application/json");
        JSONObject json = new JSONObject();
        if (code != null) {
            json.put("code", code);
            json.put("msg", msg);
            return json.toJSONString();
        } else {
            return JSON.toJSONString(msg);
        }

    }
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(true));
    }


}
