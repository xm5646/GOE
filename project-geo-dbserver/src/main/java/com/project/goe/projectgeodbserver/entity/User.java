package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;

/**
 * 用户类，映射用户表 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
@Table(name = "tb_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// 用户id
	private long userId;

	// 用户名称
	@Column(nullable = false, unique = true, length = 100)
	private String account;

	// 用户密码
	@Column(nullable = false, length = 50)
	private String password;

	// 用户级别
	@Column(nullable = false)
	@Enumerated
	private UserLevel userLevel = UserLevel.COMMON_SALEMAN;

	// 用户类型
	@Column(nullable = false)
	@Enumerated()
	private UserType userType = UserType.COMMON;

	// 用户状态(false:未激活(初始化默认值),true:激活)
	@Column(nullable = false)
	private boolean userStatus = false;

	// 用户手机号码
	@Column
	private String userPhone;

	// 用户上级ID
	@Column
	private int parentId;

	// A节点用户id
	@Column
	private int departmentA;

	// B节点用户id
	@Column
	private int departmentB;

	// C节点用户id
	@Column
	private int departmentC;

	// 推荐人ID
	@Column
	private int recomondId;

	// 创建时间
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	// 用户下一次考核日期
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date assessDate;

	// 用户考核状态(true:重销日用户考核通过；false：重销日用户考核未通过)
	@Column
	private boolean assessStatus;

	// 用户层级数
	@Column
	private int weightCode;

	// 用户奖金
	@Column
	private double bonusCoin;

	// 报单币
	@Column
	private long consumeCoin;

	// 产品积分
	@Column
	private long productCoin;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserLevel getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(UserLevel userLevel) {
		this.userLevel = userLevel;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getDepartmentA() {
		return departmentA;
	}

	public void setDepartmentA(int departmentA) {
		this.departmentA = departmentA;
	}

	public int getDepartmentB() {
		return departmentB;
	}

	public void setDepartmentB(int departmentB) {
		this.departmentB = departmentB;
	}

	public int getDepartmentC() {
		return departmentC;
	}

	public void setDepartmentC(int departmentC) {
		this.departmentC = departmentC;
	}

	public int getRecomondId() {
		return recomondId;
	}

	public void setRecomondId(int recomondId) {
		this.recomondId = recomondId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}

	public boolean isAssessStatus() {
		return assessStatus;
	}

	public void setAssessStatus(boolean assessStatus) {
		this.assessStatus = assessStatus;
	}

	public int getWeightCode() {
		return weightCode;
	}

	public void setWeightCode(int weightCode) {
		this.weightCode = weightCode;
	}

	public double getBonusCoin() {
		return bonusCoin;
	}

	public void setBonusCoin(double bonusCoin) {
		this.bonusCoin = bonusCoin;
	}

	public long getConsumeCoin() {
		return consumeCoin;
	}

	public void setConsumeCoin(long consumeCoin) {
		this.consumeCoin = consumeCoin;
	}

	public long getProductCoin() {
		return productCoin;
	}

	public void setProductCoin(long productCoin) {
		this.productCoin = productCoin;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", account=" + account + ", password=" + password + ", userLevel=" + userLevel
				+ ", userType=" + userType + ", userStatus=" + userStatus + ", userPhone=" + userPhone + ", parentId="
				+ parentId + ", departmentA=" + departmentA + ", departmentB=" + departmentB + ", departmentC="
				+ departmentC + ", recomondId=" + recomondId + ", createTime=" + createTime + ", assessDate="
				+ assessDate + ", assessStatus=" + assessStatus + ", weightCode=" + weightCode + ", bonusCoin="
				+ bonusCoin + ", consumeCoin=" + consumeCoin + ", productCoin=" + productCoin + "]";
	}

}
