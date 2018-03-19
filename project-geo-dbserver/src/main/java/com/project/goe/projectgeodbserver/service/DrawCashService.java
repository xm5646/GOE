package com.project.goe.projectgeodbserver.service;

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

import com.project.goe.projectgeodbserver.entity.DrawCashRecord;
import com.project.goe.projectgeodbserver.repository.DrawCashRepository;

@Service
public class DrawCashService {
	@Autowired
	private DrawCashRepository drawCashRepository;

	public DrawCashRecord findDrawCashRecordByDrawId(long drawId) {
		return this.drawCashRepository.findByDrawId(drawId);
	}

	public List<DrawCashRecord> findByUserId(long userId) {
		return this.drawCashRepository.findByUserId(userId);
	}

	// 更新或新增提现记录
	@Transactional
	public DrawCashRecord save(DrawCashRecord drawCashRecord) {
		return this.drawCashRepository.save(drawCashRecord);
	}

	// 删除单个提现记录
	@Transactional
	public void delete(DrawCashRecord drawCashRecord) {
		this.drawCashRepository.delete(drawCashRecord);
	}

	public List<DrawCashRecord> findAll() {
		return this.drawCashRepository.findAll();
	}

	// 分页查询：基于用户名，按时间降序排序
	public Page<DrawCashRecord> findAll(DrawCashRecord drawCashRecord, Pageable pageable) {
		Specification<DrawCashRecord> spec = new Specification<DrawCashRecord>() {

			@Override
			public Predicate toPredicate(Root<DrawCashRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("userId").as(long.class), drawCashRecord.getUserId());

				return p;
			}
		};

		return this.drawCashRepository.findAll(spec, pageable);
	}

	// 分页查询提现记录
	public Page<DrawCashRecord> findAllDrawCardRecordBySort(Pageable pageable) {
		return this.drawCashRepository.findAll(pageable);
	}
	
	// 分页查询：基于提现状态，按时间降序查询提现记录
	public Page<DrawCashRecord> findByDrawStatus(String drawStatus, Pageable pageable) {
		Specification<DrawCashRecord> spec = new Specification<DrawCashRecord>() {

			@Override
			public Predicate toPredicate(Root<DrawCashRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("drawStatus").as(String.class), drawStatus);

				return p;
			}
		};

		return this.drawCashRepository.findAll(spec, pageable);
	}

	// 分页查询：基于提现状态和account，按时间降序查询提现记录
	public Page<DrawCashRecord> findByDrawStatusAndUserId(String drawStatus, long userId,Pageable pageable) {
		Specification<DrawCashRecord> spec = new Specification<DrawCashRecord>() {

			@Override
			public Predicate toPredicate(Root<DrawCashRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("drawStatus").as(String.class), drawStatus);
				Predicate p2 = cb.equal(root.get("userId").as(long.class), userId);
				Predicate p = cb.and(p1,p2);

				return p;
			}
		};

		return this.drawCashRepository.findAll(spec, pageable);
	}

	// 基于提现状态，查询用户提现记录
	public List<DrawCashRecord> findByDrawStatus(String drawStatus) {
		return this.drawCashRepository.findByDrawStatus(drawStatus);
	}
}
