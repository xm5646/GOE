package com.project.goe.projectgeodbserver.viewentity;

public class UserAccumulatePerformence {
	private String account;
	private long performanceA;
	private long performanceB;
	private long performanceC;

	public UserAccumulatePerformence() {
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

	@Override
	public String toString() {
		return "UserAccumulatePerformence [account=" + account + ", performanceA=" + performanceA + ", performanceB="
				+ performanceB + ", performanceC=" + performanceC + "]";
	}

}
