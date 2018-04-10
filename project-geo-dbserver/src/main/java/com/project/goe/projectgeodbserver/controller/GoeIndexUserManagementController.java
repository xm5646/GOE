package com.project.goe.projectgeodbserver.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.EarningService;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.EarningUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.PerformanceUtil;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.EarningVO;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserNode;
import com.project.goe.projectgeodbserver.viewentity.UserTypeQueryRequest;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

@RestController
@RequestMapping("/goeIndexUserManagement")
@CrossOrigin
public class GoeIndexUserManagementController {
	@Autowired
	private UserService userService;

	@Autowired
	private EarningService earningService;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private BonusPayListService bonusPayListService;
	
	@Autowired
	private PerformanceService performanceService;

	/**************** 用户信息管理 *****************/
	// 按createTime，分页查询所有用户信息
	@GetMapping("/findAllUsers")
	public RetMsg findAllUsers(@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
		try {
			Sort sort = null;
			RetMsg retMsg = new RetMsg();

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<User> pageUser = this.userService.findAllUserBySort(pageable);
			Page<UserVO> pageUserVO = pageUser.map(new Converter<User, UserVO>() {

				@Override
				public UserVO convert(User user) {
					long departmentA = user.getDepartmentA();
					long departmentB = user.getDepartmentB();
					long departmentC = user.getDepartmentC();

					if (0 == departmentA) {
						user.setAccountA("无");
					} else {
						user.setAccountA(userService.findByUserId(departmentA).getAccount());
					}

					if (0 == departmentB) {
						user.setAccountB("无");
					} else {
						user.setAccountB(userService.findByUserId(departmentB).getAccount());
					}

					if (0 == departmentC) {
						user.setAccountC("无");
					} else {
						user.setAccountC(userService.findByUserId(departmentC).getAccount());
					}

					return UserUtil.UserToUserVO(user);
				}
			});

			retMsg.setCode(200);
			retMsg.setData(pageUserVO);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("信息查询失败");
		}
	}

	// 基于用户账户名称或昵称，分页模糊查询用户
	@GetMapping("/findUsersByNickNameOrAccountLike")
	public RetMsg findUsersByNickNameOrUserId(UserTypeQueryRequest userTypeQueryRequest,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {

		RetMsg retMsg = null;
		Sort sort = null;

		if (null == userTypeQueryRequest)
			throw new RuntimeException("未传递参数");

		String type = userTypeQueryRequest.getType();
		String value = userTypeQueryRequest.getValue();

		if (null == type)
			throw new RuntimeException("参数类型能为空");

		if (null == value)
			throw new RuntimeException("参数类型值不能为空");

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<User> pageUser = this.userService.findUsersByNickNameOrAccountLike(userTypeQueryRequest, pageable);
			Page<UserVO> pageUserVO = pageUser.map(new Converter<User, UserVO>() {

				@Override
				public UserVO convert(User user) {
					return UserUtil.UserToUserVO(user);
				}
			});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(pageUserVO);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("信息查询失败");
		}
	}

