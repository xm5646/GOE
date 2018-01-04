package com.project.goe.projectgeodbserver.transfEntity;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

public class UserTransf{
	public static UserVO transfToVO(User u) {
		UserVO uvo = new UserVO();
		uvo.setName(u.getName());
		uvo.setType(u.getType());
		return uvo;
	}
	
	public static User transfToPO(UserVO uvo) {
		return null;
	}
}
