package com.project.goe.projectgeodbserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;

@RestController
@RequestMapping("/usercreaterecord")
@CrossOrigin
public class UserCreateRecordController {
	
	@Autowired
	private UserCreateRecordService userCreateRecordService;
	
	@RequestMapping("/findAll")
	public Iterable<UserCreatereRecord> getAll(){
		return this.userCreateRecordService.getAll();
	}
	
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id") long id) {
		this.userCreateRecordService.delete(id);
		return true;
	}
}
