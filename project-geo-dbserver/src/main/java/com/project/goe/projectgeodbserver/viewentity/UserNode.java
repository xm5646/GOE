package com.project.goe.projectgeodbserver.viewentity;

public class UserNode {
	private String account;
	private String userLevel;
	private long departAcount;
	private long departBcount;
	private long departCcount;
	private String parentAccount;

	public String getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getDepartAcount() {
		return departAcount;
	}

	public void setDepartAcount(long departAcount) {
		this.departAcount = departAcount;
	}

	public long getDepartBcount() {
		return departBcount;
	}

	public void setDepartBcount(long departBcount) {
		this.departBcount = departBcount;
	}

	public long getDepartCcount() {
		return departCcount;
	}

	public void setDepartCcount(long departCcount) {
		this.departCcount = departCcount;
	}
}
