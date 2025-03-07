package io.renren.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @description 检查obj转string
 * @date 2025/3/5
 */
public class CheckUtil {
    public static String objToString(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return "";
            }else{
                return str.toString();
            }
        } else {
            return "";
        }
    }

    public static Integer objToInteger(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return 0;
            }else{
                return Integer.parseInt(str.toString());
            }
        } else {
            return 0;
        }
    }

    public static Double objToDouble(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return (double)0;
            }else{
                return Double.parseDouble(str.toString());
            }
        } else {
            return (double)0;
        }
    }
    public static Float objToFloat(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return 0f;
            }else{
                return Float.parseFloat(str.toString());
            }
        } else {
            return 0f;
        }
    }
    public static Short objToShort(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return (short)0;
            }else{
                return Short.parseShort(str.toString());
            }
        } else {
            return (short)0;
        }
    }

    public static Long objToLong(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return 0L;
            }else{
                return Long.parseLong(str.toString());
            }
        } else {
            return 0L;
        }
    }

    public static Boolean objToBoolean(Object str) {
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return false;
            }else{
                return Boolean.parseBoolean(str.toString());
            }
        } else {
            return false;
        }
    }
    public static Byte objToByte(Object str){
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return null;
            }else{
                return Byte.parseByte(str.toString());
            }
        } else {
            return null;
        }
    }

    public static Date objToDate(Object str){
        if (str != null) {
            if(str.toString().equals("") || str.toString().equals("null") || str.toString().equals("NULL")){
                return null;
            }else{
                return DateUtil.getDateFromStr(objToString(str),DateUtil.DATE_FORMATE_STRING_A);
            }
        } else {
            return null;
        }
    }

    /**
     * 去除小数点后面多余的0
     * @param s
     * @return
     */
    public static String replace(String s){
        if(null != s && s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 验证字符串不能为空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

    /**
     * 验证字符串为空
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     *  string转byte字符串
     * @param str 需要转byte的字符串
     * @return
     */
    public static String byteToString(String str){
        String retbyte = "";
        for (byte sb:str.getBytes()) {
            retbyte = retbyte + sb +"/";
        }
        return retbyte;
    }

    /**
     * byte字符串转string
     * @param byt
     * @return
     */
    public static String stringToByte(String byt){
        String st[] = byt.split("/");
        byte [] retstring = new byte[st.length];
        for (int i = 0 ;i< st.length ;i++){
            retstring[i] = objToByte(st[i]);
        }
        return new String(retstring);
    }

    /**
     * 保留1位小数转为float
     * @param number
     * @return
     */
    public static float dR_1_to_float(float number){
        if(number == 0 || number == Float.NaN){
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#0.0");
        return CheckUtil.objToFloat(df.format(number));
    }

    //将秒转换为时分的格式
    public static String secondToTime(long second) {
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        String minutes_str = minutes + "";
        if(minutes_str.length() == 1){
            minutes_str = "0"+ minutes_str;
        }
        second = second % 60;//剩余秒数
        String  second_str = second +"";
        if(second_str.length() == 1){
            second_str = "0"+ second_str;
        }
        return hours+":"+minutes_str+":"+second_str;
    }

    /**
     * string转listmap
     * @param obj
     * @return
     */
    public static List<Map<String,Object>> stringToMapList(String obj){
        return (List<Map<String,Object>>) JSONObject.parse(obj);
    }

    /**
     * 将Json字符串信息转换成对应的Java对象
     * @param json json字符串对象
     * @param c    对应的类型
     */
    public static <T> T parseJsonToObj(String json, Class<T> c) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            return JSON.toJavaObject(jsonObject, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
