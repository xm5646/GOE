package com.project.goe.projectgeodbserver.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.UserRepositoy;

@Service
public class UserService {
	@Autowired
	private UserRepositoy userRepositoy;
	
	//新增用户
	@Transactional
	public User save(User user) {
		return this.userRepositoy.save(user);
	}
	
	//删除用户
	@Transactional
	public void delete(User user) {
		this.userRepositoy.delete(user);
	}
	
	//删除所有用户数据
	public void deleteAllUser() {
		this.userRepositoy.deleteAll();
	}
	
	//更新用户的节点信息
	@Transactional
	public void updateUserDepartmentId(long departmentA,long departmentB,long departmentC,long userId) {
		this.userRepositoy.updateUserDepartmentId(departmentA, departmentB, departmentC, userId);
	}
	
/****************查询相关*******************************/
	//基于account，查询用户信息
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
	//基于userId，查询用户信息
	public User findByUserId(long userId) {
		return this.userRepositoy.findByUserId(userId);
	}
	//分页查询
	public Page<User> findAllUserBySort(Pageable pageable) {
		return this.userRepositoy.findAll(pageable);
	}
}
