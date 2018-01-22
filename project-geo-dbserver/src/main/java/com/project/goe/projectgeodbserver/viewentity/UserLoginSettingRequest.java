package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

public class UserLoginSettingRequest {
	@NotBlank(message = "用户名不能为空")
	private String account;

	@NotBlank(message = "用户原密码不能为空")
	private String oldPassword;

	@NotBlank(message = "用户登录新密码不能为空")
	@Size(min = 6, max = 12, message = "用户登录新密码长度只能6-12之间")
	private String newPassword;
	
	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}",message = "支付密码只能是6位数字")
	private String paymentPassword;

	/**
	 * 验证手机号码
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145 
	 * 电信号码段:133、153、180、189
	 */
	@NotBlank(message = "用户手机号码不能为空")
	@Size(min = 11, max = 11, message = "用户手机号码长度为11位")
	@Pattern(regexp = "((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
	private String userPhone;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/*
	 * public Integer getPaymentPassword() { return paymentPassword; }
	 * 
	 * public void setPaymentPassword(Integer paymentPassword) {
	 * this.paymentPassword = paymentPassword; }
	 */
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

}
