package com.project.goe.projectgeodbserver.statusType;

import java.util.HashMap;
import java.util.Map;

import com.project.goe.projectgeodbserver.entity.*;

public class Main {

	public static void main(String[] args) {
		long l =1;
		System.out.println(l+1);
		
		Earning e = new Earning();
		e.setUserid(123l);
		Map<Long,Earning> earns = new HashMap<>();
		earns.put(123l, e);
		System.out.println("earns=====");
		printMap(earns);
		Map<Long,Earning> earns2 =test(123l, earns);
		System.out.println("earns=====222");
		printMap(earns);
		System.out.println("earns2=====");
		printMap(earns2);
	}

	private static Map<Long,Earning> test(Long l,Map<Long,Earning> earns){
		Earning e = new Earning();
		e.setUserid(321l);
		Earning e2 = earns.get(123l);
		e2.setDayMoney(1232.1);
		earns.put(321l, e);
		return earns;
	}
	
	private static void printMap(Map<Long,Earning> map) {
		for(Earning e : map.values()) {
			System.out.println(e.toString());
		}
	}
}
