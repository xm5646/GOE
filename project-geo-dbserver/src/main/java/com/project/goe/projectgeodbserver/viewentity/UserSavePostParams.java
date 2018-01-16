package com.project.goe.projectgeodbserver.viewentity;

/**
 * @description:接收新增用户post请求参数
 *
 */
public class UserSavePostParams {
	private String account;
	private String password;
	private long parentId;
	private long recomondId;
	private String position;

	public UserSavePostParams() {
	}

	public long getRecomondId() {
		return recomondId;
	}

	public void setRecomondId(long recomondId) {
		this.recomondId = recomondId;
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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
