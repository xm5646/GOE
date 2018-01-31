package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.project.goe.projectgeodbserver.entity.CardInfo;

@Repository
public interface CardInfoRepository extends JpaSpecificationExecutor<CardInfo>, JpaRepository<CardInfo, Long> {
	public CardInfo findByCardInfoId(long cardInfoId);

	public List<CardInfo> findByUserId(long userId);

	public CardInfo findByCardNumber(String cardNumber);
}
