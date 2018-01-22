package com.project.goe.projectgeodbserver.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}
