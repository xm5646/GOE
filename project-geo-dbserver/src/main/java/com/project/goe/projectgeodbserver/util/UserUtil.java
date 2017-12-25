package com.project.goe.projectgeodbserver.util;

import java.util.Date;
import java.util.Random;

import com.project.goe.projectgeodbserver.entity.User;

public class UserUtil {
	public static User addUser(User user,User parentUser,String departmentType) {
		if ("A".equals(departmentType)) {
			user.setDepartmentA(parentUser.getUserid());
		}else if ("B".equals(departmentType)) {
			user.setDepartmentB(parentUser.getUserid());
		}else if ("C".equals(departmentType)) {
			user.setDepartmentC(parentUser.getUserid());
		}else {
			return null;
		}
		user.setParentid(parentUser.getUserid());
		return user;
	}
	
	public static User getTestUser() {
		User u = new User();
		u.setName("zs001"+new Random().nextInt(10000));
		u.setPassword("123456");
		u.setCreateTime(new Date());
		return u;
	}

	public static User getTestUser(User parentUser, String departmentType) {
		return addUser(getTestUser(), parentUser, departmentType);
	}
}
