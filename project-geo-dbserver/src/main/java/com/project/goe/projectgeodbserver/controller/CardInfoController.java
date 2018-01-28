package com.project.goe.projectgeodbserver.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.CardInfo;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.CardInfoService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserCardInfoRequest;

@RestController
@RequestMapping("/cardInfo")
public class CardInfoController {

	@Autowired
	private UserService userService;
	@Autowired
	private CardInfoService cardInfoService;

	// 添加一条银行卡信息
	@PostMapping("/save")
	@Transactional
	public RetMsg save(@Validated UserCardInfoRequest userCardInfoRequest, BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String account = userCardInfoRequest.getAccount();
		String bankName = userCardInfoRequest.getBankName();
		String cardNo = userCardInfoRequest.getCardNo();
		String ownerName = userCardInfoRequest.getCardOwnerName();
		String phone = userCardInfoRequest.getPhone();
		// 验证用户是否存在
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在！");

		CardInfo cardInfo = new CardInfo();
		cardInfo.setBankName(bankName);
		cardInfo.setCardNumber(cardNo);
		cardInfo.setCardOwnerName(ownerName);
		cardInfo.setPhone(phone);
		
		//新增银行卡信息
		cardInfo = this.cardInfoService.save(cardInfo);

		cardInfo.setCardNumber(translateBankNo(cardNo));
		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(cardInfo);
		retMsg.setSuccess(true);

		return retMsg;
	}

	//显示银行卡最后四位
	private String translateBankNo(String cardNo) {
		if(null == cardNo)
			throw new RuntimeException("银行卡号不能为空!");
		
		int length = cardNo.length();
		String str = cardNo.substring(length - 4, length);
		StringBuffer sbuffer = new StringBuffer();
		for (int i = 0; i < 12; ++i) {
			sbuffer.append("*");
			
			if((i + 1)%4 == 0)
				sbuffer.append(" ");
		}
		
		return sbuffer.append(str).toString();
	}

	// 更新用户银行卡信息
	@PostMapping("/update")
	@Transactional
	public RetMsg update(@Validated UserCardInfoRequest userCardInfoRequest, long cardInfoId,
			BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 判断账户是否存在
		String account = userCardInfoRequest.getAccount();
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		// 判断expressId是否合法
		if ((cardInfoId < 0) || (cardInfoId > Long.MAX_VALUE))
			throw new RuntimeException("银行卡id值不合法!");

		// 查看当前银行卡信息是否存在
		CardInfo cardInfo = this.cardInfoService.findByCardInfoId(cardInfoId);
		if (null == cardInfo)
			throw new RuntimeException("用户的银行卡信息不存在!");

		// 更新银行卡信息
		cardInfo.setBankName(userCardInfoRequest.getBankName());
		cardInfo.setCardNumber(userCardInfoRequest.getCardNo());
		cardInfo.setCardOwnerName(userCardInfoRequest.getCardOwnerName());
		cardInfo.setCreateTime(new Date());
		
		this.cardInfoService.save(cardInfo);
		
		cardInfo.setCardNumber(translateBankNo(userCardInfoRequest.getCardNo()));

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(cardInfo);
		retMsg.setMessage("用户银行卡信息更新成功!");
		retMsg.setSuccess(true);

		return retMsg;
	}

	// 删除银行卡信息
	@PostMapping("/delete")
	@Transactional
	public RetMsg delete(@RequestParam("account") String account, @RequestParam("cardInfoId") long cardInfoId) {
		if (account == null || cardInfoId < 0 || cardInfoId > Long.MAX_VALUE)
			throw new RuntimeException("银行卡传递参数不合法!");

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		CardInfo cardInfo = this.cardInfoService.findByCardInfoId(cardInfoId);
		if (null == cardInfo)
			throw new RuntimeException("用户的银行卡信息不存在!");

		this.cardInfoService.delete(cardInfo);

		RetMsg retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(null);
		retMsg.setMessage("银行卡信息删除成功!");

		return retMsg;
	}

	// 分页查询所有银行卡信息(基于银行卡时间分页)
	@GetMapping("/findAllCardInfoBySort")
	public Page<CardInfo> findAllCardInfoByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {

		if (account == null)
			throw new RuntimeException("用户名不能为空!");

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.cardInfoService.findAllCardInfoBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
