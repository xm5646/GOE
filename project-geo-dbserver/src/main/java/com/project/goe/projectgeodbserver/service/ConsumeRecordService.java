package com.project.goe.projectgeodbserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.repository.ConsumeRecordRepository;

@Service
public class ConsumeRecordService {
	@Autowired
	private ConsumeRecordRepository consumeRecordRepository;
	
	//新增一条消费记录
	@Transactional
	public ConsumeRecord addOneConsumeRecord(ConsumeRecord consumeRecord) {
		return this.consumeRecordRepository.save(consumeRecord);
	}
	//新增多条消费记录
	@Transactional
	public List<ConsumeRecord> addBatchConsumeRecord(List<ConsumeRecord> consumeRecordsList) {
		List<ConsumeRecord> list = new ArrayList<ConsumeRecord>();
		
		for(ConsumeRecord consumeRecord : consumeRecordsList) {
			list.add(this.consumeRecordRepository.save(consumeRecord));
		}
		
		return list;
	}
	
	//按照用户id查询消费记录
	public List<ConsumeRecord> findByUserId(long userId) {
		return this.consumeRecordRepository.findByUserId(userId);
	}
	
	//按照消费记录id查询消费记录
	public List<ConsumeRecord> findByConsumeId(long consumeId) {
		return this.consumeRecordRepository.findByConsumeId(consumeId);
	}
	
}
