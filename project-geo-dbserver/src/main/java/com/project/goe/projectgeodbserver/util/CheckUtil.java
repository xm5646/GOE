package com.project.goe.projectgeodbserver.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.BonusPayRatio;
import com.project.goe.projectgeodbserver.entity.BusinessEntity;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.statusType.ConfigConstant;
import com.project.goe.projectgeodbserver.statusType.TouchType;
import com.project.goe.projectgeodbserver.statusType.UserLevel;

public class CheckUtil {

	@Autowired
	private BonusPayPercentage bpp;

	private static BonusPayPercentage staticBpp;

	@PostConstruct
	 public void init() {
	       this.staticBpp = bpp;
	 }

	/**
	 * 计算用户业绩 传递创建的用户ID，更新他上面所有节点的业绩
	 * 
	 * @param userid
	 * @param userMap
	 * @param perMap
	 * @return
	 */
	@Deprecated
	public static List<Performance> computePer(Long userid, Map<Long, User> userMap, Map<Long, Performance> perMap,Map<Long,Boolean>  isHaveTotalEarningMap) {
//		List<Performance> pers = new ArrayList<Performance>();
//		// 添加最下级节点的时候的用户信息
//		User u = userMap.get(userid);
//		Long pid = u.getParentId();
//		int weightCode = u.getWeightCode();
//		// for循环内终止条件应该是 i >=1
//		for (int i = weightCode; i > 1; i--) {
//			// 增加业绩之前做判断，未激活用户不增加任何业绩,已激活用户只增加新增业绩
//			// [取消] 增加新增业绩前判断,(当前用户级别>=业务员 或者当前业绩>=4:4, 或者判断该用户的考核日期和创建时间不一致)三个条件任何一个都行,才会计入新增
//			// [取消] 新增业绩增加前判断用户是否有未领取完的累计升级奖励
//			// [取消] 这里如果累计业绩没有超过4：4 不增加新增业绩
//			User pu = userMap.get(pid);
//			if (pu.isUserStatus()) {
//				// 用户已激活
//				Performance pm = perMap.get(pid);
//				// 2019年1月24 用户新增不再增加累积业绩,只新增业绩
//
//
//				// 计算累计
//				//判断用户级别大于等于业务员UserLevel.COMMON_SALEMAN(即是否产生收入),并且是通过考核状态,并且否则不增加累计业绩
//                // 2018-12-14 取消判断用户是否通过考核
////                if (userid == pu.getDepartmentA()) {
////                    pm.setDepartAcount(pm.getDepartAcount() + 1);
////                } else if (userid == pu.getDepartmentB()) {
////                    pm.setDepartBcount(pm.getDepartBcount() + 1);
////                } else if (userid == pu.getDepartmentC()) {
////                    pm.setDepartCcount(pm.getDepartCcount() + 1);
////                }
////				if (BusinessUtil.isBigBusSame(pu.getUserLevel(), UserLevel.COMMON_SALEMAN)) {
////
////					if (pu.isAssessStatus()) {
////						if (userid == pu.getDepartmentA()) {
////							pm.setDepartAcount(pm.getDepartAcount() + 1);
////						} else if (userid == pu.getDepartmentB()) {
////							pm.setDepartBcount(pm.getDepartBcount() + 1);
////						} else if (userid == pu.getDepartmentC()) {
////							pm.setDepartCcount(pm.getDepartCcount() + 1);
////						}
////					}
////				} else {
////					if (userid == pu.getDepartmentA()) {
////						pm.setDepartAcount(pm.getDepartAcount() + 1);
////					} else if (userid == pu.getDepartmentB()) {
////						pm.setDepartBcount(pm.getDepartBcount() + 1);
////					} else if (userid == pu.getDepartmentC()) {
//////						pm.setDepartCcount(pm.getDepartCcount() + 1);
//////					}
//////				}
////				 计算新增
////				 如果当前存在发放的累计升级奖励,则不增加新增业绩
////				 如果小于UserLevel.ADVANCED_DIRECTOR 级别, 不计算新增业绩
////				boolean isHaveTotalEarning = isHaveTotalEarningMap.get(pu.getUserId());
////				boolean isBigThanOrEqualVIP9 = BusinessUtil.isBigBusSame(pu.getUserLevel(), UserLevel.ADVANCED_DIRECTOR);
////				if (!"AA".equals(pu.getUserLevel()) && !isHaveTotalEarning && isBigThanOrEqualVIP9) {
////					if (userid == pu.getDepartmentA()) {
////						pm.setAddDepartAcount(pm.getAddDepartAcount() + 1);
////					} else if (userid == pu.getDepartmentB()) {
////						pm.setAddDepartBcount(pm.getAddDepartBcount() + 1);
////					} else if (userid == pu.getDepartmentC()) {
////						pm.setAddDepartCcount(pm.getAddDepartCcount() + 1);
////					}
////				}
//				// 为用户增加新增业绩
//				if (userid == pu.getDepartmentA()) {
//					pm.setAddDepartAcount(pm.getAddDepartAcount() + 1);
//				} else if (userid == pu.getDepartmentB()) {
//					pm.setAddDepartBcount(pm.getAddDepartBcount() + 1);
//				} else if (userid == pu.getDepartmentC()) {
//					pm.setAddDepartCcount(pm.getAddDepartCcount() + 1);
//				}
//				pers.add(pm);
//			}
//			userid = pu.getUserId();
//			pid = pu.getParentId();
//		}
//		return pers;
		return null;
	}

