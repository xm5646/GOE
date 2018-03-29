package com.project.goe.projectgeodbserver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.goe.projectgeodbserver.entity.User;

public class UserTreeUtil {
	private List<Map<Long,ArrayList<User>>> childGroupList = new ArrayList<Map<Long,ArrayList<User>>>();
	
	private UserTreeUtil() {}
	
	public static UserTreeUtil instance = new UserTreeUtil();
	
	public static UserTreeUtil getInstance() {
		return  instance;
	}
	
	private void treeTraverse(long pId,List<User> userList) {
		if(pId == 0 || null == userList || 0 == userList.size())
			return;
		
		ArrayList<User> uList = new ArrayList<User>();
		ArrayList<Long> idList = new ArrayList<Long>();
		Map<Long,ArrayList<User>> childMap = new HashMap<Long,ArrayList<User>>();
		for(User u : userList) {
			if(u.getParentId() == pId) {
				uList.add(u);
				idList.add(u.getUserId());
			}
		}
		
		childMap.put(pId,uList);
		childGroupList.add(childMap);
		
		if(0 == idList.size())
			return;
		
		for(Long id : idList) 
			treeTraverse(id, userList);
	}
	
	public List<Map<Long,ArrayList<User>>> getChildGroupList(long pId,List<User> userList) {
		treeTraverse(pId,userList);
		
		return childGroupList;
	}
	
}
