package com.project.goe.projectgeodbserver.viewentity;

import java.util.List;

public class UserNode {
	// 用户基本信息
	private UserVO userVO;
	// 用户业绩信息
	private PerformanceVO performanceVO;
	// 用户子节点信息
	private List<UserNode> childNodeList;

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public PerformanceVO getPerformanceVO() {
		return performanceVO;
	}

	public void setPerformanceVO(PerformanceVO performanceVO) {
		this.performanceVO = performanceVO;
	}

	public List<UserNode> getChildNodeList() {
		return childNodeList;
	}

	public void setChildNodeList(List<UserNode> childNodeList) {
		this.childNodeList = childNodeList;
	}

}
