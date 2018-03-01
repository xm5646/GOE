package com.project.goe.projectgeodbserver.viewentity.excel;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExcelDrawcash {
	@Excel(name = "提现单号", orderNum = "0")
	private long drawId;

	// 用户id
	@Excel(name = "用户编号", orderNum = "1")
	private long userId;
	
	//用戶姓名
	@Excel(name = "用户姓名", orderNum = "2")
	private String userName;

	// 提现银行卡号
	@Excel(name = "所在银行", orderNum = "3")
	private String bankName;
	
	// 提现银行卡号
	@Excel(name = "银行卡号", orderNum = "4",width = 20)
	private String cardInfoId;

	// 申请提现金额
	@Excel(name = "申请提现金额", orderNum = "5",width = 15)
	private double drawnumber;
	
	// 提现实际金额
	@Excel(name = "实际提现金额", orderNum = "6",width = 15)
	private double finalNumber;

	// 提现的状态
	@Excel(name = "状态", orderNum = "7")
	private String drawStatus;

	// 提现申请提交时间
	@Excel(name = "申请提交时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "8",width = 20)
	private Date drawCommitTime;

	// 公司打款时间
	@Excel(name = "公司打款时间", exportFormat = "yyyy-MM-dd HH:mm:ss", orderNum = "9",width = 20)
	private Date payTime;

	// 电话号码
	@Excel(name = "电话号码", orderNum = "10",width = 15)
	private String phone;
	
	public ExcelDrawcash() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getDrawId() {
		return drawId;
	}

	public void setDrawId(long drawId) {
		this.drawId = drawId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCardInfoId() {
		return cardInfoId;
	}

	public void setCardInfoId(String cardInfoId) {
		this.cardInfoId = cardInfoId;
	}

	public double getDrawnumber() {
		return drawnumber;
	}

	public void setDrawnumber(double drawnumber) {
		this.drawnumber = drawnumber;
	}

	public double getFinalNumber() {
		return finalNumber;
	}

	public void setFinalNumber(double finalNumber) {
		this.finalNumber = finalNumber;
	}

	public String getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}

	public Date getDrawCommitTime() {
		return drawCommitTime;
	}

	public void setDrawCommitTime(Date drawCommitTime) {
		this.drawCommitTime = drawCommitTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
