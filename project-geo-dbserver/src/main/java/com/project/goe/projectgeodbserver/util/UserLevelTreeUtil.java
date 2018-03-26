package com.project.goe.projectgeodbserver.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLevelTreeUtil {
	private UserLevelTreeUtil() {}
	
	public static List searchLevelTree(List tree) throws Exception {
		if(null != tree) {
			List t_list = new ArrayList();
			Map map = new HashMap();
			
			for(Object o : tree) {
				Class clazz = o.getClass();
				Field id = clazz.getDeclaredField("userId");
				
				if(!id.isAccessible()) {
					id.setAccessible(true);
				}
				
				Long lId = id.getLong(o);
				map.put(lId, o);
			}
			
			for(Object o : map.keySet()) {
				Long cId = (Long)o;
				Object obj = map.get(cId);
				Class clazz = obj.getClass();
				
			}
		}
		
		return null;
	}
	
}
