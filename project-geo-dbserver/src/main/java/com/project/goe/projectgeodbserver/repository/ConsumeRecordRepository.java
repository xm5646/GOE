package com.project.goe.projectgeodbserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;

@Repository
public interface ConsumeRecordRepository
		extends JpaSpecificationExecutor<ConsumeRecord>, JpaRepository<ConsumeRecord, Long> {
	public List<ConsumeRecord> findByConsumeId(long consumeId);

	public List<ConsumeRecord> findByUserId(long userId);

	// 多条件分页查询
	public Page<ConsumeRecord> findAll(Specification<ConsumeRecord> spec, Pageable pageable);

	// 基于consumeTime，查询消费记录
	@Query("select r from ConsumeRecord r where consumeTime between date_format(?1,'%Y-%m-%d %H:%i:%S') and date_format(?2,'%Y-%m-%d %H:%i:%S')")
	public List<ConsumeRecord> findByConsumeTimeBetween(Date startDate, Date endDate);
	
	// 基于消费类型,查询消费记录
	public List<ConsumeRecord> findByConsumeType(String consumeType);
}
