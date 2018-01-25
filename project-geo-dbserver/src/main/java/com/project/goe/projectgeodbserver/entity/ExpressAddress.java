package com.project.goe.projectgeodbserver.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description：收货人地址实体
 */
@Entity
@Table(name = "tb_expressAddress")
public class ExpressAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long expressId;

	// 收货地址所属用户
	@Column(nullable = false)
	private long userId;

	// 收货人姓名
	@Column(nullable = false)
	private String receiverName;

	// 收货人地址信息
	// 省
	@Column(nullable = false)
	private String province;
	// 市
	@Column(nullable = false)
	private String city;
	// 县(区)
	@Column(nullable = false)
	private String district;
	// 地址详细信息
	@Column(nullable = false)
	private String detailAddress;

	// 联系电话
	@Column(nullable = false)
	private String phone;

	// 收货地址创建日期
	@Column
	private Date createTime;

	// 是否为默认地址
	@Column
	private boolean defaultAddress = false;

	// 地址描述信息
	@Column
	private String description;

	public long getExpressId() {
		return expressId;
	}

	public void setExpressId(long expressId) {
		this.expressId = expressId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

}
