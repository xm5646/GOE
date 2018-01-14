package com.project.goe.projectgeodbserver.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.CheckUtil;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private UserCreateRecordService userCreateRecordService;
	
	@RequestMapping("/testsave/{id}/{type}")
	public String saveTest(@PathVariable("id") Long id, @PathVariable("type") String type) {
		User puser = userService.getUserById(id);
			User newuser = UserUtil.getTestUser();
			mainUpdateUser(puser.getUserId(), type, newuser);
		return "测试数据插入成功" + type;
	}
	
	private String mainUpdateUser(Long parentId,String type,User newuser) {
		User puser = userService.getUserById(parentId);
		if (puser != null) {
//			User newuser = UserUtil.getTestUser();
			if ("A".equals(type)) {
				if (puser.getDepartmentA() != 0) {
					return "A的下级节点已存在";
				} else {
					saveuser(newuser, puser, type);
				}
			} else if ("B".equals(type)) {
				if (puser.getDepartmentB() != 0) {
					return "B的下级节点已存在";
				} else {
					saveuser(newuser, puser, type);
				}
			}
		}
		return "测试数据插入成功" + type;
	}
	
	
	@RequestMapping("/savemain")
	public String saveMain() {
		User newuser = UserUtil.getTestUser();
		newuser.setWeightCode(1);
		this.userService.save(newuser);
		savePer(newuser.getUserId());
		return "测试数据插入根成功";
	}
	
	/**
	 * 创建新用户，同时更新上级用户的A B节点ID
	 * @param newuser
	 * @param puser
	 * @param type
	 */
	private void saveuser(User newuser,User puser,String type) {
		newuser.setParentId(puser.getUserId());
		newuser.setWeightCode(puser.getWeightCode()+1);
		this.userService.save(newuser);
		if ("A".equals(type)) {
			puser.setDepartmentA(newuser.getUserId());
		}else if ("B".equals(type)) {
			puser.setDepartmentB(newuser.getUserId());
		}
		this.userService.save(puser);
		savePer(newuser.getUserId());
		//用户创建完成后更新用户收益信息
	}
	
	@RequestMapping("/testusercreate/{id}")
	public String saveUserCreate(@PathVariable("id") Long id) {
		return mainUpdatePerformance(id);
	}
	
	/**
	 * 更新收益表 传递新创建的用户ID
	 * @param createId
	 * @return
	 */
	private String mainUpdatePerformance(Long createId) {
		//创建用户更新收益表，记录是否更新收益数据，如果没有更新数据更新收益表并记录收益表更新状态
		UserCreatereRecord ucr  = saveUserCreateEntity(createId);
		if (ucr!=null && !ucr.isStatus()) {
			updateUserPerformance(createId);
			ucr.setStatus(true);
			userCreateRecordService.save(ucr);
		}else {
			return "此用户已更新过收益";
		}
		return "更新用户收益成功";
	}
	
	private void updateUserPerformance(Long userid) {
		//取得所有收益表和用户表数据
		Iterable<User> userlist = this.userService.getAll();
		Iterable<Performance> pers = performanceService.getAll();
		
		Map<Long,User> userMap = new HashMap<Long,User>();
		Map<Long,Performance> perMap = new HashMap<Long,Performance>();
		for (User user : userlist) {
			userMap.put(user.getUserId(), user);
		}
		for (Performance per : pers) {
			perMap.put(per.getUserId(), per);
		}
		//获得需要更新的业绩表数据
		List<Performance> perlist = CheckUtil.computePer(userid, userMap, perMap);
		for (Performance performance : perlist) {
			//更新收益表数据
			performanceService.save(performance);
		}
		//发生变化的业绩更新收益表
	}
	
	/**
	 * 在创建用户的时候同时创建用户更新表默认数据
	 * @param id
	 */
	private UserCreatereRecord saveUserCreateEntity(long userId) {
		UserCreatereRecord ucr = userCreateRecordService.getUserCreatereRecordByUserId(userId);
		if (ucr==null) {
			ucr = new UserCreatereRecord();
			Date newdate = new Date();
			ucr.setUserId(userId);
			ucr.setStatus(false);
			ucr.setCreateTime(newdate);
			ucr.setUpdateTime(newdate);
			userCreateRecordService.save(ucr);
		}
		return ucr;
	}
	
	/**
	 * 在创建用户的时候同时创建业绩表默认数据
	 * @param id
	 */
	private void savePer(long id) {
		Performance per = performanceService.getOne(id);
		if (per==null) {
			per = new Performance();
			Date newdate = new Date();
			per.setUserId(id);
			per.setCreateTime(newdate);
			per.setUpdateTime(newdate);
			performanceService.save(per);
		}
	}
	
	@RequestMapping("/findAll")
	public Iterable<User> getAll(){
		return userService.getAll();
	}
	
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
	
	@RequestMapping("/delete/{id}")
	public void deleteOne(@PathVariable("id") Long id) {
		this.userService.delete(id);
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
