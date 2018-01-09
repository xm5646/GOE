package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.service.EarningService;
import com.project.goe.projectgeodbserver.statusType.TouchType;

/**
 * 收益控制层
 *
 */
@RestController
@RequestMapping("/earning")
@CrossOrigin
public class EarningController {

	@Autowired
	private EarningService earningService;
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Earning save(@ModelAttribute Earning earning) {
		return this.earningService.save(earning);
	}
	
}
