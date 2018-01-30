package com.project.goe.projectgeodbserver.controller;

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

@CrossOrigin
@RestController
@RequestMapping("/bonus")
public class BonusPayListController {

	@Autowired
	private BonusPayListService bonusPayListService;
	
	@Autowired
	private UserService userService;
	
	//分页查询所有用户奖金
	@GetMapping("/findAllBonusByPage")
	public Page<BonusPayList> findAllBonusPayRecordBySort(
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

			return this.bonusPayListService.findAllBonusBySort(pageable);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	
	//分页查询单个用户（按时间降序排序）
	@GetMapping("/findBonusPageByAccount")
	public Page<BonusPayList> findBonusPageByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "payTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		if(null == account)
			throw new RuntimeException("用户名不能为空!");
		
		User user = this.userService.findByAccount(account);
		if(null == user)
			throw new RuntimeException("用户名不存在!");
		
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
			throw new RuntimeException("查询失败!");
		}
	}

}
