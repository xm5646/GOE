package com.project.goe.projectgeodbserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cardinfo")
public class CardInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cardInfoId;
	
	//用户id
	@Column(nullable = false)
	private long userId;
	
	//用户银行卡号
	@Column
	private String cardNumber;
	
	//银行卡户主名
	@Column
	private String cardOwnerName;
	
	//银行卡所属银行
	@Column
	private String bankName;

	public long getCardInfoId() {
		return cardInfoId;
	}

	public void setCardInfoId(long cardInfoId) {
		this.cardInfoId = cardInfoId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardOwnerName() {
		return cardOwnerName;
	}

	public void setCardOwnerName(String cardOwnerName) {
		this.cardOwnerName = cardOwnerName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}	
