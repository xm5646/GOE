package com.project.goe.projectgeodbserver.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.BusinessEntity;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.statusType.ConfigConstant;
import com.project.goe.projectgeodbserver.statusType.TouchType;

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
	public static List<Performance> computePer(Long userid, Map<Long, User> userMap, Map<Long, Performance> perMap) {
		List<Performance> pers = new ArrayList<Performance>();
		// 添加最下级节点的时候的用户信息
		User u = userMap.get(userid);
		Long pid = u.getParentId();
		int weightCode = u.getWeightCode();
		// for循环内终止条件应该是 i >=1
		for (int i = weightCode; i > 1; i--) {
			// 增加业绩之前做判断，未激活用户不增加任何业绩
			// 增加新增业绩前判断,(当前用户级别>=业务员 或者当前业绩>=4:4, 或者判断该用户的考核日期和创建时间不一致)三个条件任何一个都行,才会计入新增
			// 新增业绩增加前判断用户是否有未领取完的累计升级奖励
			// 这里如果累计业绩没有超过4：4 不增加新增业绩
			User pu = userMap.get(pid);
			if (pu.isUserStatus()) {
				// 用户已激活
				Performance pm = perMap.get(pid);
				// 计算累计
				if (userid == pu.getDepartmentA()) {
					pm.setDepartAcount(pm.getDepartAcount() + 1);
				} else if (userid == pu.getDepartmentB()) {
					pm.setDepartBcount(pm.getDepartBcount() + 1);
				} else if (userid == pu.getDepartmentC()) {
					pm.setDepartCcount(pm.getDepartCcount() + 1);
				}
				// 计算新增
				// 这里如果累计业绩没有超过4：4 不增加新增业绩
				if (pm.getDepartAcount() > 4 && pm.getDepartBcount() > 4) {
					if (userid == pu.getDepartmentA()) {
						pm.setAddDepartAcount(pm.getAddDepartAcount() + 1);
					} else if (userid == pu.getDepartmentB()) {
						pm.setAddDepartBcount(pm.getAddDepartBcount() + 1);
					} else if (userid == pu.getDepartmentC()) {
						pm.setAddDepartCcount(pm.getAddDepartCcount() + 1);
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
		if (user != null && earn != null) {
			BonusPayList bpl = new BonusPayList();
			bpl.setUserId(user.getUserId());
			bpl.setPayTime(new Date());
			bpl.setTotalMoney(earn.getDayMoney());
			bpl.setBonusNumber(earn.getDayMoney() * 0.9);
			bpl.setManageCost(earn.getDayMoney() * 0.1);
			bpl.setProductCoinNumber(earn.getDayMoney() * 0);

			// 将积分和金钱添加到user表
			user.setBonusCoin(user.getBonusCoin() + bpl.getBonusNumber());
			user.setProductCoin(user.getProductCoin() + bpl.getProductCoinNumber());
			return bpl;
		}
		return null;
	}

	/**
	 * 根据用户的业绩更新用户的收益
	 */
	public static List<Earning> userEarning(List<Performance> pers) {
		List<Earning> earnList = new ArrayList<>();
		// earns 只取得还可以获得收益的数据当前时间-30天
		if (pers != null && pers.size() > 0) {
			for (Performance per : pers) {
				// 这里是计算累计业绩
				Earning e = accumulativeEarning(per);
				if (e == null) {
					// 这里是计算新增业绩
					e = increasedEarning(per);
				}
				// 保存需要的业绩数据
				earnList.add(e);
			}
		}
		return earnList;
	}

	/**
	 * 计算累计业绩
	 */
	private static Earning accumulativeEarning(Performance per) {
		if (per != null && per.getDepartAcount() >= 4 && per.getDepartBcount() >= 4) {
			BusinessEntity busentity = BusinessUtil.getBusinesLevel(per.getDepartAcount(), per.getDepartBcount(),
					per.getDepartCcount());
			if (busentity != null) {
				Earning e = new Earning();
				e.setUserid(per.getUserId());
				e.setTouchType(TouchType.ACCUMULATION);
				// 剩余发放天数
				e.setSurplusNumber(ConfigConstant.EARNING_SURPLUSNUMBER);
				// 级别对应的金钱
				e.setDayMoney(busentity.getMoney());
				e.setUserLevel(busentity.getUserLevel());
				return e;
			}
		}
		return null;
	}

	/**
	 * 计算新增业绩
	 */
	private static Earning increasedEarning(Performance per) {
		if (per != null && per.getDepartAcount() >= 4 && per.getDepartBcount() >= 4) {
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
	 * 
	 * @param earns
	 * @param earnList
	 */
	public static void computeEarn(Iterable<Earning> earns, List<Earning> earnList) {
		if (earns != null && earnList != null && earnList.size() > 0) {
			// 将获得的对象封装成MAP
			Map<String, Earning> earnMap = new HashMap<String, Earning>();
			for (Earning earn : earns) {
				String key = getEarnKey(earn);
				if (key != null && key.length() > 0) {
					earnMap.put(key, earn);
				}
			}
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
			}
		}
	}

	/**
	 * 判断KEY，元数据中是否存在
	 * 
	 * @param earn
	 * @return
	 */
	private static String getEarnKey(Earning earn) {
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
}
