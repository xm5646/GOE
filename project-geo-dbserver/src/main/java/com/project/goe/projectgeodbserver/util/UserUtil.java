package com.project.goe.projectgeodbserver.util;

import java.util.Date;
import java.util.Random;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;

import java.util.Date;
import java.util.Random;

import com.project.goe.projectgeodbserver.entity.User;

public class UserUtil {
	public static User addUser(User user,User parentUser,String departmentType) {
		if ("A".equals(departmentType)) {
			parentUser.setDepartmentA(user.getUserId());
		}else if ("B".equals(departmentType)) {
			parentUser.setDepartmentB(user.getUserId());
		}else if ("C".equals(departmentType)) {
			parentUser.setDepartmentC(user.getUserId());
		}else {
			return null;
		}
		user.setParentId(user.getUserId());
		return user;
	}
	
	public static User getTestUser() {
		Date createDate = new Date();
		User u = new User();
		String name = "zs001"+new Random().nextInt(10000);
		u.setNickName(name);
		u.setAccount(name);
		u.setPassword("123456");
		u.setCreateTime(createDate);
		u.setUserLevel(UserLevel.CONSUMER);
		u.setUserType(UserType.COMMON);
		u.setAssessStatus(false);
		u.setAssessDate(createDate);
		return u;
	}

	public static User getTestUser(User parentUser, String departmentType) {
		return addUser(getTestUser(), parentUser, departmentType);
	}
}
