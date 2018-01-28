package com.project.goe.projectgeodbserver.viewentity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class ConsumeRecordRequest {
	@NotBlank(message = "支出方用户不能为空!")
	private String sendUserAccount;

	@NotBlank(message = "收入方用户不能为空!")
	private String receiveUserAccount;

	@NotNull(message = "消费类型不能为空!")
	@Range(min = 1, max = 4, message = "消费类型参数不合法!")
	private Integer consumeTypeCode;

	@NotNull(message = "消费金额不能为空!")
	@Min(0)
	private Double consumeNumber;

	@NotBlank(message = "用户支付密码不能为空")
	@Pattern(regexp = "[0-9]{6}", message = "支付密码只能是6位数字")
	private String paymentPassword;

	private String description;

	public String getSendUserAccount() {
		return sendUserAccount;
	}

	public void setSendUserAccount(String sendUserAccount) {
		this.sendUserAccount = sendUserAccount;
	}

	public String getReceiveUserAccount() {
		return receiveUserAccount;
	}

	public void setReceiveUserAccount(String receiveUserAccount) {
		this.receiveUserAccount = receiveUserAccount;
	}

	public Integer getConsumeTypeCode() {
		return consumeTypeCode;
	}

	public void setConsumeTypeCode(Integer consumeTypeCode) {
		this.consumeTypeCode = consumeTypeCode;
	}

	public Double getConsumeNumber() {
		return consumeNumber;
	}

	public void setConsumeNumber(Double consumeNumber) {
		this.consumeNumber = consumeNumber;
	}

	public String getPaymentPassword() {
		return paymentPassword;
	}

	public void setPaymentPassword(String paymentPassword) {
		this.paymentPassword = paymentPassword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
