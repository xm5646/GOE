package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public CardInfo findByCardNumber(String cardNumber) {
		return this.findByCardNumber(cardNumber);
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
	
}
