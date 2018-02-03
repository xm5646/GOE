package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.goe.projectgeodbserver.statusType.DeliveryStatus;

/**
 * @descripton:订单信息
 */
@Entity
@Table(name = "tb_orderInfo")
public class OrderInfo {
	// 订单编号
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;

	// 订单关联用户
	@Column(nullable = false)
	private long userId;

	// 订单关联用户快递id
	@Column(nullable = false)
	private long expressId;

	// 产品名称
	@Column
	private String prouductName;

	// 订单类型：重销购买or积分兑换
	@Column(nullable = false)
	private String orderType;

	// 产品数量
	@Column(nullable = false)
	private long productCount;

	// 订单总价格
	@Column
	private double totalPrice;

	// 订单状态:未发货or已发货？
	@Column(nullable = false)
	private String isDelivery = DeliveryStatus.ORDER_DELIVERY_NO;

	// 订单描述
	@Column
	private String description;

	// 快递单号
	@Column
	private String expressNo;

	// 快递公司
	@Column
	private String expressCompany;

	// 订单产生时间
	@Column(nullable = false)
	private Date createTime;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getExpressId() {
		return expressId;
	}

	public void setExpressId(long expressId) {
		this.expressId = expressId;
	}

	public String getProuductName() {
		return prouductName;
	}

	public void setProuductName(String prouductName) {
		this.prouductName = prouductName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public long getProductCount() {
		return productCount;
	}

	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
