package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.goe.projectgeodbserver.entity.Transfer;
import com.project.goe.projectgeodbserver.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	User findByName(String name);

}
