package com.project.goe.projectgeodbserver.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.viewentity.ChargeBonusAndProductCoin;
import com.project.goe.projectgeodbserver.viewentity.ChargeConsumeCoin;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/goeIndexCharge")
@CrossOrigin
public class GoeIndexChargeController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private BonusPayListService bonusPayListService;

	// 充值报单币
	@PostMapping("/chargeReconsumeCoin")
	public RetMsg chargeReconsumeCoin(ChargeConsumeCoin chargeConsumeCoin) {
		RetMsg retMsg = null;
		// 充值用户账号
		String account = chargeConsumeCoin.getAccount();
		// 管理员的支付密码
		String paymentPassword = chargeConsumeCoin.getPaymentPassword();
		// 充值额度
		double consumeCoin = chargeConsumeCoin.getConsumeCoin();

		if (null == account)
			throw new RuntimeException("充值用户名不能为空");

		User adminUser = this.userService.findByAccount("administrator");
		if (null == adminUser)
			throw new RuntimeException("管理员账户不存在");

		User chargeUser = this.userService.findByAccount(account);
		if (null == chargeUser)
			throw new RuntimeException("充值用户不存在");

		String paymentPassword2 = adminUser.getPaymentPassword();
		if (!paymentPassword2.equals(MD5Util.encrypeByMd5(paymentPassword)))
			throw new RuntimeException("支付密码输入有误");

		if (consumeCoin < 0 || consumeCoin > Double.MAX_VALUE)
			throw new RuntimeException("充值金额不合法");

		// 更新管理员用户和充值用户信息
		try {
			adminUser.setConsumeCoin(adminUser.getConsumeCoin() - consumeCoin);
			chargeUser.setConsumeCoin(chargeUser.getConsumeCoin() + consumeCoin);
			this.userService.save(adminUser);
			this.userService.save(chargeUser);

			// 新增一条消费信息
			ConsumeRecord consumeRecord = new ConsumeRecord();
			consumeRecord.setConsumeNumber(consumeCoin);
			consumeRecord.setConsumeStatus(true);
			consumeRecord.setConsumeTime(new Date());
			consumeRecord.setConsumeType(ConsumeType.COMPANY_TRANSFER_CONIN);
			consumeRecord.setDescription(ConsumeType.COMPANY_TRANSFER_CONIN);
			consumeRecord.setReceiveUserId(chargeUser.getUserId());
			consumeRecord.setSendUserId(adminUser.getUserId());
			consumeRecord.setUserId(adminUser.getUserId());

			this.consumeRecordService.addOneConsumeRecord(consumeRecord);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(consumeCoin);
			retMsg.setMessage("充值成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("充值失败");
		}

	}

	// 充值奖金和产品积分
	@PostMapping("/chargeBonusAndProductCoin")
	public RetMsg chargeBonusAndProductCoin(ChargeBonusAndProductCoin chargeBonusAndProductCoin) {
		// 充值账户
		String account = chargeBonusAndProductCoin.getAccount();
		// 管理员支付密码
		String paymentPassword = chargeBonusAndProductCoin.getPaymentPassword();
		// 充值奖金
		double bonus = chargeBonusAndProductCoin.getBonus();
		// 充值产品积分
		double productCoin = chargeBonusAndProductCoin.getProductCoin();
		BonusPayList bonusPayList = null;
		RetMsg retMsg = null;

		if (null == account)
			throw new RuntimeException("充值用户名不能为空");

		User adminUser = this.userService.findByAccount("administrator");
		if (null == adminUser)
			throw new RuntimeException("管理员账户不存在");

		User chargeUser = this.userService.findByAccount(account);
		if (null == chargeUser)
			throw new RuntimeException("充值用户不存在");

		String paymentPassword2 = adminUser.getPaymentPassword();
		if (!paymentPassword2.equals(MD5Util.encrypeByMd5(paymentPassword)))
			throw new RuntimeException("支付密码输入有误");

		if (bonus < 0 || bonus > Double.MAX_VALUE || productCoin < 0 || productCoin > Double.MAX_VALUE)
			throw new RuntimeException("充值金额不合法");

		try {
			// 新增一条奖金发放记录
			Date date = new Date();
			bonusPayList = new BonusPayList();
			bonusPayList.setBonusNumber(bonus);
			bonusPayList.setCreateTime(date);
			bonusPayList.setManageCost(0f);
			bonusPayList.setPayTime(date);
			bonusPayList.setProductCoinNumber(productCoin);
			bonusPayList.setTotalMoney(0f);
			bonusPayList.setUserId(chargeUser.getUserId());
			this.bonusPayListService.save(bonusPayList);

			// 更新管理员和充值用户信息
			adminUser.setBonusCoin(adminUser.getBonusCoin() - bonus);
			adminUser.setProductCoin(adminUser.getProductCoin() - productCoin);

			chargeUser.setBonusCoin(chargeUser.getBonusCoin() + bonus);
			chargeUser.setProductCoin(chargeUser.getProductCoin() + productCoin);

			this.userService.save(adminUser);
			this.userService.save(chargeUser);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(new Double[] { bonus, productCoin });
			retMsg.setMessage("充值成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("充值失败");
		}
	}
}
