package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.viewentity.ConsumeRecordRequest;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@CrossOrigin
@RestController
@RequestMapping("/consumerRecord")
public class ConsumerRecordController {

	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	@Autowired
	private UserService userService;
	
	//新增一条消费记录
	@PostMapping("/save")
	@Transactional
	public RetMsg addOneConsumeRecord(@ModelAttribute ConsumeRecordRequest consumeRecordRequest) {
		//请求参数：description可以为null
		int consumeTypeCode = consumeRecordRequest.getConsumeTypeCode();
		String sendUserAccount = consumeRecordRequest.getSendUserAccount();
		String receiveUserAccount =  consumeRecordRequest.getReceiveUserAccount();
		double consumeNumber = consumeRecordRequest.getConsumeNumber();
		String description = consumeRecordRequest.getDescription();
		
		String consumeType = null;
		
		switch(consumeTypeCode) {
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
				consumeType = ConsumeType.PRODCUTCOIN_TRANSFER_COIN;
				break;
			case 6:
				consumeType = ConsumeType.COIN_TRANSFER_RECONSUME;
				break;
			default:
				throw new RuntimeException("消费类型状态码有误！");
		}
		
		//支出方对象
		User sendUser = this.userService.findByAccount(sendUserAccount);
		//收入方对象
		User receiveUser = this.userService.findByAccount(receiveUserAccount);
		
		if(null==sendUser || null==receiveUser) 
			throw new RuntimeException("收入方或支出方的账户不存在!");

		//消费类型：公司转账报单币,不更新支出方,
		if(consumeTypeCode == 1) {
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			this.userService.save(receiveUser);
		//消费类型：奖金转报单币
		}else if(consumeTypeCode == 2) {
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			receiveUser.setBonusCoin(receiveUser.getBonusCoin() - consumeNumber);
			this.userService.save(receiveUser);
		//消费类型：报单币转报单币
		}else if(consumeTypeCode == 3) {
			sendUser.setConsumeCoin(sendUser.getConsumeCoin() - consumeNumber);
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			this.userService.save(sendUser);
			this.userService.save(receiveUser);
		//消费类型：报单币消费报单
		}else if(consumeTypeCode == 4) {
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() - consumeNumber);
			this.userService.save(receiveUser);
		//消费类型：产品积分兑换报单币
		}else if(consumeTypeCode == 5) {
			receiveUser.setProductCoin(receiveUser.getProductCoin() - consumeNumber);
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() + consumeNumber);
			this.userService.save(receiveUser);
		//消费类型：报单币兑换重复消费
		}else if(consumeTypeCode == 6){
			receiveUser.setConsumeCoin(receiveUser.getConsumeCoin() - consumeNumber);
			this.userService.save(receiveUser);
		}else {
			throw new RuntimeException("消费类型有误!");
		}
		
		//新增一条消费记录
		ConsumeRecord consumeRecord = new ConsumeRecord();
		consumeRecord.setConsumeTime(new Date());
		consumeRecord.setUserId(sendUser.getUserId());
		consumeRecord.setSendUserId(sendUser.getUserId());
		consumeRecord.setReceiveUserId(receiveUser.getUserId());
		consumeRecord.setConsumeNumber(consumeNumber);
		consumeRecord.setConsumeStatus(true);
		consumeRecord.setConsumeType(consumeType);
		
		//如果描述信息为空
		if(null == description)
			consumeRecord.setDescription(consumeType);
		else
			consumeRecord.setDescription(description);
		
		RetMsg retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(this.consumeRecordService.addOneConsumeRecord(consumeRecord));
		retMsg.setMessage("消费记录添加成功！");
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
	

}
