package com.project.goe.projectgeodbserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.User;

@Repository
public interface UserRepositoy extends JpaSpecificationExecutor<User>,JpaRepository<User, Long> {
	public User findByAccount(String account);
	public User findByUserId(long userId);
	
	//基于createTime，查询用户数
	public List<User> findByCreateTimeBetween(Date startDate,Date endDate);
	
	//多条件分页查询
	public Page<User> findAll(Specification<User> spec, Pageable pageable);
}
