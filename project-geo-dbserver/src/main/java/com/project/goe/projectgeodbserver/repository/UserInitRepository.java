package com.project.goe.projectgeodbserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.UserInitInfo;

@Repository
public interface UserInitRepository extends JpaSpecificationExecutor<UserInitInfo>,JpaRepository<UserInitInfo, Long>{

	@Query("SELECT count(*) from UserInitInfo r where str=?1 and strcount>=3")
	public Long findUserRepeatCount(String str);
}
