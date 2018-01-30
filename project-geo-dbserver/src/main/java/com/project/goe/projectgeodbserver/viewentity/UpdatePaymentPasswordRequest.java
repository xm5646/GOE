package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UpdatePaymentPasswordRequest {
	@NotBlank(message = "用户名不能为空")
	@Size(min = 5, max = 20, message = "用户名的长度不合法(5-20)!")
	private String account;

	@NotBlank(message = "用户登录密码不能为空")
	private String loginPassword;

	@NotBlank(message = "用户支付密码不能为空")
	private String oldPaymentpassword;

	@NotBlank(message = "用户支付密码不能为空")
	private String newPaymentPassword;
	
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOldPaymentpassword() {
		return oldPaymentpassword;
	}

	public void setOldPaymentpassword(String oldPaymentpassword) {
		this.oldPaymentpassword = oldPaymentpassword;
	}

	public String getNewPaymentPassword() {
		return newPaymentPassword;
	}

	public void setNewPaymentPassword(String newPaymentPassword) {
		this.newPaymentPassword = newPaymentPassword;
	}

}
