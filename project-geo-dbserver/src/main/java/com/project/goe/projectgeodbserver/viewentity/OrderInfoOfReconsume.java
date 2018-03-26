package com.project.goe.projectgeodbserver.viewentity;

public class OrderInfoOfReconsume {
	// 订单编号
	private long orderId;
	// 订单产生时间
	private String createTime;
	// 创建订单的用户编号
	private String account;
	// 收件联系人
	private String receiverName;
	// 收件人电话
	private String phone;
	// 收件人快递地址信息
	private String[] addressInfo;
	// 订单描述
	private String description;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String[] getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String[] addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
