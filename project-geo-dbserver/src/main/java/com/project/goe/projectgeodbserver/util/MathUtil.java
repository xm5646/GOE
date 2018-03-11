package com.project.goe.projectgeodbserver.util;

import java.util.List;

public class MathUtil {

	public static List<Integer> getDateCycle() {
		return null;
	}

	public static void main(String[] args) {
		double d = 12.12321321;
		System.out.println((int)(Math.floor(d)));
		System.out.println(Math.ceil(d));
	}
	
	public static int floor(double value) {
		return (int)(Math.floor(value));
	}
	
	public static int ceil(double value) {
		return (int)(Math.ceil(value));
	}
	
}
