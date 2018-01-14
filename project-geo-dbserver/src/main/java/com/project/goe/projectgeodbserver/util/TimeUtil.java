package com.project.goe.projectgeodbserver.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	private final static int DAY_SECOND = 86400000;  //1000*60*60*24
	private final static int DATE_CYCLE = 30;
	/**
	 * 相差时间 已天为单位
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static int getDiscrepantDays(String dateStart,String dateEnd){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date beginDate = df.parse(dateStart);
			Date endDate = df.parse(dateEnd);
			return getDiscrepantDays(beginDate, endDate);
		} catch (ParseException e) {
			return -1;
		}
	}
	
	/**
	 * 将时间格式化
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	/**
	 * 将时间格式化 现在时间
	 * @return
	 */
	public static String getDateFormat() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	/**
	 * 相差时间 已天为单位 重载方法
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static int getDiscrepantDays(Date dateStart,Date dateEnd) {
		long betweenDate = (dateEnd.getTime()-dateStart.getTime())/DAY_SECOND;
		return (int)betweenDate;
	}

	/**
	 * 已当前时间未边界增加天数
	 * @param day
	 * @return
	 */
	public static Date addDay(int day) {
		Date nowdate = new Date();
		Date dateStart = new Date((nowdate.getTime()+(DAY_SECOND* day)));
		return dateStart;
	}
	
	public static Date addDay(Date nowdate,int day) {
		Date dateStart = new Date((nowdate.getTime()+(DAY_SECOND* day)));
		return dateStart;
	}
	
	public static int getDateCycle(int discrepantDays) {
		return discrepantDays/DATE_CYCLE;
	}
	
	public static int getDateCycleRemainder(int discrepantDays) {
		return discrepantDays%DATE_CYCLE;
	}
	
	/**
	 * 比较两个时间的大小
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareDate(Date d1,Date d2) {
		System.out.println(d1.before(d2));
		System.out.println(d1.after(d2));
		return false;
	}
	
	public static void main(String[] args) {
		String date1 = "2017-12-12 23:59:59";
		String date2 = "2017-11-13 23:59:59";
		
//		compareDate(date1, date2);
		
		int days = getDiscrepantDays(date1,date2);
		System.out.println(days);
		int cycle = getDateCycle(days);
		int cycleR = getDateCycleRemainder(days);
		System.out.println(cycle);
		System.out.println(cycleR);
		
		Date date = new Date();
		String nowDate = getDateFormat(date);
		System.out.println(nowDate);
		
		Date datea  = addDay(100);
		String nowDate1 = getDateFormat(datea);
		System.out.println(nowDate1);
		
		compareDate(date, datea);
	}

}
