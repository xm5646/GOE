package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transfer {
	@Id
	@GeneratedValue
	private int transfid; 
	
	@Column(nullable = false)
	private int userid;
	
	private Date createtime;
	
	@Column(nullable = false)
	private int money;
	
	@Column(nullable = false)
	private int receiveuserid;
	
	private String status;
	
	private String discribe;

	public int getTransfid() {
		return transfid;
	}

	public void setTransfid(int transfid) {
		this.transfid = transfid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getReceiveuserid() {
		return receiveuserid;
	}

	public void setReceiveuserid(int receiveuserid) {
		this.receiveuserid = receiveuserid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDiscribe() {
		return discribe;
	}

	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}
	
}
 