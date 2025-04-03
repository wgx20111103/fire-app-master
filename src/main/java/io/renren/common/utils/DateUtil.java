package io.renren.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期工具类，提供各种常用的日期处理方法
 * 
 * @author suntao
 * 
 */
public class DateUtil {

	// 日志记录器
	private static Logger log = LoggerFactory.getLogger(DateUtil.class);

	public static final String DATE_FORMATE_STRING_A = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATE_STRING_B = "yyyy-MM-dd";
	private static final String DATE_FORMATE_STRING_C = "yyyy/MM/dd";
	private static final String DATE_FORMATE_STRING_D = "yyyy/MM/dd HH:mm:ss";
	
	// 日期格式yyyy-MM-dd
	public static final String 									DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String  DATE_FORMAT_STRING_E = "yyyy-MM-dd HH:mm";

	public static final String  DATE_FORMAT_STRING_F = "yyyy年MM月dd日 HH:mm:ss";

	public static final String DATE_FORMAT_STRING_G = "yyyyMMddHHmmss";
	//并发日期转化安全变量
	private static  ThreadLocal threadLocalA = new ThreadLocal(){
		protected synchronized SimpleDateFormat initialValue(){
			return new  SimpleDateFormat(DATE_FORMATE_STRING_A);
		}
	};
	private static ThreadLocal threadLocalB = new ThreadLocal(){
		protected synchronized Object initialValue(){
			return new  SimpleDateFormat(DATE_FORMATE_STRING_B);
		}
	};
	private static ThreadLocal threadLocalC = new ThreadLocal(){
		protected synchronized Object initialValue(){
			return new  SimpleDateFormat(DATE_FORMATE_STRING_C);
		}
	};
	private static ThreadLocal threadLocalD = new ThreadLocal(){
		protected synchronized Object initialValue(){
			return new  SimpleDateFormat(DATE_FORMATE_STRING_D);
		}
	};
	
	/**
	 * 将字符串按照指定格式转换成日期类型，如果没有指定格式，则使用默认格式yyyy-MM-dd
	 * 
	 * @param dateStr
	 *            源字符串
	 * @param format
	 *            日期格式
	 * @return 日期类型数据
	 */
	public static Date getDateFromStr(String dateStr, String format) {
		log.debug("[getDateFromStr] 日期字符串: dateStr = " + dateStr);
		if (dateStr == null || dateStr.trim().length() == 0) {
			return null;
		}

		SimpleDateFormat sdf = null;

		try {
			if (format == null || format.trim().length() == 0) {
				// 默认格式
				sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
			} else {
				// 指定格式
				sdf = new SimpleDateFormat(format);
			}

			return sdf.parse(dateStr);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
			log.error("[getDateFromStr] 解析日期字符串: dateStr = " + dateStr
					+ "出现异常, 异常信息: " + e.toString());
			return null;
		}
	}

