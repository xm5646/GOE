package com.project.goe.projectgeodbserver.util;

public class StringUtils {

	public static boolean isBlank(String filePath) {
		if (filePath != null && filePath.length()>0) {
			return true;
		}
		return false;
	}

}
