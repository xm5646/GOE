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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	// 用户登录
	@PostMapping("/login")
	public RetMsg login(@ModelAttribute User user) {
		if ((null == user) || (null == user.getAccount()) || (null == user.getPassword())) {
			throw new RuntimeException("用户登录信息为空！");
		}

		String account = user.getAccount();
		User u = this.userService.findByAccount(account);
		if (null == u) {
			throw new RuntimeException("当前用户不存在!");
		}

		try {
			if (!(MD5Util.encrypeByMd5(user.getPassword()).equals(u.getPassword()))) {
				throw new RuntimeException("用户密码输入有误！");
			} else {
				RetMsg retMsg = new RetMsg();
				retMsg.setCode(200);
				retMsg.setData(UserUtil.UserToUserVO(u));
				retMsg.setMessage("用户登录成功！");
				retMsg.setSuccess(true);

				return retMsg;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	// 新增用户
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Transactional
	public RetMsg saveUser(@ModelAttribute User user) {
		try {
			
			
			
			user.setCreateTime(new Date());
			user.setPassword(MD5Util.encrypeByMd5(user.getPassword()));
			user.setParentId(user.getParentId());
			this.userService.save(user);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("添加用户成功!");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("添加用户失败!------->" + e.getMessage());
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

	// 基于单个关键字进行分页查询：默认按照userId字段j查询；默认显示第一页；默认每页显示5条数据
	@GetMapping("/findUsersBySort")
	public Page<User> findAllUserBySort(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "userId") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.userService.findAllUserBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
