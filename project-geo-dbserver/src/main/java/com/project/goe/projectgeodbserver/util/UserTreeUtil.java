package com.project.goe.projectgeodbserver.util;

import java.util.ArrayList;
import java.util.List;

import com.project.goe.projectgeodbserver.entity.User;

public class UserTreeUtil {
	
	public static void traverse(long pId,List<User> userList) {
		if(pId == 0 || null == userList || 0 == userList.size())
			return;
		
		List<User> uList = new ArrayList<User>();
		List<Long> idList = new ArrayList<Long>();
		for(User u : userList) {
			if(u.getParentId() == pId) {
				uList.add(u);
				idList.add(u.getUserId());
			}
		}
		
		System.out.println(uList);
		System.out.println(idList);
		
		if(0 == idList.size())
			return;
		
		for(Long id : idList) 
			traverse(id, userList);
	}
	
}
