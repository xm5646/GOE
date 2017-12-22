package com.project.goe.projectgeodbserver.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.UserRepository;
/**
 * 用户服务层，控制用户的事物
 * @author zhangqiankun
 *
 */
@Service
public class UserService {
	@Resource
	private UserRepository userRepository;
	
	@Transactional
	public void save(User u) {
		userRepository.save(u);
	}
	
	@Transactional
	public void delete(Integer id) {
		userRepository.delete(id);
	}
	
	@Transactional
	public Iterable<User> getAll(){
		return userRepository.findAll();
	}
	
	@Transactional
	public User getUserById(Integer id){
		
		return userRepository.findOne(id);
	}

	@Transactional
	public User getUserByName(String name){
		return userRepository.findByName(name);
	}
}
