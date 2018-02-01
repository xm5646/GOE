package com.project.goe.projectgeodbserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.ReconsumeRecord;

@Repository
public interface ReconsumeRecordRepository extends JpaSpecificationExecutor<ReconsumeRecord>,JpaRepository<ReconsumeRecord, Long> {
	public ReconsumeRecord findByReconsumeRecordId(long reconsumeRecordId);
	public List<ReconsumeRecord> findByUserId(long userId);
	
	@Query("select r from ReconsumeRecord r where date_format(createTime,'%Y-%m-%d')=date_format(?1,'%Y-%m-%d') and userId=?2")
	public ReconsumeRecord findReconsumeByCreateTime(Date createTime,long userId);
}