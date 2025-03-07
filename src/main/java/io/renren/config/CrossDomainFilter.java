package io.renren.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.renren.common.annotation.ComLogin;
import io.renren.common.utils.*;
import io.renren.modules.app.entity.UserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @description 拦截
 * @Date 2022年04月15日 12:00
 */
@Component
//@WebFilter(filterName = "CrossDomainFilter", urlPatterns = "/*")
public class CrossDomainFilter extends OncePerRequestFilter {
    private static Logger logger = LogManager.getLogger(CrossDomainFilter.class);

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin",url);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
        String requestURI = request.getRequestURI();
        String params = request.getParameter("params");
        while (!SpringUtil.checkReady()){
            UseUtil.waitTime(1);
        }
        RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
        try {
            if(CheckUtil.isNotBlank(params)){
                Map paramMap = (Map) JSONObject.parse(params);
                String token = CheckUtil.objToString(paramMap.get("token"));
            }
            //从header中获取token
            String token = request.getHeader("token");

            //如果header中不存在token，则从参数中获取token
            if(StringUtils.isBlank(token)){
                if(CheckUtil.isNotBlank(params)){
                    Map paramMap = (Map) JSONObject.parse(params);
                     token = CheckUtil.objToString(paramMap.get("token"));
                }
            }
            UserEntity userInfoVo=null;
            if(CheckUtil.isNotBlank(token)){
                userInfoVo= redisUtils.get(token, UserEntity.class,RedisUtils.DEFAULT_EXPIRE);
            }

            if (userInfoVo!=null){
                Locale locale = new Locale("ru", "RUS");
                if(userInfoVo.getLanguage()==0){
                    locale=Locale.CHINA;
                }
                if(userInfoVo.getLanguage()==1){
                    locale=Locale.US;
                }
                request.setAttribute("language",locale);
            }else{
                request.setAttribute("language",Locale.CHINA);
            }
            if(CheckUtil.isNotBlank(token) && !ComLogin.loginUserInfoTokenMap.containsKey(token)) {

                if (userInfoVo!=null){
                    ComLogin.loginUserInfoTokenMap.put(token,userInfoVo);
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("pad block corrupted")){
                ServletOutputStream out = response.getOutputStream();
                out.write("error password!!!".getBytes());
                out.flush();
                out.close();
                return;
            }
        }
            logger.info("路径:" + requestURI + "入参:" + params);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
