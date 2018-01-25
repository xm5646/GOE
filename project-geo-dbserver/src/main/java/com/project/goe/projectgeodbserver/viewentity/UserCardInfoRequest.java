package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UserCardInfoRequest {
	@NotBlank(message = "用户名不能为空!")
	@Size(min = 5,max = 20,message = "用户名长度不合法!")
	private String account;
	
	@NotBlank(message = "银行卡号不能为空!")
	@Pattern(regexp = "(^\\d{16,19}$", message = "银行卡号不合法!")
	private String cardNo;
	
	@NotBlank(message = "银行名称不能为空!")
	@Size(min = 1,max = 20,message = "银行名称长度不合法!")
	private String bankName;
	
	@NotBlank(message = "银行卡户主名不能为空!")
	@Size(min = 2,max = 20,message = "银行卡户主名长度不合法!")
	private String cardOwnerName;

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
