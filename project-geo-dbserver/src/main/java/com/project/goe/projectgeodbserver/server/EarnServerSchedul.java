package com.project.goe.projectgeodbserver.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.EarningService;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.TouchType;
import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;
import com.project.goe.projectgeodbserver.util.BusinessUtil;
import com.project.goe.projectgeodbserver.util.CheckUtil;
import com.project.goe.projectgeodbserver.util.TimeUtil;

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
	
	@Autowired
	private BonusPayListService bonusPayListService;
	
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
		//创建用户更新业绩表，记录是否更新业绩数据，如果没有更新数据更新业绩表并记录业绩表更新状态
		UserCreatereRecord ucr  = saveUserCreateEntity(createId);
		if (ucr!=null && !ucr.isStatus()) {
			//在更新业绩的时候判断是否触发新增或者累计收益
			updateUserPerformance(createId);
			ucr.setStatus(true);
			userCreateRecordService.save(ucr);
		}else {
			return "此用户已更新过业绩";
		}
		return "更新用户业绩成功";
	}
	
	/**
	 * 每天计算收益
	 */
	@Transactional
	public void mainComperBonuspaylist() {
		//取得所有收益表和用户表数据
		Iterable<User> userlist = this.userService.getAll();
		//这里面的数据要根据当期未过期的时间数据
		Iterable<Earning> earns = earningService.getAll();
		
//		Map<Long,User> userMap = new HashMap<Long,User>();
		Map<Long,Earning> earnsMap = new HashMap<Long,Earning>();
		
		//这里由于时间关系不操作数据库了，直接代码实现时间的处理
		conductEarnTime(earns, earnsMap);
		
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
		bonusPayListService.saveAll(buslist);
		userService.saveAll(userupdate);
	}
	
	private void conductEarnTime(Iterable<Earning> earns,Map<Long,Earning> earnsMap) {
		for (Earning earn : earns) {
			Earning em = earnsMap.get(earn.getUserid());
			if (em!=null) {
				//得到可用使用的业绩对象
				Earning eu = getUsedEarning(earn, em);
				if (eu!=null) {
					earnsMap.put(eu.getUserid(), eu);
				}
				
			}else {
				if (TouchType.ADDITION.equals(earn.getTouchType())) {
					if (earn.getEndTime()!=null && earn.getEndTime().after(new Date())) {
						earnsMap.put(earn.getUserid(), earn);
					}
				}else {
					if (earn.getDayMoney()>0) {
						earnsMap.put(earn.getUserid(), earn);
					}
				}
			}
		}
	}
	
	//得到可用Earning对象
	//这里是复杂的业务逻辑
	private Earning getUsedEarning(Earning earn,Earning em) {
		//earn 是新要插入的数据 em是已经插入MAP的数据
		//如果存在userID对应的业绩对象，比较用户级别
		if (TouchType.ADDITION.equals(earn.getTouchType()) && TouchType.ADDITION.equals(em.getTouchType())) {
			//新增 判断结束时间是不是已经结束
			if (earn.getEndTime()!=null && earn.getEndTime().after(new Date())) {
				return BusinessUtil.isBigBus(earn, em);
			}
		}else if (TouchType.ADDITION.equals(earn.getTouchType()) && TouchType.ACCUMULATION.equals(em.getTouchType())) {
			//新增 return null 或者return em都可以，但是em 已经在map中，直接是替换了
			return null;
		}else if (TouchType.ACCUMULATION.equals(earn.getTouchType()) && TouchType.ADDITION.equals(em.getTouchType())) {
			//累计
			if (earn.getDayMoney()>0) {
				return earn;
			}
		}else if (TouchType.ACCUMULATION.equals(earn.getTouchType()) && TouchType.ACCUMULATION.equals(em.getTouchType())) {
			//累计
			if (earn.getDayMoney()>0) {
				return BusinessUtil.isBigBus(earn, em);
			}
		}
		return null;
	}
	
	/**
	 * 如果业绩是累计的且累计的剩余次数大于1 使累计次数-1
	 * @param earn
	 */
	private boolean updateComperBonuspayForEarning(Earning earn) {
		if(earn!=null) {
			if (TouchType.ACCUMULATION.equals(earn.getTouchType())) {
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
	
	/**
	 * 更加用户ID更新用户上级的所有业绩，并在所有业绩更新后判断业绩能否触发收益，并保存
	 * @param userid
	 */
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
		//在checkUtil中已经做了为空判断，这里不需要做
		for (Performance performance : perlist) {
			//更新收益表数据
			performanceService.save(performance);
		}
		//发生变化的业绩更新收益表
		List<Earning> earnList = CheckUtil.userEarning(perlist);
		Iterable<Earning> earns = earningService.getAll();
		CheckUtil.computeEarn(earns,earnList);
		if (earnList!=null && earnList.size()>0) {
			for (Earning earn : earnList) {
				earningService.save(earn);
			}
		}
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
