package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户类，映射用户表
 * @author zhangqiankun
 * 注解@Entity一定要带，这个是数据库映射的注解
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	//用户名称
	@Column(nullable = false)
	private String name;
	//用户密码
	@Column(nullable = false)
	private String password;
	//用户类型
	private String usertype;
	//用户状态
	private String userstatus;
	//用户银行卡号
	private String card;
	//用户电话
	private String phone;
	//用户部门A的下级
	private int departmentA;
	//用户部门B的下级
	private int departmentB;
	//用户部门C的下级
	private int departmentC;
	//他的上级ID
	private int parentid;
	//推荐人ID
	private int recomondid;
	//创建时间
	@Column(nullable = false)
	private Date createTime;
	//重销时间
	private Date recommendedTime; 
	//周期次数
	private String cycleCount;
	//类型--{激活，冻结}
	private String type;
	//考核状态
	private String assessType;	
	//层级，权重
	private int weightCode;
	//金额
	private int money;
	//报单币数目
	private int customCoin;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getRecomondid() {
		return recomondid;
	}
	public void setRecomondid(int recomondid) {
		this.recomondid = recomondid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getRecommendedTime() {
		return recommendedTime;
	}
	public void setRecommendedTime(Date recommendedTime) {
		this.recommendedTime = recommendedTime;
	}
	public String getCycleCount() {
		return cycleCount;
	}
	public void setCycleCount(String cycleCount) {
		this.cycleCount = cycleCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAssessType() {
		return assessType;
	}
	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	public int getWeightCode() {
		return weightCode;
	}
	public void setWeightCode(int weightCode) {
		this.weightCode = weightCode;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getCustomCoin() {
		return customCoin;
	}
	public void setCustomCoin(int customCoin) {
		this.customCoin = customCoin;
	}
	
}
