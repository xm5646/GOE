package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.ExpressAddress;

@Repository
public interface ExpressAddressRepository extends JpaRepository<ExpressAddress,Long> {
	public ExpressAddress findByExpressId(long expressId);
	public List<ExpressAddress> findByUserId(long userId);
}
