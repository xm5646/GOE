package com.project.goe.projectgeodbserver.viewentity;

public class TransferCoinRecord {
	private String consumeTime;
	private String sendUserAccount;
	private String receiveUserAccount;
	private long transferCoinNumber;

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
