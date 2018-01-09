package com.project.goe.projectgeodbserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.goe.projectgeodbserver.entity.Performance;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {

}
