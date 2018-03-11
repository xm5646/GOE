package com.project.goe.projectgeodbserver.viewentity;

public class EarningVO {
	private String account;
	private String userLevel;
	private String touchType;
	private String createTime;
	private String endTime;
	private int dayMoney;
	private int surplusNumber;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getTouchType() {
		return touchType;
	}

	public void setTouchType(String touchType) {
		this.touchType = touchType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getDayMoney() {
		return dayMoney;
	}

	public void setDayMoney(int dayMoney) {
		this.dayMoney = dayMoney;
	}

	public int getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(int surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

}
