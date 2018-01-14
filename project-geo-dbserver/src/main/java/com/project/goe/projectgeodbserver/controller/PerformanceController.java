package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.viewentity.UserAccumulatePerformence;
import com.project.goe.projectgeodbserver.viewentity.UserAndFollowerPerformance;

@RestController
@RequestMapping("/performance")
@CrossOrigin
public class PerformanceController {
	@Autowired
	private PerformanceService performanceService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Performance save(@ModelAttribute Performance performance) {
		return this.performanceService.save(performance);
	}
	
	@RequestMapping("/findAll")
	public Iterable<Performance> getAll(){
		return this.performanceService.getAll();
	}
	
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id") long id) {
		return this.performanceService.delete(id);
	}
	
	@RequestMapping("/{userId}")
	public Performance p(@PathVariable long userId) {
		return this.performanceService.findByUserId(userId);
	}

	// 根据userId，查询当前用户业绩信息和子节点业绩信息
	@RequestMapping("/findUserAndFollowerPerformance/{userId}")
	public UserAndFollowerPerformance findUserAndFollowerPerformance(@PathVariable long userId) {
		// 查询当前用户
		User user = this.userService.findByUserId(userId);
		if (null == user)
			throw new RuntimeException("用户id不存在!");

		long departmentA = user.getDepartmentA();
		long departmentB = user.getDepartmentB();
		long departmentC = user.getDepartmentC();

		// 基于用户id，查询当前用户业绩信息
		UserAccumulatePerformence u = getUserAccumulatePerformance(userId);
		// 基于departmentA，查询用户业绩信息
		UserAccumulatePerformence uA = getUserAccumulatePerformance(departmentA);
		// 基于departmentA，查询用户业绩信息
		UserAccumulatePerformence uB = getUserAccumulatePerformance(departmentB);
		// 基于departmentA，查询用户业绩信息
		UserAccumulatePerformence uC = getUserAccumulatePerformance(departmentC);
	
		UserAndFollowerPerformance userAndFollowerPerformance = new UserAndFollowerPerformance();
		userAndFollowerPerformance.setAccount(user.getAccount());
		userAndFollowerPerformance.setPerformanceA(u.getPerformanceA());
		userAndFollowerPerformance.setPerformanceB(u.getPerformanceB());
		userAndFollowerPerformance.setPerformanceC(u.getPerformanceC());
		
		userAndFollowerPerformance.setDepartUserA(uA);
		userAndFollowerPerformance.setDepartUserB(uB);
		userAndFollowerPerformance.setDepartUserC(uC);

		return userAndFollowerPerformance;
	}

	private UserAccumulatePerformence getUserAccumulatePerformance(long userId) {
		// 基于userId，查询用户信息
		User user = this.userService.findByUserId(userId);
		// 基于userId，查询用户业绩信息
		Performance performance = this.performanceService.findByUserId(userId);
		// 返回UserPerformence对象：用户名，部门A、B、C业绩
		UserAccumulatePerformence userAccumulatePerformence = new UserAccumulatePerformence();
		userAccumulatePerformence.setAccount(user.getAccount());
		userAccumulatePerformence.setPerformanceA(performance.getDepartAcount());
		userAccumulatePerformence.setPerformanceB(performance.getDepartBcount());
		userAccumulatePerformence.setPerformanceB(performance.getDepartCcount());

		return userAccumulatePerformence;
	}

}
