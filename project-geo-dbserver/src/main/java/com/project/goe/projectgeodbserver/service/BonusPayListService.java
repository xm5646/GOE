package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	// 分页查询
	public Page<BonusPayList> findAllBonusBySort(Pageable pageable) {
		return this.bonusPayListRepository.findAll(pageable);
	}
	
	//删除所有数据
	public void deleteAllBonus() {
		this.bonusPayListRepository.deleteAll();
	}
}