	/**
	 * 将日期类型数据按照指定格式转换成字符串，如果没有指定格式，则使用默认格式yyyy-MM-dd
	 * 
	 * @param date
	 *            源日期类型数据
	 * @param format
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String getStrFromDate(Date date, String format) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = null;

		if (format == null || format.trim().length() == 0) {
			// 默认格式
			sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
		} else {
			// 指定格式
			sdf = new SimpleDateFormat(format);
		}

		return sdf.format(date);
	}

	/**
	 * 获取当天的开始时间，即前一天晚上00:00:00
	 * 
	 * @return
	 */
	public static Date getStartOfDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 获取指定日期前几天的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 获取当天的结束时间，即当天晚上23:59:59
	 * 
	 * @return
	 */
	public static Date getEndOfDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}
	/**
	 * 查询某年某月的最后一天,注：月份是从0开始的
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	/**
	 * 查询某年某月的第一天,注：月份是从0开始的
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取当前的年月, 格式yyyyMM
	 * 
	 * @return 当前的年月
	 */
	public static String getCurrentYearMonth(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		return year + "" + (month + 1);
	}
	

	/**
	 * 获取当前的年月日, 格式yyyyMMdd
	 * 
	 * @return 当前的年月日
	 */
	public static String getCurrentYearMonthDay(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		month = month + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String result = year + "";
		if(month < 10){
			result = result + "0" + month;
		}else{
			result = result + month;
		}
		
		if(day < 10){
			result = result + "0" + day;
		}else{
			result = result + day;
		}
		
		return result;
	}
	
	/**
	 * 获取当前的年月日, 格式yyyyMMddHHmmss
	 * 
	 * @return 当前的年月日
	 */
	public static String getCurrentYearMonthDayTime(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		month = month + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String result = year + "";
		if(month < 10){
			result = result + "0" + month;
		}else{
			result = result + month;
		}
		
		if(day < 10){
			result = result + "0" + day;
		}else{
			result = result + day;
		}
		
		if(hour < 10){
			result = result + "0" + hour;
		}else{
			result = result + hour;
		}
		
		if(minute < 10){
			result = result + "0" + minute;
		}else{
			result = result + minute;
		}
		
		if(second < 10){
			result = result + "0" + second;
		}else{
			result = result + second;
		}
		
		return result;
	}
	
	/**
	 * 获取当前的年月日, 格式yyyy-MM-dd
	 * 
	 * @return 当前的年月日
	 */
	public static String getCurrentYearMonthDayOther(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		month = month + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String result = year + "-";
		if(month < 10){
			result = result + "0" + month;
		}else{
			result = result + month;
		}
		
		if(day < 10){
			result = result + "-0" + day;
		}else{
			result = result +"-"+ day;
		}
		
		return result;
	}
	
	/**
	 * 获取第一个时间参数与第二个时间参数的时间差, 以天为单位
	 * 
	 * @param firstDay 第一个时间
	 * @param secondDay 第二个时间
	 * @param dateFormat 时间格式, 默认为yyyy-MM-dd HH:mm:ss
	 * @return 时间差, 以天为单位
	 */
	public static int getTimeDifference(String firstDay, String secondDay, String dateFormat){
		if(dateFormat == null || dateFormat.trim().length() == 0){
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		
		DateFormat df = new SimpleDateFormat(dateFormat);

		try{
		    Date d1 = df.parse(firstDay);
		    Date d2 = df.parse(secondDay);
		    long diff = d1.getTime() - d2.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		    
		    return (int)days;
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
 
	
	/**
	 * 获取第一个时间参数与第二个时间参数的时间差, 以小时为单位
	 * 
	 * @param firstDay 第一个时间
	 * @param secondDay 第二个时间
	 * @param dateFormat 时间格式, 默认为yyyy-MM-dd HH:mm:ss
	 * @return 时间差, 以小时为单位
	 * @throws ParseException 
	 */
	public static int getTimeDifferenceOther(String firstDay, String secondDay, String dateFormat) throws ParseException{
		if(dateFormat == null || dateFormat.trim().length() == 0){
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat df = new SimpleDateFormat(dateFormat);
	    Date d1 = df.parse(firstDay);
	    Date d2 = df.parse(secondDay);
	    long diff = d1.getTime() - d2.getTime();
	    long days = diff / (1000 * 60 * 60 );
	    return (int)days;
	}

	/**
	 * 获取两个 Date 对象之间的时间差, 以分钟为单位
	 *
	 * @param startDate 第一个时间
	 * @param endDate 第二个时间
	 * @return 时间差, 以分钟为单位
	 */
	public static long getTimeDifferenceInMinutes(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("Date objects cannot be null");
		}

		// 将 Date 转换为 Instant
		Instant startInstant = startDate.toInstant();
		Instant endInstant = endDate.toInstant();

		// 计算两个 Instant 之间的差值
		Duration duration = Duration.between(startInstant, endInstant);

		// 获取差值以分钟为单位
		long minutes = duration.toMinutes();

		return minutes;
	}

	/**
	 * 获取两个 Date 对象之间的时间差, 以天为单位
	 *
	 * @param startDate 第一个时间
	 * @param endDate 第二个时间
	 * @return 时间差, 以天为单位
	 */
	public static long getTimeDifferenceInDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException("Date objects cannot be null");
		}

		// 将 Date 转换为 Instant
		Instant startInstant = startDate.toInstant();
		Instant endInstant = endDate.toInstant();

		// 计算两个 Instant 之间的差值
		Duration duration = Duration.between(startInstant, endInstant);

		// 获取差值以分钟为单位
		long minutes = duration.toDays();

		return minutes;
	}


	/**
	 * 自动适配两种格式的日期字符串转换为date对象
	 * A格式	:	yyyy-MM-dd HH:mm:ss
	 * B格式	:	yyyy-MM-dd
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromStringAdaptTwoPattern(String date)  {
		try{
			return getDateFromString(date, DATE_FORMATE_STRING_A);
		}catch(ParseException e){
			try{
				return getDateFromString(date, DATE_FORMATE_STRING_B);
			}catch(ParseException e1){
				try {
					return getDateFromString(date, DATE_FORMATE_STRING_C);
				}catch(ParseException e2){
					log.error("没有匹配的时间格式!");
					return null;
				}
			}
		}
	}
	
	/**
	 * 将格式将日期字符串转换为Date对象
	 * 
	 * @param date 字符串
	 * @param pattern 格式如下：
	 * 	yyyyMMddHHmmss
	 *	yyyy-MM-dd HH:mm:ss
	 *	yyyy-MM-dd
	 *	MM/dd/yyyy HH:mm:ss a
	 *	yyyy-MM-dd HH:mm:ss a
	 *	yyyy-MM-dd'T'HH:mm:ss'Z'
	 *	yyyy-MM-dd'T'HH:mm:ssZ
	 *	yyyy-MM-dd'T'HH:mm:ssz
	 * @return 日期Date对象
	 * @throws ParseException
	 * @see Date
	 */
	public static Date getDateFromString(String date, String pattern) throws ParseException {
		SimpleDateFormat sDateFormat = getDateFormat(pattern);
		return sDateFormat.parse(date);
	}
	
	/**
	 * 根据pattern取得的date formate
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(String pattern) {
		if(DATE_FORMATE_STRING_A.equals(pattern)){
			return (SimpleDateFormat) threadLocalA.get();
		}else if(DATE_FORMATE_STRING_B.equals(pattern)){
			return (SimpleDateFormat) threadLocalB.get();
		}else if(DATE_FORMATE_STRING_C.equals(pattern)){
			return (SimpleDateFormat) threadLocalC.get();
		}else{
			return (SimpleDateFormat) threadLocalD.get();
		}
	}
 
	
	/**
	 * 获取当前周的周一，每周的第一天为周日，第七天为周六
	 * 
	 * @return
	 */
	public static Date getMonday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
	
	/**
	 * 获取当前周的开始时间，周一的00:00:00
	 * 
	 * @return
	 */
	public static Date getWeekStart() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取当前周的周日，每周的第一天为周日，第七天为周六
	 * 
	 * @return
	 */
	public static Date getSunday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return cal.getTime();
	}
	
	/**
	 * 获取当前周的结束时间，周日的23:59:59
	 * 
	 * @return
	 */
	public static Date getWeekEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定时间格式的一天的开始时间
	 * @param dt
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateStartTime(String dt, String format) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(dt);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();

	}

	/**
	 * 判断字符串是否是合法的日期
	 * @param strDate
	 * @return
	 */
	public static boolean isValidDate(String strDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2018-02-29会被接受，并转换成2018-03-01
			format.setLenient(false);
			Date date = format.parse(strDate);
			//判断传入的yyyy年-MM月-dd日 字符串是否为数字
			String[] sArray = strDate.split("-");
			for (String s : sArray) {
				boolean isNum = s.matches("[0-9]+");
				//+表示1个或多个（如"3"或"225"），*表示0个或多个（[0-9]*）（如""或"1"或"22"），?表示0个或1个([0-9]?)(如""或"7")
				if (!isNum) {
					return false;
				}
			}
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
		return true;
	}
	/**
	 * 获取指定时间格式的一天的结束时间
	 * @param dt
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateEndTime(String dt, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(dt);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static String getCurrentDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_STRING_A);
		return sdf.format(date);
	}
	public static Date getCurrentDate(){
		return new Date();
	}

	/*
	 * 将时间戳转换为时间(精确到毫秒)
	 */
	public static String getStampToDate(Long st){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date(st);
		res = simpleDateFormat.format(date);
		return res;
	}
	/*
	 * 将时间戳转换为时间(精确到秒)
	 */
	public static String getStampToDateInSec(Long st){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(st);
		res = simpleDateFormat.format(date);
		return res;
	}
	/**
	 * 获取第一个时间参数与第二个时间参数的时间差, 以天为单位
	 *
	 * @param firstDay 第一个时间
	 * @param dateFormat 时间格式, 默认为yyyy-MM-dd HH:mm:ss
	 * @return 时间差, 以ms为单位
	 * @throws ParseException
	 */
	public static Long getTimeDiffer(String firstDay,  String dateFormat) {
		if(dateFormat == null || dateFormat.trim().length() == 0){
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat df = new SimpleDateFormat(dateFormat);
		Date d1 = null;
		try {
			d1 = df.parse(firstDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long diff =   System.currentTimeMillis()-d1.getTime();
		return diff;
	}

	public static void main(String[] args) throws ParseException, InterruptedException {
		String dbtime="2023-08-17 15:10:00.0";

		long timeDifferenceInMinutes = getTimeDifferenceInMinutes(getDateFromStringAdaptTwoPattern("2025-03-31 14:40:00"),new Date());
		System.out.println("分钟："+timeDifferenceInMinutes);
		System.out.println(getTimeDiffer(dbtime,null));
	}
 
}
