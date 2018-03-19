package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.service.OrderInfoService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RequestMapping("/goeIndexOrderController")
@RestController
@CrossOrigin
public class GoeIndexOrderController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	//分页查询所有订单
	public RetMsg findAllOrders() {
		
		
		return null;
	}
	
	
	//基于account，分页查询用户所有订单
	
	//分页查询所有待处理的重销订单
	
	//基于account，分页查询用户待处理的重销订单
	
	
	//分页查询所有待处理的积分订单
	
	
	//基于account，分页查询用户待处理的积分订单
	
	
}
