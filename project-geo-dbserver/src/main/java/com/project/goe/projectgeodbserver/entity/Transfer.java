package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 消费记录表
 * 仅限用户之间转账
 * 
 * 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
public class Transfer {
	
	//转账表主键
	@Id
	@GeneratedValue
	private int transfid; 
	
	//发送人ID
	@Column(nullable = false)
	private int userid;
	
	//创建时间
	private Date createtime;
	
	//金额
	@Column(nullable = false)
	private int money;
	
	//接收人ID
	@Column(nullable = false)
	private int receiveuserid;
	
	//转账类型状态  
	/**
	 * 个人金额到报单币（customCoinToMoney）
	 * 公司发放报单币到个人
	 * 报单币用户间转账
	 * 报单币消费-推荐 
	 */
	@Column(nullable = false)
	private String type;
	
	//处理状态  {完成，未完成}
	private String status;
	
	//描述
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
 