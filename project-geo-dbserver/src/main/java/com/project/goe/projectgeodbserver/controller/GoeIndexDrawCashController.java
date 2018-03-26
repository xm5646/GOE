package com.project.goe.projectgeodbserver.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.goe.projectgeodbserver.entity.CardInfo;
import com.project.goe.projectgeodbserver.entity.DrawCashRecord;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.CardInfoService;
import com.project.goe.projectgeodbserver.service.DrawCashService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.DrawStatus;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;
import com.project.goe.projectgeodbserver.util.MathUtil;
import com.project.goe.projectgeodbserver.viewentity.DrawCashRecordOfAuditWait;
import com.project.goe.projectgeodbserver.viewentity.DrawCashRecordVO;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RestController
@RequestMapping("/goeIndexDrawCash")
@CrossOrigin
public class GoeIndexDrawCashController {
	@Autowired
	private DrawCashService drawCashService;

	@Autowired
	private CardInfoService cardInfoService;

	@Autowired
	private UserService userService;

	// 分页查询"待审核"状态的所有提现记录
	@GetMapping("/findByDrawStatusOfAuditWait")
	public RetMsg findByDrawStatusOfAuditWait(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "drawCommitTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		Sort sort = null;
		RetMsg retMsg = null;

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);
			Page<DrawCashRecord> drawCashRecordPage = this.drawCashService.findByDrawStatus(DrawStatus.AUDIT_WAIT,
					pageable);

			Page<DrawCashRecordOfAuditWait> drawCashRecordOfAuditWaitPage = drawCashRecordPage
					.map(new Converter<DrawCashRecord, DrawCashRecordOfAuditWait>() {

						@Override
						public DrawCashRecordOfAuditWait convert(DrawCashRecord drawCashRecord) {
							DrawCashRecordOfAuditWait drawCashRecordOfAuditWait = new DrawCashRecordOfAuditWait();

							long cardInfoId = drawCashRecord.getCardInfoId();
							long userId = drawCashRecord.getUserId();

							CardInfo cardInfo = cardInfoService.findByCardInfoId(cardInfoId);
							User user = userService.getUserById(userId);
							drawCashRecordOfAuditWait.setCommitTime(DateFormatUtil.DateObjectToString(drawCashRecord.getDrawCommitTime()));
							drawCashRecordOfAuditWait.setBankName(cardInfo.getBankName());
							drawCashRecordOfAuditWait.setAccount(user.getAccount());
							drawCashRecordOfAuditWait.setCardNumber(cardInfo.getCardNumber());
							drawCashRecordOfAuditWait.setCardOwnerName(cardInfo.getCardOwnerName());
							drawCashRecordOfAuditWait.setDrawId(drawCashRecord.getDrawId());
							drawCashRecordOfAuditWait.setDrawStatus(drawCashRecord.getDrawStatus());
							drawCashRecordOfAuditWait.setFinalNumber(MathUtil.floor(drawCashRecord.getFinalNumber()));
							drawCashRecordOfAuditWait.setPhone(drawCashRecord.getPhone());

							return drawCashRecordOfAuditWait;
						}

					});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(drawCashRecordOfAuditWaitPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}

