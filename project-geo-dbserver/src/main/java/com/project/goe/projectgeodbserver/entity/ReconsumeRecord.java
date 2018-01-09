package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 重销记录表
 */
@Entity
@Table(name = "tb_reconsumerecord")
public class ReconsumeRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long reconsumeRecordId;

	// 用户id
	@Column(nullable = false)
	private long userId;

	// 重销支付日期
	@Column(nullable = false)
	private Date createTime;

	// 重销支付金额
	@Column(nullable = false)
	private double reconsumePayment;

	public long getReconsumeRecordId() {
		return reconsumeRecordId;
	}

	public void setReconsumeRecordId(long reconsumeRecordId) {
		this.reconsumeRecordId = reconsumeRecordId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getReconsumePayment() {
		return reconsumePayment;
	}

	public void setReconsumePayment(double reconsumePayment) {
		this.reconsumePayment = reconsumePayment;
	}

}
