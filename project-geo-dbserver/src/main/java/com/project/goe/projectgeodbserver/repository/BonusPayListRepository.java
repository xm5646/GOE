package com.project.goe.projectgeodbserver.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
@Repository
public interface BonusPayListRepository extends JpaSpecificationExecutor<BonusPayList>,JpaRepository<BonusPayList, Long>{

	//多条件分页查询
	public Page<BonusPayList> findAll(Specification<BonusPayList> spec, Pageable pageable);
	
	//所有记录分页查询
	public Page<BonusPayList> findAll(Pageable pageable);
	
	// 基于支付payTime，查询奖金记录
	@Query("select r from BonusPayList r where payTime between date_format(?1,'%Y-%m-%d %H:%i:%S') and date_format(?2,'%Y-%m-%d %H:%i:%S')")
	public List<BonusPayList> findByPayTimeBetween(Date startDate, Date endDate);
}
