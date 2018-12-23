package com.project.goe.projectgeodbserver.util;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.viewentity.BonusVO;

public class BonusUtil {
	
	private BonusUtil() {}
	
	public static BonusVO bonusToBonusVO(BonusPayList bonusPayList) {
		BonusVO bonusVO = new BonusVO();
		
		bonusVO.setAccount(bonusPayList.getAccount());
		bonusVO.setBonusNumber(MathUtil.floor(bonusPayList.getBonusNumber()));
		bonusVO.setRepeatCoinNumber(MathUtil.floor(bonusPayList.getRepeatCoinNumber()));
		bonusVO.setCreateTime(DateFormatUtil.DateObjectToString(bonusPayList.getCreateTime()));
		bonusVO.setManageCost(MathUtil.floor(bonusPayList.getManageCost()));
		bonusVO.setPayTime(DateFormatUtil.DateObjectToString(bonusPayList.getPayTime()));
		bonusVO.setProductCoinNumber(MathUtil.floor(bonusPayList.getProductCoinNumber()));
		bonusVO.setTotalMoney(MathUtil.floor(bonusPayList.getTotalMoney()));
		bonusVO.setTouchType(bonusPayList.getTouchType());
		
		return bonusVO;
	}
}
