/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.xss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.CheckUtil;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisUtils;
import io.renren.common.utils.SpringUtil;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.interceptor.RequestWrapper;
import io.renren.modules.app.interceptor.ResponseWrapper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * XSS过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
public class XssFilter implements Filter {
	private String[] excludedUris;


	@Override
	public void init(FilterConfig config) throws ServletException {
		String param = config.getInitParameter("excludedUris");
		if (StringUtils.isNotBlank(param)) {
			this.excludedUris = param.split(",");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String httpMethod = httpServletRequest.getMethod();
		// 定义表示变量 并验证用户请求URI 是否包含不过滤路径
		boolean flag = false;
		String urls = httpServletRequest.getRequestURI();
		for (String uri:excludedUris) {
			//是需要规则匹配
			if (urls.startsWith(uri)){
				flag = true;
				break;
			}
		}
		if(flag){
			chain.doFilter(request,response);
			return;
		}else {
			ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);//转换成代理类
			ServletRequest requestWrapper = null;
			requestWrapper = new RequestWrapper((HttpServletRequest) request);
			RedisUtils redisUtils = SpringUtil.getBean(RedisUtils.class);
			chain.doFilter(requestWrapper, wrapperResponse);
			String params = httpServletRequest.getParameter("params");
			byte[] content = wrapperResponse.getContent();//获取返回值
			try {
				//从header中获取token
				String token = httpServletRequest.getHeader("token");

				//如果header中不存在token，则从参数中获取token
				if (StringUtils.isBlank(token)) {
					if (CheckUtil.isNotBlank(params)) {
						Map paramMap = (Map) JSONObject.parse(params);
						token = CheckUtil.objToString(paramMap.get("token"));
					}
					//token = request.getParameter("token");
				}
				if (CheckUtil.isNotBlank(token)) {
					UserEntity userInfoVo = redisUtils.get(token, UserEntity.class);
					if (userInfoVo == null) {
						String s1 = JSON.toJSONString(R.error(503, "token已失效,请重新登录!"));
						ServletOutputStream out = response.getOutputStream();
						out.write(s1.getBytes());
						out.flush();
						out.close();
						return;
					}
				}
			} catch (Exception e) {
					e.printStackTrace();
				}
			//把返回值输出到客户端
			ServletOutputStream out = response.getOutputStream();
			out.write(content);
			out.flush();
			out.close();
			return;

	     }
	}

	@Override
	public void destroy() {
	}

}