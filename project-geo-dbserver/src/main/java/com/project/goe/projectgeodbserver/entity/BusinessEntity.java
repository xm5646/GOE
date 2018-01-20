package com.project.goe.projectgeodbserver.entity;

public class BusinessEntity {
	public BusinessEntity(int code,String userLevel, String userLevel_CH, long countA, long countB, long countC, double money) {
		super();
		this.code = code;
		this.userLevel = userLevel;
		this.userLevel_CH = userLevel_CH;
		this.countA = countA;
		this.countB = countB;
		this.countC = countC;
		this.money = money;
	}
	
	public BusinessEntity() {
		super();
	}
	/**
	 * 参考
	 * public final static String VIP1 = "AA";	//VIP    0  0
	public final static String VIP2 = "BB";	//业务员  4  4
	public final static String VIP3 = "CC";	//组长    10 10
	public final static String VIP4 = "DD";	//主任    20 20
	public final static String VIP5 = "EE";	//经理    40 40
	public final static String VIP6 = "FF";	//部门经理 80 80
	public final static String VIP7 = "GG";	//高级经理 160 160
	public final static String VIP8 = "HH";	//市场总监 260 260
	public final static String VIP9 = "II";	//高级总监 380 380
		public static final String CONSUMER = "AA";
		public static final String COMMON_SALEMAN = "BB";
		public static final String GROUP_LEADER = "CC";
		public static final String DIRECOTR = "DD";
		public static final String MANAGEAR = "EE";
		public static final String BRANCH_MANAGER = "FF";
		public static final String ADVANCE_MANAGER = "GG";
		public static final String MARKET_DIRECTOR = "HH";
		public static final String ADVANCED_DIRECTOR = "II";
		public static final String BOARD_DIRECOTR = "JJJ";
		public static final String EXECUTIVE_PRESIDEANT = "KKK";
		public static final String PRESIDEANT = "LLL";
		public static final String CROWN = "MMM";
		public static final String CROWN_AMBASSADOR = "NNN";
	 */
	private String userLevel;
	
	/**
	 * 	
	 	public static final String CONSUMER_CH = "普通会员";
		public static final String COMMON_SALEMAN_CH = "业务员";
		public static final String GROUP_LEADER_CH = "组长";
		public static final String DIRECOTR_CH = "主任";
		public static final String MANAGEAR_CH = "经理";
		public static final String BRANCH_MANAGER_CH = "部门经理";
		public static final String ADVANCE_MANAGER_CH = "高级经理";
		public static final String MARKET_DIRECTOR_CH = "市场总监";
		public static final String ADVANCED_DIRECTOR_CH = "高级总监";
		public static final String BOARD_DIRECOTR_CH = "董事";
		public static final String EXECUTIVE_PRESIDEANT_CH = "执行总裁";
		public static final String PRESIDEANT_CH = "总裁";
		public static final String CROWN_CH = "皇冠";
		public static final String CROWN_AMBASSADOR_CH = "皇冠大使";
	 */
	private String userLevel_CH;
	/**
	 * 满足级别需要的条件A
	 */
	private long countA;
	/**
	 * 满足级别需要的条件B
	 */
	private long countB;
	/**
	 * 满足级别需要的条件C
	 */
	private long countC;
	/**
	 * 满足级别得到的金钱
	 */
	private double money;
	
	private int code;
	
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getUserLevel_CH() {
		return userLevel_CH;
	}
	public void setUserLevel_CH(String userLevel_CH) {
		this.userLevel_CH = userLevel_CH;
	}
	public long getCountA() {
		return countA;
	}
	public void setCountA(long countA) {
		this.countA = countA;
	}
	public long getCountB() {
		return countB;
	}
	public void setCountB(long countB) {
		this.countB = countB;
	}
	public long getCountC() {
		return countC;
	}
	public void setCountC(long countC) {
		this.countC = countC;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "BusinessEntity [userLevel=" + userLevel + ", userLevel_CH=" + userLevel_CH + ", countA=" + countA
				+ ", countB=" + countB + ", countC=" + countC + ", money=" + money + ", code=" + code + "]";
	}

	
	
}
