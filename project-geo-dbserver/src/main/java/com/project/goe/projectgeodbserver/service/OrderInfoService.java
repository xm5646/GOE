package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.OrderInfo;
import com.project.goe.projectgeodbserver.repository.OrderInfoRepository;

@Service
public class OrderInfoService {
	@Autowired
	private OrderInfoRepository orderInfoRepository;

	public OrderInfo findByOrderId(long orderId) {
		return this.orderInfoRepository.findByOrderId(orderId);
	}

	public List<OrderInfo> findByUserId(long userId) {
		return this.orderInfoRepository.findByUserId(userId);
	}

	public List<OrderInfo> findByExpressId(long expressId) {
		return this.orderInfoRepository.findByExpressId(expressId);
	}

	public  Page<OrderInfo> findAllOrdersRecord(Pageable pageable) {
		return this.orderInfoRepository.findAll(pageable);
	}
	
	//更新或新增订单信息
	@Transactional
	public OrderInfo save(OrderInfo orderInfo) {
		return this.orderInfoRepository.save(orderInfo);
	}

	// 删除单个订单信息
	@Transactional
	public void delete(OrderInfo orderInfo) {
		this.orderInfoRepository.delete(orderInfo);
	}

}
