package com.project.goe.projectgeodbserver.util;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.project.goe.projectgeodbserver.entity.BusinessEntity;
import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.statusType.UserLevel;

public class BusinessUtil {
//	public final static String VIP1 = "AA";	//VIP     	0  	0	0
//	public final static String VIP2 = "BB";	//业务员   	1  	1   10
//	public final static String VIP3 = "CC";	//组长     	4  	4  	40
//	public final static String VIP4 = "DD";	//主任     	15 	15  100
//	public final static String VIP5 = "EE";	//经理     	30 	30  200
//	public final static String VIP6 = "FF";	//部门经理 	60 	60  400
//	public final static String VIP7 = "GG";	//高级经理 	140 140 600
//	public final static String VIP8 = "HH";	//市场总监 	260 260 1000
//	public final static String VIP9 = "II";	//高级总监 	380 380 1500
//	
//	public final static String DS1 = "JJJ";	//董事 750 750 750 3000
//	public final static String DS2 = "KKK";	//执行总裁 2500 2500 2500 5000
//	public final static String DS3 = "LLL";	//总裁 5000 5000 5000  15000

// 原规则
//	VIP1 = "AA"; //VIP    0  0
//	VIP2 = "BB"; //业务员  4  4   40
//	VIP3 = "CC"; //组长    10 10  100
//	VIP4 = "DD"; //主任    20 20  180
//	VIP5 = "EE"; //经理    40 40  300
//	VIP6 = "FF"; //部门经理 80 80  600
//	VIP7 = "GG"; //高级经理 160 160  900
//	VIP8 = "HH"; //市场总监 260 260  1200
//	VIP9 = "II"; //高级总监 380 380  1800
//
//	DS1 = "JJJ"; //董事 750 750 750 3000
//	DS2 = "KKK"; //执行总裁 2500 2500 2500 5000
//	DS3 = "LLL"; //总裁 5000 5000 5000  15000
//
//
//	DS4 = "MMM"; //皇冠 3000 3000 3000  10000
//	DS5 = "NNN"; //皇冠大使 5000 5000 5000 20000
	
	
	
//	public final static String DS4 = "MMM";	//皇冠 3000 3000 3000  10000
//	public final static String DS5 = "NNN";	//皇冠大使 5000 5000 5000 20000
	
	public static Map<String,BusinessEntity> businessMap = new HashMap<>();
	
	public static List<BusinessEntity> businessList = new ArrayList<BusinessEntity>();
	
	static{
		BusinessEntity VIP1 = new BusinessEntity(1,UserLevel.CONSUMER, UserLevel.CONSUMER_CH, 0, 0, 0, 0);
		businessMap.put(UserLevel.CONSUMER, VIP1);
		BusinessEntity VIP2 = new BusinessEntity(2,UserLevel.COMMON_SALEMAN, UserLevel.COMMON_SALEMAN_CH, 1, 1, 0, 10);
		businessMap.put(UserLevel.COMMON_SALEMAN, VIP2);
		BusinessEntity VIP3 = new BusinessEntity(3,UserLevel.GROUP_LEADER, UserLevel.GROUP_LEADER_CH, 4, 4, 0, 40);
		businessMap.put(UserLevel.GROUP_LEADER, VIP3);
		BusinessEntity VIP4 = new BusinessEntity(4,UserLevel.DIRECOTR, UserLevel.DIRECOTR_CH, 15, 15, 0, 100);
		businessMap.put(UserLevel.DIRECOTR, VIP4);
		BusinessEntity VIP5 = new BusinessEntity(5,UserLevel.MANAGEAR, UserLevel.MANAGEAR_CH, 30, 30, 0, 200);
		businessMap.put(UserLevel.MANAGEAR, VIP5);
		BusinessEntity VIP6 = new BusinessEntity(6,UserLevel.BRANCH_MANAGER, UserLevel.BRANCH_MANAGER_CH, 60, 60, 0, 400);
		businessMap.put(UserLevel.BRANCH_MANAGER, VIP6);
		BusinessEntity VIP7 = new BusinessEntity(7,UserLevel.ADVANCE_MANAGER, UserLevel.ADVANCE_MANAGER_CH, 140, 140, 0, 600);
		businessMap.put(UserLevel.ADVANCE_MANAGER, VIP7);
		BusinessEntity VIP8 = new BusinessEntity(8,UserLevel.MARKET_DIRECTOR, UserLevel.MARKET_DIRECTOR_CH, 260, 260, 0, 1000);
		businessMap.put(UserLevel.MARKET_DIRECTOR, VIP8);
		BusinessEntity VIP9 = new BusinessEntity(9,UserLevel.ADVANCED_DIRECTOR, UserLevel.ADVANCED_DIRECTOR_CH, 380, 380, 0, 1500);
		businessMap.put(UserLevel.ADVANCED_DIRECTOR, VIP9);
		
		BusinessEntity DS1 = new BusinessEntity(10,UserLevel.BOARD_DIRECOTR, UserLevel.BOARD_DIRECOTR_CH, 750, 750, 750, 3000);
		businessMap.put(UserLevel.BOARD_DIRECOTR, DS1);
		BusinessEntity DS2 = new BusinessEntity(11,UserLevel.EXECUTIVE_PRESIDEANT, UserLevel.EXECUTIVE_PRESIDEANT_CH, 2500, 2500, 2500, 5000);
		businessMap.put(UserLevel.EXECUTIVE_PRESIDEANT, DS2);
		BusinessEntity DS3 = new BusinessEntity(12,UserLevel.PRESIDEANT, UserLevel.PRESIDEANT_CH, 5000, 5000, 5000, 15000);
		businessMap.put(UserLevel.PRESIDEANT, DS3);
//		BusinessEntity DS4 = new BusinessEntity(13,UserLevel.CROWN, UserLevel.CROWN_CH, 3000, 3000, 3000, 10000);
//		businessMap.put(UserLevel.CROWN, DS4);
//		BusinessEntity DS5 = new BusinessEntity(14,UserLevel.CROWN_AMBASSADOR, UserLevel.CROWN_AMBASSADOR_CH, 5000, 5000, 5000, 20000);
//		businessMap.put(UserLevel.CROWN_AMBASSADOR, DS5);
		
		businessList.add(VIP1);
		businessList.add(VIP2);
		businessList.add(VIP3);
		businessList.add(VIP4);
		businessList.add(VIP5);
		businessList.add(VIP6);
		businessList.add(VIP7);
		businessList.add(VIP8);
		businessList.add(VIP9);
		
		businessList.add(DS1);
		businessList.add(DS2);
		businessList.add(DS3);
//		businessList.add(DS4);
//		businessList.add(DS5);
	}
	
