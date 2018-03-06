package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.UserUtil;
import com.project.goe.projectgeodbserver.viewentity.ChargeBonusAndProductCoin;
import com.project.goe.projectgeodbserver.viewentity.ChargeConsumeCoin;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserTypeQueryRequest;

@RestController
@RequestMapping("/goeUserManagement")
@CrossOrigin
public class GoeUserManagementController {
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
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		try {
			Sort sort = null;
			RetMsg retMsg = new RetMsg();

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);
			
			retMsg.setCode(200);
			retMsg.setData(this.userService.findAllUserBySort(pageable));
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);
			
			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("信息查询失败");
		}
	}

	// 基于用户账户名称或昵称，分页模糊查询
	/*
	@GetMapping("/findUsersByNickNameOrAccount")
	public RetMsg findUsersByNickNameOrUserId(UserTypeQueryRequest userTypeQueryRequest,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		String type = userTypeQueryRequest.getType();
		String value = userTypeQueryRequest.getValue();
		User user = null;
		List<User> userList = null;
		RetMsg retMsg = null;
		
		if(null == type || null == value)
			throw new RuntimeException("参数类型或参数值不能为空");
		
		if (type.equals("account")) {
			user = this.userService.findByAccount(value);
		}
		else if(type.equals("nickName"))
			userList = this.userService.findByNickName(value);
		else
			throw new RuntimeException("类型参数有误");
		
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);
			System.out.println(name);

			return this.userService.findUsersByNickNameOrUserId(name, pageable);
		} catch (Exception e) {
			throw new RuntimeException("信息查询失败");
		}
	}
	*/

	// 更新用户信息
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

	/**************** 用户充值 *******************/
	// 充值报单币
	@PostMapping("/chargeReconsumeCoin")
	public RetMsg chargeReconsumeCoin(ChargeConsumeCoin chargeConsumeCoin) {
		RetMsg retMsg = null;
		// 充值用户账号
		String account = chargeConsumeCoin.getAccount();
		// 管理员的支付密码
		String paymentPassword = chargeConsumeCoin.getPaymentPassword();
		// 充值额度
		double consumeCoin = chargeConsumeCoin.getConsumeCoin();

		if (null == account)
			throw new RuntimeException("充值用户名不能为空");

		User adminUser = this.userService.findByAccount("administrator");
		if (null == adminUser)
			throw new RuntimeException("管理员账户不存在");

		User chargeUser = this.userService.findByAccount(account);
		if (null == chargeUser)
			throw new RuntimeException("充值用户不存在");

		String paymentPassword2 = adminUser.getPaymentPassword();
		if (!paymentPassword2.equals(MD5Util.encrypeByMd5(paymentPassword)))
			throw new RuntimeException("支付密码输入有误");

		if (consumeCoin < 0 || consumeCoin > Double.MAX_VALUE)
			throw new RuntimeException("充值金额不合法");

		// 更新管理员用户和充值用户信息
		try {
			adminUser.setConsumeCoin(adminUser.getConsumeCoin() - consumeCoin);
			chargeUser.setConsumeCoin(chargeUser.getConsumeCoin() + consumeCoin);
			this.userService.save(adminUser);
			this.userService.save(chargeUser);

			// 新增一条消费信息
			ConsumeRecord consumeRecord = new ConsumeRecord();
			consumeRecord.setConsumeNumber(consumeCoin);
			consumeRecord.setConsumeStatus(true);
			consumeRecord.setConsumeTime(new Date());
			consumeRecord.setConsumeType(ConsumeType.COMPANY_TRANSFER_CONIN);
			consumeRecord.setDescription(ConsumeType.COMPANY_TRANSFER_CONIN);
			consumeRecord.setReceiveUserId(chargeUser.getUserId());
			consumeRecord.setSendUserId(adminUser.getUserId());
			consumeRecord.setUserId(adminUser.getUserId());

			this.consumeRecordService.addOneConsumeRecord(consumeRecord);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(consumeCoin);
			retMsg.setMessage("充值成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("充值失败");
		}

	}

	// 充值奖金和产品积分
	@PostMapping("/chargeBonusAndProductCoin")
	public RetMsg chargeBonusAndProductCoin(ChargeBonusAndProductCoin chargeBonusAndProductCoin) {
		// 充值账户
		String account = chargeBonusAndProductCoin.getAccount();
		// 管理员支付密码
		String paymentPassword = chargeBonusAndProductCoin.getPaymentPassword();
		// 充值奖金
		double bonus = chargeBonusAndProductCoin.getBonus();
		// 充值产品积分
		double productCoin = chargeBonusAndProductCoin.getProductCoin();
		BonusPayList bonusPayList = null;
		RetMsg retMsg = null;

		if (null == account)
			throw new RuntimeException("充值用户名不能为空");

		User adminUser = this.userService.findByAccount("administrator");
		if (null == adminUser)
			throw new RuntimeException("管理员账户不存在");

		User chargeUser = this.userService.findByAccount(account);
		if (null == chargeUser)
			throw new RuntimeException("充值用户不存在");

		String paymentPassword2 = adminUser.getPaymentPassword();
		if (!paymentPassword2.equals(MD5Util.encrypeByMd5(paymentPassword)))
			throw new RuntimeException("支付密码输入有误");

		if (bonus < 0 || bonus > Double.MAX_VALUE || productCoin < 0 || productCoin > Double.MAX_VALUE)
			throw new RuntimeException("充值金额不合法");

		try {
			// 新增一条奖金发放记录
			Date date = new Date();
			bonusPayList = new BonusPayList();
			bonusPayList.setBonusNumber(bonus);
			bonusPayList.setCreateTime(date);
			bonusPayList.setManageCost(0f);
			bonusPayList.setPayTime(date);
			bonusPayList.setProductCoinNumber(productCoin);
			bonusPayList.setTotalMoney(0f);
			bonusPayList.setUserId(chargeUser.getUserId());
			this.bonusPayListService.save(bonusPayList);

			// 更新管理员和充值用户信息
			adminUser.setBonusCoin(adminUser.getBonusCoin() - bonus);
			adminUser.setProductCoin(adminUser.getProductCoin() - productCoin);

			chargeUser.setBonusCoin(chargeUser.getBonusCoin() + bonus);
			chargeUser.setProductCoin(chargeUser.getProductCoin() + productCoin);

			this.userService.save(adminUser);
			this.userService.save(chargeUser);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(new Double[] { bonus, productCoin });
			retMsg.setMessage("充值成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("充值失败");
		}
	}

}
