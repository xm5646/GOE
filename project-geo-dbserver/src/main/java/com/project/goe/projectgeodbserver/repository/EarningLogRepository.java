package com.project.goe.projectgeodbserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.goe.projectgeodbserver.entity.EarningLog;

@Repository
public interface EarningLogRepository extends JpaRepository<EarningLog,Integer> {
	public List<EarningLog> findByUserid(int userid);
}
