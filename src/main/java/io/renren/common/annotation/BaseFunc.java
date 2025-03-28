package io.renren.common.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author suntao
 * @description 对插件通用的一些方法
 * @date 2019/4/4
 */
public class BaseFunc {
    private static Logger logger = LogManager.getLogger(BaseFunc.class);




    /**
     * 组件之间互相调用
     * @param comRequest
     * @param comFinger
     * @param methodName
     * @return
     */
    protected ComResponse callOtherPlugin(ComRequest comRequest, ComFinger comFinger, String methodName){
        GateService gateService = SpringUtil.getBean(methodName,GateService.class);
        try {
            return gateService.invoke(comRequest,comFinger);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    /**
     * 对象转Json字符串
     * @param obj 对象
     * @return
     */

    /**
     * 把Java对象转换成json字符串
     *
     * @param object 待转化为JSON字符串的Java对象
     * @return json 串 or null
     */
    protected static String parseObjToJson(Object object) {
        String string = null;
        try {
            string = JSONObject.toJSONString(object);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return string;
    }
    /**
     * 将Json字符串信息转换成对应的Java对象
     * @param json json字符串对象
     * @param c    对应的类型
     */
    protected static <T> T parseJsonToObj(String json, Class<T> c) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            return JSON.toJavaObject(jsonObject, c);
        } catch (Exception e) {
            logger.error("json报错："+json);
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * string转map
     * @param obj
     * @return
     */
    protected Map<String,Object> stringToMap(String obj) {
        return (Map<String,Object>) JSONObject.parse(obj);
    }

    /**
     * string转listmap
     * @param obj
     * @return
     */
    protected List<Map<String,Object>> stringToMapList(String obj){
        return (List<Map<String,Object>>) JSONObject.parse(obj);
    }

    /**
     * string转list
     * @param obj
     * @return
     */
    protected List<Object> stringToList(String obj){
        return (List<Object>) JSONObject.parse(obj);
    }



    /**
     * 800检测器间隔取点
     * @return
     */
    protected List<Float> getPoint(List<Float> floatList){
        List<Float> retlist = new ArrayList<>();
        for (int i = 0 ;i < floatList.size();i += 2) {
            retlist.add(floatList.get(i));
        }
        return retlist;
    }
    /**
     * 800检测器间隔取点
     * @return
     */
    protected List<Integer> getPointInteger(List<Integer> floatList){
        List<Integer> retlist = new ArrayList<>();
        for (int i = 0 ;i < floatList.size();i += 2) {
            retlist.add(floatList.get(i));
        }
        return retlist;
    }


}
