package com.project.goe.projectgeodbserver.viewentity;

public class FinanceOfAll {
	// 收入
	private double accumulateEarning;
	// 支出
	private double productCoinCost;
	private double managementCost;
	private double bonusPaymentCost;
	private double repeatCoinCost;

	public double getAccumulateEarning() {
		return accumulateEarning;
	}

	public void setAccumulateEarning(double accumulateEarning) {
		this.accumulateEarning = accumulateEarning;
	}

	public double getProductCoinCost() {
		return productCoinCost;
	}

	public void setProductCoinCost(double productCoinCost) {
		this.productCoinCost = productCoinCost;
	}

	public double getManagementCost() {
		return managementCost;
	}

	public void setManagementCost(double managementCost) {
		this.managementCost = managementCost;
	}

	public double getBonusPaymentCost() {
		return bonusPaymentCost;
	}

	public void setBonusPaymentCost(double bonusPaymentCost) {
		this.bonusPaymentCost = bonusPaymentCost;
	}

	public double getRepeatCoinCost() {
		return repeatCoinCost;
	}

	public void setRepeatCoinCost(double repeatCoinCost) {
		this.repeatCoinCost = repeatCoinCost;
	}
}
