package io.renren.common.utils;

import com.sun.management.OperatingSystemMXBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @description 放置一些通用方法
 *
 */
public class UseUtil {
    private static Logger logger = LogManager.getLogger(UseUtil.class);
    /**
     * 等待时间单位（S）(秒)
     * @param time
     */
    public static void waitTime(long time){
        try {
            if(time > 0) {
                Thread.sleep(time * 1000);
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
    }
    /**
     * 等待时间单位（ms）(毫米秒)
     * @param time
     */
    public static void waitTimeForMill(long time){
        try {
            if(time > 0) {
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 除法
     * @param molecular 分子
     * @param denominator 分母
     * @param scale 舍入位数
     * @return
     * @throws Exception
     */
    public static float divide(float molecular,float denominator,int scale) throws Exception{
        if (scale < 0 ){
            throw new IllegalArgumentException("舍入位数不能小于0");
        }
        if(molecular == 0 || denominator == 0 ){
            return 0;
        }
        BigDecimal b1 = new BigDecimal(Float.toString(molecular));
        BigDecimal b2 = new BigDecimal(Float.toString(denominator));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 乘法
     * @param val1 值1
     * @param val2 值2
     * @return
     */
    public static float multiply(float val1, float val2){
        BigDecimal b1 = new BigDecimal(Float.toString(val1));
        BigDecimal b2 = new BigDecimal(Float.toString(val2));
        return b1.multiply(b2).floatValue();
    }

    /**
     * 毫秒转分钟，低精度
     * @param time
     * @return
     */
    public static float milliToMin(long time){
        float ret = ((float)time)/60000f;
        return ret;
    }

    /**
     * 毫秒转分钟，高精度
     * @param time
     * @return
     */
    public static double milliToMinDouble(long time){
        double ret = ((double)time)/60000f;
        return ret;
    }

    /**
     * 分钟转毫秒
     * @param time
     * @return
     */
    public static long minToMilli(float time){
        float ret = time * 60000f;
        return (long)ret;
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
    /**
     * 保留2位小数转为float
     * @param number
     * @return
     */
    public static float dR_2_to_float(float number){
        if(number == 0 || number == Float.NaN){
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return CheckUtil.objToFloat(df.format(number));
    }
    /**
     * 保留2位小数转为float
     * @param number
     * @return
     */
    public static float dR_3_to_float(float number){
        if(number == 0 || number == Float.NaN){
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#0.000");
        return CheckUtil.objToFloat(df.format(number));
    }
    /**
     * 保留5位小数转为float
     * @param number
     * @return
     */
    public static float dR_5_to_float(float number){
        if(number == 0 || number == Float.NaN){
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#0.00000");
        return CheckUtil.objToFloat(df.format(number));
    }
    /**
     * 保留1位小数转为double
     * @param number
     * @return
     */
    public static double dR_1_to_double(double number){
        if(number == 0 || number == Float.NaN){
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#0.0");
        return CheckUtil.objToDouble(df.format(number));
    }
    /**
     * 保留两位小数
     * @param number
     * @return
     */
    public static String decimalReserved2(float number){
        if(number == 0 || number == Float.NaN){
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(number);
    }
    /**
     * 保留一位小数
     * @param number
     * @return
     */
    public static String decimalReserved1(float number){
        if(number == 0 || number == Float.NaN){
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(number);
    }

    /**
     * 不保留小数
     * @param number
     * @return
     */
    public static String decimalReserved0(float number){
        return (int) number +"";
    }
    public static boolean checkCpuLoad(float cpuCut){
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 获取系统CPU负载
        double systemCpuLoad = osBean.getSystemCpuLoad();
        // 检查是否获取到了有效的CPU负载值
        if (systemCpuLoad >= 0.0) {
            // 判断CPU负载是否达到85%
            if (systemCpuLoad >= cpuCut) {
                //logger.info("警告：CPU负载已达到阈值，当前负载为：" + (systemCpuLoad * 100) + "%");
                return false;
            } else {
               // logger.info("当前CPU负载正常，负载为：" + (systemCpuLoad * 100) + "%");
                return true;
            }
        } else {
            logger.error("无法获取有效的CPU负载信息。");
            return false;
        }
    }


}
