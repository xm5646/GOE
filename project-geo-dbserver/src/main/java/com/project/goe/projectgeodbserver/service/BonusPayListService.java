package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.BonusPayListRepository;

@Service
public class BonusPayListService {

	@Autowired
	private BonusPayListRepository bonusPayListRepository;
	
	//批量更新或者新加用户
	@Transactional
	public List<BonusPayList> saveAll(List<BonusPayList> bonusPaylists) {
		if (bonusPaylists!=null && bonusPaylists.size()>0) {
			for (BonusPayList bonusPaylist : bonusPaylists) {
				this.bonusPayListRepository.save(bonusPaylist);
			}
		}
		return bonusPaylists;
	}
}
