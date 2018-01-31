package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.ReconsumeRecord;

@Repository
public interface ReconsumeRecordRepository extends JpaSpecificationExecutor<ReconsumeRecord>,JpaRepository<ReconsumeRecord, Long> {
	public ReconsumeRecord findByReconsumeRecordId(long reconsumeRecordId);
	public List<ReconsumeRecord> findByUserId(long userId);
}