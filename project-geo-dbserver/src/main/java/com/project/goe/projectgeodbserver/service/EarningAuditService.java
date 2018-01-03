package com.project.goe.projectgeodbserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.repository.EarningAuditRepository;

@Service
public class EarningAuditService {
	@Autowired
	private EarningAuditRepository earningAuditRepository;
}
