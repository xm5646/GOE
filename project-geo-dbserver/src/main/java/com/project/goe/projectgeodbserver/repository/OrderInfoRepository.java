package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaSpecificationExecutor<OrderInfo>,JpaRepository<OrderInfo, Long> {
	public OrderInfo findByOrderId(long orderId);
	public List<OrderInfo> findByUserId(long userId);
	// 分页查询：基于用户名，按时间降序排序
	public Page<OrderInfo> findAll(Specification<OrderInfo> spec, Pageable pageable);
	
	public List<OrderInfo> findByIsDeliveryAndOrderType(String isDelivery,String orderType);
}
