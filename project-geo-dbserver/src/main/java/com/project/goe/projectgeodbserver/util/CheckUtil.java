package com.project.goe.projectgeodbserver.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;

public class CheckUtil {
	public static void audit() {
		//创建用户信息触发
		//用户所有上级的信息情况并更新
		   //更新上级每个节点 有A B C部门数目  ABC新增数目 审计表变为业绩表  每人1条
		   //判断用户是否达到条件
		Long userid = 1l;
		User u1 = new User();
		u1.setUserId(userid);
		u1.setNickName("user1");
		u1.setDepartmentA(2);
		u1.setWeightCode(1);
		u1.setDepartmentA(2);
		
		User u2 = new User();
		u2.setUserId(2l);
		u2.setNickName("user2");
		u2.setParentId(1l);
		u2.setDepartmentA(1);
		u2.setWeightCode(2);
		u2.setDepartmentA(3);
		
		User u3 = new User();
		u3.setUserId(3l);
		u3.setNickName("user3");
		u3.setParentId(2l);
		u3.setWeightCode(3);
		u3.setDepartmentA(4);
		
		User u4 = new User();
		u4.setUserId(4l);
		u4.setNickName("user4");
		u4.setParentId(3l);
		u4.setWeightCode(4);
		u4.setDepartmentA(3);
		
		Performance p1 = new Performance();
		p1.setpId(1l);
		p1.setUserId(1l);
		p1.setDepartAcount(3);
		Performance p2 = new Performance();
		p2.setpId(2l);
		p2.setUserId(1l);
		p2.setUserId(2l);
		p2.setDepartAcount(2);
		Performance p3 = new Performance();
		p3.setpId(3l);
		p3.setUserId(1l);
		p3.setUserId(3l);
		p3.setDepartAcount(1);
		Performance p4 = new Performance();
		p4.setpId(4l);
		p4.setUserId(1l);
		p4.setUserId(4l);
		
		//用户表
		Map<Long,User> userMap = new HashMap<Long, User>();
		userMap.put(1l, u1);
		userMap.put(2l, u2);
		userMap.put(3l, u3);
		userMap.put(4l, u4);
		//业绩表
		Map<Long,Performance> perMap = new HashMap<Long,Performance>();
		perMap.put(1l, p1);
		perMap.put(2l, p2);
		perMap.put(3l, p3);
		perMap.put(4l, p4);
		
		printMap(userMap);
		printMap(perMap);
		//收益表：tb_earning
		Map<Long,Earning> earnMap = new HashMap<Long,Earning>();
		//奖金发放表：tb_bonus_paylist
		Map<Long,BonusPayList> bonusPayMap = new HashMap<Long,BonusPayList>();
		
		computePer(4l, userMap, perMap);
		printMap(userMap);
		printMap(perMap);
		
	}
	
	
	private static void computePer(Long userid,Map<Long,User> userMap,Map<Long,Performance> perMap) {
		//添加最下级节点的时候的用户信息
		User u = userMap.get(userid);
		Long pid = u.getParentId();
		int weightCode = 4;
		for (int i = weightCode; i >0; i--) {
			User pu = userMap.get(pid);
			System.out.println("k:"+perMap.get(pid).getDepartAcount());
			if(userid == pu.getDepartmentA()) {
				perMap.get(pid).setAddDepartAcount(perMap.get(pid).getAddDepartAcount()+1);
				perMap.get(pid).setDepartAcount(perMap.get(pid).getDepartAcount()+1);
			}else if(userid == pu.getDepartmentB()) {
				perMap.get(pid).setAddDepartBcount(perMap.get(pid).getAddDepartBcount()+1);
				perMap.get(pid).setDepartBcount(perMap.get(pid).getDepartBcount()+1);
			}else if(userid == pu.getDepartmentC()) {
				perMap.get(pid).setAddDepartCcount(perMap.get(pid).getAddDepartCcount()+1);
				perMap.get(pid).setDepartCcount(perMap.get(pid).getDepartCcount()+1);
			}			
			System.out.println("e:"+perMap.get(pid).getDepartAcount());
			pid = pu.getParentId();
			userid = pu.getUserId();
		}
		
	}
	
	public void auditDay() {
		
	}
	
	public static void main(String[] args) {
		audit();
	}
	
	private static void printMap(Map map) {
		for(Object e : map.values()) {
			System.out.println(e.toString());
		}
	}
}
