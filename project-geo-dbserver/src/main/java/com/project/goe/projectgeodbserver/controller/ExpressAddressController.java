package com.project.goe.projectgeodbserver.controller;

import java.util.Date;
import java.util.List;

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

import com.project.goe.projectgeodbserver.entity.ExpressAddress;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ExpressAddressService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.ValidateErrorUtil;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserExpressAddressRequest;

@RestController
@RequestMapping("/expressAddress")
public class ExpressAddressController {
	@Autowired
	private UserService UserService;

	@Autowired
	private ExpressAddressService expressAddressService;

	// 添加一条快递地址信息
	@PostMapping("/save")
	@Transactional
	public RetMsg save(@Validated UserExpressAddressRequest userExpressAddressRequest, BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		String account = userExpressAddressRequest.getAccount();
		String receiverName = userExpressAddressRequest.getReceiverName();
		String province = userExpressAddressRequest.getProvince();
		String city = userExpressAddressRequest.getCity();
		String district = userExpressAddressRequest.getDistrict();
		String detailAddress = userExpressAddressRequest.getDetailAddress();
		String phone = userExpressAddressRequest.getPhone();
		String description = userExpressAddressRequest.getDescription();
		int defaultAddress = userExpressAddressRequest.getDefaultAddress();

		// 验证用户是否存在
		User user = this.UserService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在！");

		ExpressAddress expressAddress = new ExpressAddress();
		expressAddress.setCity(city);
		expressAddress.setCreateTime(new Date());

		// 获取用户所有收地址信息
		List<ExpressAddress> expressAddresses = this.expressAddressService.findByUserId(user.getUserId());
		// 用户第一次添加收货地址，该地址默认为收货地址
		if ((null == expressAddresses) || (0 == expressAddresses.size())) {
			expressAddress.setDefaultAddress(true);
		} else if (0 == defaultAddress) {// 非第一次添加，如果不指定该地址为默认收货地址，则该地址为非默认地址
			expressAddress.setDefaultAddress(false);
		} else if (1 == defaultAddress) {// 非第一次添加，如果指定该地址为默认收货地址，修改之前默认地址状态
			for (ExpressAddress ed : expressAddresses) {
				if (ed.isDefaultAddress()) {
					ed.setDefaultAddress(false);
					this.expressAddressService.save(ed);
					break;
				}
			}
			expressAddress.setDefaultAddress(true);
		} else {
			throw new RuntimeException("默认地址参数值不正确！");
		}

		expressAddress.setDescription(description);
		expressAddress.setDetailAddress(detailAddress);
		expressAddress.setDistrict(district);
		expressAddress.setPhone(phone);
		expressAddress.setProvince(province);
		expressAddress.setReceiverName(receiverName);
		expressAddress.setUserId(user.getUserId());

		expressAddress = this.expressAddressService.save(expressAddress);

		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(expressAddress);
		retMsg.setSuccess(true);

		return retMsg;
	}

	// 更新用户快递地址信息
	@PostMapping("/update")
	@Transactional
	public RetMsg update(@Validated UserExpressAddressRequest userExpressAddressRequest, long expressId,
			BindingResult bindingResult) {
		// 如果数据校验有误，则直接返回校验错误信息
		RetMsg retMsg = ValidateErrorUtil.getInstance().errorList(bindingResult);
		if (null != retMsg)
			return retMsg;

		// 判断账户是否存在
		String account = userExpressAddressRequest.getAccount();
		User user = this.UserService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		// 判断expressId是否合法
		if ((expressId < 0) || (expressId > Long.MAX_VALUE))
			throw new RuntimeException("地址id值不合法!");

		// 查看当前快递地址是否存在
		ExpressAddress expressAddress = this.expressAddressService.findByExpressId(expressId);
		if (null == expressAddress)
			throw new RuntimeException("用户的快递地址信息不存在!");

		// 更新快递地址信息
		expressAddress.setCity(userExpressAddressRequest.getCity());
		expressAddress.setCreateTime(new Date());

		if (0 == userExpressAddressRequest.getDefaultAddress()) {
			expressAddress.setDefaultAddress(false);
		} else if (1 == userExpressAddressRequest.getDefaultAddress()) {
			expressAddress.setDefaultAddress(true);
		} else {
			throw new RuntimeException("默认地址参数值不正确！");
		}

		expressAddress.setDescription(userExpressAddressRequest.getDescription());
		expressAddress.setDetailAddress(userExpressAddressRequest.getDetailAddress());
		expressAddress.setDistrict(userExpressAddressRequest.getDistrict());
		expressAddress.setPhone(userExpressAddressRequest.getPhone());
		expressAddress.setProvince(userExpressAddressRequest.getProvince());
		expressAddress.setReceiverName(userExpressAddressRequest.getReceiverName());

		expressAddress = this.expressAddressService.save(expressAddress);
		retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(expressAddress);
		retMsg.setMessage("用户地址信息更新成功!");
		retMsg.setSuccess(true);

		return retMsg;
	}

	// 删除地址信息
	@PostMapping("/delete")
	@Transactional
	public RetMsg delete(@RequestParam("account") String account, @RequestParam("expressId") long expressId) {
		if (account == null || expressId < 0 || expressId > Long.MAX_VALUE)
			throw new RuntimeException("删除传递参数不合法!");

		User user = this.UserService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");

		ExpressAddress expressAddress = this.expressAddressService.findByExpressId(expressId);
		if (null == expressAddress)
			throw new RuntimeException("用户的快递地址信息不存在!");

		this.expressAddressService.delete(expressAddress);

		RetMsg retMsg = new RetMsg();
		retMsg.setCode(200);
		retMsg.setData(null);
		retMsg.setMessage("快递地址删除成功!");

		return retMsg;
	}

	// 分页查询指定用户的所有地址信息
	@GetMapping("/findExpressAddressesByAccount")
	public Page<ExpressAddress> findExpressAddressesByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "5", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		
		if (account == null)
			throw new RuntimeException("用户名不能为空!");

		User user = this.UserService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在!");
		
		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.expressAddressService.findAllExpressAddressBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
