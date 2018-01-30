package com.project.goe.projectgeodbserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.repository.ConsumeRecordRepository;

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
	@GetMapping("/findByAccountAndConsumeType")
	public Page<ConsumeRecord> findByAccountAndConsumeType(ConsumeRecord consumeRecord, Pageable pageable) {
		Specification<ConsumeRecord> spec = new Specification<ConsumeRecord>() {

			@Override
			public Predicate toPredicate(Root<ConsumeRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Long> userId = root.get("userId");
				Path<String> consumeType = root.get("consumeType");

				Predicate p1 = cb.equal(userId, consumeRecord.getUserId());
				Predicate p2 = cb.equal(consumeType, consumeRecord.getConsumeType());
				Predicate p = cb.and(p1,p2);
				
				return p;
			}

		};

		return this.consumeRecordRepository.findAll(spec, pageable);
	}

}
