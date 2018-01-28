package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UpdatePaymentPasswordRequest {
	@NotBlank(message = "用户名不能为空")
	@Size(min = 5, max = 20, message = "用户名的长度不合法(5-20)!")
	private String account;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String oldPaymentpassword;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String newPaymentPassword;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String newPaymentPassword2;

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

	public String getNewPaymentPassword2() {
		return newPaymentPassword2;
	}

	public void setNewPaymentPassword2(String newPaymentPassword2) {
		this.newPaymentPassword2 = newPaymentPassword2;
	}

}
