package com.project.goe.projectgeodbserver.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.UserType;
import com.project.goe.projectgeodbserver.util.MD5Util;

@RestController
@RequestMapping("/testUser")
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
			user.setAccount("admin001");
			user.setPassword(MD5Util.encrypeByMd5("admin001"));
			user.setActivateTime(null);
			Date cTime = new Date();
			user.setAssessDate(cTime);
			user.setAssessStatus(false);
			user.setBonusCoin(10000f);
			user.setConsumeCoin(10000f);
			user.setCreateTime(cTime);
			user.setDepartmentA(0);
			user.setDepartmentB(0);
			user.setDepartmentC(0);
			user.setParentId(0);
			user.setWeightCode(1);
			user.setUserType(UserType.ADMIN);
			
			//新增公司管理员账户
			User u = new User();
			u.setAccount("administrator");
			u.setPassword(MD5Util.encrypeByMd5("admin001"));
			u.setCreateTime(cTime);
			u.setUserType(UserType.COMPANY);
			
			this.userService.save(user);
			this.userService.save(u);
			
			this.performanceService.deleteAllPerformance();
			Performance p = new Performance();
			p.setUserId(user.getUserId());
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
