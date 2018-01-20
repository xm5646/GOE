package com.project.goe.projectgeodbserver.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.service.BonusPayListService;


@CrossOrigin
@RestController
@RequestMapping("/testBonus")
public class TestBonus {
	@Autowired
	private BonusPayListService bonusPayListService;
	
	@GetMapping("/initBonus")
	public String initBonus() throws Exception {
		this.bonusPayListService.deleteAllBonus();
		
		for(int i = 0;i < 27;++ i) {
			BonusPayList bonusPayList = new BonusPayList();
			bonusPayList.setBonusNumber(1000f);
			bonusPayList.setManageCost(200f);
			bonusPayList.setPayTime(new Date());
			
			Thread.sleep(3000);
			
			bonusPayList.setProductCoinNumber(1000f);
			bonusPayList.setTotalMoney(20000f);
			bonusPayList.setUserId(i);
			
			this.bonusPayListService.save(bonusPayList);
		}
		
		return "初始化奖金发放表成功";
	}
	
}