	/**
	 * 为新增用户及所有上线增加新增业绩
	 * @param userid 新增用户ID
	 * @param userMap 所有用户
	 * @param perMap 所有业绩对象
	 * @return	有业绩变化的业业绩对象
	 */
	public static List<Performance> ComputeNewLinePerformance(Long userid, Map<Long, User> userMap, Map<Long, Performance> perMap, Map<Long, Boolean> isHaveTotalEarningMap) {
		List<Performance> pers = new ArrayList<Performance>();
		// 添加最下级节点的时候的用户信息
		User u = userMap.get(userid);
		Long pid = u.getParentId();
		int weightCode = u.getWeightCode();
		// 根据用户层数,进行该层数迭代, 迭代至最顶层
		for (int i = weightCode; i > 1; i--) {
			// 获取上一层用户
			User pu = userMap.get(pid);
			// 判断用户是否是激活状态,激活状态可以进行业绩增加
			if (pu.isUserStatus()) {
				Performance pm = perMap.get(pid);
				//
				// 判断用户是否产生过收入,如果没有,直接将业绩计入累积业绩
				if (BusinessUtil.isVIP1(pu.getUserLevel())) {
					if (userid == pu.getDepartmentA()) {
						pm.setDepartAcount(pm.getDepartAcount() + 1);
					} else if (userid == pu.getDepartmentB()) {
						pm.setDepartBcount(pm.getDepartBcount() + 1);
					} else if (userid == pu.getDepartmentC()) {
						pm.setDepartCcount(pm.getDepartCcount() + 1);
					}
				} else {
					// 判断用户是否在领取累积升级奖励
					boolean isHaveTotalEarning = isHaveTotalEarningMap.get(pu.getUserId());
					// 如果没有领取累积升级奖励则为用户增加新增业绩
					if (!isHaveTotalEarning) {
						if (userid == pu.getDepartmentA()) {
							pm.setAddDepartAcount(pm.getAddDepartAcount() + 1);
						} else if (userid == pu.getDepartmentB()) {
							pm.setAddDepartBcount(pm.getAddDepartBcount() + 1);
						} else if (userid == pu.getDepartmentC()) {
							pm.setAddDepartCcount(pm.getAddDepartCcount() + 1);
						}
					}
				}

				pers.add(pm);
			}
			userid = pu.getUserId();
			pid = pu.getParentId();
		}
		return pers;
	}



