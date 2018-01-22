package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

public class UserLoginPasswordUpdateRequest {
	@NotBlank(message = "用户名不能为空")
	private String account;
	@NotBlank(message = "用户原密码不能为空")
	private String oldPassword;
	@NotBlank(message = "用户新密码不能为空")
	@Size(min = 6,max = 12,message = "用户登录新密码长度只能6-12之间")
	private String newPassword;

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

}
