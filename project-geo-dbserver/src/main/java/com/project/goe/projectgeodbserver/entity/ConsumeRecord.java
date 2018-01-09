package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.project.goe.projectgeodbserver.statusType.ConsumeType;

@Entity
@Table(name = "tb_consume")
public class ConsumeRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long consumeId;
	
	@Column(nullable = false)
	private long userId;
	
	//消费日期
	@Column
	private Date consumeTime;
	
	//消费类型
	@Column
	private ConsumeType consumeType;
	
	//发送方userId
	@Column
	private long sendUserId;
	
	//接收方userId
	@Column
	private long receiveUserId;
	
	//消费金额
	@Column
	private double consumeNumber;
	
	//消费状态
	@Column
	public boolean consumeStatus;
	
	//消费记录描述
	@Column
	public String description;

	public long getConsumeId() {
		return consumeId;
	}

	public void setConsumeId(long consumeId) {
		this.consumeId = consumeId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public ConsumeType getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(ConsumeType consumeType) {
		this.consumeType = consumeType;
	}

	public long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public long getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public double getConsumeNumber() {
		return consumeNumber;
	}

	public void setConsumeNumber(double consumeNumber) {
		this.consumeNumber = consumeNumber;
	}

	public boolean isConsumeStatus() {
		return consumeStatus;
	}

	public void setConsumeStatus(boolean consumeStatus) {
		this.consumeStatus = consumeStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
