package com.project.goe.projectgeodbserver.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.UserRepositoy;

@Service
public class UserService {
	@Autowired
	private UserRepositoy userRepositoy;

	public User save(User user) {
		return this.userRepositoy.save(user);
	}
	
	public User findByAccount(String account) {
		return this.userRepositoy.findByAccount(account);
	}
	
	@Transactional
	public void delete(Long id) {
		userRepositoy.delete(id);
	}
	
	@Transactional
	public Iterable<User> getAll(){
		return userRepositoy.findAll();
	}
	
	@Transactional
	public User getUserById(Long id){
		
		return userRepositoy.findOne(id);
	}
}
