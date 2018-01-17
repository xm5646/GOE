package com.project.goe.projectgeodbserver.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bonus.percentage")
public class BonusPayPercentage {
	// 管理费 0.1
	private double manageCostPercentage;
	// 奖金 0.81
	private double bonusNumberPercentage;
	// 产品积分 0.09
	private double productCoinNumberPercentage;
	// 产品报单币单价
	private double consumeCoinUnitPrice;

	public double getManageCostPercentage() {
		return manageCostPercentage;
	}

	public void setManageCostPercentage(double manageCostPercentage) {
		this.manageCostPercentage = manageCostPercentage;
	}

	public double getBonusNumberPercentage() {
		return bonusNumberPercentage;
	}

	public void setBonusNumberPercentage(double bonusNumberPercentage) {
		this.bonusNumberPercentage = bonusNumberPercentage;
	}

	public double getProductCoinNumberPercentage() {
		return productCoinNumberPercentage;
	}

	public void setProductCoinNumberPercentage(double productCoinNumberPercentage) {
		this.productCoinNumberPercentage = productCoinNumberPercentage;
	}

	public double getConsumeCoinUnitPrice() {
		return consumeCoinUnitPrice;
	}

	public void setConsumeCoinUnitPrice(double consumeCoinUnitPrice) {
		this.consumeCoinUnitPrice = consumeCoinUnitPrice;
	}

}
