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
	private double drawNumber;

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

	public double getDrawNumber() {
		return drawNumber;
	}

	public void setDrawNumber(double drawNumber) {
		this.drawNumber = drawNumber;
	}

}
