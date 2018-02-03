package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.project.goe.projectgeodbserver.statusType.DeliveryStatus;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.ProductCoinTransferRequest;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/productCoin")
@CrossOrigin
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

		String account = productCoinTransferRequest.getAccount();
		double productCoin = productCoinTransferRequest.getProductCoin();
		String paymentPassword = productCoinTransferRequest.getPaymentPassword();
		long expressId = productCoinTransferRequest.getExpressId();

		// 验证用户是否存在
		User user = this.userService.findByAccount(account);

		if (null == user)
			throw new RuntimeException("用户不存在");

		// 验证产品积分输入是否合法
		if (productCoin < 0 || productCoin > Double.MAX_VALUE)
			throw new RuntimeException("产品积分输入不合法");

		// 验证用户的产品积分是否足够兑换产品
		double productCoinUnitPrice = this.bonusPayPercentage.getConsumeCoinUnitPrice();
		double userProductCoin = user.getProductCoin();
		// 产品积分小于产品所需最低积分
		if (userProductCoin < productCoinUnitPrice)
			throw new RuntimeException("产品积分余额不足");

		// productCoin输入兑换积分大于用户产品积分
		if (productCoin > userProductCoin)
			throw new RuntimeException("产品积分输入不合法");

		// 验证用户的支付密码
		if (!(MD5Util.encrypeByMd5(paymentPassword)).equals(user.getPaymentPassword()))
			throw new RuntimeException("支付密码输入有误");

		// 更新指出方用户信息和收入方用户信息
		// 兑换产品的数量
		int num = (int) Math.floor(productCoin / productCoinUnitPrice);
		user.setProductCoin(userProductCoin - productCoinUnitPrice * num);

		User company = this.userService.findByAccount("administrator");
		if (null == company)
			throw new RuntimeException("公司账户不存在");

		company.setProductCoin(company.getProductCoin() + productCoinUnitPrice * num);

		this.userService.save(user);
		this.userService.save(company);

		// 更新消费记录表
		ConsumeRecord consumeRecord = new ConsumeRecord();
		consumeRecord.setConsumeTime(new Date());
		consumeRecord.setUserId(user.getUserId());
		consumeRecord.setSendUserId(user.getUserId());
		consumeRecord.setReceiveUserId(company.getUserId());
		consumeRecord.setConsumeNumber(productCoinUnitPrice * num);
		consumeRecord.setConsumeStatus(false);
		consumeRecord.setConsumeType(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
		consumeRecord.setDescription(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
		this.consumeRecordService.addOneConsumeRecord(consumeRecord);

		// 更新订单列表
		// 生成用户订单列表
		OrderInfo orderInfo = new OrderInfo();

		if (expressId > Long.MAX_VALUE)
			throw new RuntimeException("快递地址id不合法");

		// -1:使用用户默认地址
		if (-1 == expressId) {
			List<ExpressAddress> expressAddresses = this.expressAddressService.findByUserId(user.getUserId());

			if (null == expressAddresses || 0 == expressAddresses.size())
				throw new RuntimeException("用户未设置快递地址");

			for (ExpressAddress expressAddress : expressAddresses) {
				if (expressAddress.isDefaultAddress()) {
					orderInfo.setExpressId(expressAddress.getExpressId());
					break;
				}
			}
		} else {
			// 验证expressId是否存在
			ExpressAddress expressAddress = this.expressAddressService.findByExpressId(expressId);
			if (null == expressAddress) {
				throw new RuntimeException("未找到快递地址");
			}
			orderInfo.setExpressId(expressId);
		}

		orderInfo.setUserId(user.getUserId());
		orderInfo.setCreateTime(new Date());
		orderInfo.setIsDelivery(DeliveryStatus.ORDER_DELIVERY_NO);
		orderInfo.setDescription(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
		orderInfo.setExpressNo(null);
		orderInfo.setProductCount(num);
		orderInfo.setTotalPrice(productCoinUnitPrice * num);
		orderInfo.setOrderType(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);

		try {
			this.orderInfoService.save(orderInfo);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData("积分兑换成功");
			retMsg.setMessage("积分兑换成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("积分兑换失败");
		}
	}

}
