package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.User;

@Repository
public interface UserRepositoy extends JpaRepository<User, Long> {
	public User findByAccount(String account);
	public User findByUserId(long userId);
	
	@Query("update tuser set departmentA = ?1,departmentB = ?2,departmentC = ?3 where userId = ?4")
	@Modifying
	public void updateUserDepartmentId(long departmentA,long departmentB,long departmentC,long userId);
}
