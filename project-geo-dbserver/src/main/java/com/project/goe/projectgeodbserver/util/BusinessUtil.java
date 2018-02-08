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
//	public final static String VIP1 = "AA";	//VIP    0  0
//	public final static String VIP2 = "BB";	//业务员  4  4   40
//	public final static String VIP3 = "CC";	//组长    10 10  140
//	public final static String VIP4 = "DD";	//主任    20 20  180
//	public final static String VIP5 = "EE";	//经理    40 40  330
//	public final static String VIP6 = "FF";	//部门经理 80 80  660
//	public final static String VIP7 = "GG";	//高级经理 160 160  990
//	public final static String VIP8 = "HH";	//市场总监 260 260  1200
//	public final static String VIP9 = "II";	//高级总监 380 380  1800
//	
//	public final static String DS1 = "JJJ";	//一星董事 380 380 300 3000
//	public final static String DS2 = "KKK";	//执行总裁 750 750 600 5000
//	public final static String DS3 = "LLL";	//总裁 1500 1500 1200  6000     
//	public final static String DS4 = "MMM";	//皇冠 2200 2200 1800  13000
//	public final static String DS5 = "NNN";	//皇冠大使 3000 3000 2100 20000
	
	public static Map<String,BusinessEntity> businessMap = new HashMap<>();
	
	public static List<BusinessEntity> businessList = new ArrayList<BusinessEntity>();
	
	static{
		BusinessEntity VIP1 = new BusinessEntity(1,UserLevel.CONSUMER, UserLevel.CONSUMER_CH, 0, 0, 0, 0);
		businessMap.put(UserLevel.CONSUMER, VIP1);
		BusinessEntity VIP2 = new BusinessEntity(2,UserLevel.COMMON_SALEMAN, UserLevel.COMMON_SALEMAN_CH, 4, 4, 0, 40);
		businessMap.put(UserLevel.COMMON_SALEMAN, VIP2);
		BusinessEntity VIP3 = new BusinessEntity(3,UserLevel.GROUP_LEADER, UserLevel.GROUP_LEADER_CH, 10, 10, 0, 140);
		businessMap.put(UserLevel.GROUP_LEADER, VIP3);
		BusinessEntity VIP4 = new BusinessEntity(4,UserLevel.DIRECOTR, UserLevel.DIRECOTR_CH, 20, 20, 0, 180);
		businessMap.put(UserLevel.DIRECOTR, VIP4);
		BusinessEntity VIP5 = new BusinessEntity(5,UserLevel.MANAGEAR, UserLevel.MANAGEAR_CH, 40, 40, 0, 330);
		businessMap.put(UserLevel.MANAGEAR, VIP5);
		BusinessEntity VIP6 = new BusinessEntity(6,UserLevel.BRANCH_MANAGER, UserLevel.BRANCH_MANAGER_CH, 80, 80, 0, 660);
		businessMap.put(UserLevel.BRANCH_MANAGER, VIP6);
		BusinessEntity VIP7 = new BusinessEntity(7,UserLevel.ADVANCE_MANAGER, UserLevel.ADVANCE_MANAGER_CH, 160, 160, 0, 990);
		businessMap.put(UserLevel.ADVANCE_MANAGER, VIP7);
		BusinessEntity VIP8 = new BusinessEntity(8,UserLevel.MARKET_DIRECTOR, UserLevel.MARKET_DIRECTOR_CH, 260, 260, 0, 1200);
		businessMap.put(UserLevel.MARKET_DIRECTOR, VIP8);
		BusinessEntity VIP9 = new BusinessEntity(9,UserLevel.ADVANCED_DIRECTOR, UserLevel.ADVANCED_DIRECTOR_CH, 380, 380, 0, 1800);
		businessMap.put(UserLevel.ADVANCED_DIRECTOR, VIP9);
		
		BusinessEntity DS1 = new BusinessEntity(10,UserLevel.BOARD_DIRECOTR, UserLevel.BOARD_DIRECOTR_CH, 380, 380, 300, 3000);
		businessMap.put(UserLevel.BOARD_DIRECOTR, DS1);
		BusinessEntity DS2 = new BusinessEntity(11,UserLevel.EXECUTIVE_PRESIDEANT, UserLevel.EXECUTIVE_PRESIDEANT_CH, 750, 750, 600, 5000);
		businessMap.put(UserLevel.EXECUTIVE_PRESIDEANT, DS2);
		BusinessEntity DS3 = new BusinessEntity(12,UserLevel.PRESIDEANT, UserLevel.PRESIDEANT_CH, 1500, 1500, 1200, 6000);
		businessMap.put(UserLevel.PRESIDEANT, DS3);
		BusinessEntity DS4 = new BusinessEntity(13,UserLevel.CROWN, UserLevel.CROWN_CH, 2200, 2200, 1800, 13000);
		businessMap.put(UserLevel.CROWN, DS4);
		BusinessEntity DS5 = new BusinessEntity(14,UserLevel.CROWN_AMBASSADOR, UserLevel.CROWN_AMBASSADOR_CH, 3000, 3000, 2100, 20000);
		businessMap.put(UserLevel.CROWN_AMBASSADOR, DS5);
		
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
		businessList.add(DS4);
		businessList.add(DS5);
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
	
	public static boolean isBigBusSame(String ea) {
		//第一个比第二个级别大
		BusinessEntity bus1 = businessMap.get(ea);
		BusinessEntity bus2 = businessMap.get(UserLevel.MARKET_DIRECTOR);
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		个人工单数:[2247,313,582,]
//				排序后:[2247, 313, 582]
//				你的等级是：普通会员
		BusinessEntity bus = getBusinesLevel(10,11,0);
		System.out.println(bus);
		
//		CheckUtil.printMap(businessMap);
	}
}
