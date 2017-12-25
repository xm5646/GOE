package com.project.goe.projectgeodbserver.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Person;
import com.project.goe.projectgeodbserver.service.PersonService;

@RestController
@RequestMapping("/index")
public class PersonController {
	@Resource
	private PersonService personService;

	@RequestMapping("/save")
	public String save() {
		Person p = new Person();
		p.setAge(21);
		p.setName("DLP");
		personService.save(p);
		return "存入用户成功";
	}

	@RequestMapping("/delete")
	public String delete() {
		personService.delete(1);
		return "删除编号为1的用户";
	}

	@RequestMapping("/findAll")
	public Iterable<Person> getAll() {
		return personService.getAll();
	}
}
