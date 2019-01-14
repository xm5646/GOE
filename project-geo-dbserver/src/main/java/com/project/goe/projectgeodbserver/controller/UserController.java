package com.project.goe.projectgeodbserver.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.project.goe.projectgeodbserver.entity.*;
import com.project.goe.projectgeodbserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.project.goe.projectgeodbserver.server.EarnServerSchedul;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.util.BonusPayPercentage;
import com.project.goe.projectgeodbserver.util.BusinessUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.UserLoginSetting;
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
	private UserLoginSetting userLoginSetting;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private EarningService earningService;

	@Autowired
	private UserRepeatCheckService UserRepeatCheckService;
	
	@Autowired
	private PerformanceService performanceService;

	@Autowired
	private BonusPayPercentage bonusPayPercentage;

	@Autowired
	private ConsumeRecordService consumeRecordService;
	
	@Autowired
	private CardInfoService cardInfoService;

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
			BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
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
			throw new RuntimeException("用户不存在");

		// 判断原密码是否正确
		if (!MD5Util.encrypeByMd5(oldPassword).equals(user.getPassword())) {
			throw new RuntimeException("原密码不正确");
		}

		user.setPassword(MD5Util.encrypeByMd5(newPassword));
		this.userService.save(user);

		setUserLoginCookie(user, request, response);
		response.setHeader("loginStatus", "true");

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(user.getAccount());
		retMsg.setMessage("用户登录密码更新成功");
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
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "测试数据插入根成功";
	}


	@RequestMapping("/getLastEarningByUserId/{userId}")
	public String getLastEarningByUserId(@PathVariable("userId") Long userId) {
		Earning earning = earningService.getLastEarningByUserID(userId);
		return earning.toString();
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

	// 定时考核启动
	@RequestMapping("/testmainAssessInspect")
	public String testmainAssessInspect() {
		earnServerSchedul.mainAssessInspect();
		return "定时考核";
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
	public RetMsg login(@Validated UserLoginRequest userLoginRequest, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		String account = userLoginRequest.getAccount();
		String password = userLoginRequest.getPassword();

		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 验证账户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user) {
			throw new RuntimeException("当前用户不存在");
		}

		// 验证用户的密码是否正确
		if (!(MD5Util.encrypeByMd5(password).equals(user.getPassword()))) {
			throw new RuntimeException("用户密码输入有误");
		}

		// 设置cookie
		setUserLoginCookie(user, request, response);
		response.setHeader("loginStatus", "true");

		retMsg = new RetMsg();
		// 验证用户是否需要重置密码
		if (!user.isPasswordReset()) {
			retMsg.setCode(200);
			retMsg.setSuccess(true);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("用户重置密码");

			return retMsg;
		}

		retMsg.setCode(200);
		retMsg.setSuccess(true);
		retMsg.setData(UserUtil.UserToUserVO(user));

		return retMsg;
	}

	private void setUserLoginCookie(User user, HttpServletRequest request, HttpServletResponse response) {
		long time = System.currentTimeMillis() + this.userLoginSetting.getExpireTime() * 60 * 10000;
		String account = user.getAccount();
		String password = user.getPassword();
		String data = MD5Util.encrypeByMd5(account + ":" + time + ":" + password);
		String cookieStr = account + ":" + time + ":" + data;

		Cookie cookie = new Cookie("autoLogin", cookieStr);
		cookie.setComment("自动登录cookie!");
		cookie.setPath("/");
		cookie.setMaxAge((int) (this.userLoginSetting.getExpireTime() * 60));
		response.addCookie(cookie);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET");
	}

	/**
	 * @Description：重置登录密码，设置支付密码和用户电话号码,银行卡
	 * @return：RetMsg
	 */
	@PostMapping("/initUserInfo")
	@Transactional
	public RetMsg initUserInfo(@Validated UserLoginSettingRequest userLoginSettingRequest, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {
		String account = userLoginSettingRequest.getAccount();
		String newPassword = userLoginSettingRequest.getNewPassword();
		String paymentPassword = userLoginSettingRequest.getPaymentPassword();
		String userPhone = userLoginSettingRequest.getUserPhone();
		String nickName = userLoginSettingRequest.getNickName();
		String bankName = userLoginSettingRequest.getBankName();
		String cardNumber = userLoginSettingRequest.getCardNumber();

		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 验证用户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户账号不存在");
		
		// 验证姓名手机号银行卡相同用户数量是否超过3个
		if (this.UserRepeatCheckService.checkUserIsMaxRepeat(nickName + userPhone + bankName + cardNumber)) {
			throw new RuntimeException("该用户信息超过最大注册次数");
		}
		
		
		// 添加用户银行卡
		CardInfo cardInfo = new CardInfo();
		cardInfo.setBankName(bankName);
		cardInfo.setCardNumber(cardNumber);
		cardInfo.setCardOwnerName(nickName);
		cardInfo.setPhone(userPhone);
		cardInfo.setUserId(userService.findByAccount(account).getUserId());
		cardInfo.setCreateTime(new Date());
		cardInfoService.save(cardInfo);
		

		// 设置用户登录新密码、支付密码和手机号码
		user.setPassword(MD5Util.encrypeByMd5(newPassword));
		user.setPaymentPassword(MD5Util.encrypeByMd5(paymentPassword));
		user.setUserPhone(userPhone);
		user.setPasswordReset(true);
		user.setNickName(nickName);

		// 更新用户信息
		this.userService.save(user);

		// 设置cookie
		setUserLoginCookie(user, request, response);
		response.setHeader("loginStatus", "true");

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(UserUtil.UserToUserVO(user));
		retMsg.setSuccess(true);
		retMsg.setMessage("用户设置成功");

		return retMsg;
	}

	// 返回用户的业绩信息
	@GetMapping("/performance")
	public RetMsg RetMsg(@RequestParam("account") String account) {
		if (null == account)
			throw new RuntimeException("用户名不能为空");

		User user = this.userService.findByAccount(account);

		if (null == user)
			throw new RuntimeException("当前用户不存在");

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
			retMsg.setMessage("查询用户业绩成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询用户业绩失败");
		}
	}

	/**
	 * @Description：新增用户
	 */
	@PostMapping("/save")
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
			throw new RuntimeException("用户名重复");

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
			throw new RuntimeException("父节点或推荐人节点不存在");
		}

		// 判断推荐人的报单币余额
		if (recommendUser.getConsumeCoin() < this.bonusPayPercentage.getConsumeCoinUnitPrice())
			throw new RuntimeException("报单币余额不足，请充值");

		try {
			/******* 新增用户 *******/
			// 设置用户名、密码、父id、推荐人id
			User user = new User();
			user.setAccount(account);
			user.setPassword(MD5Util.encrypeByMd5(password));

			user.setParentId(parentUser.getUserId());
			user.setRecomondId(recommendUser.getUserId());
			user.setUserStatus(true);

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
				throw new RuntimeException("传入的位置参数有误");
			}

			// 更新父节点
			this.userService.save(parentUser);

			// 更新推荐人激活状态:如果推荐人为未激活状态，则修改其状态，否则不发生变化
			if (!recommendUser.isUserStatus()) {
				recommendUser.setActivateTime(new Date());
				recommendUser.setUserStatus(true);
			}

			// 添加新建用户对应的业绩表记录
			earnServerSchedul.savePer(user.getUserId());

			// 更新业绩信息
			redisService.pushObjToList("userIDList", user.getUserId());
