package com.project.goe.projectgeodbserver.controller;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.DrawCashRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.CardInfoService;
import com.project.goe.projectgeodbserver.service.DrawCashService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.DrawStatus;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.MD5Util;
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
		String paymentPassword = userDrawCashRequest.getPaymentPassword();
		String phone = userDrawCashRequest.getPhone();

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
		
		if(!(MD5Util.encrypeByMd5(paymentPassword).equals(user.getPaymentPassword())))
			throw new RuntimeException("支付密码输入有误!");

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
			drawCashRecord.setPhone(phone);

			this.drawCashService.save(drawCashRecord);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setMessage("用户提现申请成功!");
			retMsg.setData(drawCashRecord);
			retMsg.setSuccess(true);
			
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户提现申请失败!");
		}
	}
	
	//分页查询所有用户的提现信息(基于提现申请时间)
	@GetMapping("/findAllDrawCashRecordBySort")
	public Page<DrawCashRecord> findAllDrawCashRecord(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "drawCommitTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.drawCashService.findAllDrawCardRecordBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException("查询失败!");
		}
	}
	

}
