package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;

@RestController
@RequestMapping("/consumerRecord")
public class ConsumerRecordController {

	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	//新增一条消费记录
	@PostMapping("/addOneConsumeRecord")
	public ConsumeRecord addOneConsumeRecord(@ModelAttribute ConsumeRecord consumeRecord) {
		consumeRecord.setConsumeTime(new Date());
		return this.consumeRecordService.addOneConsumeRecord(consumeRecord);
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
