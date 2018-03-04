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

import com.project.goe.projectgeodbserver.entity.CardInfo;
import com.project.goe.projectgeodbserver.entity.ExpressAddress;
import com.project.goe.projectgeodbserver.repository.ExpressAddressRepository;

@Service
public class ExpressAddressService {
	@Autowired
	private ExpressAddressRepository expressAddressRepository;

	public ExpressAddress findByExpressId(long expressId) {
		return this.expressAddressRepository.findByExpressId(expressId);
	}

	public List<ExpressAddress> findByUserId(long userId) {
		return this.expressAddressRepository.findByUserId(userId);
	}

	// 更新或新增快递地址信息
	@Transactional
	public ExpressAddress save(ExpressAddress expressAddress) {
		return this.expressAddressRepository.save(expressAddress);
	}

	// 删除单个快递地址信息
	@Transactional
	public void delete(ExpressAddress expressAddress) {
		this.expressAddressRepository.delete(expressAddress);
	}

	// 分页查询
	public Page<ExpressAddress> findAllExpressAddressBySort(Pageable pageable) {
		return this.expressAddressRepository.findAll(pageable);
	}
	
	// 基于userId，分页查询地址信息
	public Page<ExpressAddress> findExpressAddressInfoByAccount(ExpressAddress expressAddress, Pageable pageable) {
		Specification<ExpressAddress> spec = new Specification<ExpressAddress>() {

			@Override
			public Predicate toPredicate(Root<ExpressAddress> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = cb.equal(root.get("userId").as(long.class), expressAddress.getUserId());

				return p;
			}

		};

		return this.expressAddressRepository.findAll(spec, pageable);
	}
}
