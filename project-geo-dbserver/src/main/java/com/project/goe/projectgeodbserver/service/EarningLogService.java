package com.project.goe.projectgeodbserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.goe.projectgeodbserver.entity.EarningLog;
import com.project.goe.projectgeodbserver.repository.EarningLogRepository;

@Service
public class EarningLogService {

	@Autowired
	private EarningLogRepository earningLogRepository;

	@Transactional
	public EarningLog save(EarningLog earningLog) {
		return this.earningLogRepository.save(earningLog);
	}
	
	//查询所有的收益日志
	public List<EarningLog> findAll() {
		return this.earningLogRepository.findAll();
	}
	
	//查询指定用户所有的收益日志
	public List<EarningLog> findByUserid(int userid) {
		return this.earningLogRepository.findByUserid(userid);
	}

	//查询新增奖励的所有收益日志
	
	//查询累积奖励的所有收益日志
	
	//查询指定当天的所有收益日志
	
	//查询指定时间段的所有收益日志
	

}
