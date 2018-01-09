package com.project.goe.projectgeodbserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.repository.PerformanceRepository;

@Service
public class PerformanceService {
	@Autowired
	private PerformanceRepository performanceRepository;
	
	public Performance save(Performance performance) {
		return this.performanceRepository.save(performance);
	}
}
