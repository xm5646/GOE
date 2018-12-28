package com.project.goe.projectgeodbserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.BonusPayList;
import com.project.goe.projectgeodbserver.entity.ConsumeRecord;
import com.project.goe.projectgeodbserver.entity.OrderInfo;
import com.project.goe.projectgeodbserver.service.BonusPayListService;
import com.project.goe.projectgeodbserver.service.ConsumeRecordService;
import com.project.goe.projectgeodbserver.service.DrawCashService;
import com.project.goe.projectgeodbserver.service.OrderInfoService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.statusType.DeliveryStatus;
import com.project.goe.projectgeodbserver.statusType.DrawStatus;
import com.project.goe.projectgeodbserver.viewentity.FinanceOfAll;
import com.project.goe.projectgeodbserver.viewentity.FinanceOfDay;
import com.project.goe.projectgeodbserver.viewentity.FinanceOfMonth;
import com.project.goe.projectgeodbserver.viewentity.OrderStatusCodeRequest;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/goeIndex")
@CrossOrigin
public class GoeIndexController {
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 本日新增会员数量
	@GetMapping("/newUsersOfNowDay")
	public RetMsg newUsersOfNowDay() {
		RetMsg retMsg = null;

		try {
			int count = this.userService.findByCreateTimeBetweenNowDay();

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(count);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 公司当日累计收入和累计支出
	@GetMapping("/financeOfDay")
	public RetMsg financeOfDay() {
		FinanceOfDay finance = new FinanceOfDay();
		// 本月累计收入
		double accumulateEarningOfDay = 0;
		// 本月累计支出
		double accumulateCostOfDay = 0;
		RetMsg retMsg = null;

		try {

			List<BonusPayList> bonusPayLists = this.bonusPayListService.findByPayTimeOfNowDay();
			List<ConsumeRecord> consumeRecordList = this.consumeRecordService.findByConsumeTimeOfNowDay();

			for (ConsumeRecord consumeRecord : consumeRecordList) {
				accumulateEarningOfDay += consumeRecord.getConsumeNumber();
			}

			for (BonusPayList bonusPayList : bonusPayLists) {
				accumulateCostOfDay += bonusPayList.getBonusNumber() + bonusPayList.getProductCoinNumber();
			}

			finance.setAccumulateCostOfDay(accumulateCostOfDay);
			finance.setAccumulateEarningOfDay(accumulateEarningOfDay);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(finance);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 公司当月截止目前累积收入和累计支出
	@GetMapping("/financeOfMonth")
	public RetMsg financeOfMonth() {
		FinanceOfMonth finance = new FinanceOfMonth();
		// 本月累计收入
		double accumulateEarningOfMonth = 0;
		// 本月累计支出
		double accumulateCostOfMonth = 0;
		RetMsg retMsg = null;

		try {

			List<BonusPayList> bonusPayLists = this.bonusPayListService.findByPayTimeOfNowMonth();
			List<ConsumeRecord> consumeRecordList = this.consumeRecordService.findByConsumeTimeOfNowMonth();

			for (ConsumeRecord consumeRecord : consumeRecordList) {
				accumulateEarningOfMonth += consumeRecord.getConsumeNumber();
			}

			for (BonusPayList bonusPayList : bonusPayLists) {
				accumulateCostOfMonth += bonusPayList.getBonusNumber() + bonusPayList.getProductCoinNumber();
			}

			finance.setAccumulateCostOfMonth(accumulateCostOfMonth);
			finance.setAccumulateEarningOfMonth(accumulateEarningOfMonth);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(finance);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 查询待审核的提现记录数量
	@GetMapping("/countOfAuditWait")
	public RetMsg countOfAuditWait() {
		RetMsg retMsg = null;

		try {
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(this.drawCashService.findByDrawStatus(DrawStatus.AUDIT_WAIT).size());
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 查询未发货的订单记录数量:重销未发货、积分兑换未发货
	// consumeTypeCode: 5 积分兑换 6 重销
	// deliveryStatusCode： 1 未发货 2 已发货
	@GetMapping("/countOfOrderStatus")
	public RetMsg countOfOrderNODelivery(OrderStatusCodeRequest orderStatusCodeRequest) {
		int consumeTypeCode = orderStatusCodeRequest.getConsumeTypeCode();
		int deliveryStatusCode = orderStatusCodeRequest.getDeliveryStatusCode();
		RetMsg retMsg = null;
		//订单类型
		String orderType = null;
		String deliveryStatus = null;

		switch (consumeTypeCode) {
		case 5:
			orderType = ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT;
			break;
		case 6:
			orderType = ConsumeType.COIN_TRANSFER_RECONSUME;
			break;
		default:
			throw new RuntimeException("订单类型状态码有误");
		}

		switch (deliveryStatusCode) {
		case 1:
			deliveryStatus = DeliveryStatus.ORDER_DELIVERY_NO;
			break;
		case 2:
			deliveryStatus = DeliveryStatus.ORDER__DELIVERY_OK;
			break;
		default:
			throw new RuntimeException("发货状态码有误");
		}

		try {
			retMsg = new RetMsg();
			retMsg.setCode(200);
			List<OrderInfo> orderInfoList = this.orderInfoService.findByIsDeliveryAndOrderType(deliveryStatus,orderType);
			retMsg.setData(orderInfoList.size());
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}
	}

	// 查询公司累计总收入和公司累计发放总金额
	@GetMapping("/financeOfAll")
	public RetMsg financeOfAll() {
		FinanceOfAll financeOfAll = new FinanceOfAll();
		RetMsg retMsg = null;
		double accumulateEarning = 0;
		double productCoinCost = 0;
		double managementCost = 0;
		double bonusPaymentCost = 0;
		double repeatCoinCost = 0;

		try {
			// 报单
			List<ConsumeRecord> consumeRecordListOfAddUser = this.consumeRecordService
					.findByConsumeType(ConsumeType.COIN_TRANSFER_ADDCONSUMER);
			for (ConsumeRecord consumeRecord : consumeRecordListOfAddUser) {
				accumulateEarning += consumeRecord.getConsumeNumber();
			}
			// 重销
			List<ConsumeRecord> consumeRecordListOfReconsume = this.consumeRecordService
					.findByConsumeType(ConsumeType.COIN_TRANSFER_RECONSUME);
			for (ConsumeRecord consumeRecord : consumeRecordListOfReconsume) {
				accumulateEarning += consumeRecord.getConsumeNumber();
			}

			financeOfAll.setAccumulateEarning(accumulateEarning);

			List<BonusPayList> bonusPayLists = this.bonusPayListService.findAll();
			for (BonusPayList bonusPayList : bonusPayLists) {
				productCoinCost += bonusPayList.getProductCoinNumber();
				managementCost += bonusPayList.getManageCost();
				bonusPaymentCost += bonusPayList.getBonusNumber();
				repeatCoinCost += bonusPayList.getRepeatCoinNumber();
			}
			financeOfAll.setBonusPaymentCost(bonusPaymentCost);
			financeOfAll.setManagementCost(managementCost);
			financeOfAll.setProductCoinCost(productCoinCost);
			financeOfAll.setRepeatCoinCost(repeatCoinCost);

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(financeOfAll);
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

}
