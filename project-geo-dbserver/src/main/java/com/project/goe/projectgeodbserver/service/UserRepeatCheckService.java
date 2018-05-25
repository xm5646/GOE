package com.project.goe.projectgeodbserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.repository.UserInitRepository;

@Service
public class UserRepeatCheckService{
	
	@Autowired
	private UserInitRepository userInitRepository;
	
	
	public boolean checkUserIsMaxRepeat(String str) {
		return userInitRepository.findUserRepeatCount(str) == 1;
	}

}
