package com.project.goe.projectgeodbserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.DrawCashRecord;

@Repository
public interface DrawCashRepository extends JpaRepository<DrawCashRecord, Long> {
	public DrawCashRecord findByDrawId(long drawId);
	public List<DrawCashRecord> findByUserId(long userId);
}