//			earnServerSchedul.mainUpdatePerformance(user.getUserId());

			// 更新推荐人的报单币
			recommendUser
					.setConsumeCoin(recommendUser.getConsumeCoin() - this.bonusPayPercentage.getConsumeCoinUnitPrice());
			this.userService.save(recommendUser);

			// 查询公司id,更新公司的报单币
			User company = this.userService.findByAccount("administrator");
			if (null == company)
				throw new RuntimeException("公司账户不存在");
			company.setConsumeCoin(company.getConsumeCoin() + this.bonusPayPercentage.getConsumeCoinUnitPrice());
			this.userService.save(company);

			// 新增推荐人消费记录
			ConsumeRecord consumeRecord = new ConsumeRecord();
			consumeRecord.setUserId(recommendUser.getUserId());
			consumeRecord.setSendUserId(recommendUser.getUserId());
			consumeRecord.setReceiveUserId(company.getUserId());
			consumeRecord.setConsumeType(ConsumeType.COIN_TRANSFER_ADDCONSUMER);
			consumeRecord.setConsumeTime(new Date());
			consumeRecord.setConsumeNumber(this.bonusPayPercentage.getConsumeCoinUnitPrice());
			consumeRecord.setConsumeStatus(false);
			consumeRecord.setDescription("推荐新用户:" + user.getAccount());
			consumeRecordService.addOneConsumeRecord(consumeRecord);

			// 返回新增用户信息
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("用户添加成功");
			retMsg.setSuccess(true);
			return retMsg;

		} catch (Exception e) {
			throw new RuntimeException("用户添加失败");
		}
	}

	// 删除用户
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional
	public RetMsg deleteUser(User user) {
		String account = user.getAccount();
		// account值为空或者account用户不存在，抛出异常
		if ((account == null) || (this.userService.findByAccount(account) == null)) {
			throw new RuntimeException("当前用户不存在");
		}

		try {
			this.userService.delete(user);
			RetMsg retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(account);
			retMsg.setMessage("用户删除成功");
			retMsg.setSuccess(true);
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("用户删除失败");
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
			retMsg.setMessage("用户查询成功");
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
		String loginPassword = updatePaymentPasswordRequest.getLoginPassword();
		String oldPaymentPassword = updatePaymentPasswordRequest.getOldPaymentpassword();
		String newPaymentPassword = updatePaymentPasswordRequest.getNewPaymentPassword();
		String phone = updatePaymentPasswordRequest.getPhone();

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在");

		if (!(MD5Util.encrypeByMd5(loginPassword)).equals(user.getPassword()))
			throw new RuntimeException("登录密码输入有误");

		if (!(MD5Util.encrypeByMd5(oldPaymentPassword)).equals(user.getPaymentPassword()))
			throw new RuntimeException("原支付密码输入错误");

		if (!phone.equals(user.getUserPhone()))
			throw new RuntimeException("电话号码输入有误");

		try {
			user.setPaymentPassword(MD5Util.encrypeByMd5(newPaymentPassword));

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(UserUtil.UserToUserVO(user));
			retMsg.setMessage("支付密码修改成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("支付密码修改失败");
		}
	}

}
