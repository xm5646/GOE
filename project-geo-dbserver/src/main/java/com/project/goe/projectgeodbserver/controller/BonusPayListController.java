package com.project.goe.projectgeodbserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;
import com.project.goe.projectgeodbserver.viewentity.UserTypeQueryRequest;

@CrossOrigin
@RestController
@RequestMapping("/bonus")
public class BonusPayListController {

	@Autowired
	private BonusPayListService bonusPayListService;

	@Autowired
	private UserService userService;

	// 分页查询所有用户奖金
	@GetMapping("/findAllBonusByPage")
	public Page<BonusPayList> findAllBonusPayRecordBySort(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {

		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.bonusPayListService.findAllBonusBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	// 分页查询单个用户（按时间降序排序）
	@GetMapping("/findBonusPageByAccount")
	public Page<BonusPayList> findBonusPageByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "payTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		if (null == account)
			throw new RuntimeException("用户名不能为空");

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户名不存在");

		BonusPayList bonusPayList = new BonusPayList();
		bonusPayList.setUserId(user.getUserId());

		try {
			Sort sort = null;

			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			return this.bonusPayListService.findBonusPageByAccount(bonusPayList, pageable);
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 分页查询单个用户:account或nickName查询（按时间降序排序）
	@GetMapping("/findBonusPageByAccountOrNickName")
	public RetMsg findBonusPageByAccountOrNickName(UserTypeQueryRequest userTypeQueryRequest,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "payTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		String type = userTypeQueryRequest.getType();
		String value = userTypeQueryRequest.getValue();
		User user = null;
		List<User> userList = null;
		RetMsg retMsg = null;

		if (type.equals("account"))
			user = this.userService.findByAccount(value);
		else if(type.equals("nickName"))
			userList = this.userService.findByNickName(value);
		else
			throw new RuntimeException("类型参数有误");

		if (null == user && null == userList)
			throw new RuntimeException("用户不存在");

		try {
			List<Page<BonusPayList>> pageList = new ArrayList<Page<BonusPayList>>();
			retMsg = new RetMsg();
			// 使用account查询，用户存在
			if (null != user) {
				BonusPayList bonusPayList = new BonusPayList();
				bonusPayList.setUserId(user.getUserId());
				pageList.add(bonusPayPageByUserId(bonusPayList, pageNum, size, order, keyword));
			} else { // 使用nickName查询
				for (User u : userList) {
					BonusPayList bonusPayList = new BonusPayList();
					bonusPayList.setUserId(u.getUserId());
					pageList.add(bonusPayPageByUserId(bonusPayList, pageNum, size, order, keyword));
				}
			}

			retMsg.setCode(200);
			retMsg.setData(pageList);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	private Page<BonusPayList> bonusPayPageByUserId(BonusPayList bonusPayList, int pageNum, int size, String order,
			String keyword) {

		Sort sort = null;

		if (order.equals("asc"))
			sort = new Sort(Direction.ASC, keyword);
		else
			sort = new Sort(Direction.DESC, keyword);

		Pageable pageable = new PageRequest(pageNum, size, sort);

		return this.bonusPayListService.findBonusPageByAccount(bonusPayList, pageable);

	}

}
