package com.project.goe.projectgeodbserver.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private static BonusPayPercentage bpp;
	/**
	 * 计算用户业绩
	 * 传递创建的用户ID，更新他上面所有节点的业绩
	 * @param userid
	 * @param userMap
	 * @param perMap
	 * @return
	 */
	public static List<Performance> computePer(Long userid,Map<Long,User> userMap,Map<Long,Performance> perMap) {
		List<Performance> pers = new ArrayList<Performance>();
		//添加最下级节点的时候的用户信息
		User u = userMap.get(userid);
		Long pid = u.getParentId();
		int weightCode = u.getWeightCode();
		// for循环内终止条件应该是 i >=1 
		for (int i = weightCode; i >1; i--) {
			//增加业绩之前做判断，未激活用户不增加任何业绩
			//增加新增业绩前判断,(当前用户级别>=业务员  或者当前业绩>=4:4, 或者判断该用户的考核日期和创建时间不一致)三个条件任何一个都行,才会计入新增
			//新增业绩增加前判断用户是否有未领取完的累计升级奖励
			User pu = userMap.get(pid);
			if(userid == pu.getDepartmentA()) {
				perMap.get(pid).setAddDepartAcount(perMap.get(pid).getAddDepartAcount()+1);
				perMap.get(pid).setDepartAcount(perMap.get(pid).getDepartAcount()+1);
			}else if(userid == pu.getDepartmentB()) {
				perMap.get(pid).setAddDepartBcount(perMap.get(pid).getAddDepartBcount()+1);
				perMap.get(pid).setDepartBcount(perMap.get(pid).getDepartBcount()+1);
			}else if(userid == pu.getDepartmentC()) {
				perMap.get(pid).setAddDepartCcount(perMap.get(pid).getAddDepartCcount()+1);
				perMap.get(pid).setDepartCcount(perMap.get(pid).getDepartCcount()+1);
			}	
			pers.add(perMap.get(pid));
			userid = pu.getUserId();
			pid = pu.getParentId();
		}
		return pers;
	}
	
	/**
	 * 奖金发放
	 * @param user
	 * @param earn
	 * @return
	 */
	public static BonusPayList sendBonusPaylist(User user,Earning earn) {
		//earn 是当前用户以时间判断-可发的收益，时间最新的
		if (user!=null && earn != null) {
			BonusPayList bpl = new BonusPayList();
			bpl.setUserId(user.getUserId());
			bpl.setPayTime(new Date());
			bpl.setTotalMoney(earn.getDayMoney());
			bpl.setBonusNumber(earn.getDayMoney());
			bpl.setManageCost(earn.getDayMoney());
			bpl.setProductCoinNumber(earn.getDayMoney());
			
			//将积分和金钱添加到user表
			user.setBonusCoin(user.getBonusCoin()+bpl.getBonusNumber());
			user.setProductCoin(user.getProductCoin()+bpl.getProductCoinNumber());
			return bpl;
		}
		return null;
	}
	
	/**
	 * 根据用户的业绩更新用户的收益
	 */
	public static List<Earning> userEarning(List<Performance> pers) {
		List<Earning> earnList = new ArrayList<>();
		//earns  只取得还可以获得收益的数据当前时间-30天
		if (pers!=null && pers.size()>0) {
			for (Performance per : pers) {
				//这里是计算累计业绩
				Earning e = accumulativeEarning(per);
				if (e==null) {
					//这里是计算新增业绩
					e = increasedEarning(per);
				}
				//保存需要的业绩数据
				earnList.add(e);
			}
		}
		return earnList;
	}
	
	/**
	 * 计算累计业绩
	 */
	private static Earning accumulativeEarning(Performance per) {
		if (per!=null && per.getDepartAcount()>=4 && per.getDepartBcount()>=4) {
			BusinessEntity busentity =  BusinessUtil.getBusinesLevel(per.getDepartAcount(), per.getDepartBcount(),per.getDepartCcount());
			if (busentity!=null) {
				Earning e = new Earning();
				e.setUserid(per.getUserId());
				e.setTouchType(TouchType.ACCUMULATION);
				//剩余发放天数
				e.setSurplusNumber(ConfigConstant.EARNING_SURPLUSNUMBER);
				//级别对应的金钱
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
		if (per!=null && per.getDepartAcount()>=4 && per.getDepartBcount()>=4) {
			BusinessEntity busentity =  BusinessUtil.getBusinesLevel(per.getAddDepartAcount(),per.getAddDepartBcount(),per.getAddDepartCcount());
			if (busentity!=null) {
				Earning e = new Earning();
				Date createTime = new Date();
				e = new Earning();
				e.setUserid(per.getUserId());
				e.setTouchType(TouchType.ADDITION);
				//级别对应的金钱
				e.setDayMoney(busentity.getMoney());
				e.setUserLevel(busentity.getUserLevel());
				e.setCreateTime(createTime);
				return e;
			}
		}
		return null;
	}
	
	public void auditDay() {
		
	}
	
	public static void main(String[] args) {
		User user = new User();
		Earning earn = new Earning();
		earn.setDayMoney(100);
		BonusPayList bpl = sendBonusPaylist(user, earn);
		System.out.println(bpl);
	}
	
	public static void printMap(Map map) {
		if (map!=null && map.size()>0) {
			for(Object e : map.values()) {
				System.out.println(e.toString());
			}
			System.out.println("总数："+map.size());
		}

	}
	
	public static void printList(List list) {
		if (list!=null && list.size()>0) {
			for (Object o : list) {
				System.out.println(o.toString());
			}
			System.out.println("总数："+list.size());
		}

	}
}
