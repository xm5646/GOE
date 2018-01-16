package com.project.goe.projectgeodbserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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

	public static boolean compareDateObject(Date srcDate, Date destDate) {
		if((null == srcDate) || (null == destDate) ) 
			throw new RuntimeException("比较日期不能为null");
		
		long srcMillisecond = srcDate.getTime();
		long destMillsecond = destDate.getTime();
		long gap = destMillsecond - srcMillisecond;
		
		if(0 == gap)
			return true;
		else
			return true;
	}

}
