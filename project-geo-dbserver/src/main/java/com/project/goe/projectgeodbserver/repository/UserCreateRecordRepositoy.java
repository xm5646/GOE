package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;

@Repository
public interface UserCreateRecordRepositoy  extends JpaRepository<UserCreatereRecord,Long>{
	public UserCreatereRecord findByUserId(Long userId);
}
