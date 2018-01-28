package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.repository.BonusPayListRepository;

@Service
public class BonusPayListService {

	@Autowired
	private BonusPayListRepository bonusPayListRepository;

	// 批量更新或者新加用户
	@Transactional
	public List<BonusPayList> saveAll(List<BonusPayList> bonusPaylists) {
		if (bonusPaylists != null && bonusPaylists.size() > 0) {
			for (BonusPayList bonusPaylist : bonusPaylists) {
				this.bonusPayListRepository.save(bonusPaylist);
			}
		}
		return bonusPaylists;
	}
	
	@Transactional
	public BonusPayList save(BonusPayList bonusPaylist) {
		return this.bonusPayListRepository.save(bonusPaylist);
	}

	// 所有记录分页查询
	public Page<BonusPayList> findAllBonusBySort(Pageable pageable) {
		return this.bonusPayListRepository.findAll(pageable);
	}
	
	//多条件分页查询
	public Page<BonusPayList> findBonusPageByAccout(BonusPayList bonusPayList, Pageable pageable) {
		Specification<BonusPayList> spec = new Specification<BonusPayList>() {

			@Override
			public Predicate toPredicate(Root<BonusPayList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		
		return null;
	}
	
	//删除所有数据
	public void deleteAllBonus() {
		this.bonusPayListRepository.deleteAll();
	}
}
