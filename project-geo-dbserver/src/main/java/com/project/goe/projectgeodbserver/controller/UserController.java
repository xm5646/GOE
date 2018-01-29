package com.project.goe.projectgeodbserver.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.server.EarnServerSchedul;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.BusinessUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.viewentity.PerformanceLevel;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UpdatePaymentPasswordRequest;
import com.project.goe.projectgeodbserver.viewentity.UserLoginRequest;
import com.project.goe.projectgeodbserver.viewentity.UserLoginSettingRequest;
import com.project.goe.projectgeodbserver.viewentity.UserLoginPasswordUpdateRequest;
import com.project.goe.projectgeodbserver.viewentity.UserSaveRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PerformanceService performanceService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	// 将业务全部移动到调度服务上
	@Autowired
	private EarnServerSchedul earnServerSchedul;

	/**
	 * @Description：更新用户登录密码
	 * @return：RetMsg
	 */
	@PostMapping("/updateLoginPassword")
	@Transactional
	public RetMsg updatePassword(@Validated UserLoginPasswordUpdateRequest userPasswordUpdateRequest,
			BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String account = userPasswordUpdateRequest.getAccount();
		String oldPassword = userPasswordUpdateRequest.getOldPassword();
		String newPassword = userPasswordUpdateRequest.getNewPassword();

		// 验证用户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		// 判断原密码是否正确
		if (!MD5Util.encrypeByMd5(oldPassword).equals(user.getPassword())) {
			throw new RuntimeException("原密码不正确!");
		}

		user.setPassword(MD5Util.encrypeByMd5(newPassword));
		this.userService.save(user);

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(user.getAccount());
		retMsg.setMessage("用户登录密码更新成功!");
		retMsg.setSuccess(true);

		return retMsg;

	}

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

	@RequestMapping("/saveall")
	public String saveTestAll() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		earnServerSchedul.mainTest(0, 0);

		return "测试数据插入根成功";
	}

	// 在某个节点下添N层用户
	@RequestMapping("/testuser/{pid}/{weight}")
	public String saveUserCreate(@PathVariable("pid") Long pid, @PathVariable("weight") Long weight) {
		try {
			earnServerSchedul.mainTest(pid, weight);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "测试数据插入根成功";
	}

	@RequestMapping("/testusercreate/{id}")
	public String saveUserCreate(@PathVariable("id") Long id) {
		return earnServerSchedul.mainUpdatePerformance(id);
	}

	// 发放N天薪水
	@RequestMapping("/testbonus/{count}")
	public String testbonus(@PathVariable("count") Long count) {
		for (int i = 0; i < count; i++) {
			earnServerSchedul.mainComperBonuspaylist();
		}
		return "";
	}

	@RequestMapping("/findAll")
	public Iterable<User> getAll() {
		return userService.getAll();
	}

	/**
	 * @Description：用户登录,passwordReset：密码重置标识，true：密码已重置；false：密码未重置
	 * @return：retMsg
	 */
	@PostMapping("/login")
	public RetMsg login(@Validated UserLoginRequest userLoginRequest, BindingResult bindingResult) {
		String account = userLoginRequest.getAccount();
		String password = userLoginRequest.getPassword();

		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 验证账户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user) {
			throw new RuntimeException("当前用户不存在!");
		}

		// 验证用户的密码是否正确
		if (!(MD5Util.encrypeByMd5(password).equals(user.getPassword()))) {
			throw new RuntimeException("用户密码输入有误！");
		}

		retMsg = new RetMsg();

		retMsg.setCode(200);
		retMsg.setSuccess(true);
		retMsg.setData(UserUtil.UserToUserVO(user));

		return retMsg;
	}

	/**
	 * @Description：重置登录密码，设置支付密码和用户电话号码
	 * @return：RetMsg
	 */
	@PostMapping("/initUserInfo")
	@Transactional
	public RetMsg initUserInfo(@Validated UserLoginSettingRequest userLoginSettingRequest,
			BindingResult bindingResult) {
		String account = userLoginSettingRequest.getAccount();
		String newPassword = userLoginSettingRequest.getNewPassword();
		String paymentPassword = userLoginSettingRequest.getPaymentPassword();
		String userPhone = userLoginSettingRequest.getUserPhone();
		String nickName = userLoginSettingRequest.getNickName();

		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 验证用户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户账号不存在!");

		// 设置用户登录新密码、支付密码和手机号码
		user.setPassword(MD5Util.encrypeByMd5(newPassword));
		user.setPaymentPassword(MD5Util.encrypeByMd5(paymentPassword));
		user.setUserPhone(userPhone);
		user.setPasswordReset(true);
		user.setNickName(nickName);

		// 更新用户信息
		this.userService.save(user);

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(UserUtil.UserToUserVO(user));
		retMsg.setSuccess(true);
		retMsg.setMessage("用户设置成功！");

		return retMsg;
	}

	// 返回用户的业绩信息
	@GetMapping("/performance")
	public RetMsg RetMsg(@RequestParam("account") String account) {
		if (null == account)
			throw new RuntimeException("用户名不能为空!");

		User user = this.userService.findByAccount(account);

		if (null == user)
			throw new RuntimeException("当前用户不存在!");

		long userId = user.getUserId();

		try {
			Performance p = this.performanceService.findByUserId(userId);
			PerformanceLevel pLevel = new PerformanceLevel();
			pLevel.setPerformance(p);
			String userLevelCH = BusinessUtil.getBusinessEntity(user.getUserLevel()).getUserLevel_CH();
			pLevel.setUserLevel(userLevelCH);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(pLevel);
			retMsg.setMessage("查询用户业绩成功!");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询用户业绩失败!");
		}
	}

	/**
	 * @Description：新增用户
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@Transactional
	public RetMsg saveUser(@Validated UserSaveRequest userSavePostParams, BindingResult bindingResult) {
		String account = userSavePostParams.getAccount();
		String password = userSavePostParams.getPassword();
		String parentAccount = userSavePostParams.getParentAccount();
		String recommendAccount = userSavePostParams.getRecommendAccount();
		String position = userSavePostParams.getPosition();

		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// account不允许重复
		if (null != this.userService.findByAccount(account))
			throw new RuntimeException("用户名重复！");

		User parentUser = null;
		User recommendUser = null;

		// 查询推荐人和父对象
		// 父节点和推荐人为同一个人
		if (parentAccount.equals(recommendAccount)) {
			parentUser = recommendUser = this.userService.findByAccount(parentAccount);
		} else {
			parentUser = this.userService.findByAccount(parentAccount);
			recommendUser = this.userService.findByAccount(recommendAccount);
		}

		// 判断父节点或推荐人节点是否为null
		if (null == parentUser || null == recommendUser) {
			throw new RuntimeException("父节点或推荐人节点不存在!");
		}

		// 判断推荐人的报单币余额
		if (recommendUser.getConsumeCoin() < this.bonusPayPercentage.getConsumeCoinUnitPrice())
			throw new RuntimeException("报单币余额不足，请充值!");

		try {
			/******* 新增用户 *******/
			// 设置用户名、密码、父id、推荐人id
			User user = new User();
			user.setAccount(account);
			user.setPassword(MD5Util.encrypeByMd5(password));

			user.setParentId(parentUser.getUserId());
			user.setRecomondId(recommendUser.getUserId());

			// 设置用户的层级数：父节点层级数+1
			user.setWeightCode(parentUser.getWeightCode() + 1);

			// 设置用户创建时间
			Date userCreateTime = new Date();
			user.setCreateTime(userCreateTime);

			// 设置用户下次考核日期
			user.setAssessDate(userCreateTime);

			// 保存新增用户信息
			user = this.userService.save(user);

			/******* 更新用户上级信息 ********/
			// 修改父节点下新增用户id
			switch (position) {
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

			// 更新父节点
			this.userService.save(parentUser);

			// 更新推荐人激活状态:如果推荐人为未激活状态，则修改其状态，否则不发生变化
			if (!recommendUser.isUserStatus()) {
				recommendUser.setActivateTime(new Date());
				recommendUser.setUserStatus(true);
			}

			// 更新业绩信息
			earnServerSchedul.mainUpdatePerformance(user.getUserId());

			// 更新推荐人的报单币
			recommendUser
					.setConsumeCoin(recommendUser.getConsumeCoin() - this.bonusPayPercentage.getConsumeCoinUnitPrice());

			// 更新推荐人信息
			this.userService.save(recommendUser);

			// 新增推荐人消费记录
			ConsumeRecord consumeRecord = new ConsumeRecord();
			consumeRecord.setUserId(recommendUser.getUserId());
			consumeRecord.setSendUserId(recommendUser.getUserId());
			consumeRecord.setConsumeType(ConsumeType.COIN_TRANSFER_ADDCONSUMER);
			consumeRecord.setConsumeTime(new Date());
			consumeRecord.setDescription("推荐新用户:" + user.getAccount());

			// 查询公司id
			User company = this.userService.findByAccount("管理员");
			consumeRecord.setReceiveUserId(company.getUserId());
			consumeRecord.setConsumeNumber(this.bonusPayPercentage.getConsumeCoinUnitPrice());
			consumeRecord.setConsumeStatus(true);
			consumeRecord.setDescription(ConsumeType.COIN_TRANSFER_ADDCONSUMER);

			consumeRecordService.addOneConsumeRecord(consumeRecord);

			// 返回新增用户信息
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("用户添加成功!");
			retMsg.setSuccess(true);
			return retMsg;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
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
	@GetMapping("/findByAccount")
	public RetMsg findUserByAccount(@RequestParam("account") String account) {
		if (null == account)
			throw new RuntimeException("用户账户名不能为null");

		try {
			User u = this.userService.findByAccount(account);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(u));
			retMsg.setMessage("用户查询成功!");
			retMsg.setSuccess(true);
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户账户不存在！");
		}
	}

	@RequestMapping("/delete/{id}")
	public void deleteOne(@PathVariable("id") Long id) {
		this.userService.delete(id);
	}

	// 修改用户的交易密码
	@PostMapping("/updatePaymentPassword")
	public RetMsg updatePaymentPassword(@Validated UpdatePaymentPasswordRequest updatePaymentPasswordRequest,
			BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String account = updatePaymentPasswordRequest.getAccount();
		String oldPassword = updatePaymentPasswordRequest.getOldPaymentpassword();
		String newPassword = updatePaymentPasswordRequest.getNewPaymentPassword();
		String newPassword2 = updatePaymentPasswordRequest.getNewPaymentPassword2();

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		if (!(MD5Util.encrypeByMd5(oldPassword)).equals(user.getPaymentPassword()))
			throw new RuntimeException("原支付密码输入错误!");

		if (!newPassword.equals(newPassword2))
			throw new RuntimeException("两次密码输入不一致!");

		try {
			user.setPaymentPassword(MD5Util.encrypeByMd5(newPassword));

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("支付密码修改成功!");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("支付密码修改失败!");
		}
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
