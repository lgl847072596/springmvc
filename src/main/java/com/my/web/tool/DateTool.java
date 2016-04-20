package com.my.web.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 日期工具类
 * @author guilin
 *
 */
public class DateTool {

	private DateTool(){}
	
	public static final String DATE_FORMAT="yyyy-MM-dd";
	public static final String TIME_FORMAT="HH:mm:ss";
	public static final String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_NO_FORMAT="yyyyMMdd HHmmss";
	/**
	 * @description 去除日期的格式
	 * @param sdate
	 * @return
	 */
	public static  String   dateNoFormat(String sdate){
		return sdate.replaceAll("\\s", "").replace("-", "").replace(":", "").replace("/", "");
	}
	/**
	 * @description 返回日期
	 * @param sDate
	 * @return
	 * @throws ParseException
	 */
	public static Date dateFormat(String sDate,String formats) throws ParseException{
		SimpleDateFormat format=new SimpleDateFormat(formats);
		return format.parse(sDate);
	}
	/**
	 * @description 生成统一格式的日期
	 * @param date
	 * @return
	 */
	public static String formatData(Date date){
		SimpleDateFormat format=new SimpleDateFormat(DATE_TIME_FORMAT);
		return format.format(date);
	}
	
}
