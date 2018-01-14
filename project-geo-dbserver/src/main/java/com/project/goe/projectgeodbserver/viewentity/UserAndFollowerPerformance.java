package com.project.goe.projectgeodbserver.viewentity;

public class UserAndFollowerPerformance {
	private String account;
	private long performanceA;
	private long performanceB;
	private long performanceC;

	private UserAccumulatePerformence departUserA;
	private UserAccumulatePerformence departUserB;
	private UserAccumulatePerformence departUserC;

	public UserAndFollowerPerformance() {
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getPerformanceA() {
		return performanceA;
	}

	public void setPerformanceA(long performanceA) {
		this.performanceA = performanceA;
	}

	public long getPerformanceB() {
		return performanceB;
	}

	public void setPerformanceB(long performanceB) {
		this.performanceB = performanceB;
	}

	public long getPerformanceC() {
		return performanceC;
	}

	public void setPerformanceC(long performanceC) {
		this.performanceC = performanceC;
	}

	public UserAccumulatePerformence getDepartUserA() {
		return departUserA;
	}

	public void setDepartUserA(UserAccumulatePerformence departUserA) {
		this.departUserA = departUserA;
	}

	public UserAccumulatePerformence getDepartUserB() {
		return departUserB;
	}

	public void setDepartUserB(UserAccumulatePerformence departUserB) {
		this.departUserB = departUserB;
	}

	public UserAccumulatePerformence getDepartUserC() {
		return departUserC;
	}

	public void setDepartUserC(UserAccumulatePerformence departUserC) {
		this.departUserC = departUserC;
	}

	@Override
	public String toString() {
		return "UserAndFollowerPerformance [account=" + account + ", performanceA=" + performanceA + ", performanceB="
				+ performanceB + ", performanceC=" + performanceC + ", departUserA=" + departUserA + ", departUserB="
				+ departUserB + ", departUserC=" + departUserC + "]";
	}

}
