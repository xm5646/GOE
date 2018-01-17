package com.project.goe.projectgeodbserver.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.service.EarningService;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.TouchType;
import com.project.goe.projectgeodbserver.util.CheckUtil;

@Service
public class EarnServerSchedul {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PerformanceService performanceService;
	
	@Autowired
	private UserCreateRecordService userCreateRecordService;
	
	@Autowired
	private EarningService earningService;
	
	/**
	 * 传递
	 * @param parentId
	 * @param type
	 * @param newuser
	 * @return
	 */
	public String mainUpdateUser(Long parentId,String type,User newuser) {
		User puser = userService.getUserById(parentId);
		if (puser != null) {
			if ("A".equals(type)) {
				if (puser.getDepartmentA() != 0) {
					return "A的下级节点已存在";
				} else {
					saveuser(newuser, puser, type);
				}
			} else if ("B".equals(type)) {
				if (puser.getDepartmentB() != 0) {
					return "B的下级节点已存在";
				} else {
					saveuser(newuser, puser, type);
				}
			}
		}
		return "测试数据插入成功" + type;
	}
	
	/**
	 * 更新收益表 传递新创建的用户ID
	 * @param createId
	 * @return
	 */
	public String mainUpdatePerformance(Long createId) {
		//创建用户更新收益表，记录是否更新收益数据，如果没有更新数据更新收益表并记录收益表更新状态
		UserCreatereRecord ucr  = saveUserCreateEntity(createId);
		if (ucr!=null && !ucr.isStatus()) {
			updateUserPerformance(createId);
			ucr.setStatus(true);
			userCreateRecordService.save(ucr);
		}else {
			return "此用户已更新过收益";
		}
		return "更新用户收益成功";
	}
	
	/**
	 * 每天计算收益
	 */
	public void mainComperBonuspaylist() {
		//取得所有收益表和用户表数据
		Iterable<User> userlist = this.userService.getAll();
		//这里面的数据要根据当期未过期的时间数据
		Iterable<Earning> earns = earningService.getAll();
		
//		Map<Long,User> userMap = new HashMap<Long,User>();
		Map<Long,Earning> earnsMap = new HashMap<Long,Earning>();
		
		for (Earning earn : earns) {
			earnsMap.put(earn.getUserid(), earn);
		}
		List<BonusPayList> buslist = new ArrayList<BonusPayList>();
		List<User> userupdate = new ArrayList<User>();
		for (User user : userlist) {
			// userMap.put(user.getUserId(), user);
			if (updateComperBonuspayForEarning(earnsMap.get(user.getUserId()))) {
				BonusPayList bus = CheckUtil.sendBonusPaylist(user, earnsMap.get(user.getUserId()));
				if (bus != null) {
					buslist.add(bus);
					userupdate.add(user);
				}
			}
		}
		CheckUtil.printList(buslist);
		CheckUtil.printList(userupdate);

	}
	
	/**
	 * 如果业绩是累计的且累计的剩余次数大于1 使累计次数-1
	 * @param earn
	 */
	private boolean updateComperBonuspayForEarning(Earning earn) {
		if(earn!=null) {
			if (TouchType.ADDITION.equals(earn.getTouchType())) {
				if (earn.getSurplusNumber()>0) {
					earn.setSurplusNumber(earn.getSurplusNumber()-1);
					return true;
				}else {
					return false;
				}
			}else {
				return true;
			}
		}else {
			return false;
		}

	}
	
	/**
	 * 创建新用户，同时更新上级用户的A B节点ID
	 * @param newuser
	 * @param puser
	 * @param type
	 */
	private void saveuser(User newuser,User puser,String type) {
		newuser.setParentId(puser.getUserId());
		newuser.setWeightCode(puser.getWeightCode()+1);
		this.userService.save(newuser);
		if ("A".equals(type)) {
			puser.setDepartmentA(newuser.getUserId());
		}else if ("B".equals(type)) {
			puser.setDepartmentB(newuser.getUserId());
		}
		this.userService.save(puser);
		savePer(newuser.getUserId());
		//用户创建完成后更新用户收益信息
	}
	
	/**
	 * 在创建用户的时候同时创建业绩表默认数据
	 * @param id
	 */
	public void savePer(long id) {
		Performance per = performanceService.getOne(id);
		if (per==null) {
			per = new Performance();
			Date newdate = new Date();
			per.setUserId(id);
			per.setCreateTime(newdate);
			per.setUpdateTime(newdate);
			performanceService.save(per);
		}
	}
	
	private void updateUserPerformance(Long userid) {
		//取得所有收益表和用户表数据
		Iterable<User> userlist = this.userService.getAll();
		Iterable<Performance> pers = performanceService.getAll();
		
		Map<Long,User> userMap = new HashMap<Long,User>();
		Map<Long,Performance> perMap = new HashMap<Long,Performance>();
		for (User user : userlist) {
			userMap.put(user.getUserId(), user);
		}
		for (Performance per : pers) {
			perMap.put(per.getUserId(), per);
		}
		//获得需要更新的业绩表数据
		List<Performance> perlist = CheckUtil.computePer(userid, userMap, perMap);
		for (Performance performance : perlist) {
			//更新收益表数据
			performanceService.save(performance);
		}
		//发生变化的业绩更新收益表
	}
	
	/**
	 * 在创建用户的时候同时创建用户更新表默认数据
	 * @param id
	 */
	private UserCreatereRecord saveUserCreateEntity(long userId) {
		UserCreatereRecord ucr = userCreateRecordService.getUserCreatereRecordByUserId(userId);
		savePer(userId);
		if (ucr==null) {
			ucr = new UserCreatereRecord();
			Date newdate = new Date();
			ucr.setUserId(userId);
			ucr.setStatus(false);
			ucr.setCreateTime(newdate);
			ucr.setUpdateTime(newdate);
			userCreateRecordService.save(ucr);
		}
		return ucr;
	}
}
