package com.project.goe.projectgeodbserver.viewentity;

import java.util.Date;

public class UserVO {
	private String account;
	private String nickName;
	private String userLevel;
	private String userType;
	private boolean userStatus;
	private String userPhone;
	private long departmentA;
	private long departmentB;
	private long departmentC;
	private Date createTime;
	private Date assessDate;
	private boolean assessStatus;
	private double bonusCoin;
	private double consumeCoin;
	private double productCoin;
	private Date activateTime;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public long getDepartmentA() {
		return departmentA;
	}

	public void setDepartmentA(long departmentA) {
		this.departmentA = departmentA;
	}

	public long getDepartmentB() {
		return departmentB;
	}

	public void setDepartmentB(long departmentB) {
		this.departmentB = departmentB;
	}

	public long getDepartmentC() {
		return departmentC;
	}

	public void setDepartmentC(long departmentC) {
		this.departmentC = departmentC;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}

	public boolean isAssessStatus() {
		return assessStatus;
	}

	public void setAssessStatus(boolean assessStatus) {
		this.assessStatus = assessStatus;
	}

	public double getBonusCoin() {
		return bonusCoin;
	}

	public void setBonusCoin(double bonusCoin) {
		this.bonusCoin = bonusCoin;
	}

	public double getConsumeCoin() {
		return consumeCoin;
	}

	public void setConsumeCoin(double consumeCoin) {
		this.consumeCoin = consumeCoin;
	}

	public double getProductCoin() {
		return productCoin;
	}

	public void setProductCoin(double productCoin) {
		this.productCoin = productCoin;
	}

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

}
