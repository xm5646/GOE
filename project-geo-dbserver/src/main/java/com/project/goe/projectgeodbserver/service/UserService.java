package com.project.goe.projectgeodbserver.service;

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
}
