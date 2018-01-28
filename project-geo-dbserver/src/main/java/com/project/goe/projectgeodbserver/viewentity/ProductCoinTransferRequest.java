package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ProductCoinTransferRequest {
	@NotBlank(message = "支付方用户名不能为空!")
	@Size(min = 5, max = 20, message = "用户名长度不合法!")
	private String sendAccount;

	@NotBlank(message = "收入方用户名不能为空!")
	@Size(min = 5, max = 20, message = "用户名长度不合法!")
	private String receiveAccout;

	@NotNull(message = "兑换产品积分数不能为空!")
	@Min(value = 0, message = "积分数不合法!")
	@Max(value = Long.MAX_VALUE, message = "积分数不合法!")
	private Double productCoin;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String paymentPassword;

	@NotNull(message = "快递地址id不能为空!")
	private Long expressId;

	public String getSendAccount() {
		return sendAccount;
	}

	public void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}

	public String getReceiveAccout() {
		return receiveAccout;
	}

	public void setReceiveAccout(String receiveAccout) {
		this.receiveAccout = receiveAccout;
	}

	public Double getProductCoin() {
		return productCoin;
	}

	public void setProductCoin(Double productCoin) {
		this.productCoin = productCoin;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public Long getExpressId() {
		return expressId;
	}

	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}

}
