package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.DrawCashRecord;
import com.project.goe.projectgeodbserver.repository.DrawCashRepository;

@Service
public class DrawCashService {
	@Autowired
	private DrawCashRepository drawCashRepository;
	
	public DrawCashRecord findByCardInfoId(long drawId) {
		return this.drawCashRepository.findByDrawId(drawId);
	}

	public List<DrawCashRecord> findByUserId(long userId) {
		return this.drawCashRepository.findByUserId(userId);
	}

	// 更新或新增提现记录
	@Transactional
	public DrawCashRecord save(DrawCashRecord drawCashRecord) {
		return this.drawCashRepository.save(drawCashRecord);
	}

	// 删除单个提现记录
	@Transactional
	public void delete(DrawCashRecord drawCashRecord) {
		this.drawCashRepository.delete(drawCashRecord);
	}

	// 分页查询提现记录
	public Page<DrawCashRecord> findAllDrawCardRecordBySort(Pageable pageable) {
		return this.drawCashRepository.findAll(pageable);
	}
}
