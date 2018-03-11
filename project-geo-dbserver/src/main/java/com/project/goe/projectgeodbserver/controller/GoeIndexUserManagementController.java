package com.project.goe.projectgeodbserver.controller;

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

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserTypeQueryRequest;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

@RestController
@RequestMapping("/goeIndexUserManagement")
@CrossOrigin
public class GoeIndexUserManagementController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	@Autowired
	private BonusPayListService bonusPayListService;
	
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
			Page<UserVO> pageUserVO = pageUser.map(new Converter<User,UserVO>() {

				@Override
				public UserVO convert(User user) {
					return UserUtil.UserToUserVO(user);
				}
			});
			
			retMsg.setCode(200);
			retMsg.setData(pageUserVO);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);
			
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("信息查询失败");
		}
	}

	// 基于用户账户名称或昵称，分页模糊查询
	@GetMapping("/findUsersByNickNameOrAccountLike")
	public RetMsg findUsersByNickNameOrUserId(UserTypeQueryRequest userTypeQueryRequest,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		
		RetMsg retMsg = null;
		Sort sort = null;
		
		if(null == userTypeQueryRequest)
			throw new RuntimeException("未传递参数");
		
		String type = userTypeQueryRequest.getType();
		String value = userTypeQueryRequest.getValue();
		
		if(null == type)
			throw new RuntimeException("参数类型能为空");
		
		if(null == value)
			throw new RuntimeException("参数类型值不能为空");
		
		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<User> pageUser = this.userService.findUsersByNickNameOrAccountLike(userTypeQueryRequest, pageable);
			Page<UserVO> pageUserVO = pageUser.map(new Converter<User,UserVO>() {

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
	//必须传递的参数：需要更新的account
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

}
