package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.DrawCashRecord;

@Repository
public interface DrawCashRepository
		extends JpaSpecificationExecutor<DrawCashRecord>, JpaRepository<DrawCashRecord, Long> {
	public DrawCashRecord findByDrawId(long drawId);

	public List<DrawCashRecord> findByUserId(long userId);
	
	public List<DrawCashRecord> findByDrawStatus(String drawStatus);

	// 分页查询：多个条件，按时间降序排序
	public Page<DrawCashRecord> findAll(Specification<DrawCashRecord> spec, Pageable pageable);
}
