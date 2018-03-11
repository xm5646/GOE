package com.project.goe.projectgeodbserver.util;

import com.project.goe.projectgeodbserver.entity.Earning;
import com.project.goe.projectgeodbserver.viewentity.EarningVO;

public class EarningUtil {
	public void getUserLeave() {
		
	}
	
	public static EarningVO earningToEarningVO(Earning earning) {
		EarningVO earningVO = new EarningVO();
		
		earningVO.setAccount(earning.getAccount());
		earningVO.setCreateTime(DateFormatUtil.DateObjectToString(earning.getCreateTime()));
		earningVO.setDayMoney(MathUtil.floor(earning.getDayMoney()));
		earningVO.setEndTime(DateFormatUtil.DateObjectToString(earning.getEndTime()));
		earningVO.setSurplusNumber(earning.getSurplusNumber());
		earningVO.setTouchType(earning.getTouchType());
		earningVO.setUserLevel(UserLevelUtil.transfer(earning.getUserLevel()));
		
		return earningVO;
	}
}
