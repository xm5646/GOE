package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.service.PerformanceService;

@RestController
@RequestMapping("/performance")
@CrossOrigin
public class PerformanceController {
	@Autowired
	private PerformanceService performanceService;
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Performance save(@ModelAttribute Performance performance) {
		return this.performanceService.save(performance);
	}
}
