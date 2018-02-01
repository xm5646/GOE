package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class UserDrawCashRequest {
	// 用户账户名称
	@NotBlank(message = "用户名不能为空")
	private String account;
	// 银行卡号id
	@NotNull(message = "银行卡号不能为空!")
	private long cardInfoId;
	// 申请提现金额
	@NotNull(message = "提现金额不能为空!")
	private Double drawNumber;

	@NotNull(message = "电话号码不能为空!")
	private String phone;

	@NotBlank(message = "用户支付密码不能为空")
	private String paymentPassword;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getCardInfoId() {
		return cardInfoId;
	}

	public void setCardInfoId(long cardInfoId) {
		this.cardInfoId = cardInfoId;
	}

	public Double getDrawNumber() {
		return drawNumber;
	}

	public void setDrawNumber(Double drawNumber) {
		this.drawNumber = drawNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

}
