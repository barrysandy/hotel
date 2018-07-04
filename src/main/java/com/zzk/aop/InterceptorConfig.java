package com.zzk.aop;

import com.zzk.common.Constact;
import com.zzk.util.JsonUtils;
import com.zzk.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.zzk.common.Constact.IS_AUTHENTICATION;

/**
 * @Author:lichangqing
 * @Description
 * @Date Create in 9:35 2018/3/14
 * @modified By
 */
public class InterceptorConfig implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);

    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("---------------------开始进入请求地址拦截----------------------------"+httpServletRequest.getRequestURL());
        HttpSession session = httpServletRequest.getSession();
        if(session.getAttribute(IS_AUTHENTICATION)!=null){
            if((boolean)session.getAttribute(IS_AUTHENTICATION)){
                return true;
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("success", false);
        map.put("msg", "您未登录或者没有访问权限");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.append(JsonUtils.turnJsonString(new Result(0,"false","您未登录或者没有访问权限",null)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------0");
    }
}