package com.project.goe.projectgeodbserver.service;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
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
	
	public Page<Earning> findEarningsByUserId(long userId,Pageable pageable) {
		
		Specification<Earning> spec = new Specification<Earning>() {

			@Override
			public Predicate toPredicate(Root<Earning> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("userid").as(long.class), userId);
				
				return p;
			}

		};

		return this.earningRepository.findAll(spec, pageable);
	}
	
	/**
	 * 当有新增累计的时候,判断这条新增累计是否存在，如果存在，则将除了这条累计的数据
	 * @return
	 */
	public Iterable<Earning> updateEarnWhenAddCumulative(long userid,Earning addEarn){
		return null;
	}
	
	//得到可用发放的奖金
	public Iterable<Earning> getAllEarning(){
		//得到可用发放的奖金
		return earningRepository.getAllEarningForUsable();
	}
}
