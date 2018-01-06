package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 收益日志类，映射用户表 每日发放金额日志记录，有发放和未发放状态
 * 奖金发放表
 * 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
public class EarningLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int earnid;
	// 外键，用户ID
	@Column(nullable = false)
	private int userid;
	// 金钱
	@Column(nullable = false)
	private int money;
	//20%股份 35%工资提现 35%作为报单币 10%手续费
	// 触发类型--{新增，累计，公司手工调整}
	@Column(nullable = false)
	private String touchtype;
	// 状态--{已发，未发}
	@Column(nullable = false)
	private String status;
	// 描述
	private String description;
	// 时间
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public EarningLog() {
	}

	public int getEarnid() {
		return earnid;
	}

	public void setEarnid(int earnid) {
		this.earnid = earnid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getTouchtype() {
		return touchtype;
	}

	public void setTouchtype(String touchtype) {
		this.touchtype = touchtype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}