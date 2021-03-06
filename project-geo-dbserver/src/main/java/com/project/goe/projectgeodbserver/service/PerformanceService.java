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
	
	public Iterable<Performance> getAll(){
		return this.performanceRepository.findAll();
	}
	
	public Performance getOne(Long id) {
		return this.performanceRepository.findOne(id);
	}
	
	public boolean delete(Long id) {
		this.performanceRepository.delete(id);
		return true;
	}
	//基于userId，查询用户业绩信息
	public Performance findByUserId(long userId) {
		return this.performanceRepository.findByUserId(userId);
	}
	
	public void deleteAllPerformance() {
		this.performanceRepository.deleteAll();
	}
}
