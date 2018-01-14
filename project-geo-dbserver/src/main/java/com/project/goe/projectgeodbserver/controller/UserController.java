package com.project.goe.projectgeodbserver.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.CheckUtil;
import com.project.goe.projectgeodbserver.util.UserUtil;

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
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public User save(@ModelAttribute User user) {
		boolean isroot = true;
		User u = UserUtil.getTestUser();
		if (isroot) {
		}else {
			
		}
		return this.userService.save(user);
	}
	
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
		//获得需要更新的收益表数据
		List<Performance> perlist = CheckUtil.computePer(userid, userMap, perMap);
		for (Performance performance : perlist) {
			//更新收益表数据
			performanceService.save(performance);
		}
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
	
	@RequestMapping("/findByAccount/{account}")
	public User findByAccount(@PathVariable String account) {
		return this.userService.findByAccount(account);
	}
	
	@RequestMapping("/delete/{id}")
	public void deleteOne(@PathVariable("id") Long id) {
		this.userService.delete(id);
	}
}