	/**
	 * 奖金发放
	 * 
	 * @param user
	 * @param earn
	 * @return
	 */
	public static BonusPayList sendBonusPaylist(User user, Earning earn) {
		// earn 是当前用户以时间判断-可发的收益，时间最新的
		// 2018-12-14  取消判断用户是否通过考核,自动扣除重销奖金
		if (user != null && earn != null) {
			BonusPayList bpl = new BonusPayList();
			bpl.setUserId(user.getUserId());
			bpl.setPayTime(new Date());
			bpl.setTotalMoney(earn.getDayMoney());
			BonusPayRatio bpr = getBonusPayRatio(earn);
			bpl.setBonusNumber(earn.getDayMoney() * bpr.getBonusNumber());
			bpl.setManageCost(earn.getDayMoney() * bpr.getManageCost());
			bpl.setProductCoinNumber(earn.getDayMoney() * bpr.getProductCoinNumber());
			bpl.setRepeatCoinNumber(earn.getDayMoney() * bpr.getRepeatCoinNumber());
			bpl.setTouchType(earn.getTouchType());

			// 将积分和金钱添加到user表
			user.setBonusCoin(user.getBonusCoin() + bpl.getBonusNumber());
			user.setProductCoin(user.getProductCoin() + bpl.getProductCoinNumber());
			user.setRepeatCoin(user.getRepeatCoin() + bpl.getRepeatCoinNumber());
			return bpl;
		}
		return null;
	}

	/**
	 * 设置奖金分配比例
	 * @param earn 收益记录
	 * @return 分配比例
	 */
	private static BonusPayRatio getBonusPayRatio(Earning earn) {
		//UPDATE 修改所有用户产品积分比例为30% 2019-01-24
		BonusPayRatio bpr = new BonusPayRatio(0.5,0.1,0.3, 0.1);
//		boolean bbpr = BusinessUtil.isBigBusSame(earn.getUserLevel(),UserLevel.DIRECOTR);
//		if (earn!=null && bbpr) {
//			 bpr = new BonusPayRatio(0.5,0.1,0.3, 0.1);
//		}
		return bpr;
	}

	/**
	 * 根据用户的业绩更新用户的收益
	 */
	public static List<Earning> userEarning(Map<String, Earning> earnMap,List<Performance> pers) {
		List<Earning> earnList = new ArrayList<>();
		// earns 只取得还可以获得收益的数据当前时间-30天
		if (pers != null && pers.size() > 0) {
			for (Performance per : pers) {
				// 这里是计算累计业绩
				Earning e = accumulativeEarning(per);	
				
				if (e==null) {
					continue;
				}
				boolean isincreased = false; //标识累积奖励是否发放完成
				String key = getEarnKey(e);
				if (key != null && key.length() > 0) {
					Earning em = earnMap.get(key);
					if (em!=null) {
						//ishava 已经存储，不需要存储
						if (em.getSurplusNumber()<=0) {
							isincreased = true;
						}
					}else {
						//需要存储
						earnList.add(e);
					}
				}
				
				if (isincreased) {  //如果累积奖励已经发放完成 则开始计算新增业绩
					// 这里是计算新增业绩
					// 级别大于 ADVANCED_DIRECTOR = "II" 开始计算新增奖励
					if (BusinessUtil.isBigBusSame(e.getUserLevel(), UserLevel.ADVANCED_DIRECTOR)) {
						e = increasedEarning(per);
						if (e==null) {
							continue;
						}else {
							//TODO 新增奖励触发条件判断不严谨,发放过的新增奖励级别则不会重复再发 不符合业务逻辑
							// 修复 待验证
							earnList.add(e);
							// 解决新增奖励只发放一次的BUG
//							String keyin = getEarnKey(e);
//							if (keyin != null && keyin.length() > 0) {
//								Earning em = earnMap.get(keyin);
//								if (em!=null) {
//									//ishava 已经存储，不需要存储
//								}else {
//									//需要存储
//									earnList.add(e);
//								}
//							}
						}
					}
				}
			}
		}
		return earnList;
	}
	
	/**
	 * 判断是否已经有存储了
	 * @param e
	 * @param earnMap
	 * @return
	 */
	private static Earning isHaveEarning(Earning e,Map<String, Earning> earnMap) {
		if (e==null) {
			return null;
		}
		String key = getEarnKey(e);
		if (key != null && key.length() > 0) {
			Earning em = earnMap.get(key);
			if (em!=null) {
				//ishava 已经存储，不需要存储
				return em;
			}else {
				//需要存储
			    return e;
			}
		}
		return null;
	}
	
	/**
	 * 第一次累计业绩触发
	 * @param e
	 */
	public static boolean theFirstEarning(Earning e) {
		if (UserLevel.COMMON_SALEMAN.equals(e.getUserLevel()) && TouchType.ACCUMULATION.equals(e.getTouchType())) {
			return true;
		}
		return false;
	}

