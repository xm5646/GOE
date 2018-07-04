package com.project.goe.projectgeodbserver.repository;

import com.project.goe.projectgeodbserver.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoticeRepository extends JpaSpecificationExecutor<Notice>, JpaRepository<Notice, Long>{

    public Notice findByNoticeId(Long noticeId);
    public Page<Notice> findAll(Pageable pageable);
    @Query("select n from  Notice  n where n.showStatus = true ")
    public List<Notice> findAllShownNotices();
 }
