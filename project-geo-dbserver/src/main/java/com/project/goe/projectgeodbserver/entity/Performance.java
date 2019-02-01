package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 用户业绩类
 */
@Entity
@Table(name = "tb_performance")
public class Performance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pId;

	// 用户id
	@Column(nullable = false)
	private long userId;

	// A市场累积业绩
	@Column
	private long departAcount = 0;

	// B市场累积业绩
	@Column
	private long departBcount = 0;

	// C市场累积业绩
	@Column
	private long departCcount = 0;

	// A市场新增业绩
	@Column
	private long addDepartAcount = 0;

	// B市场新增业绩
	@Column
	private long addDepartBcount = 0;

	// C市场新增业绩
	@Column
	private long addDepartCcount = 0;

	// 新增用户第一次添加至业绩表
	@Column
	private Date createTime;

	// 最后一次用户业绩更新的时间
	@Column
	private Date updateTime;

	public long getpId() {
		return pId;
	}

	public void setpId(long pId) {
		this.pId = pId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getDepartAcount() {
		return departAcount;
	}

	public void setDepartAcount(long departAcount) {
		this.departAcount = departAcount;
	}

	public long getDepartBcount() {
		return departBcount;
	}

	public void setDepartBcount(long departBcount) {
		this.departBcount = departBcount;
	}

	public long getDepartCcount() {
		return departCcount;
	}

	public void setDepartCcount(long departCcount) {
		this.departCcount = departCcount;
	}

	public long getAddDepartAcount() {
		return addDepartAcount;
	}

	public void setAddDepartAcount(long addDepartAcount) {
		this.addDepartAcount = addDepartAcount;
	}

	public long getAddDepartBcount() {
		return addDepartBcount;
	}

	public void setAddDepartBcount(long addDepartBcount) {
		this.addDepartBcount = addDepartBcount;
	}

	public long getAddDepartCcount() {
		return addDepartCcount;
	}

	public void setAddDepartCcount(long addDepartCcount) {
		this.addDepartCcount = addDepartCcount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void clearNewPerformance() {
		this.setAddDepartAcount(0);
		this.setAddDepartBcount(0);
		this.setAddDepartCcount(0);
	}

	@Override
	public String toString() {
		return "Performance [pId=" + pId + ", userId=" + userId + ", departAcount=" + departAcount + ", departBcount="
				+ departBcount + ", departCcount=" + departCcount + ", addDepartAcount=" + addDepartAcount
				+ ", addDepartBcount=" + addDepartBcount + ", addDepartCcount=" + addDepartCcount + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

}
