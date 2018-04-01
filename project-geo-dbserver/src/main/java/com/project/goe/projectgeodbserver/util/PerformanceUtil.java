package com.project.goe.projectgeodbserver.util;

import com.project.goe.projectgeodbserver.entity.Performance;
import com.project.goe.projectgeodbserver.viewentity.PerformanceVO;

public class PerformanceUtil {

	public static PerformanceVO pToPerformanceVO(Performance performance) {
		if(null == performance)
			return null;
		
		PerformanceVO pVO = new PerformanceVO();
		pVO.setAddDepartAcount(performance.getAddDepartAcount());
		pVO.setAddDepartBcount(performance.getAddDepartBcount());
		pVO.setAddDepartCcount(performance.getAddDepartCcount());

		pVO.setDepartAcount(performance.getDepartAcount());
		pVO.setDepartBcount(performance.getDepartBcount());
		pVO.setDepartCcount(performance.getDepartCcount());

		return pVO;
	}
}
