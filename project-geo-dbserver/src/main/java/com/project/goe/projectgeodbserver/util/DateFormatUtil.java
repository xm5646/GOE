package com.project.goe.projectgeodbserver.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormatUtil {
	private DateFormatUtil() {
	}

	public static String DateObjectToString(Date date) {
		if (null == date)
			return null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static int compareDateObject(Date srcDate, Date destDate) {
		if ((null == srcDate) || (null == destDate))
			throw new RuntimeException("比较日期不能为null");

		long srcMillisecond = srcDate.getTime();
		long destMillsecond = destDate.getTime();
		long gap = destMillsecond - srcMillisecond;

		if (0 == gap)
			return 0;
		else if (gap > 0)
			return 1;
		else
			return -1;
	}

	// 获取当前月的开始和结束时间
	public static List<Date> getStartDateAndEndDateOfNowMonth() {
		Date startDate = null;
		Date endDate = null;

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int days = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(year, month, 1, 0, 0, 0);
		startDate = calendar.getTime();

		calendar.set(year, month, days, 23, 59, 59);
		endDate = calendar.getTime();

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startDate);
		dateList.add(endDate);
		
		System.out.println(startDate);
		System.out.println(endDate);
		
		return dateList;
	}

	// 获取上一周的开始和结束时间
	public static List<Date> getStartDayAndEndDayOfLastWeek() {
		Date startDateOfLastWeek = null;
		Date endDateOfLastWeek = null;
		List<Date> dateList = new ArrayList<Date>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar calendar = Calendar.getInstance();
		//获取本周第几天
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println(dayOfWeek);
		//获取当天时分秒
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		int s = calendar.get(Calendar.SECOND);
		//当前时间
		long nowTime = System.currentTimeMillis();
		calendar.setTimeInMillis(nowTime);
		System.out.println(simpleDateFormat.format(calendar.getTime()));
		
		//上周截止时间
		long endTimeOfLastWeek = 0;
		//上周开始时间
		long startTimeOfLastWeek = 0;
		endTimeOfLastWeek = nowTime - (dayOfWeek - 1)*24*3600*1000 - h*3600*1000 - m*60*1000 - s*1000 - 1000;
		
		calendar.setTimeInMillis(endTimeOfLastWeek);
		System.out.println(simpleDateFormat.format(calendar.getTime()));
		
		startTimeOfLastWeek =  nowTime - (dayOfWeek - 1 + 7)*24*3600*1000 - h*3600*1000 - m*60*1000 - s*1000;
		calendar.setTimeInMillis(startTimeOfLastWeek);
		System.out.println(simpleDateFormat.format(calendar.getTime()));
		
		startDateOfLastWeek = new Date(startTimeOfLastWeek);
		endDateOfLastWeek = new Date(endTimeOfLastWeek);
		dateList.add(startDateOfLastWeek);
		dateList.add(endDateOfLastWeek);
		
		return dateList;
	}

	public static void main(String[] args) {
		getStartDateAndEndDateOfNowMonth();
		getStartDayAndEndDayOfLastWeek();
	}
}
