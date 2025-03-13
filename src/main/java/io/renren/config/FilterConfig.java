package io.renren.config;

import io.renren.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

/*** @author shaoduo* @program wrapper-demo* @description 过滤器配置类* @since 1.0**/
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CrossDomainFilter> crossDomainFilterRegistration() {
        FilterRegistrationBean<CrossDomainFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CrossDomainFilter());
        registration.setOrder(1); //
        registration.addUrlPatterns("/*");
        return registration;
    }
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        StringBuffer buffer=new StringBuffer();
        //登录
        buffer.append("/fire-app/app/login,/fire-app/swagger/,/fire-app/app/logout");;
        registration.addInitParameter("excludedUris",buffer.toString());
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }


}
