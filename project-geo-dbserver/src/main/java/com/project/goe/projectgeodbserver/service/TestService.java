package com.project.goe.projectgeodbserver.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.repository.TestRepository;

@Service
public class TestService {

	@Autowired
	private TestRepository testRepository;
	
	/*****************JpaRepository********************/
	//新增或修改
	@Transactional
	public User saveUser(User user) {
		return this.testRepository.save(user);
	}
	
	//删除
	@Transactional
	public void deleteUser(User user) {
		 this.testRepository.delete(user);
	}
	
	//基于userId查询用户
	public User find(User user) {
		return this.testRepository.findOne(user.getUserId());
	}
	//查询所有用户
	public List<User> findAll() {
		return this.testRepository.findAll();
	}
	
	//分页查询所有用户
	public Page<User> findAll(Pageable pageable) {
		return this.testRepository.findAll(pageable);
	}
	
	
	/****************JpaSpecificationExecutor*****************/
	//基于用户名和用户创建时间进行排序
	public Page<User> findUserByAccountAndCreateTime(User user, Pageable pageable) {
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//account/userStatus：实体字段名称
				Path<String> a = root.get("account");
				Path<Boolean> s = root.get("userStatus");
				
				//where account a like %user.getAccount()% and createTime c=user.getCreateTime()
				Predicate p = cb.and(cb.like(a.as(String.class),"%" + user.getAccount() + "%"),cb.equal(s.as(boolean.class),user.isUserStatus()));
				return p;
			}
		};
		
		return this.testRepository.findAll(spec, pageable);
	} 
	
	/*******************@Query************************/
	//?1:sql中第一个参数，以此类推,?2:sql中的第二个参数
	@Query("select u from user u where u.account like ?1 and userStatus=?2")
	public List<User> queryUserByAccountAndUserStatus(User user) {
		return this.testRepository.findUsersByAccountAndUserStatus("%" + user.getAccount() + "%", user.isUserStatus());
	}
	
}
