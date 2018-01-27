package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UserLoginRequest {
	@NotBlank(message="用户名不能为空")
	@Size(min = 5,max = 20,message = "用户名的长度不合法(5-20)!")
	private String account;
	
	@NotBlank(message="用户密码不能为空")
	@Size(min = 6,max = 12,message = "用户密码长度不合法(6-12位)!")
	private String password;

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

}