	// 更新用户信息:可更新昵称、电话号码、激活状态、考核状态、密码、支付密码
	// 必须传递的参数：需要更新的account
	@PostMapping("/updateUserInfo")
	public RetMsg updateUserInfo(@RequestParam("account") String account,
			@RequestParam(value = "nickName", required = false) String nickName,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "activateStatus", required = false) String activateStatus,
			@RequestParam(value = "assessStatus", required = false) String assessStatus,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "paymentPassword", required = false) String paymentPassword) {
		RetMsg retMsg = null;

		// 查询用户是否存在
		User u = this.userService.findByAccount(account);
		if (null == u)
			throw new RuntimeException("用户信息不存在");

		if ("administrator".equals(u.getAccount()))
			throw new RuntimeException("不允许修改管理员密码");

		if (null != nickName)
			u.setNickName(nickName);

		if (null != phone)
			u.setUserPhone(phone);

		if (null != activateStatus) {
			if (activateStatus.equals("false"))
				u.setUserStatus(false);
			else if (activateStatus.equals("true"))
				u.setUserStatus(true);
			else
				throw new RuntimeException("激活状态输入有误");
		}

		if (null != assessStatus) {
			if (assessStatus.equals("false"))
				u.setAssessStatus(false);
			else if (assessStatus.equals("true"))
				u.setAssessStatus(true);
			else
				throw new RuntimeException("考核状态输入有误");
		}

		if (null != password) {
			u.setPassword(MD5Util.encrypeByMd5(password));
		}

		if (null != paymentPassword) {
			u.setPaymentPassword(MD5Util.encrypeByMd5(paymentPassword));
		}

		try {
			u = this.userService.save(u);
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(u));
			retMsg.setMessage("用户信息更新成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户信息更新失败");
		}

	}

	// 查询所有累计收益
	@GetMapping("/findAllEarnings")
	public RetMsg findAllEarnings(@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;
			RetMsg retMsg = new RetMsg();

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<Earning> pageEarning = this.earningService.findAllEarnings(pageable);
			Page<EarningVO> pageEarningVO = pageEarning.map(new Converter<Earning, EarningVO>() {

				@Override
				public EarningVO convert(Earning earning) {
					long userId = earning.getUserid();
					User user = userService.findByUserId(userId);
					earning.setAccount(user.getAccount());

					return EarningUtil.earningToEarningVO(earning);
				}
			});

			retMsg.setCode(200);
			retMsg.setData(pageEarningVO);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("信息查询失败");
		}

	}

	// 基于用户账户名，分页模糊查询收益记录
	@GetMapping("/findEarningsByAccountLike")
	public RetMsg findEarningsByAccountLike(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {

		RetMsg retMsg = null;
		Sort sort = null;

		if (null == account)
			throw new RuntimeException("用户名不能为空");

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在");

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<Earning> pageEarning = this.earningService.findEarningsByUserId(user.getUserId(), pageable);
			Page<EarningVO> pageEarningVO = pageEarning.map(new Converter<Earning, EarningVO>() {

				@Override
				public EarningVO convert(Earning earning) {
					earning.setAccount(user.getAccount());
					return EarningUtil.earningToEarningVO(earning);
				}
			});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(pageEarningVO);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("信息查询失败");
		}
	}

	/************ 重置管理员交易密码和支付密码 **************************/
	@PostMapping("/updatePasswordOfAdmin")
	public RetMsg updatePasswordOfAdmin(
			@RequestParam(value = "oldLoginPassword", required = false) String oldLoginPassword,
			@RequestParam(value = "newLoginPassword", required = false) String newLoginPassword,
			@RequestParam(value = "oldPaymentPassword", required = false) String oldPaymentPassword,
			@RequestParam(value = "newPaymentPassword", required = false) String newPaymentPassword) {

		User user = this.userService.findByAccount("administrator");
		if (null == user)
			throw new RuntimeException("管理员账号不存在");

		if (null != oldLoginPassword && null != newLoginPassword) {
			String encryOldLoginPassword = MD5Util.encrypeByMd5(oldLoginPassword);
			String encryNewLoginPassword = MD5Util.encrypeByMd5(newLoginPassword);

			if (!encryOldLoginPassword.equals(user.getPassword()))
				throw new RuntimeException("旧登录密码输入错误");

			user.setPassword(encryNewLoginPassword);
		}

		if (null != oldPaymentPassword && null != newPaymentPassword) {
			String encryOldPaymentPassword = MD5Util.encrypeByMd5(oldPaymentPassword);
			String encryNewPaymentPassword = MD5Util.encrypeByMd5(newPaymentPassword);

			if (!encryOldPaymentPassword.equals(user.getPaymentPassword()))
				throw new RuntimeException("旧支付密码输入错误");

			user.setPaymentPassword(encryNewPaymentPassword);
		}

		// 更新用户信息
		try {
			this.userService.save(user);

			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData("密码更新成功");
			retMsg.setMessage("密码更新成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("密码更新失败");
		}

	}

	/******************** 用户层级 ******************************/
	@GetMapping("/userLevelInfo")
	public RetMsg userLevelInfo(@RequestParam("account") String account) {
		//父节点的基本信息和业绩信息
		User pUser = this.userService.findByAccount(account);
		if(null == pUser)
			throw new RuntimeException("父节点不存在");
		
		Performance pPerformamce = this.performanceService.findByUserId(pUser.getUserId());
		if(null == pPerformamce)
			throw new RuntimeException("父节点业绩信息不存在");
		
		UserNode userNode = new UserNode();
		userNode.setId(pUser.getUserId());
		userNode.setLabel(pUser.getAccount());
		userNode.setUserNodeVO(UserUtil.UserToUserNodeVO(pUser));
		userNode.setPerformanceVO(PerformanceUtil.pToPerformanceVO(pPerformamce));
		List<UserNode> childNodeList = new ArrayList<UserNode>();
		
		long childId1 = pUser.getDepartmentA();
		long childId2 = pUser.getDepartmentB();
		long childId3 = pUser.getDepartmentC();
		
		User cUser1 = null;
		User cUser2 = null;
		User cUser3 = null;
		
		if(0 != childId1) {
			cUser1 = this.userService.findByUserId(childId1);
			if(null == cUser1)
				throw new RuntimeException("子节点不存在");
			
			Performance cp1 = this.performanceService.findByUserId(cUser1.getUserId());
			if(null == cp1) {
				throw new RuntimeException("子节点业绩信息不存在");
			}
			
			UserNode cUserNode = new UserNode();
			cUserNode.setId(cUser1.getUserId());
			cUserNode.setLabel(cUser1.getAccount());
			cUserNode.setUserNodeVO(UserUtil.UserToUserNodeVO(cUser1));
			cUserNode.setPerformanceVO(PerformanceUtil.pToPerformanceVO(cp1));
			cUserNode.setChildren(null);
			
			childNodeList.add(cUserNode);
		}
		
		if(0 != childId2) {
			cUser2 = this.userService.findByUserId(childId2);
			if(null == cUser2)
				throw new RuntimeException("用户不存在");
			
			Performance cp2 = this.performanceService.findByUserId(cUser2.getUserId());
			if(null == cp2)
				throw new RuntimeException("用户业绩信息不存在");
			
			UserNode cUserNode = new UserNode();
			cUserNode.setId(cUser2.getUserId());
			cUserNode.setLabel(cUser2.getAccount());
			cUserNode.setUserNodeVO(UserUtil.UserToUserNodeVO(cUser2));
			cUserNode.setPerformanceVO(PerformanceUtil.pToPerformanceVO(cp2));
			cUserNode.setChildren(null);
			
			childNodeList.add(cUserNode);
		}
		
		if(0 != childId3) {
			cUser3 = this.userService.findByUserId(childId3);
			if(null == cUser3)
				throw new RuntimeException("用户不存在");
			
			Performance cp3 = this.performanceService.findByUserId(cUser3.getUserId());
			if(null == cp3)
				throw new RuntimeException("用户业绩信息不存在");
			
			UserNode cUserNode = new UserNode();
			cUserNode.setId(cUser3.getUserId());
			cUserNode.setLabel(cUser3.getAccount());
			cUserNode.setUserNodeVO(UserUtil.UserToUserNodeVO(cUser3));
			cUserNode.setPerformanceVO(PerformanceUtil.pToPerformanceVO(cp3));
			cUserNode.setChildren(null);
			
			childNodeList.add(cUserNode);
		}
		
		userNode.setChildren(childNodeList);
		RetMsg retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setSuccess(true);
		retMsg.setMessage("二层用户节点显示");
		retMsg.setData(userNode);
		
		return retMsg;
	}
	
		

}
