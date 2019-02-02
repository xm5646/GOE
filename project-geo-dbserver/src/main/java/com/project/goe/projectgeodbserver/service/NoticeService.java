package com.project.goe.projectgeodbserver.service;

import com.project.goe.projectgeodbserver.entity.Notice;
import com.project.goe.projectgeodbserver.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Transactional
    public Notice save(Notice notice) {return this.noticeRepository.save(notice);}

    @Transactional
    public void delete(Notice notice) {this.noticeRepository.delete(notice);}

    public Page<Notice> getAll(Pageable pageable) {return this.noticeRepository.findAll(pageable);}

    public Notice findById(Long noticeId) {return this.noticeRepository.findByNoticeId(noticeId);}

    public List<Notice> findAllShownNotices() {return this.noticeRepository.findAllShownNotices();}

    public void deleteById(Long id) {this.noticeRepository.delete(id);}

}
