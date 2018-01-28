package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UserDrawCashRequest {
	// 用户账户名称
	@NotBlank(message = "用户名不能为空")
	@Size(min = 5, max = 20, message = "用户名的长度不合法(5-20)!")
	private String account;
	// 银行卡号
	@NotBlank(message = "银行卡号不能为空!")
	@Pattern(regexp = "(^\\d{16,19}$", message = "银行卡号不合法!")
	private String cardNumber;
	// 申请提现金额
	@NotNull(message = "提现金额不能为空!")
	@Min(value = 0, message = "提取金额不合法!")
	@Max(value = Long.MAX_VALUE, message = "提取金额不合法!")
	private Double drawNumber;

	@NotNull(message = "电话号码不能为空!")
	private String phone;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String paymentPassword;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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
