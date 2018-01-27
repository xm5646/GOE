package com.project.goe.projectgeodbserver.util;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

import java.util.Date;
import java.util.Random;

public class UserUtil {
	
	private UserUtil() {}
	
	public static UserVO UserToUserVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setAccount(user.getAccount());
		userVO.setActivateTime(DateFormatUtil.DateObjectToString(user.getActivateTime()));
		
		if(DateFormatUtil.compareDateObject(user.getAssessDate(),user.getCreateTime()) == 0) {
			userVO.setAssessDate("未达到考核级别");
		}else {
			userVO.setAssessDate(DateFormatUtil.DateObjectToString(user.getAssessDate()));
		}
		
		if(user.isAssessStatus()) {
			userVO.setAssessStatus("已通过考核");
		}else {
			userVO.setAssessStatus("未通过考核");
		}

		userVO.setBonusCoin(user.getBonusCoin());
		userVO.setConsumeCoin(user.getConsumeCoin());
		
		userVO.setCreateTime(DateFormatUtil.DateObjectToString(user.getCreateTime()));
		userVO.setDepartmentA(user.getDepartmentA());
		userVO.setDepartmentB(user.getDepartmentB());
		userVO.setDepartmentC(user.getDepartmentC());
		userVO.setProductCoin(user.getProductCoin());
		userVO.setUserType(user.getUserType());
		
		if(user.isUserStatus())
			userVO.setUserStatus("已激活");
		else 
			userVO.setUserStatus("未激活");
		
		userVO.setUserPhone(user.getUserPhone());
		String  userLevelCH = BusinessUtil.getBusinessEntity(user.getUserLevel()).getUserLevel_CH();
		userVO.setUserLevel(userLevelCH);
		userVO.setNickName(user.getNickName());
		userVO.setUserPhone(user.getUserPhone());
		userVO.setIdentityNo(user.getIdentityNo());
		userVO.setProvince(user.getProvince());
		userVO.setCity(user.getCity());
		userVO.setPasswordReset(user.isPasswordReset());
		
		return userVO;
	}
	
	public static User addUser(User user,User parentUser,String departmentType) {
		if ("A".equals(departmentType)) {
			parentUser.setDepartmentA(user.getUserId());
		}else if ("B".equals(departmentType)) {
			parentUser.setDepartmentB(user.getUserId());
		}else if ("C".equals(departmentType)) {
			parentUser.setDepartmentC(user.getUserId());
		}else {
			return null;
		}
		user.setParentId(user.getUserId());
		return user;
	}
	
	public static User getTestUser() {
		Date createDate = new Date();
		User u = new User();
		String name = "zs001"+new Random().nextInt(10000);
		u.setNickName(name);
		u.setAccount(name);
		u.setPassword("123456");
		u.setCreateTime(createDate);
		u.setUserLevel(UserLevel.CONSUMER);
		u.setUserType(UserType.COMMON);
		u.setAssessStatus(true);
		u.setAssessDate(createDate);
		return u;
	}

	public static User getTestUser(User parentUser, String departmentType) {
		return addUser(getTestUser(), parentUser, departmentType);
	}
}
