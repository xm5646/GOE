package com.project.goe.projectgeodbserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.DrawCashService;
import com.project.goe.projectgeodbserver.service.OrderInfoService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.statusType.DeliveryStatus;
import com.project.goe.projectgeodbserver.statusType.DrawStatus;
import com.project.goe.projectgeodbserver.viewentity.FinanceOfAll;
import com.project.goe.projectgeodbserver.viewentity.FinanceOfMonth;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@CrossOrigin
@RequestMapping("/dataStatistics")
public class DataStatisticsController {
	@Autowired
	private UserService userService;

	@Autowired
	private ConsumeRecordService consumeRecordService;

	@Autowired
	private BonusPayListService bonusPayListService;
	
	@Autowired
	private DrawCashService drawCashService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	// 本月新增用户数量
	@GetMapping("/newUsersOfNowMonth")
	public RetMsg newUsersOfNowMonth() {
		try {
			RetMsg retMsg = new RetMsg();
			long count = this.userService.findByCreateTimeBetweenDate().size();
			retMsg.setCode(200);
			retMsg.setData(count);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);
			return retMsg;
		}catch(Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 上一周新增用户数量:数组第一个元素为上周日的新增用户数，依次类推
	@GetMapping("/newUsersOfLastWeek")
	public RetMsg newUsersOfLastWeek() {
		try {
			RetMsg retMsg = new RetMsg();
			
			int[] countArr = this.userService.findByCreateTimeBetweenWeek();
			retMsg.setCode(200);
			retMsg.setData(countArr);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);
			
			return retMsg;
		}catch(Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 公司当月截止目前累积收入和累计支出
	@GetMapping("/financeOfMonth")
	public FinanceOfMonth financeOfMonth() {
		FinanceOfMonth finance = new FinanceOfMonth();
		// 本月累计收入
		double accumulateEarningOfMonth = 0;
		// 本月累计支出
		double accumulateCostOfMonth = 0;

		List<BonusPayList> bonusPayLists = this.bonusPayListService.findByPayTimeOfNowMonth();
		List<ConsumeRecord> consumeRecordList = this.consumeRecordService.findByConsumeTimeOfNowMonth();

		for (ConsumeRecord consumeRecord : consumeRecordList) {
			accumulateEarningOfMonth += consumeRecord.getConsumeNumber();
		}

		for (BonusPayList bonusPayList : bonusPayLists) {
			accumulateCostOfMonth += bonusPayList.getBonusNumber() + bonusPayList.getProductCoinNumber();
		}

		finance.setAccumulateCostOfMonth(accumulateCostOfMonth);;
		finance.setAccumulateEarningOfMonth(accumulateEarningOfMonth);

		return finance;
	}
	
	//查询待审核的提现记录数量
	@GetMapping("/countOfAuditWait")
	public long countOfAuditWait() {
		return  this.drawCashService.findByDrawStatus(DrawStatus.AUDIT_WAIT).size();
	}
	
	//查询未发货的订单记录数量
	@GetMapping("/countOfOrderNODelivery")
	public long countOfOrderNODelivery() {
		return this.orderInfoService.findByIsDelivery(DeliveryStatus.ORDER_DELIVERY_NO).size();
	}
	
	//查询公司累计总收入和公司累计发放总金额
	@GetMapping("/financeOfAll")
	public FinanceOfAll financeOfAll() {
		FinanceOfAll financeOfAll = new FinanceOfAll();
		double accumulateEarning = 0;
		double productCoinCost = 0;
		double managementCost = 0;
		double bonusPaymentCost = 0;
		
		// 报单
		List<ConsumeRecord> consumeRecordListOfAddUser = this.consumeRecordService.findByConsumeType(ConsumeType.COIN_TRANSFER_ADDCONSUMER);
		for(ConsumeRecord consumeRecord : consumeRecordListOfAddUser) {
			accumulateEarning += consumeRecord.getConsumeNumber();
		}
		// 重销
		List<ConsumeRecord> consumeRecordListOfReconsume = this.consumeRecordService.findByConsumeType(ConsumeType.COIN_TRANSFER_RECONSUME);
		for(ConsumeRecord consumeRecord : consumeRecordListOfReconsume) {
			accumulateEarning += consumeRecord.getConsumeNumber();
		}
		
		financeOfAll.setAccumulateEarning(accumulateEarning);
		
		List<BonusPayList> bonusPayLists = this.bonusPayListService.findAll();
		for(BonusPayList bonusPayList : bonusPayLists) {
			productCoinCost += bonusPayList.getProductCoinNumber();
			managementCost += bonusPayList.getManageCost();
			bonusPaymentCost += bonusPayList.getBonusNumber();
		}
		financeOfAll.setBonusPaymentCost(bonusPaymentCost);
		financeOfAll.setManagementCost(managementCost);
		financeOfAll.setProductCoinCost(productCoinCost);
		
		return financeOfAll;
	}
	
	
}
