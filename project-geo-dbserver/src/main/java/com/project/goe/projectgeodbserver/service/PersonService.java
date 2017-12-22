package com.project.goe.projectgeodbserver.service;

import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.Person;
import com.project.goe.projectgeodbserver.repository.PersonRepository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonService {
	@Resource
	private PersonRepository personRepository;

	@Transactional
	public void save(Person p) {
		personRepository.save(p);
	}

	@Transactional
	public void delete(int id) {
		personRepository.delete(id);
	}

	@Transactional
	public Iterable<Person> getAll() {
		return personRepository.findAll();
	}
}
