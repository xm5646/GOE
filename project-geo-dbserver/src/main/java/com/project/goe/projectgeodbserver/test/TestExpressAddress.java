package com.project.goe.projectgeodbserver.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserExpressAddressRequest;

/**
 * @description:测试用户相关收货地址
 *
 */
@RestController
@RequestMapping("/test")
public class TestExpressAddress {
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	/*
	 * 1、测试新增收货地址是否成功：新增12数据（后续分页）
	 * 2、测试设置新增收货地址为默认为地址，用户原默认收货地址是否会发生变化
	
	@GetMapping("/addExpressAddress")
	public List<RetMsg> testAddExpressAddress() {
		List<RetMsg> retMsgList = new ArrayList<RetMsg>();
		
		for(int i = 0;i < 11;++ i) {
			UserExpressAddressRequest userExpressAddressRequest = new UserExpressAddressRequest();
			userExpressAddressRequest.setAccount("admin");
			userExpressAddressRequest.setCity("JZ" + i);
			userExpressAddressRequest.setDetailAddress("HBxxxxxxx" + i);
			userExpressAddressRequest.setDistrict("JL" + i);
			userExpressAddressRequest.setPhone("13787651234");
			userExpressAddressRequest.setProvince("HB" + i);
			userExpressAddressRequest.setReceiverName("receiver" + i);
			
			ResponseEntity<RetMsg> responseEntity  = this.restTemplate.postForEntity("http://localhost:8088/expressAddress/save",userExpressAddressRequest,RetMsg.class);
			retMsgList.add(responseEntity.getBody());
		}
		
		return retMsgList;
	}
	 */
	
}
