package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.project.goe.projectgeodbserver.entity.Earning;

public interface EarningRepository extends JpaSpecificationExecutor<Earning>, JpaRepository<Earning, Long> {
	// select count(1) from (select * from tb_earning e where e.userid=127 and
	// e.touch_type='累积' limit 0,1) el where el.surplus_number>120
	@Query(value = "select count(1) from (select * from tb_earning e where e.userid=?1 and e.touch_type=?2 order by e.create_time desc limit 0,1) el where el.surplus_number>?3", nativeQuery = true)
	int getEarningExist(long userid, int surplusNumber, String touchType);

	// @Query(value = "select count(1) from (select * from tb_earning e where
	// e.userid=?1 and e.touch_type=?2 order by e.create_time desc limit 0,1) el
	// where el.surplus_number>?3",nativeQuery=true)
	// Iterable<Earning> getAllEarningForUsable();
}
