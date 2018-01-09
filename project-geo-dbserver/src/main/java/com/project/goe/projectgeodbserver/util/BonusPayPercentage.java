package com.project.goe.projectgeodbserver.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bonus.percentage")
public class BonusPayPercentage {
	private double manageCostPercentage;
	private double bonusNumberPercentage;
	private double productCoinNumberPercentage;

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

}
