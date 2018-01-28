package com.project.goe.projectgeodbserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.goe.projectgeodbserver.repository.EarningRepository;
import com.project.goe.projectgeodbserver.statusType.TouchType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectGeoDbserverApplicationTests {
	
	@Autowired
	private EarningRepository earningRepository;

	@Test
	public void contextLoads() {
		int count = earningRepository.getEarningExist(1, 0, TouchType.ACCUMULATION);
		System.out.println(count);
	}

}
