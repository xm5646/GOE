package com.project.goe.projectgeodbserver.viewentity;

public class TransferCoinRecord {
	private long consumeId;
	private String consumeTime;
	private String sendUserAccount;
	private String receiveUserAccount;
	private long transferCoinNumber;
	private String description;
	
	
	public long getConsumeId() {
		return consumeId;
	}

	public void setConsumeId(long consumeId) {
		this.consumeId = consumeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}

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

	public long getTransferCoinNumber() {
		return transferCoinNumber;
	}

	public void setTransferCoinNumber(long transferCoinNumber) {
		this.transferCoinNumber = transferCoinNumber;
	}

}
