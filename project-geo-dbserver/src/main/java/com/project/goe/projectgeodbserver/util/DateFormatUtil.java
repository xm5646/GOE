package com.project.goe.projectgeodbserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	private DateFormatUtil() {}
	
	public static String getNowDateShort(Date date) {
		if(null == date)
			return null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		String dateString = formatter.format(date);   
	    return dateString; 
	}
	
	public static void main(String[] args) {
		System.out.println(getNowDateShort(null));
	}
	
}
