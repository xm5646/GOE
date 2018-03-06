package com.project.goe.projectgeodbserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.repository.ConsumeRecordRepository;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;

@Service
public class ConsumeRecordService {
	@Autowired
	private ConsumeRecordRepository consumeRecordRepository;

	// 新增一条消费记录
	@Transactional
	public ConsumeRecord addOneConsumeRecord(ConsumeRecord consumeRecord) {
		return this.consumeRecordRepository.save(consumeRecord);
	}

	// 新增多条消费记录
	@Transactional
	public List<ConsumeRecord> addBatchConsumeRecord(List<ConsumeRecord> consumeRecordsList) {
		List<ConsumeRecord> list = new ArrayList<ConsumeRecord>();

		for (ConsumeRecord consumeRecord : consumeRecordsList) {
			list.add(this.consumeRecordRepository.save(consumeRecord));
		}

		return list;
	}

	// 按照用户id查询消费记录
	public List<ConsumeRecord> findByUserId(long userId) {
		return this.consumeRecordRepository.findByUserId(userId);
	}

	// 按照消费记录id查询消费记录
	public List<ConsumeRecord> findByConsumeId(long consumeId) {
		return this.consumeRecordRepository.findByConsumeId(consumeId);
	}

	// 多条件分页查询:按用户名和消费类型
	public Page<ConsumeRecord> findByAccountAndConsumeType(ConsumeRecord consumeRecord, Pageable pageable) {
		Specification<ConsumeRecord> spec = new Specification<ConsumeRecord>() {

			@Override
			public Predicate toPredicate(Root<ConsumeRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("userId").as(long.class), consumeRecord.getUserId());
				Predicate p2 = cb.equal(root.get("consumeType").as(String.class), consumeRecord.getConsumeType());
				Predicate p = cb.and(p1, p2);

				return p;
			}

		};

		return this.consumeRecordRepository.findAll(spec, pageable);
	}

	// 查询本月消费类型为重销和报单的消费记录
	public List<ConsumeRecord> findByConsumeTimeOfNowMonth() {
		List<ConsumeRecord> list = new ArrayList<ConsumeRecord>();

		// 本月起始和结束时间
		List<Date> dateList = DateFormatUtil.getStartDateAndEndDateOfNowMonth();

		// 本月起始和结束时间内的所有消费记录
		List<ConsumeRecord> consumeRecordList = this.consumeRecordRepository.findByConsumeTimeBetween(dateList.get(0),
				dateList.get(1));

		// 将重销和报单的消费记录添加至list集合中
		for (ConsumeRecord consumeRecord : consumeRecordList) {
			String consumeType = consumeRecord.getConsumeType();

			if (consumeType.equals(ConsumeType.COIN_TRANSFER_ADDCONSUMER)
					|| consumeType.equals(ConsumeType.COIN_TRANSFER_RECONSUME)) {
				list.add(consumeRecord);
			}
		}

		return list;
	}

	// 查询本日消费类型为重销和报单的消费记录
	public List<ConsumeRecord> findByConsumeTimeOfNowDay() {
		List<ConsumeRecord> list = new ArrayList<ConsumeRecord>();

		// 本月起始和结束时间
		List<Date> dateList = DateFormatUtil.getStartDateAndEndDateOfNowDay();

		// 本月起始和结束时间内的所有消费记录
		List<ConsumeRecord> consumeRecordList = this.consumeRecordRepository.findByConsumeTimeBetween(dateList.get(0),
				dateList.get(1));

		// 将重销和报单的消费记录添加至list集合中
		for (ConsumeRecord consumeRecord : consumeRecordList) {
			String consumeType = consumeRecord.getConsumeType();

			if (consumeType.equals(ConsumeType.COIN_TRANSFER_ADDCONSUMER)
					|| consumeType.equals(ConsumeType.COIN_TRANSFER_RECONSUME)) {
				list.add(consumeRecord);
			}
		}

		return list;
	}

	// 基于消费类型,查询消费记录
	public List<ConsumeRecord> findByConsumeType(String consumeType) {
		return this.consumeRecordRepository.findByConsumeType(consumeType);
	}
}
