package com.project.goe.projectgeodbserver.viewentity;

import java.util.Date;

public class DrawCashRecordVO {
	private long drawId;
	private String account;
	private long finalNumber;
	private String cardOwnerName;
	private String phone;
	private String bankName;
	private String cardNumber;
	private String drawStatus;
	private String payTime;
	private String commitTime;


	public long getDrawId() {
		return drawId;
	}

	public void setDrawId(long drawId) {
		this.drawId = drawId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getFinalNumber() {
		return finalNumber;
	}

	public void setFinalNumber(long finalNumber) {
		this.finalNumber = finalNumber;
	}

	public String getCardOwnerName() {
		return cardOwnerName;
	}

	public void setCardOwnerName(String cardOwnerName) {
		this.cardOwnerName = cardOwnerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}


	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

}