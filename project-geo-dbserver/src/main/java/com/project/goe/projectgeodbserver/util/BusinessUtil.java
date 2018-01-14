package com.project.goe.projectgeodbserver.util;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.project.goe.projectgeodbserver.entity.BusinessEntity;
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
	
	static{
//		BusinessEntity VIP1 = new BusinessEntity(UserLevel.CONSUMER, UserLevel.CONSUMER_CH, 0, 0, 0, 0);
//		businessMap.put(UserLevel.CONSUMER, VIP1);
		BusinessEntity VIP2 = new BusinessEntity(UserLevel.COMMON_SALEMAN, UserLevel.COMMON_SALEMAN_CH, 4, 4, 0, 40);
		businessMap.put(UserLevel.COMMON_SALEMAN, VIP2);
		BusinessEntity VIP3 = new BusinessEntity(UserLevel.GROUP_LEADER, UserLevel.GROUP_LEADER_CH, 10, 10, 0, 140);
		businessMap.put(UserLevel.GROUP_LEADER, VIP3);
		BusinessEntity VIP4 = new BusinessEntity(UserLevel.DIRECOTR, UserLevel.DIRECOTR_CH, 20, 20, 0, 180);
		businessMap.put(UserLevel.DIRECOTR, VIP4);
		BusinessEntity VIP5 = new BusinessEntity(UserLevel.MANAGEAR, UserLevel.MANAGEAR_CH, 40, 40, 0, 330);
		businessMap.put(UserLevel.MANAGEAR, VIP5);
		BusinessEntity VIP6 = new BusinessEntity(UserLevel.BRANCH_MANAGER, UserLevel.BRANCH_MANAGER_CH, 80, 80, 0, 660);
		businessMap.put(UserLevel.BRANCH_MANAGER, VIP6);
		BusinessEntity VIP7 = new BusinessEntity(UserLevel.ADVANCE_MANAGER, UserLevel.ADVANCE_MANAGER_CH, 160, 160, 0, 990);
		businessMap.put(UserLevel.ADVANCE_MANAGER, VIP7);
		BusinessEntity VIP8 = new BusinessEntity(UserLevel.MARKET_DIRECTOR, UserLevel.MARKET_DIRECTOR_CH, 260, 260, 0, 1200);
		businessMap.put(UserLevel.MARKET_DIRECTOR, VIP8);
		BusinessEntity VIP9 = new BusinessEntity(UserLevel.ADVANCED_DIRECTOR, UserLevel.ADVANCED_DIRECTOR_CH, 380, 380, 0, 1800);
		businessMap.put(UserLevel.ADVANCED_DIRECTOR, VIP9);
		
		BusinessEntity DS1 = new BusinessEntity(UserLevel.BOARD_DIRECOTR, UserLevel.BOARD_DIRECOTR_CH, 380, 380, 300, 3000);
		businessMap.put(UserLevel.BOARD_DIRECOTR, DS1);
		BusinessEntity DS2 = new BusinessEntity(UserLevel.EXECUTIVE_PRESIDEANT, UserLevel.EXECUTIVE_PRESIDEANT_CH, 750, 750, 600, 5000);
		businessMap.put(UserLevel.EXECUTIVE_PRESIDEANT, DS2);
		BusinessEntity DS3 = new BusinessEntity(UserLevel.PRESIDEANT, UserLevel.PRESIDEANT_CH, 1500, 1500, 1200, 6000);
		businessMap.put(UserLevel.PRESIDEANT, DS3);
		BusinessEntity DS4 = new BusinessEntity(UserLevel.CROWN, UserLevel.CROWN_CH, 2200, 2200, 1800, 13000);
		businessMap.put(UserLevel.CROWN, DS4);
		BusinessEntity DS5 = new BusinessEntity(UserLevel.CROWN_AMBASSADOR, UserLevel.CROWN_AMBASSADOR_CH, 3000, 3000, 2100, 20000);
		businessMap.put(UserLevel.CROWN_AMBASSADOR, DS5);
	}
	
	public static BusinessEntity getBusinesLevel(long DepartAcount,long DepartBcount,long DepartCcount) {
		if (businessMap!=null && businessMap.size()>0) {
			for(BusinessEntity bus : businessMap.values()) {
				if (bus.getCountA()==DepartAcount&&bus.getCountB()==DepartBcount&&bus.getCountC()==DepartCcount) {
					return bus;
				}
			}
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		个人工单数:[2247,313,582,]
//				排序后:[2247, 313, 582]
//				你的等级是：普通会员
		BusinessEntity bus = getBusinesLevel(1,1,0);
		System.out.println(bus);
		
//		CheckUtil.printMap(businessMap);
	}
}
