package com.project.goe.projectgeodbserver.controller;

import com.project.goe.projectgeodbserver.entity.*;
import com.project.goe.projectgeodbserver.server.EarnServerSchedul;
import com.project.goe.projectgeodbserver.service.*;
import com.project.goe.projectgeodbserver.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/util")
@CrossOrigin
public class UtilController {

	@Autowired
	private UserLoginSetting userLoginSetting;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private EarningService earningService;

	@Autowired
	private UserRepeatCheckService UserRepeatCheckService;
	
	@Autowired
	private PerformanceService performanceService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	@Autowired
	private CardInfoService cardInfoService;

	// 将业务全部移动到调度服务上
	@Autowired
	private EarnServerSchedul earnServerSchedul;

	@RequestMapping("/updateUserLevel")
	public String UpdateUserLevel() {
		System.out.println("更新用户级别");
		Iterable<User> userlist = this.userService.getAll();
		for (User user : userlist) {
			if (user.getAccount().equals("administrator")) {
			} else {
				Performance p = performanceService.findByUserId(user.getUserId());
				BusinessEntity entity = BusinessUtil.getBusinesLevel(p.getDepartAcount(), p.getDepartBcount(), p.getDepartCcount());
				System.out.println("当前用户:" + user.getAccount() + ",业绩信息:" + entity.toString());
				user.setUserLevel(entity.getUserLevel());
				userService.save(user);
			}

		}
		return "更新用户级别成功";
	}

}
