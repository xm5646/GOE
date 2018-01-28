package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.OrderInfo;
import com.project.goe.projectgeodbserver.service.OrderInfoService;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderInfoController {
	
	@Autowired
	private OrderInfoService orderInfoService;

	// 分页查询所有订单(基于订单创建时间)
	@GetMapping("/findAllOrdersBySort")
	public Page<OrderInfo> findAllOrdersRecord(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.orderInfoService.findAllOrdersRecord(pageable);
		} catch (Exception e) {
			throw new RuntimeException("查询失败!");
		}
	}

}
