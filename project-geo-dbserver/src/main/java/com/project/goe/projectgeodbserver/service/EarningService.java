package com.project.goe.projectgeodbserver.service;


import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.repository.EarningRepository;
/**
 * 收益服务层，控制收益的事务
 * @author xiaoming
 *
 */

@Service
public class EarningService {

	@Resource
	private EarningRepository earningRepository;
	
	@Transactional
	public void save(Earning e) {
		earningRepository.save(e);
	}
	
	@Transactional
	public void delete(Integer id) {
		earningRepository.delete(id);
	}
	
	@Transactional
	public Iterable<Earning> getAll(){
		return earningRepository.findAll();
	}
	
	@Transactional
	public Earning getEarningById(Integer id) {
		return earningRepository.findOne(id);
	}
	
}
