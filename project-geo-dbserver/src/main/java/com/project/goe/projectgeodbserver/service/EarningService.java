package com.project.goe.projectgeodbserver.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.repository.EarningRepository;
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
}
