package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
@Repository
public interface BonusPayListRepository extends JpaSpecificationExecutor<BonusPayList>,JpaRepository<BonusPayList, Long>{

	//多条件分页查询
	public Page<BonusPayList> findAll(Specification<BonusPayList> spec, Pageable pageable);
	
	//所有记录分页查询
	public Page<BonusPayList> findAll(Pageable pageable);
	
}
