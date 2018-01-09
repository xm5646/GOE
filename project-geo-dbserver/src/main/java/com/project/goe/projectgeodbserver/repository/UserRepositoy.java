package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.User;

@Repository
public interface UserRepositoy extends JpaRepository<User,Long>{
	public User findByAccount(String account);
}
