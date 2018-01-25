package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
	public OrderInfo findByOrderId(long orderId);
	public List<OrderInfo> findByUserId(long userId);
	public List<OrderInfo> findByExpressId(long expressId);
}
