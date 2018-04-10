package com.project.goe.projectgeodbserver.viewentity;

import java.util.List;

public class UserNode {
	// 用户基本信息
	private long id;
	private String label;
	private UserNodeVO userNodeVO;
	// 用户业绩信息
	private PerformanceVO performanceVO;
	// 用户子节点信息
	private List<UserNode> children;

	
	
	public UserNodeVO getUserNodeVO() {
		return userNodeVO;
	}

	public void setUserNodeVO(UserNodeVO userNodeVO) {
		this.userNodeVO = userNodeVO;
	}

	public PerformanceVO getPerformanceVO() {
		return performanceVO;
	}

	public void setPerformanceVO(PerformanceVO performanceVO) {
		this.performanceVO = performanceVO;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<UserNode> getChildren() {
		return children;
	}

	public void setChildren(List<UserNode> children) {
		this.children = children;
	}

	

}
