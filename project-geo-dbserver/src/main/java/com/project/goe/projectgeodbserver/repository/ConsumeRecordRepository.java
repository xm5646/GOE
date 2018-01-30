package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;

@Repository
public interface ConsumeRecordRepository
		extends JpaSpecificationExecutor<ConsumeRecord>, JpaRepository<ConsumeRecord, Long> {
	public List<ConsumeRecord> findByConsumeId(long consumeId);

	public List<ConsumeRecord> findByUserId(long userId);

	// 多条件分页查询
	public Page<ConsumeRecord> findAll(Specification<ConsumeRecord> spec, Pageable pageable);
}
