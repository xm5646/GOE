package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	// 新增用户
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Transactional
	public RetMsg saveUser(@ModelAttribute User user) {
		String defaultPassword = "test";

		try {
			user.setCreateTime(new Date());

			if (user.getPassword() == null) {
				user.setPassword(MD5Util.encrypeByMd5(defaultPassword));
			} else {
				user.setPassword(MD5Util.encrypeByMd5(user.getPassword()));
			}
			user.setParentId(user.getParentId());
			this.userService.save(user);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(user);
			retMsg.setMessage("添加用户成功!");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加用户失败!");
		}
	}

	// 删除用户
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	public RetMsg deleteUser(User user) {
		String account = user.getAccount();
		// account值为空或者account用户不存在，抛出异常
		if ((account == null) || (this.userService.findByAccount(account) == null)) {
			throw new RuntimeException("当前用户不存在!");
		}

		try {
			this.userService.delete(user);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(account);
			retMsg.setMessage("用户删除成功!");
			retMsg.setSuccess(true);
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户删除失败!");
		}
	}

	/************************** 用户查询方法 *******************************/

	// 根据用户名查找用户信息
	@RequestMapping("/findByAccount/{account}")
	public User findUserByAccount(@PathVariable String account) {
		try {
			return this.userService.findByAccount(account);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	// 基于单个关键字进行分页查询：默认按照userId字段升序查询；默认显示第一页；默认每页显示5条数据
	@GetMapping("/findUsersBySort")
	public Page<User> findAllUserBySort(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "userId") String keyword) {
		try {
			Sort sort = new Sort(Direction.ASC, keyword);
			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.userService.findAllUserBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
