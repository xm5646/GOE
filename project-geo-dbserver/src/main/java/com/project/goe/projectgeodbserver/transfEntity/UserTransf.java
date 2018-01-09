package com.project.goe.projectgeodbserver.transfEntity;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

public class UserTransf{
	public static UserVO transfToVO(User u) {
		UserVO uvo = new UserVO();
		return uvo;
	}
	
	public static User transfToPO(UserVO uvo) {
		return null;
	}
}
