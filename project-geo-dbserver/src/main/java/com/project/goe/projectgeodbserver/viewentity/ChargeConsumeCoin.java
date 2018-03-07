package com.project.goe.projectgeodbserver.viewentity;

public class ChargeConsumeCoin {
	//充值账户
	private String account;
	//管理员支付密码
	private String paymentPassword;
	//充值报单币额度
	private double consumeCoin;

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

	public double getConsumeCoin() {
		return consumeCoin;
	}

	public void setConsumeCoin(double consumeCoin) {
		this.consumeCoin = consumeCoin;
	}

}
