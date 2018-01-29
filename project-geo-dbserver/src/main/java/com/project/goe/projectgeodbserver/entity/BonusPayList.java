package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * 奖金发放表
 */
@Entity
@Table(name = "tb_bonuspaylist")
public class BonusPayList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bonusPayId;

	@Column(nullable = false)
	private long userId;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date payTime;

	// 奖金总金额
	@Column
	private double totalMoney;

	// 奖金比金额
	@Column
	private double bonusNumber;

	// 管理费用
	@Column
	private double manageCost;

	// 产品积分
	@Column
	private double productCoinNumber;
	
	@Column
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getBonusPayId() {
		return bonusPayId;
	}

	public void setBonusPayId(long bonusPayId) {
		this.bonusPayId = bonusPayId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public double getBonusNumber() {
		return bonusNumber;
	}

	public void setBonusNumber(double totalMoney) {
		this.bonusNumber = totalMoney ;
	}

	public double getManageCost() {
		return this.manageCost;
	}

	public void setManageCost(double totalMoney) {
		this.manageCost = totalMoney;
	}

	public double getProductCoinNumber() {
		return productCoinNumber;
	}

	public void setProductCoinNumber(double totalMoney) {
		this.productCoinNumber = totalMoney;
	}
	
}
