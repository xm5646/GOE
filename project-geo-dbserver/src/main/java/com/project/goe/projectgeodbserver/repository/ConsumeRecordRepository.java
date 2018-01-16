package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;

@Repository
public interface ConsumeRecordRepository extends JpaRepository<ConsumeRecord, Long> {
	public List<ConsumeRecord> findByConsumeId(long consumeId);
	public List<ConsumeRecord> findByUserId(long userId);
}
