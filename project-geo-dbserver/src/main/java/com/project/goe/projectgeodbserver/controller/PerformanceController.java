package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
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
	public Iterable<Performance> getAll() {
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
	@GetMapping("/findUserAndFollowerPerformance")
	public RetMsg findUserAndFollowerPerformance(@RequestParam("account") String account) {
		// 查询当前用户
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");
		try {
			long departmentA = user.getDepartmentA();
			long departmentB = user.getDepartmentB();
			long departmentC = user.getDepartmentC();

			// 基于用户id，查询当前用户业绩信息
			UserAccumulatePerformence u = getUserAccumulatePerformance(user.getUserId());
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

			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(userAndFollowerPerformance);
			retMsg.setMessage("用户及用户关联业绩查询成功!");
			retMsg.setSuccess(true);
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户及用户关联业绩查询成功>>>>>" +e.getMessage());
		}
	}

	private UserAccumulatePerformence getUserAccumulatePerformance(long userId) {
		if (0 == userId)
			return null;

		// 基于userId，查询用户信息
		User user = this.userService.findByUserId(userId);
		// 基于userId，查询用户业绩信息
		Performance performance = this.performanceService.findByUserId(userId);
		// 返回UserPerformence对象：用户名，部门A、B、C业绩
		UserAccumulatePerformence userAccumulatePerformence = new UserAccumulatePerformence();
		userAccumulatePerformence.setAccount(user.getAccount());
		userAccumulatePerformence.setPerformanceA(performance.getDepartAcount());
		userAccumulatePerformence.setPerformanceB(performance.getDepartBcount());
		userAccumulatePerformence.setPerformanceC(performance.getDepartCcount());

		return userAccumulatePerformence;
	}

}
