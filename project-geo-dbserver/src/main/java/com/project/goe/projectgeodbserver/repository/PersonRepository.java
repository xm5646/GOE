package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.goe.projectgeodbserver.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

}
