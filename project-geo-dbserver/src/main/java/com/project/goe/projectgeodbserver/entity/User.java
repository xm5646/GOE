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

import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;

/**
 * 用户类，映射用户表 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity(name = "tuser")
@Table(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// 用户id
	private long userId;

	// 用户名称
	@Column(nullable = false, unique = true, length = 100)
	private String account;

	// 昵称
	@Column
	private String nickName;

	// 用户密码
	@Column(nullable = false, length = 50)
	private String password;

	// 用户级别
	@Column(nullable = false)
	private String userLevel = UserLevel.CONSUMER;

	// 用户类型
	@Column(nullable = false)
	private String userType = UserType.COMMON;

	// 用户状态(false:未激活(初始化默认值),true:激活)
	@Column(nullable = false)
	private boolean userStatus = false;

	// 用户手机号码
	@Column
	private String userPhone;

	// 用户上级ID
	@Column
	private long parentId;

	// A节点用户id
	@Column
	private long departmentA;

	// B节点用户id
	@Column
	private long departmentB;

	// C节点用户id
	@Column
	private long departmentC;

	// 推荐人ID
	@Column
	private long recomondId;

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
	private boolean assessStatus = false;

	// 用户层级数
	@Column(nullable = false)
	private int weightCode;

	// 用户奖金
	@Column
	private double bonusCoin;

	// 报单币
	@Column
	private double consumeCoin;

	// 产品积分
	@Column
	private double productCoin;

	// 用户激活时间
	@Column
	private Date activateTime;

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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getDepartmentA() {
		return departmentA;
	}

	public void setDepartmentA(long departmentA) {
		this.departmentA = departmentA;
	}

	public long getDepartmentB() {
		return departmentB;
	}

	public void setDepartmentB(long departmentB) {
		this.departmentB = departmentB;
	}

	public long getDepartmentC() {
		return departmentC;
	}

	public void setDepartmentC(long departmentC) {
		this.departmentC = departmentC;
	}

	public long getRecomondId() {
		return recomondId;
	}

	public void setRecomondId(long recomondId) {
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

	public void setConsumeCoin(long consumeCoin) {
		this.consumeCoin = consumeCoin;
	}

	public void setProductCoin(long productCoin) {
		this.productCoin = productCoin;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public double getConsumeCoin() {
		return consumeCoin;
	}

	public void setConsumeCoin(double consumeCoin) {
		this.consumeCoin = consumeCoin;
	}

	public double getProductCoin() {
		return productCoin;
	}

	public void setProductCoin(double productCoin) {
		this.productCoin = productCoin;
	}

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", account=" + account + ", nickName=" + nickName + ", password=" + password
				+ ", userLevel=" + userLevel + ", userType=" + userType + ", userStatus=" + userStatus + ", userPhone="
				+ userPhone + ", parentId=" + parentId + ", departmentA=" + departmentA + ", departmentB=" + departmentB
				+ ", departmentC=" + departmentC + ", recomondId=" + recomondId + ", createTime=" + createTime
				+ ", assessDate=" + assessDate + ", assessStatus=" + assessStatus + ", weightCode=" + weightCode
				+ ", bonusCoin=" + bonusCoin + ", consumeCoin=" + consumeCoin + ", productCoin=" + productCoin
				+ ", activateTime=" + activateTime + "]";
	}

	
}