	// 查询指定用户，"待审核"状态的提现记录
	@GetMapping("/findDrawStatusOfAuditWaitByAccount")
	public RetMsg findDrawStatusOfAuditWaitByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "drawCommitTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		RetMsg retMsg = null;
		Sort sort = null;

		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在");

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);
			Page<DrawCashRecord> drawCashRecordPage = this.drawCashService
					.findByDrawStatusAndUserId(DrawStatus.AUDIT_WAIT, user.getUserId(), pageable);

			Page<DrawCashRecordOfAuditWait> drawCashRecordOfAuditWaitPage = drawCashRecordPage
					.map(new Converter<DrawCashRecord, DrawCashRecordOfAuditWait>() {

						@Override
						public DrawCashRecordOfAuditWait convert(DrawCashRecord drawCashRecord) {
							DrawCashRecordOfAuditWait drawCashRecordOfAuditWait = new DrawCashRecordOfAuditWait();

							long cardInfoId = drawCashRecord.getCardInfoId();
							long userId = drawCashRecord.getUserId();

							CardInfo cardInfo = cardInfoService.findByCardInfoId(cardInfoId);
							User user = userService.getUserById(userId);

							drawCashRecordOfAuditWait.setBankName(cardInfo.getBankName());
							drawCashRecordOfAuditWait.setAccount(user.getAccount());
							drawCashRecordOfAuditWait.setCardNumber(cardInfo.getCardNumber());
							drawCashRecordOfAuditWait.setCardOwnerName(cardInfo.getCardOwnerName());
							drawCashRecordOfAuditWait.setDrawId(drawCashRecord.getDrawId());
							drawCashRecordOfAuditWait.setDrawStatus(drawCashRecord.getDrawStatus());
							drawCashRecordOfAuditWait.setFinalNumber(MathUtil.floor(drawCashRecord.getFinalNumber()));
							drawCashRecordOfAuditWait.setPhone(drawCashRecord.getPhone());

							return drawCashRecordOfAuditWait;
						}

					});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(drawCashRecordOfAuditWaitPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}
	
	//更新用户提现状态信息
	@PostMapping("/updateDrawCashStaus")
	public RetMsg updateDrawCashStaus(long drawId,String drawStatus) {
		DrawCashRecord drawCashRecord = this.drawCashService.findDrawCashRecordByDrawId(drawId);
		if(null == drawCashRecord)
			throw new RuntimeException("提现记录不存在");
		
		switch(drawStatus) {
			case DrawStatus.AUDIT_NO_PASS:
				break;
			case DrawStatus.AUDIT_WAIT:
				break;
			case DrawStatus.TRANSFERED:
				break;
			default:
				throw new RuntimeException("提现状态参数有误");
		}
		
		drawCashRecord.setDrawStatus(drawStatus);
		drawCashRecord.setPayTime(new Date());
		this.drawCashService.save(drawCashRecord);
		RetMsg retMsg = null;
		
		try {
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData("提现状态更新成功");
			retMsg.setMessage("提现状态更新成功");
			retMsg.setSuccess(true);
			
			return retMsg;
		}catch(Exception e) {
			throw new RuntimeException("提现状态更新失败");
		}
	}

	// 分页查询所有用户的提现信息(基于提现申请时间)
	@GetMapping("/findAllDrawCashRecord")
	public RetMsg findAllDrawCashRecord(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "drawCommitTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		Sort sort = null;
		RetMsg retMsg = null;
		
		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);
			Page<DrawCashRecord> drawCashRecordPage = this.drawCashService.findAllDrawCardRecordBySort(pageable);

			Page<DrawCashRecordVO> drawCashRecordVOPage = drawCashRecordPage
					.map(new Converter<DrawCashRecord, DrawCashRecordVO>() {

						@Override
						public DrawCashRecordVO convert(DrawCashRecord drawCashRecord) {
							DrawCashRecordVO drawCashRecordVO = new DrawCashRecordVO();

							long cardInfoId = drawCashRecord.getCardInfoId();
							long userId = drawCashRecord.getUserId();

							CardInfo cardInfo = cardInfoService.findByCardInfoId(cardInfoId);
							User user = userService.getUserById(userId);

							drawCashRecordVO.setBankName(cardInfo.getBankName());
							drawCashRecordVO.setAccount(user.getAccount());
							drawCashRecordVO.setCardNumber(cardInfo.getCardNumber());
							drawCashRecordVO.setCardOwnerName(cardInfo.getCardOwnerName());
							drawCashRecordVO.setDrawId(drawCashRecord.getDrawId());
							drawCashRecordVO.setDrawStatus(drawCashRecord.getDrawStatus());
							drawCashRecordVO.setFinalNumber(MathUtil.floor(drawCashRecord.getFinalNumber()));
							drawCashRecordVO.setPayTime(DateFormatUtil.DateObjectToString(drawCashRecord.getPayTime()));
							drawCashRecordVO.setCommitTime(DateFormatUtil.DateObjectToString(drawCashRecord.getDrawCommitTime()));
							drawCashRecordVO.setPhone(drawCashRecord.getPhone());

							return drawCashRecordVO;
						}

					});
			
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(drawCashRecordVOPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}
	
	// 分页查询指定用户的提现记录
	@GetMapping("/findAllDrawCashRecordByAccount")
	public RetMsg findAllDrawCashRecordByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "drawCommitTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		Sort sort = null;
		RetMsg retMsg = null;
		
		User user = this.userService.findByAccount(account);
		if (null == user)
			throw new RuntimeException("用户不存在");
		
		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);
			DrawCashRecord drawCashRecord = new DrawCashRecord();
			drawCashRecord.setUserId(user.getUserId());
			
			Page<DrawCashRecord> drawCashRecordPage = this.drawCashService.findAll(drawCashRecord, pageable);

			Page<DrawCashRecordVO> drawCashRecordVOPage = drawCashRecordPage
					.map(new Converter<DrawCashRecord, DrawCashRecordVO>() {

						@Override
						public DrawCashRecordVO convert(DrawCashRecord drawCashRecord) {
							DrawCashRecordVO drawCashRecordVO = new DrawCashRecordVO();

							long cardInfoId = drawCashRecord.getCardInfoId();
							long userId = drawCashRecord.getUserId();

							CardInfo cardInfo = cardInfoService.findByCardInfoId(cardInfoId);
							User user = userService.getUserById(userId);

							drawCashRecordVO.setBankName(cardInfo.getBankName());
							drawCashRecordVO.setAccount(user.getAccount());
							drawCashRecordVO.setCardNumber(cardInfo.getCardNumber());
							drawCashRecordVO.setCardOwnerName(cardInfo.getCardOwnerName());
							drawCashRecordVO.setDrawId(drawCashRecord.getDrawId());
							drawCashRecordVO.setDrawStatus(drawCashRecord.getDrawStatus());
							drawCashRecordVO.setFinalNumber(MathUtil.floor(drawCashRecord.getFinalNumber()));
							drawCashRecordVO.setPayTime(DateFormatUtil.DateObjectToString(drawCashRecord.getPayTime()));
							drawCashRecordVO.setCommitTime(DateFormatUtil.DateObjectToString(drawCashRecord.getDrawCommitTime()));
							drawCashRecordVO.setPhone(drawCashRecord.getPhone());

							return drawCashRecordVO;
						}

					});
			
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(drawCashRecordVOPage);
			retMsg.setMessage("查询成功");
			retMsg.setSuccess(true);

			return retMsg;
		} catch (Exception e) {
			throw new RuntimeException("查询失败");
		}
	}
}
