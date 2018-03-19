package com.project.goe.projectgeodbserver.viewentity;

import java.util.Date;

public class OrderInfoVO {
	// 订单编号
	private long orderId;
	// 订单关联用户
	private String account;
	// 快递地址信息
	private String address;
	private String receiverName;
	private String phone;
	// 订单类型：重销购买or积分兑换
	private String orderType;
	// 产品数量
	private long productCount;
	// 订单状态:未发货
	private String isDelivery;
	// 订单描述
	private String description;
	// 快递单号
	private String expressNo;
	// 快递公司
	private String expressCompany;
	// 订单产生时间
	private Date createTime;
}
