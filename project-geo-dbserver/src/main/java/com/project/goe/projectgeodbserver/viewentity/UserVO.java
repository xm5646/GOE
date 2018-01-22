package com.project.goe.projectgeodbserver.viewentity;

public class UserVO {
	private String account;
	private String nickName;
	private String userLevel;
	private String userType;
	private String userStatus;
	private String userPhone;
	private long departmentA;
	private long departmentB;
	private long departmentC;
	private String createTime;
	private String assessDate;
	private String assessStatus;
	private double bonusCoin;
	private double consumeCoin;
	private double productCoin;
	private String activateTime;
	private String identityNo;
	private boolean passwordReset;
	private String province;
	private String city;

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public boolean isPasswordReset() {
		return passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		this.passwordReset = passwordReset;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(String assessDate) {
		this.assessDate = assessDate;
	}

	public String getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(String activateTime) {
		this.activateTime = activateTime;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getAssessStatus() {
		return assessStatus;
	}

	public void setAssessStatus(String assessStatus) {
		this.assessStatus = assessStatus;
	}

}
