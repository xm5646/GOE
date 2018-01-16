package com.project.goe.projectgeodbserver.viewentity;

/**
 * @description:接收新增用户post请求参数
 *
 */
public class UserSavePostParams {
	private String account;
	private String password;
	private String parentAccount;
	private String recomendAccount;
	private String position;

	public String getParentAccount() {
		return parentAccount;
	}

	public void setParentAccount(String parentAccount) {
		this.parentAccount = parentAccount;
	}

	public String getRecomendAccount() {
		return recomendAccount;
	}

	public void setRecomendAccount(String recomendAccount) {
		this.recomendAccount = recomendAccount;
	}

	public UserSavePostParams() {
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
