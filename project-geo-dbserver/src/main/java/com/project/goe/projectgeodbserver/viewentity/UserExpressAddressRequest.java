package com.project.goe.projectgeodbserver.viewentity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class UserExpressAddressRequest {
	// 用户账户名
	@NotBlank(message = "用户名不能为空")
	private String account;

	// 收货人姓名
	@NotBlank(message = "收货人名不能为空")
	@Length(min = 0, max = 20, message = "收货人姓名长度过长")
	private String receiverName;

	// 收货人地址信息
	// 省
	@NotBlank(message = "省名不能为空")
	@Length(min = 0, max = 20, message = "字符长度过长")
	private String province;
	// 市
	@NotBlank(message = "市名不能为空")
	@Length(min = 0, max = 20, message = "字符长度过长")
	private String city;
	// 县(区)
	@NotBlank(message = "县区不能为空")
	@Length(min = 0, max = 20, message = "字符长度过长")
	private String district;
	// 地址详细信息
	@NotBlank(message = "地址详细不能为空")
	@Length(min = 0, max = 100, message = "字符长度过长")
	private String detailAddress;

	// 联系电话
	@NotBlank(message = "用户手机号码不能为空")
	private String phone;
	
	// 是否为默认地址
	@Range(min = 0,max = 1)
	private int defaultAddress;
	
	// 地址描述信息
	private String description;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(int defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
