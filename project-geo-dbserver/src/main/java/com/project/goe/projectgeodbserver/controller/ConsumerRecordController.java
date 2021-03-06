package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.ConsumeRecordRequest;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.TransferCoinRecord;

@CrossOrigin
@RestController
@RequestMapping("/consumerRecord")
public class ConsumerRecordController {

	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private UserService userService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	// 新增一条消费记录
	@PostMapping("/save")
	@Transactional
	public RetMsg addOneConsumeRecord(@Validated ConsumeRecordRequest consumeRecordRequest,
			BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 请求参数：description可以为null
		int consumeTypeCode = consumeRecordRequest.getConsumeTypeCode();
		String sendUserAccount = consumeRecordRequest.getSendUserAccount();
		String receiveUserAccount = consumeRecordRequest.getReceiveUserAccount();
		double consumeNumber = consumeRecordRequest.getConsumeNumber();
		String paymentPassword = consumeRecordRequest.getPaymentPassword();
		String description = consumeRecordRequest.getDescription();
		// 支出方对象
		User sendUser = this.userService.findByAccount(sendUserAccount);
		// 收入方对象
		User receiveUser = this.userService.findByAccount(receiveUserAccount);

		// 验证用户账号
		if (null == sendUser || null == receiveUser)
			throw new RuntimeException("收入方或支出方的账户不存在");

		// 验证用户的支付密码
		if (!(MD5Util.encrypeByMd5(paymentPassword)).equals(sendUser.getPaymentPassword())) {
			throw new RuntimeException("用户支付密码输入有误");
		}

		String consumeType = null;

		switch (consumeTypeCode) {
		case 1:
			consumeType = ConsumeType.COMPANY_TRANSFER_CONIN;
			break;
		case 2:
			consumeType = ConsumeType.BONUS_TRANSFER_CONIN;
			break;
		case 3:
			consumeType = ConsumeType.COIN_TRANSFER_COIN;
			break;
		default:
			throw new RuntimeException("消费类型码有误");
		}

		/********************* 更新用户表 ******************/
		// 消费类型：公司转账报单币
		if (consumeTypeCode == 1) {
			if (!(sendUser.getAccount()).equals("administrator"))
				throw new RuntimeException("支出方必须为公司账户");

			sendUser.setConsumeCoin(sendUser.getConsumeCoin() - consumeNumber);
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			this.userService.save(receiveUser);
			this.userService.save(sendUser);
			// 消费类型：奖金转报单币
		} else if (consumeTypeCode == 2) {
			if (!sendUser.getAccount().equals(receiveUser.getAccount()))
				throw new RuntimeException("支出方和收入方必须为同一账户");

			double bonousCoin = receiveUser.getBonusCoin();
			if (consumeNumber > bonousCoin)
				throw new RuntimeException("奖金余额不足");

			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			receiveUser.setBonusCoin(receiveUser.getBonusCoin() - consumeNumber);
			this.userService.save(receiveUser);
			// 消费类型：报单币转报单币
		} else if (consumeTypeCode == 3) {
			double consumeCoin = sendUser.getConsumeCoin();
			if (consumeNumber > consumeCoin)
				throw new RuntimeException("报单币余额不足");

			sendUser.setConsumeCoin(sendUser.getConsumeCoin() - consumeNumber);
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			this.userService.save(sendUser);
			this.userService.save(receiveUser);
		} else {
			throw new RuntimeException("消费类型有误");
		}

		// 新增一条消费记录
		ConsumeRecord consumeRecord = new ConsumeRecord();
		consumeRecord.setConsumeTime(new Date());
		consumeRecord.setUserId(sendUser.getUserId());
		consumeRecord.setSendUserId(sendUser.getUserId());
		consumeRecord.setReceiveUserId(receiveUser.getUserId());
		consumeRecord.setConsumeNumber(consumeNumber);
		consumeRecord.setConsumeStatus(true);
		consumeRecord.setConsumeType(consumeType);

		if (consumeTypeCode == 3)
			consumeRecord.setDescription(receiveUser.getAccount());
		else {
			consumeRecord.setDescription(consumeType);
		}

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(this.consumeRecordService.addOneConsumeRecord(consumeRecord));
		retMsg.setMessage("消费记录添加成功");
		retMsg.setSuccess(true);

		return retMsg;
	}

