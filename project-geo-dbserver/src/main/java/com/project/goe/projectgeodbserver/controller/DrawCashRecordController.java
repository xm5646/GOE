package com.project.goe.projectgeodbserver.controller;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.DrawCashRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.CardInfoService;
import com.project.goe.projectgeodbserver.service.DrawCashService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.DrawStatus;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserDrawCashRequest;

@RestController
@RequestMapping("/drawCashRecord")
public class DrawCashRecordController {

	@Autowired
	private UserService userService;

	@Autowired
	private CardInfoService cardInfoService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	@Autowired
	private DrawCashService drawCashService;

	// 保存一条提现记录信息
	@PostMapping("/save")
	@Transactional
	public RetMsg save(@Validated UserDrawCashRequest userDrawCashRequest, BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String account = userDrawCashRequest.getAccount();
		double drawNumber = userDrawCashRequest.getDrawNumber();
		String cardNumber = userDrawCashRequest.getCardNumber();

		// 验证用户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		// 验证用户提取限额是否足够
		if (drawNumber > user.getBonusCoin())
			throw new RuntimeException("现金提取额度不够!");

		// 验证用户银行卡是否与用户真名是否一致
		String nickName = user.getNickName();
		String cardOwnerName = this.cardInfoService.findByCardNumber(cardNumber).getCardOwnerName();

		if (!nickName.equals(cardOwnerName))
			throw new RuntimeException("银行卡用户名与用户真名不一致!");

		try {
			// 新增一条提现记录
			DrawCashRecord drawCashRecord = new DrawCashRecord();
			drawCashRecord.setUserId(user.getUserId());
			drawCashRecord.setCardNumber(cardNumber);
			drawCashRecord.setDrawnumber(drawNumber);

			double finalNumber = this.bonusPayPercentage.getDrawCostPercentage() * drawNumber;
			drawCashRecord.setFinalNumber(finalNumber);
			drawCashRecord.setDrawStatus(DrawStatus.AUDIT_WAIT);
			drawCashRecord.setDrawCommitTime(new Date());
			drawCashRecord.setPayTime(null);

			this.drawCashService.save(drawCashRecord);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setMessage("用户新增提现记录成功!");
			retMsg.setData(drawCashRecord);
			retMsg.setSuccess(true);
			
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户新增提现记录失败!");
		}
	}
	

}
