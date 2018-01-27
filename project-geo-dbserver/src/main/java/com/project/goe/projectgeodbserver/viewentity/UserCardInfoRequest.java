package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class UserCardInfoRequest {
	@NotBlank(message = "用户名不能为空!")
	@Size(min = 5, max = 20, message = "用户名长度不合法!")
	private String account;

	@NotBlank(message = "银行卡号不能为空!")
	@Pattern(regexp = "(^\\d{16,19}$", message = "银行卡号不合法!")
	private String cardNo;

	@NotBlank(message = "银行名称不能为空!")
	@Size(min = 1, max = 20, message = "银行名称长度不合法!")
	private String bankName;

	@NotBlank(message = "银行卡户主名不能为空!")
	@Size(min = 2, max = 20, message = "银行卡户主名长度不合法!")
	private String cardOwnerName;

	/**
	 * 验证手机号码
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
	 */
	@NotBlank(message = "用户手机号码不能为空")
	@Size(min = 11, max = 11, message = "用户手机号码长度为11位")
//	@Pattern(regexp = "((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardOwnerName() {
		return cardOwnerName;
	}

	public void setCardOwnerName(String cardOwnerName) {
		this.cardOwnerName = cardOwnerName;
	}

}
