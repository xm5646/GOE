package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.TransferCoinRecord;

@CrossOrigin
@RestController
@RequestMapping("/goeIndexConsumeController")
public class GoeIndexConsumeController {
	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private UserService userService;

	// 分页查询转账信息
	@GetMapping("/findAllTransferByConsumeType")
	public RetMsg findAllTransferByConsumeType(@RequestParam("code") int code,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "consumeTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		String consumeType = null;

		switch (code) {
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
			throw new RuntimeException("消费类型码错误");
		}

		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);
			Pageable pageable = new PageRequest(pageNum, size, sort);
			Page<ConsumeRecord> consumeRecordPage = this.consumeRecordService.findByConsumeTypeOfPage(consumeType,
					pageable);
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

	// 基于用户id和消费类型，按时间降序排序分页查询
	@GetMapping("/findByAccountAndConsumeType")
	public RetMsg findByAccountAndConsumeType(@RequestParam("account") String account,
			@RequestParam("code") int code,
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

		switch (code) {
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

			if (3 == code) {
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
