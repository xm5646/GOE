package com.project.goe.projectgeodbserver.util;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

public class UserUtil {
	private UserUtil() {}
	
	public static UserVO UserToUserVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setAccount(user.getAccount());
		userVO.setActivateTime(user.getActivateTime());
		userVO.setAssessStatus(user.isAssessStatus());
		userVO.setBonusCoin(user.getBonusCoin());
		userVO.setConsumeCoin(user.getConsumeCoin());
		userVO.setCreateTime(user.getCreateTime());
		userVO.setDepartmentA(user.getDepartmentA());
		userVO.setDepartmentB(user.getDepartmentB());
		userVO.setDepartmentC(user.getDepartmentC());
		userVO.setProductCoin(user.getProductCoin());
		userVO.setUserType(user.getUserType());
		userVO.setUserStatus(user.isUserStatus());
		userVO.setUserPhone(user.getUserPhone());
		userVO.setUserLevel(user.getUserLevel());
		userVO.setNickName(user.getNickName());
		userVO.setUserPhone(user.getUserPhone());
		
		return userVO;
	}
}	