	// 按照用户id查询所有消费记录
	@GetMapping("/findByUserId")
	public List<ConsumeRecord> findByUserId(@RequestParam("userId") long userId) {
		return this.consumeRecordService.findByUserId(userId);
	}

	// 按照消费记录id查询所有消费记录
	@GetMapping("/findByConsumeId")
	public List<ConsumeRecord> findByConsumeId(@RequestParam("consumeId") long consumeId) {
		return this.consumeRecordService.findByConsumeId(consumeId);
	}

	// 基于用户id和消费类型，按时间降序排序分页查询
	@GetMapping("/findByAccountAndConsumeType")
	public RetMsg findByAccountAndConsumeType(@RequestParam("account") String account,
			@RequestParam("consumeTypeCode") int consumeTypeCode,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "consumeTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		if (null == account)
			throw new RuntimeException("用户名不能为空");

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户名不存在");

		ConsumeRecord consumeRecord = new ConsumeRecord();
		consumeRecord.setUserId(user.getUserId());
		consumeRecord.setSendUserId(user.getUserId());
		consumeRecord.setReceiveUserId(user.getUserId());

		String consumeType = null;

		switch (consumeTypeCode) {
		case 1:
			consumeType = ConsumeType.COMPANY_TRANSFER_CONIN;
			break;
		case 2:
			consumeType = ConsumeType.BONUS_TRANSFER_CONIN;
			break;
		case 3:
			consumeType = ConsumeType.COIN_TRANSFER_COIN;
			break;
		case 4:
			consumeType = ConsumeType.COIN_TRANSFER_ADDCONSUMER;
			break;
		case 5:
			consumeType = ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT;
			break;
		case 6:
			consumeType = ConsumeType.COIN_TRANSFER_RECONSUME;
			break;
		default:
			throw new RuntimeException("消费类型码有误");
		}

		consumeRecord.setConsumeType(consumeType);

		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<ConsumeRecord> consumeRecordPage = null;

			if (3 == consumeTypeCode) {
				consumeRecordPage = this.consumeRecordService.findByAccountAndConsumeType1(consumeRecord, pageable);
			} else
				consumeRecordPage = this.consumeRecordService.findByAccountAndConsumeType(consumeRecord, pageable);

			Page<TransferCoinRecord> transferCoinRecordPage = consumeRecordPage
					.map(new Converter<ConsumeRecord, TransferCoinRecord>() {

						@Override
						public TransferCoinRecord convert(ConsumeRecord consumeRecord) {
							long sendUserId = consumeRecord.getSendUserId();
							long receiveUserId = consumeRecord.getReceiveUserId();

							User sender = userService.findByUserId(sendUserId);
							User receiver = userService.findByUserId(receiveUserId);
							if (null == sender)
								throw new RuntimeException("转账账号不存在");

							if (null == receiver)
								throw new RuntimeException("收账账号不存在");

							TransferCoinRecord tRecord = new TransferCoinRecord();
							tRecord.setConsumeTime(DateFormatUtil.DateObjectToString(consumeRecord.getConsumeTime()));
							tRecord.setReceiveUserAccount(receiver.getAccount());
							tRecord.setSendUserAccount(sender.getAccount());
							tRecord.setTransferCoinNumber((long) consumeRecord.getConsumeNumber());
							tRecord.setDescription(consumeRecord.getDescription());

							return tRecord;
						}

					});

			RetMsg retMsg = new RetMsg();
			
			retMsg.setCode(200);
			retMsg.setData(transferCoinRecordPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}
	}

}
