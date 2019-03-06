package com.project.goe.projectgeodbserver.util;

import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.statusType.UserLevel;
import com.project.goe.projectgeodbserver.statusType.UserType;
import com.project.goe.projectgeodbserver.viewentity.UserNodeVO;
import com.project.goe.projectgeodbserver.viewentity.UserVO;

import java.util.Date;

public class UserUtil {
	private UserUtil() {
	}
	
	public static UserNodeVO UserToUserNodeVO(User user) {
		UserNodeVO userVO = new UserNodeVO();
		userVO.setAccount(user.getAccount());
		userVO.setLabel(user.getAccount());
		userVO.setId(user.getUserId());
		userVO.setActivateTime(DateFormatUtil.DateObjectToString(user.getActivateTime()));

		// if(DateFormatUtil.compareDateObject(user.getAssessDate(),user.getCreateTime())
		// == 0) {
		// userVO.setAssessDate("未达到考核级别");
		// }else {
		// userVO.setAssessDate(DateFormatUtil.DateObjectToString(user.getAssessDate()));
		// }

		if (user.isAssessStatus()) {
			userVO.setAssessStatus("已通过考核");
		} else {
			userVO.setAssessStatus("未通过考核");
		}
		if (user.getUserLevel().equals(UserLevel.CONSUMER)) {
			userVO.setAssessDate("未达到考核级别");
		} else {
			userVO.setAssessDate(DateFormatUtil.DateObjectToString(user.getAssessDate()));
		}

		userVO.setBonusCoin(MathUtil.floor(user.getBonusCoin()));
		userVO.setConsumeCoin(MathUtil.floor(user.getConsumeCoin()));
		userVO.setRepeatCoin(MathUtil.floor(user.getRepeatCoin()));
		userVO.setCreateTime(DateFormatUtil.DateObjectToString(user.getCreateTime()));

		userVO.setAccountA(user.getAccountA());
		userVO.setAccountB(user.getAccountB());
		userVO.setAccountC(user.getAccountC());

		userVO.setProductCoin(MathUtil.floor(user.getProductCoin()));
		userVO.setUserType(user.getUserType());

		if (user.isUserStatus())
			userVO.setUserStatus("已激活");
		else
			userVO.setUserStatus("未激活");

		userVO.setUserPhone(user.getUserPhone());
		String userLevelCH = BusinessUtil.getBusinessEntity(user.getUserLevel()).getUserLevel_CH();
		userVO.setUserLevel(userLevelCH);
		userVO.setNickName(user.getNickName());
		userVO.setUserPhone(user.getUserPhone());
		userVO.setIdentityNo(user.getIdentityNo());
		userVO.setProvince(user.getProvince());
		userVO.setCity(user.getCity());

		if (user.isPasswordReset()) {
			userVO.setPasswordReset("是");
		} else {
			userVO.setPasswordReset("否");
		}

		return userVO;
	}

	public static UserVO UserToUserVO(User user) {
		UserVO userVO = new UserVO();
		userVO.setAccount(user.getAccount());
		userVO.setActivateTime(DateFormatUtil.DateObjectToString(user.getActivateTime()));

		// if(DateFormatUtil.compareDateObject(user.getAssessDate(),user.getCreateTime())
		// == 0) {
		// userVO.setAssessDate("未达到考核级别");
		// }else {
		// userVO.setAssessDate(DateFormatUtil.DateObjectToString(user.getAssessDate()));
		// }

		if (user.isAssessStatus()) {
			userVO.setAssessStatus("已通过考核");
		} else {
			userVO.setAssessStatus("未通过考核");
		}
		if (user.getUserLevel().equals(UserLevel.CONSUMER)) {
			userVO.setAssessDate("未达到考核级别");
		} else {
			userVO.setAssessDate(DateFormatUtil.DateObjectToString(user.getAssessDate()));
		}

		userVO.setBonusCoin(MathUtil.floor(user.getBonusCoin()));
		userVO.setConsumeCoin(MathUtil.floor(user.getConsumeCoin()));
		userVO.setRepeatCoin(MathUtil.floor(user.getRepeatCoin()));

		userVO.setCreateTime(DateFormatUtil.DateObjectToString(user.getCreateTime()));

		userVO.setAccountA(user.getAccountA());
		userVO.setAccountB(user.getAccountB());
		userVO.setAccountC(user.getAccountC());

		userVO.setProductCoin(MathUtil.floor(user.getProductCoin()));
		userVO.setUserType(user.getUserType());

		if (user.isUserStatus())
			userVO.setUserStatus("已激活");
		else
			userVO.setUserStatus("未激活");

		userVO.setUserPhone(user.getUserPhone());
		String userLevelCH = BusinessUtil.getBusinessEntity(user.getUserLevel()).getUserLevel_CH();
		userVO.setUserLevel(userLevelCH);
		userVO.setNickName(user.getNickName());
		userVO.setUserPhone(user.getUserPhone());
		userVO.setIdentityNo(user.getIdentityNo());
		userVO.setProvince(user.getProvince());
		userVO.setCity(user.getCity());
		userVO.setAnnualFeeYear(user.getAnnualFeeYeal() == null ? "" : user.getAnnualFeeYeal());
		if (user.isPasswordReset()) {
			userVO.setPasswordReset("是");
		} else {
			userVO.setPasswordReset("否");
		}

		return userVO;
	}

	public static User addUser(User user, User parentUser, String departmentType) {
		if ("A".equals(departmentType)) {
			parentUser.setDepartmentA(user.getUserId());
		} else if ("B".equals(departmentType)) {
			parentUser.setDepartmentB(user.getUserId());
		} else if ("C".equals(departmentType)) {
			parentUser.setDepartmentC(user.getUserId());
		} else {
			return null;
		}
		user.setParentId(user.getUserId());
		return user;
	}

	public static User getTestUser() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date createDate = new Date();
		User u = new User();
		String name = "zs" + createDate.getTime();
		u.setNickName(name);
		u.setAccount(name);
		u.setPassword(MD5Util.encrypeByMd5("123456"));
		u.setCreateTime(createDate);
		u.setUserLevel(UserLevel.CONSUMER);
		u.setUserType(UserType.COMMON);
		u.setAssessStatus(true);
		u.setUserStatus(true);
		u.setAssessDate(createDate);
		return u;
	}

	public static User getTestUser(User parentUser, String departmentType) {
		return addUser(getTestUser(), parentUser, departmentType);
	}
}
