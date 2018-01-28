package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.goe.projectgeodbserver.entity.Earning;

public interface EarningRepository extends JpaRepository<Earning, Long>{
	//select count(1) from (select * from tb_earning e where e.userid=127 and e.touch_type='累积' limit 0,1) el where el.surplus_number>120
	@Query(value = "select count(1) from (select * from tb_earning e where e.userid=:userid and e.touch_type=:touchType limit 0,1) el where el.surplus_number>:surplusNumber",nativeQuery=true)
	int getEarningExist(@Param("userid") long userid,@Param("surplusNumber") int surplusNumber,@Param("touchType") String touchType);
}
