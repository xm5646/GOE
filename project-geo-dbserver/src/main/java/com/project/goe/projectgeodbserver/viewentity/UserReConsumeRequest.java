package com.project.goe.projectgeodbserver.viewentity;

import org.hibernate.validator.constraints.NotBlank;

public class UserReConsumeRequest {
	@NotBlank(message = "用户名不能为空")
	private String account;

	@NotBlank(message = "用户支付密码不能为空")
	private String paymentPassword;

	private long expressId;

	public long getExpressId() {
		return expressId;
	}

	public void setExpressId(long expressId) {
		this.expressId = expressId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

}
