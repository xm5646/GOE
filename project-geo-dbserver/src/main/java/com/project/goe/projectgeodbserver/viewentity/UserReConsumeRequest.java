package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class UserReConsumeRequest {
	@NotBlank(message = "用户名不能为空")
	private String account;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String paymentPassword;

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
