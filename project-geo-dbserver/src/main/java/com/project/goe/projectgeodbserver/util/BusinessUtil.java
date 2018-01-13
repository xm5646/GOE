package com.project.goe.projectgeodbserver.util;



import java.util.Arrays;
import java.util.Random;

public class BusinessUtil {

	public final static String VIP0 = "";   	//普通会员
	public final static String VIP1 = "AA";	//VIP
	public final static String VIP2 = "BB";	//业务员
	public final static String VIP3 = "CC";	//组长
	public final static String VIP4 = "DD";	//主任
	public final static String VIP5 = "EE";	//经理
	public final static String VIP6 = "FF";	//部门经理
	public final static String VIP7 = "GG";	//高级经理
	public final static String VIP8 = "HH";	//市场总监
	public final static String VIP9 = "II";	//高级总监 380
	
	public final static String DS1 = "JJJ";	//一星董事 380 380 300
	public final static String DS2 = "KKK";	//二星董事 380 380 300
	public final static String DS3 = "LLL";	//三星董事 380 380 300
	public final static String DS4 = "MMM";	//总裁 380 380 300
	public final static String DS5 = "NNN";	//执行总裁 380 380 300
	
	public static void test(String type) {
		
		switch (type) {
		case VIP0:
			System.out.println("你的等级是：普通会员");
			break;
		case VIP1:
			System.out.println("你的等级是：VIP");
			break;
		case VIP2:
			System.out.println("你的等级是：业务员");
			break;
		case VIP3:
			System.out.println("你的等级是：组长");
			break;
		case VIP4:
			System.out.println("你的等级是：主任");
			break;
		case VIP5:
			System.out.println("你的等级是：经理");
			break;
		case VIP6:
			System.out.println("你的等级是：部门经理");
			break;
		case VIP7:
			System.out.println("你的等级是：高级经理");
			break;
		case VIP8:
			System.out.println("你的等级是：市场总监");
			break;
		case VIP9:
			System.out.println("你的等级是：高级总监");
			break;
		case DS1:
			System.out.println("你的等级是：一星董事");
			break;
		case DS2:
			System.out.println("你的等级是：二星董事");
			break;
		case DS3:
			System.out.println("你的等级是：三星董事");
			break;
		case DS4:
			System.out.println("你的等级是：总裁");
			break;
		case DS5:
			System.out.println("你的等级是：执行总裁");
			break;

		default:
			break;
		}
	}
	
	/**
	 * 排序，由小到大
	 * @param departmentA
	 * @param departmentB
	 * @return
	 */
	public static int[] orderNumberSort(int departmentA,int departmentB) {
		int[] arg ={departmentA,departmentB};
		System.out.println("排序前:"+Arrays.toString(arg) );
		if (departmentA<departmentB) {
			arg[0] =departmentA;
			arg[1] =departmentB;
		}else {
			arg[0] =departmentB;
			arg[1] =departmentA;
		}
		System.out.println("排序后:"+Arrays.toString(arg) );
		return arg;
	}
	
	/**
	 * 排序，由小到大  C不变
	 * @param departmentA
	 * @param departmentB
	 * @param departmentC
	 * @return
	 */
	public static int[] orderNumberSort(int departmentA,int departmentB,int departmentC) {
		int[] arg ={departmentA,departmentB,departmentC};
		System.out.println("排序前:"+Arrays.toString(arg) );
		if (departmentA<departmentB) {
			arg[0] =departmentA;
			arg[1] =departmentB;
		}else {
			arg[0] =departmentB;
			arg[1] =departmentA;
		}
		arg[2] = departmentC;
		System.out.println("排序后:"+Arrays.toString(arg) );
		return arg;
	}
	
	private static String getBusinesType (int[] arg) {
		String type = "";
		if (arg.length == 2) {
			type =getBusinesLevel(arg[1]);
		}else if (arg.length == 3) {
			type =getBusinesLevel(arg[2],arg[1]);
		}
		return type;
	}
	
	public static String getBusinesType (int departmentA,int departmentB) {
		int[] arg = orderNumberSort(departmentA, departmentB);
		return getBusinesType(arg);
	}
	
	public static String getBusinesType (int departmentA,int departmentB,int departmentC) {
		int[] arg = orderNumberSort(departmentA, departmentB, departmentC);
		return getBusinesType(arg);
	}
	
	private static String getBusinesLevel(int ordernumber) {
		if (ordernumber>=380) {
			return "II";
		}else if (ordernumber>=260) {
			return "HH";
		}else if (ordernumber>=160) {
			return "GG";
		}else if (ordernumber>=80) {
			return "FF";
		}else if (ordernumber>=40) {
			return "EE";
		}else if (ordernumber>=20) {
			return "DD";
		}else if (ordernumber>=8) {
			return "CC";
		}else if (ordernumber>=4) {
			return "BB";
		}else if (ordernumber>2) {
			return "AA";
		}else {
			return "";
		}
	}
	
	private static String getBusinesLevel(int ordernumber,int ordernumber2) {
		if(ordernumber>=300) {
			if(ordernumber>=2100 && ordernumber2 >=3000) {
				return "NNN";
			}else if(ordernumber>=1800 && ordernumber2 >=2200) {
				return "MMM";
			}else if(ordernumber>=1200 && ordernumber2 >=1500) {
				return "LLL";
			}else if(ordernumber>=600 && ordernumber2 >=750) {
				return "KKK";
			}else if(ordernumber2 >=380) {
				return "JJJ";
			}else {
				return "";
			}
		}else {
			return getBusinesLevel(ordernumber2);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		个人工单数:[2247,313,582,]
//				排序后:[2247, 313, 582]
//				你的等级是：普通会员
		String type = getBusinesType(247,313,582);
		test(type);
		randomtest();
	}
	
	public static void randomtest() {
		int rand = 500;
		for (int i = 0; i < 100; i++) {
			
			if(i%2==1) {
				int a = new Random().nextInt(rand)+380;
				int b = new Random().nextInt(rand)+380;
				int c = new Random().nextInt(rand);
				System.out.println("个人工单数:["+a+","+b+","+c+","+"]");
				String type = getBusinesType(a,b,c);
				test(type);
			}else {
				int a = new Random().nextInt(rand);
				int b = new Random().nextInt(rand);
				System.out.println("个人工单数:["+a+","+b+"]");
				String type = getBusinesType(a,b);
				test(type);
			}

		}
	}

}
