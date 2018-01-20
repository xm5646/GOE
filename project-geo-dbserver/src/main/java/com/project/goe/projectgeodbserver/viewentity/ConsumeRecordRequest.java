package com.project.goe.projectgeodbserver.viewentity;

public class ConsumeRecordRequest {
	private String sendUserAccount;
	private String receiveUserAccount;
	private int consumeTypeCode;
	private double consumeNumber;
	private String description;

	public String getSendUserAccount() {
		return sendUserAccount;
	}

	public void setSendUserAccount(String sendUserAccount) {
		this.sendUserAccount = sendUserAccount;
	}

	public String getReceiveUserAccount() {
		return receiveUserAccount;
	}

	public void setReceiveUserAccount(String receiveUserAccount) {
		this.receiveUserAccount = receiveUserAccount;
	}

	public int getConsumeTypeCode() {
		return consumeTypeCode;
	}

	public void setConsumeTypeCode(int consumeTypeCode) {
		this.consumeTypeCode = consumeTypeCode;
	}

	public double getConsumeNumber() {
		return consumeNumber;
	}

	public void setConsumeNumber(double consumeNumber) {
		this.consumeNumber = consumeNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
