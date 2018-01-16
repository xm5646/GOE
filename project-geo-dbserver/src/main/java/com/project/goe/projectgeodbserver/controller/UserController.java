package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.project.goe.projectgeodbserver.server.EarnServerSchedul;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.CheckUtil;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserSavePostParams;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PerformanceService performanceService;
	
	//将业务全部移动到调度服务上
	@Autowired
	private EarnServerSchedul earnServerSchedul;
	
	@RequestMapping("/testsave/{id}/{type}")
	public String saveTest(@PathVariable("id") Long id, @PathVariable("type") String type) {
		User puser = userService.getUserById(id);
			User newuser = UserUtil.getTestUser();
			earnServerSchedul.mainUpdateUser(puser.getUserId(), type, newuser);
		return "测试数据插入成功" + type;
	}
	
	@RequestMapping("/savemain")
	public String saveMain() {
		User newuser = UserUtil.getTestUser();
		newuser.setWeightCode(1);
		this.userService.save(newuser);
		earnServerSchedul.savePer(newuser.getUserId());
		return "测试数据插入根成功";
	}

	
	
	@RequestMapping("/testusercreate/{id}")
	public String saveUserCreate(@PathVariable("id") Long id) {
		return earnServerSchedul.mainUpdatePerformance(id);
	}


	@RequestMapping("/findAll")
	public Iterable<User> getAll() {
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
	
	//返回用户的业绩信息
	@GetMapping("/performance")
	public RetMsg RetMsg(@RequestParam("account") String account) {
		User user = this.userService.findByAccount(account);
		
		if(null == user)
			throw new RuntimeException("当前用户不存在!");
		
		long userId = user.getUserId();
		
		try {
			Performance p = this.performanceService.findByUserId(userId);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(p);
			retMsg.setMessage("查询用户业绩成功!");
			retMsg.setSuccess(true);
			
			return retMsg;
		}catch(Exception e) {
			throw new RuntimeException("查询用户业绩失败>>>>>" + e.getMessage());
		}
	}

	// 新增用户
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Transactional
	public RetMsg saveUser(@ModelAttribute UserSavePostParams userSavePostParams) {
		try {
			String account = userSavePostParams.getAccount();
			// account不允许重复
			if (null != this.userService.findByAccount(account))
				throw new RuntimeException("用户名重复！");

			String password = userSavePostParams.getPassword();
			String parentAccount = userSavePostParams.getParentAccount();
			String recomendAccount = userSavePostParams.getRecomendAccount();
			/*******新增用户*******/
			// 设置用户名、密码、父id、推荐人id
			User user = new User();
			user.setAccount(account);
			user.setPassword(MD5Util.encrypeByMd5(password));
			User parentUser = this.userService.findByAccount(parentAccount);
			User recomondUser = this.userService.findByAccount(recomendAccount);
			
			user.setParentId(parentUser.getUserId());
			user.setRecomondId(recomondUser.getUserId());
			
			// 设置用户的层级数：父节点层级数+1
			user.setWeightCode(parentUser.getWeightCode() + 1);

			// 设置用户创建时间
			user.setCreateTime(new Date());

			// 设置用户下次考核日期
			user.setAssessDate(null);
			// 保存新增用户信息
			user = this.userService.save(user);
			
			System.out.println(user.getUserId());
			
			/*******更新用户上级信息********/
			//父节点位置处添加新用户
			String position = userSavePostParams.getPosition();
			//修改父节点下新增用户id
			switch(position) {
				case "A":
					parentUser.setDepartmentA(user.getUserId());
					break;
				case "B":
					parentUser.setDepartmentB(user.getUserId());
					break;
				case "C":
					parentUser.setDepartmentC(user.getUserId());
					break;
				default:
					throw new RuntimeException("传入的位置参数有误!");
			}
			
			//更新父节点
			this.userService.updateUserDepartmentId(parentUser.getDepartmentA(),parentUser.getDepartmentB(),parentUser.getDepartmentC(),parentUser.getParentId());
			
			//更新业绩信息
			earnServerSchedul.mainUpdatePerformance(user.getUserId());
			
			// 返回新增用户信息
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("用户添加成功!");
			retMsg.setSuccess(true);
			return retMsg;

		} catch (Exception e) {
			throw new RuntimeException("添加用户失败!------->" + e.getMessage());
		}
	}
	
	@GetMapping("/update/{departmentA}/{departmentB}/{departmentC}/{userId}")
	public String testUpdate(@PathVariable long departmentA,@PathVariable long departmentB,@PathVariable long departmentC,
			@PathVariable long userId) {
		this.userService.updateUserDepartmentId(departmentA, departmentB, departmentC, userId);
		return "success";
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
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
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
