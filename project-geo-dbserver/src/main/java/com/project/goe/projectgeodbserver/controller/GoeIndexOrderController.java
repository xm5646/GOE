package com.project.goe.projectgeodbserver.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.project.goe.projectgeodbserver.entity.ExpressAddress;
import com.project.goe.projectgeodbserver.entity.OrderInfo;
import com.project.goe.projectgeodbserver.entity.User;
import com.project.goe.projectgeodbserver.service.ExpressAddressService;
import com.project.goe.projectgeodbserver.service.OrderInfoService;
import com.project.goe.projectgeodbserver.service.UserService;
import com.project.goe.projectgeodbserver.statusType.ConsumeType;
import com.project.goe.projectgeodbserver.statusType.DeliveryStatus;
import com.project.goe.projectgeodbserver.util.DateFormatUtil;
import com.project.goe.projectgeodbserver.viewentity.OrderInfoOfProductCoin;
import com.project.goe.projectgeodbserver.viewentity.OrderInfoOfReconsume;
import com.project.goe.projectgeodbserver.viewentity.OrderInfoVO;
import com.project.goe.projectgeodbserver.viewentity.RetMsg;

@RequestMapping("/goeIndexOrderController")
@RestController
@CrossOrigin
public class GoeIndexOrderController {
	@Autowired
	private UserService userService;

	@Autowired
	private ExpressAddressService expressAddressService;

	@Autowired
	private OrderInfoService orderInfoService;

