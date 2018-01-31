package com.project.goe.projectgeodbserver.viewentity;

import org.hibernate.validator.constraints.NotBlank;

public class UserCardInfoRequest {
	@NotBlank(message = "用户名不能为空!")
	private String account;

	@NotBlank(message = "银行卡号不能为空!")
	private String cardNo;

	@NotBlank(message = "银行名称不能为空!")
	private String bankName;

	@NotBlank(message = "银行卡户主名不能为空!")
	private String cardOwnerName;

	@NotBlank(message = "用户手机号码不能为空")
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardOwnerName() {
		return cardOwnerName;
	}

	public void setCardOwnerName(String cardOwnerName) {
		this.cardOwnerName = cardOwnerName;
	}

}
