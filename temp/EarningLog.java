package com.project.goe.projectgeodbserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 收益日志类，映射用户表
 * 每日发放金额日志记录，有发放和未发放状态
 * @author zhangqiankun
 * 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
public class EarningLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int earnid;
	//外键，用户ID
	private int userid;
	//金钱
	private int money;
	//触发类型--{新增，累计}
	private String touchtype;
	//状态--{已发，未发}
	private String status;
	//描述
	private String describe;
	
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
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	
}
