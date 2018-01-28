package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @description:接收新增用户post请求参数
 *
 */
public class UserSaveRequest {
	@NotBlank(message = "新增用户名不能为空!")
	@Pattern(regexp = "^[a-zA-Z0-9_]{5,20}$",message = "用户名(5-20位)数字、字母、下划线!")
	private String account;
	
	@NotBlank(message = "新增用户密码不能为空!")
	@Size(min = 6, max = 12, message = "用户密码长度不合法(6-12位)!")
	private String password;
	
	@NotBlank(message = "上级用户名不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9_]{5,20}$",message = "用户名(5-20位)数字、字母、下划线")
	private String parentAccount;
	
	@NotBlank(message = "推荐人用户名不能为空")
	@Pattern(regexp = "^[a-zA-Z0-9_]{5,20}$",message = "用户名(5-20位)数字、字母、下划线")
	private String recommendAccount;
	
	@NotBlank(message = "用户所属部门不能为空")
	@Pattern(regexp = "[ABC]",message = "用户部门位置只能为A、B或C")
	private String position;

	public String getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}

	public String getRecommendAccount() {
		return recommendAccount;
	}

	public void setRecommendAccount(String recommendAccount) {
		this.recommendAccount = recommendAccount;
	}

	public UserSaveRequest() {
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
