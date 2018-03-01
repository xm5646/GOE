package com.project.goe.projectgeodbserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.project.goe.projectgeodbserver.entity.CardInfo;
import com.project.goe.projectgeodbserver.repository.CardInfoRepository;

@Service
public class CardInfoService {
	@Autowired
	private CardInfoRepository cardInfoRepository;

	public CardInfo findByCardInfoId(long cardInfoId) {
		return this.cardInfoRepository.findByCardInfoId(cardInfoId);
	}

	public List<CardInfo> findByUserId(long userId) {
		return this.cardInfoRepository.findByUserId(userId);
	}
	
	public Map<Long,CardInfo> findAllMap(){
		List<CardInfo> cardInfoList = this.cardInfoRepository.findAll();
		Map<Long,CardInfo> cardMap = new HashMap<Long,CardInfo>();
		if (cardInfoList!=null && cardInfoList.size()>0) {
			for (CardInfo cardInfo : cardInfoList) {
				cardMap.put(cardInfo.getCardInfoId(), cardInfo);
			}
		}
		return cardMap;
	}

	public CardInfo findByCardNumber(String cardNumber) {
		return this.cardInfoRepository.findByCardNumber(cardNumber);
	}

	// 更新或新增银行卡信息
	@Transactional
	public CardInfo save(CardInfo cardInfo) {
		return this.cardInfoRepository.save(cardInfo);
	}

	// 删除单个银行卡信息
	@Transactional
	public void delete(CardInfo cardInfo) {
		this.cardInfoRepository.delete(cardInfo);
	}

	// 分页查询银行卡信息
	public Page<CardInfo> findAllCardInfoBySort(Pageable pageable) {
		return this.cardInfoRepository.findAll(pageable);
	}

	// 多条件分页查询:按用户名和消费类型
	public Page<CardInfo> findCardInfoByAccount(CardInfo cardInfo, Pageable pageable) {
		Specification<CardInfo> spec = new Specification<CardInfo>() {

			@Override
			public Predicate toPredicate(Root<CardInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("userId").as(long.class), cardInfo.getUserId());

				return p;
			}

		};

		return this.cardInfoRepository.findAll(spec, pageable);
	}
	
}
