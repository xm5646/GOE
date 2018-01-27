package com.project.goe.projectgeodbserver.viewentity;

import com.project.goe.projectgeodbserver.entity.Performance;

public class PerformanceLevel {
	private Performance performance;
	private String userLevel;

	public Performance getPerformance() {
		return performance;
	}

	public void setPerformance(Performance performance) {
		this.performance = performance;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

}
