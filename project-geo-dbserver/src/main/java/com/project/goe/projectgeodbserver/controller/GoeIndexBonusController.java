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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.util.BonusUtil;
import com.project.goe.projectgeodbserver.viewentity.BonusVO;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

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
	public RetMsg findAllBonusPayRecordBySort(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "payTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		Sort sort = null;
		RetMsg retMsg = null;

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<BonusPayList> bonusPage = this.bonusPayListService.findAllBonusBySort(pageable);
			Page<BonusVO> bonusVOPage = bonusPage.map(new Converter<BonusPayList, BonusVO>() {

				@Override
				public BonusVO convert(BonusPayList bonusPayList) {
					long userId = bonusPayList.getUserId();
					User user = userService.findByUserId(userId);
					bonusPayList.setAccount(user.getAccount());

					return BonusUtil.bonusToBonusVO(bonusPayList);
				}

			});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(bonusVOPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	// 基于用户名称分页查询奖金信息:account查询（按时间降序排序）
	@GetMapping("/findBonusPageByAccount")
	public RetMsg findBonusPageByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "payTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		Sort sort = null;
		RetMsg retMsg = null;
		
		if(null == account)
			throw new RuntimeException("用户名不能为空");
		
		User user = this.userService.findByAccount(account);
		if(null == user)
			throw new RuntimeException("用户不存在");
		
		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			retMsg = new RetMsg();
			Pageable pageable = new PageRequest(pageNum, size, sort);
			
			BonusPayList bonusPayList = new BonusPayList();
			bonusPayList.setUserId(user.getUserId());
			
			Page<BonusPayList> bonusPage = this.bonusPayListService.findBonusPageByAccount(bonusPayList, pageable);
			Page<BonusVO> bonusVOPage = bonusPage.map(new Converter<BonusPayList, BonusVO>() {

				@Override
				public BonusVO convert(BonusPayList bonusPayList) {
					long userId = bonusPayList.getUserId();
					User user = userService.findByUserId(userId);
					bonusPayList.setAccount(user.getAccount());

					return BonusUtil.bonusToBonusVO(bonusPayList);
				}

			});
			
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(bonusVOPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}
	}

}
