package com.project.goe.projectgeodbserver.entity;

public class BonusPayRatio {
	private double bonusNumber;
	private double manageCost;
	private double productCoinNumber;
	public double getBonusNumber() {
		return bonusNumber;
	}
	public void setBonusNumber(double bonusNumber) {
		this.bonusNumber = bonusNumber;
	}
	public double getManageCost() {
		return manageCost;
	}
	public void setManageCost(double manageCost) {
		this.manageCost = manageCost;
	}
	public double getProductCoinNumber() {
		return productCoinNumber;
	}
	public void setProductCoinNumber(double productCoinNumber) {
		this.productCoinNumber = productCoinNumber;
	}
	
	public BonusPayRatio() {
		super();
	}
	
	public BonusPayRatio(double bonusNumber,double manageCost,double productCoinNumber) {
		super();
		this.bonusNumber = bonusNumber;
		this.manageCost = manageCost;
		this.productCoinNumber = productCoinNumber;
	}
	
	@Override
	public String toString() {
		return "BonusPayRatio [bonusNumber=" + bonusNumber + ", manageCost=" + manageCost + ", productCoinNumber="
				+ productCoinNumber + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bonusNumber);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(manageCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(productCoinNumber);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BonusPayRatio other = (BonusPayRatio) obj;
		if (Double.doubleToLongBits(bonusNumber) != Double.doubleToLongBits(other.bonusNumber))
			return false;
		if (Double.doubleToLongBits(manageCost) != Double.doubleToLongBits(other.manageCost))
			return false;
		if (Double.doubleToLongBits(productCoinNumber) != Double.doubleToLongBits(other.productCoinNumber))
			return false;
		return true;
	}
}
