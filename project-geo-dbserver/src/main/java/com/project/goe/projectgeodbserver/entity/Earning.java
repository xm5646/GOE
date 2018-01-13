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

/**
 * 用户收益类
 */
@Entity
@Table(name = "tb_earning")
public class Earning {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long earningId;

	// 用户ID
	@Column(nullable = false)
	private long userid;

	// 收益创建日期：产生收益的时间
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	// 触发类型
	@Column
	private String touchType;

	// 每日发放金额
	@Column
	private double dayMoney;

	// 金额发放剩余天数
	@Column
	private int surplusNumber;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getTouchType() {
		return touchType;
	}

	public void setTouchType(String touchType) {
		this.touchType = touchType;
	}

	public double getDayMoney() {
		return dayMoney;
	}

	public void setDayMoney(double dayMoney) {
		this.dayMoney = dayMoney;
	}

	public int getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(int surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public long getEarningId() {
		return earningId;
	}

	public void setEarningId(long earningId) {
		this.earningId = earningId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Earning [earningId=" + earningId + ", userid=" + userid + ", createTime=" + createTime + ", touchType="
				+ touchType + ", dayMoney=" + dayMoney + ", surplusNumber=" + surplusNumber + "]";
	}

}