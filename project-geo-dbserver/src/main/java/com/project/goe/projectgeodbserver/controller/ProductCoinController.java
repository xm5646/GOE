package com.project.goe.projectgeodbserver.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.ExpressAddress;
import com.project.goe.projectgeodbserver.entity.OrderInfo;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.ExpressAddressService;
import com.project.goe.projectgeodbserver.service.OrderInfoService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.ProductCoinTransferRequest;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/productCoin")
public class ProductCoinController {
	@Autowired
	private UserService userService;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	@Autowired
	private ExpressAddressService expressAddressService;

	@PostMapping("/transfer")
	public RetMsg productCoinTransfer(@Validated ProductCoinTransferRequest productCoinTransferRequest,
			BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String sendAccount = productCoinTransferRequest.getSendAccount();
		String receiveAccount = productCoinTransferRequest.getReceiveAccout();
		double productCoin = productCoinTransferRequest.getProductCoin();
		String paymentPassword = productCoinTransferRequest.getPaymentPassword();
		long expressId = productCoinTransferRequest.getExpressId();

		// 验证用户是否存在
		User sendUser = this.userService.findByAccount(sendAccount);
		User receiveUser = this.userService.findByAccount(receiveAccount);

		if (null == sendUser || null == receiveUser)
			throw new RuntimeException("用户不存在!");

		// 验证用户的产品积分是否足够兑换产品
		double productCoinUnitPrice = this.bonusPayPercentage.getConsumeCoinUnitPrice();
		double pCoin = sendUser.getProductCoin();
		// 产品积分小于产品所需最低积分
		if (productCoin < 0 || (productCoin < productCoinUnitPrice))
			throw new RuntimeException("产品积分余额不足!");
		// consumeNumber大于用户产品积分
		if (productCoin > pCoin)
			throw new RuntimeException("产品积分输入不合法!");

		// 验证用户的支付密码
		if (!(MD5Util.encrypeByMd5(paymentPassword)).equals(sendUser.getPaymentPassword()))
			throw new RuntimeException("支付密码输入有误!");

		// 更新指出方用户信息和收入方用户信息
		// 兑换产品的数量
		int num = (int) Math.floor(productCoin / productCoinUnitPrice);
		sendUser.setProductCoin(pCoin - productCoinUnitPrice * num);
		receiveUser.setProductCoin(productCoinUnitPrice * num);
		this.userService.save(sendUser);
		this.userService.save(receiveUser);

		// 更新消费记录表
		ConsumeRecord consumeRecord = new ConsumeRecord();
		consumeRecord.setConsumeTime(new Date());
		consumeRecord.setUserId(sendUser.getUserId());
		consumeRecord.setSendUserId(sendUser.getUserId());
		consumeRecord.setReceiveUserId(receiveUser.getUserId());
		consumeRecord.setConsumeNumber(productCoinUnitPrice * num);
		consumeRecord.setConsumeStatus(false);
		consumeRecord.setConsumeType(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
		consumeRecord.setDescription(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
		this.consumeRecordService.addOneConsumeRecord(consumeRecord);

		// 更新订单列表
		// 生成用户订单列表
		OrderInfo orderInfo = new OrderInfo();
		
		if(expressId < 0 || expressId > Long.MAX_VALUE)
			throw new RuntimeException("快递地址id不合法!");
		
		// 验证expressId是否存在
		ExpressAddress expressAddress = this.expressAddressService.findByExpressId(expressId);
		if (null == expressAddress) {
			throw new RuntimeException("未找到快递地址!");
		}
		
		orderInfo.setExpressId(expressId);
		orderInfo.setUserId(sendUser.getUserId());
		orderInfo.setCreateTime(new Date());
		orderInfo.setDelivery(false);
		orderInfo.setDescription(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
		orderInfo.setExpressNo(null);
		orderInfo.setProductCount(num);

		this.orderInfoService.save(orderInfo);
		
		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(consumeRecord);
		retMsg.setSuccess(true);
		
		return retMsg;
	}

}
