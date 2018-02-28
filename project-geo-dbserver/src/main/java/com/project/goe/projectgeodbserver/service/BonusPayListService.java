package com.project.goe.projectgeodbserver.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
import com.project.goe.projectgeodbserver.util.DateFormatUtil;

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

	// 多条件分页查询:按用户名
	public Page<BonusPayList> findBonusPageByAccount(BonusPayList bonusPayList, Pageable pageable) {
		Specification<BonusPayList> spec = new Specification<BonusPayList>() {

			@Override
			public Predicate toPredicate(Root<BonusPayList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Long> userId = root.get("userId");

				Predicate p = cb.equal(userId, bonusPayList.getUserId());
				return p;
			}

		};

		return bonusPayListRepository.findAll(spec, pageable);
	}

	// 删除所有数据
	public void deleteAllBonus() {
		this.bonusPayListRepository.deleteAll();
	}

	// 查找当月所有奖金记录
	public List<BonusPayList> findByPayTimeOfNowMonth() {
		// 本月起始和结束时间
		List<Date> dateList = DateFormatUtil.getStartDateAndEndDateOfNowMonth();
		// 本月起始和结束时间内的所有消费记录
		List<BonusPayList> bonusPayList = this.bonusPayListRepository.findByPayTimeBetween(dateList.get(0),dateList.get(1));

		return bonusPayList;
	}
	
	// 查询所有奖金记录
	public List<BonusPayList> findAll() {
		return this.bonusPayListRepository.findAll();
	}
}
