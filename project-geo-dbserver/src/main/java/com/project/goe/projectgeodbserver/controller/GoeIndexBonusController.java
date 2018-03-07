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

@RestController
@RequestMapping("/goeIndexBonus")
@CrossOrigin
public class GoeIndexBonusController {

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

	// 基于用户名或昵称分页查询奖金信息:account或nickName查询（按时间降序排序）
	@GetMapping("/findBonusPageByAccountOrNickName")
	public RetMsg findBonusPageByAccountOrNickName(UserTypeQueryRequest userTypeQueryRequest,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "payTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		Sort sort = null;
		RetMsg retMsg = null;
		List<Page<BonusPayList>> pageList = null;

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

			retMsg = new RetMsg();
			Pageable pageable = new PageRequest(pageNum, size, sort);
			
			pageList= new ArrayList<Page<BonusPayList>>();
			
			if(type.equals("account")) {
				User u = this.userService.findByAccount(value);
				
				if(null == u)
					throw new RuntimeException("用户不存在");
				
				Page<BonusPayList> bonusPayListPage = this.bonusPayListService.findBonusPageByUserId(u.getUserId(), pageable);
				pageList.add(bonusPayListPage);
			}else if(type.equals("nickName")) {
				List<User> userList = this.userService.findByNickName(value);
				
				if(null == userList)
					throw new RuntimeException("用户不存在");
				
				for(User u : userList) {
					Page<BonusPayList> bonusPayListPage = this.bonusPayListService.findBonusPageByUserId(u.getUserId(), pageable);
					pageList.add(bonusPayListPage);
				}
			}else 
				throw new RuntimeException("类型错误");
			

			retMsg.setCode(200);
			retMsg.setData(pageList);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}
	}

}
