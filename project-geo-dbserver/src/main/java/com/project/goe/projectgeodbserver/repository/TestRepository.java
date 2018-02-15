package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.User;

//JpaSpecificationExecutor:多条件分页查询；
//JpaRepository:单条件分页查询；增删改查；
//@Query:任意查询，多用于复杂查询；
@Repository
public interface TestRepository extends JpaSpecificationExecutor<User>,JpaRepository<User,Long> {
	
	@Query("select u from User u where u.account like ?1 and u.userStatus=?2")
	public List<User> findUsersByAccountAndUserStatus(String account,boolean userStatus);
}