	/**
	 * 更加用户的业绩判断用户所处的等级
	 * @param DepartAcount
	 * @param DepartBcount
	 * @param DepartCcount
	 * @return
	 */
	public static BusinessEntity getBusinesLevel(long DepartAcount,long DepartBcount,long DepartCcount) {
		if (businessList!=null && businessList.size()>0) {
			for (int i = businessList.size()-1; i >= 0; i--) {
				if (isSatisfaction(businessList.get(i), DepartAcount, DepartBcount, DepartCcount)) {
					return businessList.get(i);
				}
			}
			
//			for (BusinessEntity bus : businessList) {
//				if (isSatisfaction(bus, DepartAcount, DepartBcount, DepartCcount)) {
//					return bus;
//				}
//			}
			
		}
		return null;
	}
	
	/**
	 * 根据用户业绩的等级返回业绩的对象
	 * @param userLevel
	 * @return
	 */
	public static BusinessEntity getBusinessEntity(String userLevel) {
		return businessMap.get(userLevel);
	}
	
	//整合
	public static boolean isFirstEarn(String userLevel) {
		return false;
	}
	
	public static Earning isBigBus(Earning ea,Earning eb) {
		BusinessEntity bus1 = businessMap.get(ea.getUserLevel());
		BusinessEntity bus2 = businessMap.get(eb.getUserLevel());
		if (bus1.getCode()>bus2.getCode()) {
			return ea;
		}else {
			return eb;
		}
	}
	
	public static boolean isBigBus(String ea,String eb) {
		//第一个比第二个级别大
		BusinessEntity bus1 = businessMap.get(ea);
		BusinessEntity bus2 = businessMap.get(eb);
		if (bus1!= null && bus2!=null && bus1.getCode()>bus2.getCode()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isBigBusSame(String ea, String eb) {
		//第一个比第二个级别大
		BusinessEntity bus1 = businessMap.get(ea);
		BusinessEntity bus2 = businessMap.get(eb);
		if (bus1!= null && bus2!=null && bus1.getCode()>=bus2.getCode()) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean isSatisfaction(BusinessEntity bus,long DepartAcount,long DepartBcount,long DepartCcount) {
		if (bus.getCountA()<=DepartAcount&&bus.getCountB()<=DepartBcount&&bus.getCountC()<=DepartCcount) {
			return true;
		}
		return false;
	}

	/**
	 * 获取下一个级别
	 * @param nowLevel
	 * @return
	 */
	public static BusinessEntity getNextLevel(BusinessEntity nowLevel) {
		for (int i = businessList.size()-1; i >= 0; i--) {
			if (businessList.get(i).getUserLevel().equals(nowLevel.getUserLevel())) {
				return businessList.get(i + 1);
			}
		}
		return null;
	}

	/**
	 * 获取上一个级别
	 * @param nowLevel
	 * @return
	 */
	public static BusinessEntity getPreLevel(BusinessEntity nowLevel) {
		for (int i = businessList.size()-1; i >= 0; i--) {
			if (businessList.get(i).getUserLevel().equals(nowLevel.getUserLevel())) {
				return businessList.get(i - 1);
			}
		}
		return null;
	}

	public static Boolean isVIP1(String level) {
		return level.equals(UserLevel.CONSUMER);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		个人工单数:[2247,313,582,]
//				排序后:[2247, 313, 582]
//				你的等级是：普通会员
		BusinessEntity bus = getBusinesLevel(60,60,0);
		System.out.println(bus);
		BusinessEntity nex = getNextLevel(bus);
		System.out.println(nex);
		BusinessEntity pre = getPreLevel(bus);
		System.out.println(pre);


//		CheckUtil.printMap(businessMap);
	}
}