	// 分页查询所有订单
	@GetMapping("/findAllOrders")
	public RetMsg findAllOrders(@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		RetMsg retMsg = null;
		Sort sort = null;

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			Page<OrderInfo> orderInfoPage = this.orderInfoService.findAllOrderInfo(pageable);

			Page<OrderInfoVO> orderInfoVOPage = orderInfoPage.map(new Converter<OrderInfo, OrderInfoVO>() {

				@Override
				public OrderInfoVO convert(OrderInfo orderInfo) {
					OrderInfoVO orderInfoVO = new OrderInfoVO();

					//取消从收货地址ID去查询收货地址信息
//					long expressId = orderInfo.getExpressId();
//					ExpressAddress expressAddress = expressAddressService.findByExpressId(expressId);

					long userId = orderInfo.getUserId();
					User user = userService.findByUserId(userId);

					orderInfoVO.setAccount(user.getAccount());
					
					String addr[] = new String[] {} ;
					if (orderInfo.getAddressCode() != null && orderInfo.getAddressDetail() != null) {
						String str = orderInfo.getAddressCode() + "," + orderInfo.getAddressDetail();
						addr = str.split(",");
					}
					orderInfoVO.setAddressInfo(addr);
					
					
					orderInfoVO.setCreateTime(DateFormatUtil.DateObjectToString(orderInfo.getCreateTime()));
					orderInfoVO.setDescription(orderInfo.getDescription());
					orderInfoVO.setIsDelivery(orderInfo.getIsDelivery());
					orderInfoVO.setOrderId(orderInfo.getOrderId());
					orderInfoVO.setOrderType(orderInfo.getOrderType());
					orderInfoVO.setPhone(orderInfo.getPhoneNumber());
					orderInfoVO.setProductCount(orderInfo.getProductCount());
					orderInfoVO.setReceiverName(orderInfo.getReceiveName());

					return orderInfoVO;
				}

			});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(orderInfoVOPage);
			retMsg.setSuccess(true);
			retMsg.setMessage("查询成功");

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}

	}

	// 基于account，分页查询用户所有订单
	@GetMapping("/findAllOrdersByAccount")
	public RetMsg findAllOrdersByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
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

			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setUserId(user.getUserId());
			Page<OrderInfo> orderInfoPage = this.orderInfoService.findOrderInfoByAccount(orderInfo, pageable);

			Page<OrderInfoVO> orderInfoVOPage = orderInfoPage.map(new Converter<OrderInfo, OrderInfoVO>() {

				@Override
				public OrderInfoVO convert(OrderInfo orderInfo) {
					OrderInfoVO orderInfoVO = new OrderInfoVO();

//					long expressId = orderInfo.getExpressId();
//					ExpressAddress expressAddress = expressAddressService.findByExpressId(expressId);

					long userId = orderInfo.getUserId();
					User user = userService.findByUserId(userId);

					orderInfoVO.setAccount(user.getAccount());
					
					String addr[] = new String[] {} ;
					if (orderInfo.getAddressCode() != null && orderInfo.getAddressDetail() != null) {
						String str = orderInfo.getAddressCode() + "," + orderInfo.getAddressDetail();
						addr = str.split(",");
					}
					orderInfoVO.setAddressInfo(addr);
					
					orderInfoVO.setCreateTime(DateFormatUtil.DateObjectToString(orderInfo.getCreateTime()));
					orderInfoVO.setDescription(orderInfo.getDescription());
					orderInfoVO.setIsDelivery(orderInfo.getIsDelivery());
					orderInfoVO.setOrderId(orderInfo.getOrderId());
					orderInfoVO.setOrderType(orderInfo.getOrderType());
					orderInfoVO.setPhone(orderInfo.getPhoneNumber());
					orderInfoVO.setProductCount(orderInfo.getProductCount());
					orderInfoVO.setReceiverName(orderInfo.getReceiveName());

					return orderInfoVO;
				}

			});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(orderInfoVOPage);
			retMsg.setSuccess(true);
			retMsg.setMessage("查询成功");

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}

	}

	// 分页查询所有待处理的重销订单:基于orderType=COIN_TRANSFER_RECONSUME、isDelivery=ORDER_DELIVERY_NO
	@GetMapping("/findAllOrdersOfReconsume")
	public RetMsg findAllOrdersOfReconsume(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		RetMsg retMsg = null;
		Sort sort = null;

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderType(ConsumeType.COIN_TRANSFER_RECONSUME);
			orderInfo.setIsDelivery(DeliveryStatus.ORDER_DELIVERY_NO);
			Page<OrderInfo> orderInfoPage = this.orderInfoService.findOrderInfoByOrderTypeAndIsDelivery(orderInfo,
					pageable);

			Page<OrderInfoOfReconsume> orderInfoOfReconsumePage = orderInfoPage
					.map(new Converter<OrderInfo, OrderInfoOfReconsume>() {

						@Override
						public OrderInfoOfReconsume convert(OrderInfo orderInfo) {
							OrderInfoOfReconsume orderInfoOfReconsume = new OrderInfoOfReconsume();

//							long expressId = orderInfo.getExpressId();
//							ExpressAddress expressAddress = expressAddressService.findByExpressId(expressId);

							long userId = orderInfo.getUserId();
							User user = userService.findByUserId(userId);

							orderInfoOfReconsume.setAccount(user.getAccount());
							
							String addr[] = new String[] {} ;
							if (orderInfo.getAddressCode() != null && orderInfo.getAddressDetail() != null) {
								String str = orderInfo.getAddressCode() + "," + orderInfo.getAddressDetail();
								addr = str.split(",");
							}
							orderInfoOfReconsume.setAddressInfo(addr);
						
							
							orderInfoOfReconsume
									.setCreateTime(DateFormatUtil.DateObjectToString(orderInfo.getCreateTime()));
							orderInfoOfReconsume.setDescription(orderInfo.getDescription());
							orderInfoOfReconsume.setOrderId(orderInfo.getOrderId());
							orderInfoOfReconsume.setPhone(orderInfo.getPhoneNumber());
							orderInfoOfReconsume.setReceiverName(orderInfo.getReceiveName());

							return orderInfoOfReconsume;
						}

					});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(orderInfoOfReconsumePage);
			retMsg.setSuccess(true);
			retMsg.setMessage("查询成功");

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}

	}

	// 基于account，分页查询用户待处理的重销订单::基于orderType=COIN_TRANSFER_RECONSUME、isDelivery=ORDER_DELIVERY_NO和account
	@GetMapping("/findAllOrdersOfReconsumeByAccount")
	public RetMsg findAllOrdersOfReconsumeByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
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

			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderType(ConsumeType.COIN_TRANSFER_RECONSUME);
			orderInfo.setIsDelivery(DeliveryStatus.ORDER_DELIVERY_NO);
			orderInfo.setUserId(user.getUserId());
			Page<OrderInfo> orderInfoPage = this.orderInfoService
					.findOrderInfoByOrderTypeAndIsDeliveryAndAccount(orderInfo, pageable);

			Page<OrderInfoOfReconsume> orderInfoOfReconsumePage = orderInfoPage
					.map(new Converter<OrderInfo, OrderInfoOfReconsume>() {

						@Override
						public OrderInfoOfReconsume convert(OrderInfo orderInfo) {
							OrderInfoOfReconsume orderInfoOfReconsume = new OrderInfoOfReconsume();

//							long expressId = orderInfo.getExpressId();
//							ExpressAddress expressAddress = expressAddressService.findByExpressId(expressId);

							long userId = orderInfo.getUserId();
							User user = userService.findByUserId(userId);

							orderInfoOfReconsume.setAccount(user.getAccount());
							
							String addr[] = new String[] {} ;
							if (orderInfo.getAddressCode() != null && orderInfo.getAddressDetail() != null) {
								String str = orderInfo.getAddressCode() + "," + orderInfo.getAddressDetail();
								addr = str.split(",");
							}
							orderInfoOfReconsume.setAddressInfo(addr);
							
							orderInfoOfReconsume
									.setCreateTime(DateFormatUtil.DateObjectToString(orderInfo.getCreateTime()));
							orderInfoOfReconsume.setDescription(orderInfo.getDescription());
							orderInfoOfReconsume.setOrderId(orderInfo.getOrderId());
							orderInfoOfReconsume.setPhone(orderInfo.getPhoneNumber());
							orderInfoOfReconsume.setReceiverName(orderInfo.getReceiveName());

							return orderInfoOfReconsume;
						}

					});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(orderInfoOfReconsumePage);
			retMsg.setSuccess(true);
			retMsg.setMessage("查询成功");

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}

	}

	// 分页查询所有待处理的积分订单:基于orderType=PRODCUTCOIN_TRANSFER_PRODUCT、isDelivery=ORDER_DELIVERY_NO
	@GetMapping("/findAllOrdersOfProductCoin")
	public RetMsg findAllOrdersOfProductCoin(
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
			@RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
		RetMsg retMsg = null;
		Sort sort = null;

		try {
			if (order.equals("asc"))
				sort = new Sort(Direction.ASC, keyword);
			else
				sort = new Sort(Direction.DESC, keyword);

			Pageable pageable = new PageRequest(pageNum, size, sort);

			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderType(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
			orderInfo.setIsDelivery(DeliveryStatus.ORDER_DELIVERY_NO);
			Page<OrderInfo> orderInfoPage = this.orderInfoService.findOrderInfoByOrderTypeAndIsDelivery(orderInfo,
					pageable);

			Page<OrderInfoOfProductCoin> orderInfoOfProductCoinPage = orderInfoPage
					.map(new Converter<OrderInfo, OrderInfoOfProductCoin>() {

						@Override
						public OrderInfoOfProductCoin convert(OrderInfo orderInfo) {
							OrderInfoOfProductCoin orderInfoOfProductCoin = new OrderInfoOfProductCoin();

//							long expressId = orderInfo.getExpressId();
//							ExpressAddress expressAddress = expressAddressService.findByExpressId(expressId);

							long userId = orderInfo.getUserId();
							User user = userService.findByUserId(userId);

							orderInfoOfProductCoin.setAccount(user.getAccount());
							orderInfoOfProductCoin.setProductCount(orderInfo.getProductCount());
							
							String addr[] = new String[] {} ;
							if (orderInfo.getAddressCode() != null && orderInfo.getAddressDetail() != null) {
								String str = orderInfo.getAddressCode() + "," + orderInfo.getAddressDetail();
								addr = str.split(",");
							}
							orderInfoOfProductCoin.setAddressInfo(addr);
							orderInfoOfProductCoin
									.setCreateTime(DateFormatUtil.DateObjectToString(orderInfo.getCreateTime()));
							orderInfoOfProductCoin.setDescription(orderInfo.getDescription());
							orderInfoOfProductCoin.setOrderId(orderInfo.getOrderId());
							orderInfoOfProductCoin.setPhone(orderInfo.getPhoneNumber());
							orderInfoOfProductCoin.setReceiverName(orderInfo.getReceiveName());

							return orderInfoOfProductCoin;
						}

					});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(orderInfoOfProductCoinPage);
			retMsg.setSuccess(true);
			retMsg.setMessage("查询成功");

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}

	}

	// 基于account，分页查询用户待处理的积分订单::基于orderType=PRODCUTCOIN_TRANSFER_PRODUCT、isDelivery=ORDER_DELIVERY_NO和account
	@GetMapping("/findAllOrdersOfProductCoinByAccount")
	public RetMsg findAllOrdersOfProductCoinByAccount(@RequestParam("account") String account,
			@RequestParam(value = "pageNum", defaultValue = "0", required = false) int pageNum,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size,
			@RequestParam(value = "keyword", required = false, defaultValue = "createTime") String keyword,
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

			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderType(ConsumeType.PRODCUTCOIN_TRANSFER_PRODUCT);
			orderInfo.setIsDelivery(DeliveryStatus.ORDER_DELIVERY_NO);
			orderInfo.setUserId(user.getUserId());
			Page<OrderInfo> orderInfoPage = this.orderInfoService
					.findOrderInfoByOrderTypeAndIsDeliveryAndAccount(orderInfo, pageable);

			Page<OrderInfoOfProductCoin> orderInfoOfProductCoinPage = orderInfoPage
					.map(new Converter<OrderInfo, OrderInfoOfProductCoin>() {

						@Override
						public OrderInfoOfProductCoin convert(OrderInfo orderInfo) {
							OrderInfoOfProductCoin orderInfoOfProductCoin = new OrderInfoOfProductCoin();

//							long expressId = orderInfo.getExpressId();
//							ExpressAddress expressAddress = expressAddressService.findByExpressId(expressId);

							long userId = orderInfo.getUserId();
							User user = userService.findByUserId(userId);

							orderInfoOfProductCoin.setAccount(user.getAccount());
							orderInfoOfProductCoin.setProductCount(orderInfo.getProductCount());
							
							String addr[] = new String[] {} ;
							if (orderInfo.getAddressCode() != null && orderInfo.getAddressDetail() != null) {
								String str = orderInfo.getAddressCode() + "," + orderInfo.getAddressDetail();
								addr = str.split(",");
							}
							orderInfoOfProductCoin.setAddressInfo(addr);
							
							orderInfoOfProductCoin
									.setCreateTime(DateFormatUtil.DateObjectToString(orderInfo.getCreateTime()));
							orderInfoOfProductCoin.setDescription(orderInfo.getDescription());
							orderInfoOfProductCoin.setOrderId(orderInfo.getOrderId());
							orderInfoOfProductCoin.setPhone(orderInfo.getPhoneNumber());
							orderInfoOfProductCoin.setReceiverName(orderInfo.getReceiveName());

							return orderInfoOfProductCoin;
						}

					});

			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(orderInfoOfProductCoinPage);
			retMsg.setSuccess(true);
			retMsg.setMessage("查询成功");

			return retMsg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}

	}

	//更新用户的订单信息
	@PostMapping("/updateOrderInfo")
	public RetMsg updateOrderInfo(OrderInfo orderInfo) {
		RetMsg retMsg = null;
		long orderId = orderInfo.getOrderId();
		
		OrderInfo oInfo = this.orderInfoService.findByOrderId(orderId);
		if(null == oInfo)
			throw new RuntimeException("订单不存在");
		
		oInfo.setIsDelivery(DeliveryStatus.ORDER__DELIVERY_OK);
		
		try {
			this.orderInfoService.save(oInfo);
			
			retMsg = new RetMsg();
			retMsg.setCode(200);
			retMsg.setData(DeliveryStatus.ORDER__DELIVERY_OK);
			retMsg.setMessage("订单信息更新成功");
			retMsg.setSuccess(true);
			
			return retMsg;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("订单信息更新失败");
		}
	}

}
