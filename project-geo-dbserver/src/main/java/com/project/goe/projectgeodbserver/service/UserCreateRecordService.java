package com.project.goe.projectgeodbserver.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.repository.UserCreateRecordRepositoy;

@Service
public class UserCreateRecordService {
	@Autowired
	private UserCreateRecordRepositoy userCreateRecordRepositoy;

	public UserCreatereRecord save(UserCreatereRecord userCreateRecord) {
		return this.userCreateRecordRepositoy.save(userCreateRecord);
	}
	
	@Transactional
	public void delete(Long id) {
		userCreateRecordRepositoy.delete(id);
	}
	
	@Transactional
	public Iterable<UserCreatereRecord> getAll(){
		return userCreateRecordRepositoy.findAll();
	}
	
	@Transactional
	public UserCreatereRecord getUserCreatereRecordByUserId(Long userId){
		
		return userCreateRecordRepositoy.findByUserId(userId);
	}
	
}
