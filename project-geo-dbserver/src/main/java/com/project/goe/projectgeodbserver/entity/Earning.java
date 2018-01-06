package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 收益类，映射用户表  还是收益表
 * 
 * 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
public class Earning {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int earnid;
	//外键，用户ID
	private int userid;
	//触发类型-{新增，累计，省代}
	private String touchtype;
	//触发级别--{业务员，经理。。。}
	private String touchleave;
	//每日金额 对应级别对应的金额
	private int daymoney;
	private int starttime;
	private int endtime;
	private int surplusDay;
	//发放时间
	private Date createtime;
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
	public String getTouchtype() {
		return touchtype;
	}
	public void setTouchtype(String touchtype) {
		this.touchtype = touchtype;
	}
	public String getTouchleave() {
		return touchleave;
	}
	public void setTouchleave(String touchleave) {
		this.touchleave = touchleave;
	}
	public int getDaymoney() {
		return daymoney;
	}
	public void setDaymoney(int daymoney) {
		this.daymoney = daymoney;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
