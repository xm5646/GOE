package com.project.goe.projectgeodbserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.TestService;

@RestController
@RequestMapping("/tUser/")
public class TestController {
	@Autowired
	private TestService testService;
	
	//新增用户
	@PostMapping("/save")
	public User saveUser(User user) {
		return this.testService.saveUser(user);
	}
	
	//分页查询所有用户数据
	@GetMapping("/findAll")
	public Page<User> findAll(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);
			
			//Spring MVC PageRequest实现Pageable接口
			/*
			 * pageNum：起始页
			 * size：每页多少条数据
			 * sort：排序规则，这里基于createTime降序排序
			 */
			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.testService.findAll(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//分页查询数据：基于用户名和用户状态，按创建时间降序查询
	@GetMapping("/findUserByAccountAndUserStatus")
	public Page<User> findUserByAccountAndUserStatus(
			@RequestParam(value="account",required = true) String account,
			@RequestParam(value="userStatus",required = true) boolean userStatus,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);
			
			Pageable pageable = new PageRequest(pageNum, size, sort);
			
			User user = new User();
			user.setAccount(account);
			user.setUserStatus(userStatus);
			return this.testService.findUserByAccountAndCreateTime(user, pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@GetMapping("/queryUserByAccountAndUserStatus")
	public List<User> queryUserByAccountAndUserStatus(User user) {
		return this.testService.queryUserByAccountAndUserStatus(user);
	}
	
}
