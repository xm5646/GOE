package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public class ProductCoinTransferRequest {
	@NotBlank(message = "支付方用户名不能为空!")
	private String account;

	@NotNull(message = "兑换产品积分数不能为空!")
	private Double productCoin;

	@NotBlank(message = "用户支付密码不能为空")
	private String paymentPassword;

	private long expressId = -1;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public long getExpressId() {
		return expressId;
	}

	public void setExpressId(long expressId) {
		this.expressId = expressId;
	}

}
