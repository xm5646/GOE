package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 提现记录实体类
 */
@Entity
@Table(name = "tb_drawcash")
public class DrawCashRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long drawId;

	// 用户id
	@Column(nullable = false)
	private long userId;

	// 提现银行卡号
	@Column(nullable = false)
	private long cardInfoId;

	// 申请提现金额
	@Column(nullable = false)
	private double drawnumber;

	// 提现实际金额
	@Column(nullable = false)
	private double finalNumber;

	// 提现的状态
	@Column(nullable = false)
	private String drawStatus;

	// 提现申请提交时间
	@Column(nullable = false)
	private Date drawCommitTime;

	// 公司打款时间
	@Column
	private Date payTime;

	// 电话号码
	@Column
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public long getDrawId() {
		return drawId;
	}

	public void setDrawId(long drawId) {
		this.drawId = drawId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCardInfoId() {
		return cardInfoId;
	}

	public void setCardInfoId(long cardInfoId) {
		this.cardInfoId = cardInfoId;
	}

	public double getDrawnumber() {
		return drawnumber;
	}

	public void setDrawnumber(double drawnumber) {
		this.drawnumber = drawnumber;
	}

	public double getFinalNumber() {
		return finalNumber;
	}

	public void setFinalNumber(double finalNumber) {
		this.finalNumber = finalNumber;
	}

	public String getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}

	public Date getDrawCommitTime() {
		return drawCommitTime;
	}

	public void setDrawCommitTime(Date drawCommitTime) {
		this.drawCommitTime = drawCommitTime;
	}
}