	/**
	 * 计算累计业绩
	 */
	private static Earning accumulativeEarning(Performance per) {
		if (per != null && per.getDepartAcount() >= 1 && per.getDepartBcount() >= 1) {
			BusinessEntity busentity = BusinessUtil.getBusinesLevel(per.getDepartAcount(), per.getDepartBcount(),
					per.getDepartCcount());
			if (busentity != null) {
				Earning e = new Earning();
				Date createTime = new Date();
				e.setUserid(per.getUserId());
				e.setTouchType(TouchType.ACCUMULATION);
				// 剩余发放天数
				e.setSurplusNumber(ConfigConstant.EARNING_SURPLUSNUMBER);
				// 级别对应的金钱
				e.setDayMoney(busentity.getMoney());
				e.setUserLevel(busentity.getUserLevel());
				e.setCreateTime(createTime);
				return e;
			}
		}
		return null;
	}

	/**
	 * 计算新增业绩
	 */
	private static Earning increasedEarning(Performance per) {
		if (per != null && per.getAddDepartAcount() >= 4 && per.getAddDepartBcount() >= 4) {
			BusinessEntity busentity = BusinessUtil.getBusinesLevel(per.getAddDepartAcount(), per.getAddDepartBcount(),
					per.getAddDepartCcount());
			if (busentity != null) {
				Earning e = new Earning();
				Date createTime = new Date();
				e = new Earning();
				e.setUserid(per.getUserId());
				e.setTouchType(TouchType.ADDITION);
				// 级别对应的金钱
				e.setDayMoney(busentity.getMoney());
				e.setUserLevel(busentity.getUserLevel());
				e.setCreateTime(createTime);
				e.setEndTime(TimeUtil.addDay(createTime, 30));
				return e;
			}
		}
		return null;
	}

	/**
	 * 计算累计业绩是否有重复的
	 * @param earnMap 收益记录
	 * @param earnList 收益记录
	 */
	public static void computeEarn(Map<String, Earning> earnMap, List<Earning> earnList) {
		if (earnMap != null && earnList != null && earnList.size() > 0) {
			// 遍历要保存的业绩，去除重复的保存
			for (Earning earn : earnList) {
				if (earn != null) {
					String key = getEarnKey(earn);
					if (key != null && key.length() > 0) {
						Earning em = earnMap.get(key);
						if (em != null) {
							earnList.remove(earn);
						}
					}
				}
				if (earnList==null || earnList.size()==0) {
					//如果没数据跳出循环
					return;
				}
			}
		}
	}
	/**
	 * 第一次触发累计更新考核日期和考核状态
	 * @param user
	 */
	public static void updateUserForFirstEarning(User user) {
		if (user!=null) {
			Date assessDate = TimeUtil.addDay(30);
			user.setAssessDate(assessDate);
			user.setAssessStatus(true);
			user.setUserLevel(UserLevel.COMMON_SALEMAN);
		}
	}
	
	/**
	 * 判断KEY，元数据中是否存在
	 * 
	 * @param earn
	 * @return
	 */
	public static String getEarnKey(Earning earn) {
		String key = earn.getUserid() + earn.getUserLevel() + earn.getTouchType();
		return key;
	}

	public void auditDay() {

	}

	public static void main(String[] args) {
		// User user = new User();
		// Earning earn = new Earning();
		// earn.setDayMoney(100);
		// BonusPayList bpl = sendBonusPaylist(user, earn);
		// System.out.println(bpl);
		Map<String, String> map = new HashMap<String, String>();
		map.put("123", "1234");
		map.put("123", "12344");
		System.out.println(map.get("12"));
		printMap(map);
		for (int i = 0; i < 4; i++) {
			System.out.println(i);
		}
	}

	public static void printMap(Map map) {
		if (map != null && map.size() > 0) {
			for (Object e : map.values()) {
				System.out.println(e.toString());
			}
			System.out.println("总数：" + map.size());
		}

	}

	public static void printList(List list) {
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				System.out.println(o.toString());
			}
			System.out.println("总数：" + list.size());
		}

	}

	public static void UpdateUserLevel() {

	}
}
