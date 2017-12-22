package com.project.goe.projectgeodbserver.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.Transfer;
import com.project.goe.projectgeodbserver.repository.TransferRepository;

@Service
public class TransferService {
	@Resource
	private TransferRepository transferRepository;
	
	@Transactional
	public void save(Transfer t) {
		transferRepository.save(t);
	}
	
	@Transactional
	public void delete(Integer id) {
		transferRepository.delete(id);
	}
	
	@Transactional
	public Iterable<Transfer> getAll(){
		return transferRepository.findAll();
	}
	
	@Transactional
	public Transfer getTransferById(Integer id){
		return transferRepository.findOne(id);
	}
}
