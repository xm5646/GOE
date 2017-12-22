package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 收益审计类，映射收入审计表
 * 每天统计触发
 * @author zhangqiankun
 * 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
public class EarningAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int earnid;
	//外键，用户ID
	private int userid;
	//A部门下级数量
	private int departAcount;
	//B部门下级数量
	private int departBcount;
	//C部门下级数量
	private int departCcount;
	//新增A部门下级数量
	private int addDepartAcount;
	//新增B部门下级数量
	private int addDepartBcount;
	//新增C部门下级数量
	private int addDepartCcount;
	//审计时间
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
	public int getDepartAcount() {
		return departAcount;
	}
	public void setDepartAcount(int departAcount) {
		this.departAcount = departAcount;
	}
	public int getDepartBcount() {
		return departBcount;
	}
	public void setDepartBcount(int departBcount) {
		this.departBcount = departBcount;
	}
	public int getDepartCcount() {
		return departCcount;
	}
	public void setDepartCcount(int departCcount) {
		this.departCcount = departCcount;
	}
	public int getAddDepartAcount() {
		return addDepartAcount;
	}
	public void setAddDepartAcount(int addDepartAcount) {
		this.addDepartAcount = addDepartAcount;
	}
	public int getAddDepartBcount() {
		return addDepartBcount;
	}
	public void setAddDepartBcount(int addDepartBcount) {
		this.addDepartBcount = addDepartBcount;
	}
	public int getAddDepartCcount() {
		return addDepartCcount;
	}
	public void setAddDepartCcount(int addDepartCcount) {
		this.addDepartCcount = addDepartCcount;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
