package com.project.goe.projectgeodbserver.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.MD5Util;

public class TestUser {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PerformanceService performanceService;
	
	// 初始化用户
	@GetMapping("/initUser")
	public String initUserAndPerformance() {
		try {
			// 删除用户表所有数据
			this.userService.deleteAllUser();
			// 新增admin用户
			User user = new User();
			user.setAccount("admin");
			user.setPassword(MD5Util.encrypeByMd5("admin"));
			user.setActivateTime(null);
			Date cTime = new Date();
			user.setAssessDate(cTime);
			user.setAssessStatus(false);
			user.setBonusCoin(0f);
			user.setConsumeCoin(0f);
			user.setCreateTime(cTime);
			user.setDepartmentA(0);
			user.setDepartmentB(0);
			user.setDepartmentC(0);
			user.setParentId(0);
			user.setWeightCode(1);

			User u = this.userService.save(user);

			this.performanceService.deleteAllPerformance();
			Performance p = new Performance();
			p.setUserId(u.getUserId());
			p.setDepartAcount(0);
			p.setDepartAcount(0);
			p.setDepartCcount(0);
			p.setCreateTime(new Date());
			p.setUpdateTime(new Date());

			this.performanceService.save(p);
			
			return "初始化用户表和业绩表成功";
		} catch (Exception e) {
			throw new RuntimeException("用户信息初始化失败");
		}
	}
}
