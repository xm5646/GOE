package com.project.goe.projectgeodbserver.server;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.ReconsumeRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.entity.UserCreatereRecord;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.EarningService;
import com.project.goe.projectgeodbserver.service.PerformanceService;
import com.project.goe.projectgeodbserver.service.ReconsumeRecordService;
import com.project.goe.projectgeodbserver.service.UserCreateRecordService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.TouchType;
import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;
import com.project.goe.projectgeodbserver.util.BusinessUtil;
import com.project.goe.projectgeodbserver.util.CheckUtil;
import com.project.goe.projectgeodbserver.util.MD5Util;
import com.project.goe.projectgeodbserver.util.TimeUtil;
import com.project.goe.projectgeodbserver.util.UserUtil;

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

	@Autowired
	private ReconsumeRecordService reconsumeRecordService;

	/**
	 * 传递
	 * 
	 * @param parentId
	 * @param type
	 * @param newuser
	 * @return
	 */
	public String mainUpdateUser(Long parentId, String type, User newuser) {
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
			} else if ("C".equals(type)) {
				if (puser.getDepartmentC() != 0) {
					return "B的下级节点已存在";
				} else {
					saveuser(newuser, puser, type);
				}
			}
		}
		return "测试数据插入成功";
	}

	@Transactional
	public void mainTest(long parentid, long count) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (parentid != 0 && count != 0) {
			User puser = userService.findByUserId(parentid);
			mainUpdatePerformance(puser.getUserId());
			Map<Long, Boolean> map = new HashMap<Long, Boolean>();
			map.put(puser.getUserId(), false);
			Iterable<Long> mapkey = map.keySet();
			Map<Long, Boolean> mapnew = new HashMap<Long, Boolean>();
			// testsave(newuser.getUserId(), map);
			for (int i = 0; i < count; i++) {
				for (Long userid : mapkey) {
					if (!map.get(userid)) {
						testsave(userid, map, mapnew, count);
					}
				}
				map.putAll(mapnew);
			}
		} else {
			User newuser = UserUtil.getTestUser();
			newuser.setAccount("admin");
			newuser.setPassword(MD5Util.encrypeByMd5("123456"));
			newuser.setWeightCode(1);
			userService.save(newuser);
			savePer(newuser.getUserId());
			mainUpdatePerformance(newuser.getUserId());
			Map<Long, Boolean> map = new HashMap<Long, Boolean>();
			map.put(newuser.getUserId(), false);
			Iterable<Long> mapkey = map.keySet();
			Map<Long, Boolean> mapnew = new HashMap<Long, Boolean>();
			// testsave(newuser.getUserId(), map);
			for (int i = 0; i < 1; i++) {
				for (Long userid : mapkey) {
					if (!map.get(userid)) {
						testsave(userid, map, mapnew, count);
					}
				}
				map.putAll(mapnew);
			}
		}
	}

	private void testsave(Long puserid, Map<Long, Boolean> map, Map<Long, Boolean> mapnew, long count) {
		User newuserA = UserUtil.getTestUser();
		User newuserB = UserUtil.getTestUser();

		User pwd = userService.getUserById(puserid);
		if (pwd != null) {
			if (pwd.getWeightCode() == 1) {
				User newuserC = UserUtil.getTestUser();
				String c = mainUpdateUser(puserid, "C", newuserC);
				if ("测试数据插入成功".equals(c)) {
					mainUpdatePerformance(newuserC.getUserId());
					mapnew.put(newuserC.getUserId(), false);
				}
			}
			String a = mainUpdateUser(puserid, "A", newuserA);
			String b = mainUpdateUser(puserid, "B", newuserB);
			if ("测试数据插入成功".equals(a)) {
				mainUpdatePerformance(newuserA.getUserId());
				mapnew.put(newuserA.getUserId(), false);
			}
			if ("测试数据插入成功".equals(b)) {
				mainUpdatePerformance(newuserB.getUserId());
				mapnew.put(newuserB.getUserId(), false);
			}
		}
		map.put(puserid, true);
	}

	/**
	 * 更新收益表 传递新创建的用户ID
	 * 
	 * @param createId
	 * @return
	 */
	public String mainUpdatePerformance(Long createId) {
		// 创建用户更新业绩表，记录是否更新业绩数据，如果没有更新数据更新业绩表并记录业绩表更新状态
		UserCreatereRecord ucr = saveUserCreateEntity(createId);
		if (ucr != null && !ucr.isStatus()) {
			// 在更新业绩的时候判断是否触发新增或者累计收益
			updateUserPerformance(createId);
			ucr.setStatus(true);
			userCreateRecordService.save(ucr);
		} else {
			return "此用户已更新过业绩";
		}
		return "更新用户业绩成功";
	}

	/**
	 * 每天计算收益
	 */
	@Transactional
	public void mainComperBonuspaylist() {
		// 取得所有收益表和用户表数据
		Iterable<User> userlist = this.userService.getAll();
		// 这里面的数据要根据当期未过期的时间数据
		Iterable<Earning> earns = earningService.getAll();

		// Map<Long,User> userMap = new HashMap<Long,User>();
		Map<Long, Earning> earnsMap = new HashMap<Long, Earning>();

		// 这里由于时间关系不操作数据库了，直接代码实现时间的处理
        // 迭代用户表,获取有效的收益记录
        getLastVaildEarningByUserID(earns, earnsMap);

		List<BonusPayList> buslist = new ArrayList<BonusPayList>();
		List<User> userupdate = new ArrayList<User>();
		for (User user : userlist) {
			// userMap.put(user.getUserId(), user);
			// 2018-12-14 取消判断用户是否通过考核
			if (user!=null && updateComperBonuspayForEarning(earnsMap.get(user.getUserId()))) {
				BonusPayList bus = CheckUtil.sendBonusPaylist(user, earnsMap.get(user.getUserId()));
				if (bus != null) {
					buslist.add(bus);
					//发放奖金自动扣除重销奖金,并设置考核状态为true
					user.setAssessStatus(true);
					userupdate.add(user);
				}
			}
		}
		bonusPayListService.saveAll(buslist);
		userService.saveAll(userupdate);
	}
	
	
	/**
	 * 每天检查新用户30天内是否完善资料,如未完成资料则设置激活状态为false
	 */
	public void mainCheckNewUserInit() {
		Iterable<User> userlist = this.userService.getAll();
		if (userlist != null) {
			for (User user : userlist) {
				if (user != null) {
					//取出未完善资料的用户
					if (!user.isPasswordReset()) {
						//检查当前时间是否大于用户创建时间30天,如果是就修改激活状态为false
						if ( TimeUtil.getDiscrepantDays(user.getCreateTime(), new Date()) > 30) {
							user.setUserStatus(false);
							this.userService.save(user);
						}
					}
				}
			}
		}
	}

	/**
	 * 每天定时检查考核状态
	 */
	public void mainAssessInspect() {
		// 检查每个用户的考核时间是否是考核日
		// 如果为考核日，查看消费记录表是否有重销 考核日期加30天
		// 有考核状态为true，没有考核状态为false
		Iterable<User> userlist = this.userService.getAll();
		if (userlist != null) {
			for (User user : userlist) {
				if (user != null) {
					// 默认在考核的下一天执行,如果前一天是考核时间则更新考核状态和考核时间
					boolean issame = TimeUtil.getTimeSameDay(user.getAssessDate(), TimeUtil.addDay(new Date(), -1));
					// 需要判断考核
					if (issame) {
						user.setAssessDate(TimeUtil.addDay(user.getAssessDate(), 30));
						List<ReconsumeRecord> rrlist = reconsumeRecordService.findByUserId(user.getUserId());
						boolean isassess = false;
						for (ReconsumeRecord r : rrlist) {
							if (TimeUtil.getTimeSameDay(r.getCreateTime(), TimeUtil.addDay(new Date(), -1))) {
								isassess = true;
							}
						}
						user.setAssessStatus(isassess); // or set assessStatus ture;
						Performance p = performanceService.findByUserId(user.getUserId());
						p.setAddDepartAcount(0);
						p.setAddDepartBcount(0);
						p.setAddDepartCcount(0);
						userService.save(user);
						performanceService.save(p);
					}
				}
			}
		}
	}

    /**
     *  根据用户Id获取可以发放奖金的有效收益记录
     * @param earns 所有收益记录的集合
     * @param earnsMap 有效的收益记录字典
     */
	private void getLastVaildEarningByUserID(Iterable<Earning> earns, Map<Long, Earning> earnsMap) {
	    // 获取每个用户的最后一条收益记录
        Map<Long, Earning> lastEarns = new HashMap<Long, Earning>();
	    for (Earning earning : earns) {
	        Earning mapOfEarn = lastEarns.get(earning.getUserid());
	        if (null == mapOfEarn) {
	            lastEarns.put(earning.getUserid(), earning);
            } else {
	            if (mapOfEarn.getEarningId() < earning.getEarningId()) {
	                lastEarns.put(earning.getUserid(), earning);
                }
            }
        }
        // 迭代每个用户的最后一条记录, 判断是否有效
	    for (Long userid : lastEarns.keySet()) {
	        Earning earning = lastEarns.get(userid);
	        if (null != earning) {
	            if (TouchType.ACCUMULATION.equals(earning.getTouchType())) {
                    // 如果是累积业绩奖励,判断是否还有剩余未发放天数
	                if (earning.getSurplusNumber() > 0 ) {
                        earnsMap.put(earning.getUserid(), earning);
                    }
                } else if (TouchType.ADDITION.equals(earning.getTouchType())) {
	                // 如果是新增奖励,判断:
                    // 1. 是否超过发放截止日期
                    // 2. 是否超过380:380 vip9级别
                    boolean bigOrSameForVip9 = BusinessUtil.isBigBusSame(earning.getUserLevel(), UserLevel.ADVANCED_DIRECTOR);
                    if (null != earning.getEndTime() && earning.getEndTime().after(new Date()) && bigOrSameForVip9) {
                        earnsMap.put(earning.getUserid(), earning);
                    }

                }
            }
        }
    }

    // 该方法已经由getLastVaildEarningByUserID替代
	private void conductEarnTime(Iterable<Earning> earns, Map<Long, Earning> earnsMap) {
		for (Earning earn : earns) {
			Earning em = earnsMap.get(earn.getUserid());
			if (em != null) {
				// 得到可用使用的业绩对象
				Earning eu = getUsedEarning(earn, em);
				if (eu != null) {
					earnsMap.put(eu.getUserid(), eu);
				}

			} else {
				if (TouchType.ADDITION.equals(earn.getTouchType())) {
                    // 判断用户级别是否超过 高级总V监380:380
                    boolean bigOrSameForVip9 = BusinessUtil.isBigBusSame(earn.getUserLevel(), UserLevel.ADVANCED_DIRECTOR);
					if (earn.getEndTime() != null && earn.getEndTime().after(new Date()) && bigOrSameForVip9) {
						earnsMap.put(earn.getUserid(), earn);
					}
				} else {
					 if (earn.getSurplusNumber() > 0) {
					    earnsMap.put(earn.getUserid(), earn);
					 }
				}
			}
		}
	}

	// 得到可用Earning对象
	// 这里是复杂的业务逻辑
	private Earning getUsedEarning(Earning earn, Earning em) {
		// earn 是新要插入的数据 em是已经插入MAP的数据
		// 如果存在userID对应的业绩对象，比较用户级别
		// if (TouchType.ADDITION.equals(earn.getTouchType()) &&
		// TouchType.ADDITION.equals(em.getTouchType())) {
		// //新增 判断结束时间是不是已经结束
		// if (earn.getEndTime()!=null && earn.getEndTime().after(new Date())) {
		// return BusinessUtil.isBigBus(earn, em);
		// }
		// }else if (TouchType.ADDITION.equals(earn.getTouchType()) &&
		// TouchType.ACCUMULATION.equals(em.getTouchType())) {
		// //新增 return null 或者return em都可以，但是em 已经在map中，直接是替换了
		// return null;
		// }else if (TouchType.ACCUMULATION.equals(earn.getTouchType()) &&
		// TouchType.ADDITION.equals(em.getTouchType())) {
		// //累计
		// if (earn.getSurplusNumber()>0) {
		// return earn;
		// }
		// }else if (TouchType.ACCUMULATION.equals(earn.getTouchType()) &&
		// TouchType.ACCUMULATION.equals(em.getTouchType())) {
		// //累计
		// if (earn.getSurplusNumber()>0) {
		// return BusinessUtil.isBigBus(earn, em);
		// }
		// }
		// return null;

		if (earn.getEarningId() > em.getEarningId()) {
			return earn;
		} else {
			return em;
		}
	}

	/**
	 * 如果业绩是累计的且累计的剩余次数大于1 使累计次数-1
	 * 
	 * @param earn
	 */
	private boolean updateComperBonuspayForEarning(Earning earn) {
		if (earn != null) {
			if (TouchType.ACCUMULATION.equals(earn.getTouchType())) {
				if (earn.getSurplusNumber() > 0) {
					earn.setSurplusNumber(earn.getSurplusNumber() - 1);
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}

	}

	/**
	 * 创建新用户，同时更新上级用户的A B节点ID
	 * 
	 * @param newuser
	 * @param puser
	 * @param type
	 */
	private void saveuser(User newuser, User puser, String type) {
		newuser.setParentId(puser.getUserId());
		newuser.setWeightCode(puser.getWeightCode() + 1);
		this.userService.save(newuser);
		if ("A".equals(type)) {
			puser.setDepartmentA(newuser.getUserId());
		} else if ("B".equals(type)) {
			puser.setDepartmentB(newuser.getUserId());
		} else if ("C".equals(type)) {
			puser.setDepartmentC(newuser.getUserId());
		}
		this.userService.save(puser);
		savePer(newuser.getUserId());
		// 用户创建完成后更新用户收益信息
		// updateUserPerformance(newuser.getUserId());
	}

	/**
	 * 在创建用户的时候同时创建业绩表默认数据
	 * 
	 * @param id
	 */
	public void savePer(long id) {
		Performance per = performanceService.getOne(id);
		if (per == null) {
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
	 * 
	 * @param userid
	 */
	public void updateUserPerformance(Long userid) {
		// 取得所有收益表和用户表数据
		Iterable<User> userlist = this.userService.getAll();
		Iterable<Performance> pers = performanceService.getAll();

		Map<Long, User> userMap = new HashMap<Long, User>();
		Map<Long, Performance> perMap = new HashMap<Long, Performance>();
		Map<Long, Boolean> isHaveTotalEarningMap = new HashMap<Long, Boolean>();
		for (User user : userlist) {
			userMap.put(user.getUserId(), user);
			// 取得可发放的累计收益
			isHaveTotalEarningMap.put(user.getUserId(), earningService.isHaveTotalEarning(user.getUserId()));
		}
		for (Performance per : pers) {
			perMap.put(per.getUserId(), per);
		}
		// 获得需要更新的业绩表数据
		List<Performance> perlist = CheckUtil.computePer(userid, userMap, perMap, isHaveTotalEarningMap);
		// 在checkUtil中已经做了为空判断，这里不需要做
		for (Performance performance : perlist) {
			// 更新业绩表数据
			performanceService.save(performance);
		}

		Iterable<Earning> earns = earningService.getAll();
		// 将获得的对象封装成MAP
		Map<String, Earning> earnMap = new HashMap<String, Earning>();
		for (Earning earn : earns) {
			String key = CheckUtil.getEarnKey(earn);
			if (key != null && key.length() > 0) {
				earnMap.put(key, earn);
			}
		}

		// 发生变化的业绩更新收益表
		List<Earning> earnList = CheckUtil.userEarning(earnMap, perlist);

		if (earnList != null && earnList.size() > 0) {

			// 清除不需要保存的收益表信息
			CheckUtil.computeEarn(earnMap, earnList);
			for (Earning earn : earnList) {
				if (earn != null) {
					// 在这里判断如果是第一次触发累计4：4更新user表考核时间
					User upUser = userService.getUserById(earn.getUserid());
					if (CheckUtil.theFirstEarning(earn)) {
						// 更新考核日期和考核状态
						if (upUser != null) {
							CheckUtil.updateUserForFirstEarning(upUser);
							userService.save(upUser);
						}
					}
					// 如果是累计考核更新考核状态
					if (TouchType.ACCUMULATION.equals(earn.getTouchType())) {
						if (BusinessUtil.isBigBus(earn.getUserLevel(), upUser.getUserLevel())) {
							if (upUser != null) {
								upUser.setUserLevel(earn.getUserLevel());
								userService.save(upUser);
							}
						}
					}

					earningService.save(earn);
				}
			}
		}
	}

	/**
	 * 在创建用户的时候同时创建用户更新表默认数据
	 * 
	 * @param userId
	 */
	private UserCreatereRecord saveUserCreateEntity(long userId) {
		UserCreatereRecord ucr = userCreateRecordService.getUserCreatereRecordByUserId(userId);
		savePer(userId);
		if (ucr == null) {
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
