package com.project.goe.projectgeodbserver.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.ReconsumeRecord;
import com.project.goe.projectgeodbserver.repository.ReconsumeRecordRepository;

@Service
public class ReconsumeRecordService {
	@Autowired
	private ReconsumeRecordRepository reconsumeRecordRepository;

	@Transactional
	public ReconsumeRecord save(ReconsumeRecord reconsumeRecord) {
		return this.reconsumeRecordRepository.save(reconsumeRecord);
	}

	// 查询单个重销记录
	public ReconsumeRecord findByReconsumeRecordId(long reconsumeRecordId) {
		return this.reconsumeRecordRepository.findByReconsumeRecordId(reconsumeRecordId);
	}

	// 查询用户所有的重销记录
	public List<ReconsumeRecord> findByUserId(long userId) {
		return this.reconsumeRecordRepository.findByUserId(userId);
	}

	// 分页查询所有重销记录
	public Page<ReconsumeRecord> findAllReconsumeRecordBySort(Pageable pageable) {
		return this.reconsumeRecordRepository.findAll(pageable);
	}
	
	// 基于时间查询时间
	public ReconsumeRecord findByCreateTime(Date createTime,long userId) {
		return this.reconsumeRecordRepository.findReconsumeByCreateTime(createTime,userId);
	}

}
