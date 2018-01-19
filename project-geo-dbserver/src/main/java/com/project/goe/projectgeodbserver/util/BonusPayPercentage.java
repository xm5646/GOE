package com.project.goe.projectgeodbserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//相关比例
@Component
public class BonusPayPercentage {
	// 管理费比例
	private static double manageCostPercentage;
	// 奖金比例
	private static double bonusNumberPercentage;
	// 产品积分比例
	private static double productCoinNumberPercentage;
	// 重销产品单价
	private static double consumeCoinUnitPrice;

	public static double getManageCostPercentage() {
		return manageCostPercentage;
	}

	
	@Value("${bonus.percentage.manageCostPercentage}")
	public void setManageCostPercentage(double manageCostPercentage) {
		BonusPayPercentage.manageCostPercentage = manageCostPercentage;
	}

	public static double getBonusNumberPercentage() {
		return bonusNumberPercentage;
	}

	@Value("${bonus.percentage.bonusNumberPercentage}")
	public void setBonusNumberPercentage(double bonusNumberPercentage) {
		BonusPayPercentage.bonusNumberPercentage = bonusNumberPercentage;
	}

	public static double getProductCoinNumberPercentage() {
		return productCoinNumberPercentage;
	}

	@Value("${bonus.percentage.productCoinNumberPercentage}")
	public void setProductCoinNumberPercentage(double productCoinNumberPercentage) {
		BonusPayPercentage.productCoinNumberPercentage = productCoinNumberPercentage;
	}

	public static double getConsumeCoinUnitPrice() {
		return consumeCoinUnitPrice;
	}

	@Value("${bonus.percentage.consumeCoinUnitPrice}")
	public void setConsumeCoinUnitPrice(double consumeCoinUnitPrice) {
		BonusPayPercentage.consumeCoinUnitPrice = consumeCoinUnitPrice;
	}

}
