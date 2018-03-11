package com.project.goe.projectgeodbserver.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.repository.EarningRepository;
import com.project.goe.projectgeodbserver.statusType.TouchType;
/**
 * 收益服务层，控制收益的事务
 *
 */

@Service
public class EarningService {

	@Autowired
	private EarningRepository earningRepository;
	
	@Transactional
	public Earning save(Earning earning) {
		return this.earningRepository.save(earning);
	}
	
	@Transactional
	public Iterable<Earning> getAll(){
		return earningRepository.findAll();
	}
	
	public boolean isHaveTotalEarning(long userid) {
		int count = earningRepository.getEarningExist(userid, 0, TouchType.ACCUMULATION);
		if (count>0) {
			return true;
		}
		return false;
	}
	
	public Page<Earning> findAllEarnings(Pageable pageable) {
		return this.earningRepository.findAll(pageable);
	}
}
