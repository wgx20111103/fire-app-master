package io.renren.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * @author suntao
 * @description
 * @date 2019/3/7
 */
public  class SpringUtil {
    //上下文
    private static ApplicationContext applicationContext;

    public static boolean checkReady(){
        if(applicationContext != null){
            return true;
        }else {
            return false;
        }
    }
    //获取上下文
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    //设置上下文
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }
    //通过名字获取上下文中的bean
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
    //通过类型获取上下文中的bean
    public static <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }
    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }


}
