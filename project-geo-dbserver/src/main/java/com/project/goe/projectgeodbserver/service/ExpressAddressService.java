package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.ExpressAddress;
import com.project.goe.projectgeodbserver.entity.User;
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
}
