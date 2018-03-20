package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

	public Page<OrderInfo> findAllOrdersRecord(Pageable pageable) {
		return this.orderInfoRepository.findAll(pageable);
	}

	// 更新或新增订单信息
	@Transactional
	public OrderInfo save(OrderInfo orderInfo) {
		return this.orderInfoRepository.save(orderInfo);
	}

	// 删除单个订单信息
	@Transactional
	public void delete(OrderInfo orderInfo) {
		this.orderInfoRepository.delete(orderInfo);
	}

	// 分页查询所有订单信息
	public Page<OrderInfo> findAllOrderInfo(Pageable pageable) {
		return this.orderInfoRepository.findAll(pageable);
	}

	// 分页查询：基于用户名,查询所用订单
	public Page<OrderInfo> findOrderInfoByAccount(OrderInfo orderInfo, Pageable pageable) {
		Specification<OrderInfo> spec = new Specification<OrderInfo>() {

			@Override
			public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("userId").as(long.class), orderInfo.getUserId());
				return p;
			}
		};

		return this.orderInfoRepository.findAll(spec, pageable);
	}

	// 分页查询：基于orderType和isDelivery：重销兑换或积分兑换，分页查询所有用户未发货订单
	public Page<OrderInfo> findOrderInfoByOrderTypeAndIsDelivery(OrderInfo orderInfo, Pageable pageable) {
		Specification<OrderInfo> spec = new Specification<OrderInfo>() {

			@Override
			public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("orderType").as(String.class), orderInfo.getOrderType());
				Predicate p2 = cb.equal(root.get("isDelivery").as(String.class), orderInfo.getIsDelivery());
				return cb.and(p1, p2);
			}
		};

		return this.orderInfoRepository.findAll(spec, pageable);
	}
	
	//分页查询：基于orderType、isDelivery和account：重销兑换或积分兑换，分页查询指定用户待处理的订单
	public Page<OrderInfo> findOrderInfoByOrderTypeAndIsDeliveryAndAccount(OrderInfo orderInfo, Pageable pageable) {
		Specification<OrderInfo> spec = new Specification<OrderInfo>() {

			@Override
			public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("orderType").as(String.class), orderInfo.getOrderType());
				Predicate p2 = cb.equal(root.get("userId").as(long.class), orderInfo.getUserId());
				Predicate p3 = cb.equal(root.get("isDelivery").as(String.class), orderInfo.getIsDelivery());
				return cb.and(p1,p2,p3);
			}
		};

		return this.orderInfoRepository.findAll(spec, pageable);
	}
	

	// 基于发货状态和消费类型查询订单列表
	public List<OrderInfo> findByIsDeliveryAndOrderType(String deliveryStatus, String orderType) {
		return this.orderInfoRepository.findByIsDeliveryAndOrderType(deliveryStatus, orderType);
	}

}
