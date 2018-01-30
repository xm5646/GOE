package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.ExpressAddress;
import com.project.goe.projectgeodbserver.entity.OrderInfo;
import com.project.goe.projectgeodbserver.entity.ReconsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.ExpressAddressService;
import com.project.goe.projectgeodbserver.service.OrderInfoService;
import com.project.goe.projectgeodbserver.service.ReconsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserReConsumeRequest;

@CrossOrigin
@RestController
@RequestMapping("/reconsumeRecord")
public class ReconsumeRecordController {

	@Autowired
	private UserService userService;

	@Autowired
	private ReconsumeRecordService reconsumeRecordService;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private ExpressAddressService expressAddressService;

	@PostMapping("/purchaseReconsume")
	@Transactional
	public RetMsg purchaseReconsume(@Validated UserReConsumeRequest userReConsumeRequest, BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String account = userReConsumeRequest.getAccount();
		String paymentPassword = userReConsumeRequest.getPaymentPassword();
		long expressId = userReConsumeRequest.getExpressId();

		// 验证用户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		// 验证用户是否激活:非激活状态，不能做任何操作
		if (!(user.isUserStatus()))
			throw new RuntimeException("用户未激活!");

		// 验证用户是否有资格重销:下次重销时间等于创建用户时间,用户未达到考核状态
		if (DateFormatUtil.compareDateObject(user.getCreateTime(), user.getAssessDate()) == 0)
			throw new RuntimeException("用户没有达到考核级别！");

		if (user.isAssessStatus()) {
			Date nowDate = new Date();
			Date assDate = user.getAssessDate();
			if (!DateFormatUtil.DateObjectToString(nowDate).equals(DateFormatUtil.DateObjectToString(assDate))) {
				throw new RuntimeException("用户未达到考核日期！");
			}
		}

		// 验证支付密码是否正确
		if (!(MD5Util.encrypeByMd5(paymentPassword)).equals(user.getPaymentPassword())) {
			throw new RuntimeException("用户支付密码错误!");
		}

		// 验证用户报单币是否够用
		double consumeCoin = user.getConsumeCoin();
		double reConsumeCost = this.bonusPayPercentage.getConsumeCoinUnitPrice();
		
		if ((consumeCoin - reConsumeCost) < 0)
			throw new RuntimeException("用户报单币余额不足！");

		// 更新用户表和公司表
		user.setConsumeCoin(consumeCoin - reConsumeCost);
		user.setAssessStatus(true);
		
		User company = this.userService.findByAccount("administrator");
		if(null == company)
			throw new RuntimeException("公司账户不存在!");
		
		company.setConsumeCoin(company.getConsumeCoin() + reConsumeCost);
		this.userService.save(user);
		this.userService.save(company);

		// 更新用户重销记录表
		Date now = new Date();
		ReconsumeRecord reconsumeRecord = new ReconsumeRecord();
		reconsumeRecord.setCreateTime(now);
		reconsumeRecord.setUserId(user.getUserId());
		reconsumeRecord.setReconsumePayment(reConsumeCost);
		this.reconsumeRecordService.save(reconsumeRecord);

		// 更新用户消费记录表
		ConsumeRecord consumeRecord = new ConsumeRecord();
		consumeRecord.setUserId(user.getUserId());
		consumeRecord.setConsumeTime(now);
		consumeRecord.setConsumeType(ConsumeType.COIN_TRANSFER_RECONSUME);
		consumeRecord.setUserId(user.getUserId());
		consumeRecord.setReceiveUserId(this.userService.findByAccount("administrator").getUserId());
		consumeRecord.setConsumeNumber(reConsumeCost);
		consumeRecord.setConsumeStatus(false);
		consumeRecord.setDescription(ConsumeType.COIN_TRANSFER_RECONSUME);
		this.consumeRecordService.addOneConsumeRecord(consumeRecord);

		// 生成用户订单列表
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateTime(new Date());
		orderInfo.setDelivery(false);
		orderInfo.setDescription("重复消费");
		orderInfo.setExpressNo(null);

		// 如果expressId没有数据，则使用用户的默认收货地址
		if (0 == expressId) {
			List<ExpressAddress> expressAddresses = this.expressAddressService.findByUserId(user.getUserId());
			
			if(null == expressAddresses || 0 == expressAddresses.size())
				throw new RuntimeException("用户未设置快递地址!");
			
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
				throw new RuntimeException("未找到快递地址!");
			}
			orderInfo.setExpressId(expressId);
		}
		orderInfo.setOrderType(ConsumeType.COIN_TRANSFER_RECONSUME);
		orderInfo.setUserId(user.getUserId());
		orderInfo.setProductCount(1);

		// 新增订单
		this.orderInfoService.save(orderInfo);

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setMessage("用户重销成功!");
		retMsg.setSuccess(true);
		retMsg.setData(reconsumeRecord);
		return retMsg;
	}

	// 分页查询指定用户所有的重销记录
	public Page<ReconsumeRecord> findAllCardInfoByAccoun(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {

		if (account == null)
			throw new RuntimeException("用户名不能为空!");

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.reconsumeRecordService.findAllReconsumeRecordBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException("查询用户重销记录失败!");
		}
	}

}
