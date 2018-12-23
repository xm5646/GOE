package com.project.goe.projectgeodbserver.viewentity;

public class BonusVO {
	private String account;
	private long totalMoney;
	private long bonusNumber;
	private long manageCost;
	private long productCoinNumber;
	private long repeatCoinNumber;
	private String payTime;
	private String createTime;
	private String touchType;

	public String getTouchType() {
		return touchType;
	}

	public void setTouchType(String touchType) {
		this.touchType = touchType;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public long getBonusNumber() {
		return bonusNumber;
	}

	public void setBonusNumber(long bonusNumber) {
		this.bonusNumber = bonusNumber;
	}

	public long getManageCost() {
		return manageCost;
	}

	public void setManageCost(long manageCost) {
		this.manageCost = manageCost;
	}

	public long getProductCoinNumber() {
		return productCoinNumber;
	}

	public void setProductCoinNumber(long productCoinNumber) {
		this.productCoinNumber = productCoinNumber;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long getRepeatCoinNumber() {
		return repeatCoinNumber;
	}

	public void setRepeatCoinNumber(long repeatCoinNumber) {
		this.repeatCoinNumber = repeatCoinNumber;
	}
}
