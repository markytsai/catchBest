package com.ilsxh.catchBest.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ilsxh.catchBest.dao.OrderDao;
import com.ilsxh.catchBest.domain.CatchBestOrder;
import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.domain.OrderInfo;
import com.ilsxh.catchBest.redis.OrderKey;
import com.ilsxh.catchBest.vo.GoodsVo;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;

	@Autowired
	RedisService redisService;

	public CatchBestOrder getCatchBestOrderByUserIdGoodsId(long userId, long goodsId) {
		// return orderDao.getCatchbestOrderByUserIdGoodsId(userId, goodsId);
		return redisService.get(OrderKey.getCatchBestOrderByUidGid, "" + userId + "_" + goodsId, CatchBestOrder.class);
	}

	@Transactional
	public OrderInfo createOrder(CatchBestUser user, GoodsVo goods) {

		// 创建订单
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSalePrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId = orderDao.insert(orderInfo);

		// 创建秒杀订单
		CatchBestOrder catchbestOrder = new CatchBestOrder();
		catchbestOrder.setGoodsId(goods.getId()); // 商品ID
		catchbestOrder.setOrderId(orderId); // 订单ID
		catchbestOrder.setUserId(user.getId()); // 用户ID
		orderDao.insertCatchbestOrder(catchbestOrder);

		redisService.set(OrderKey.getCatchBestOrderByUidGid, "" + user.getId() + "_" + goods.getId(), catchbestOrder);

		return orderInfo;
	}

	public OrderInfo getOrderById(long orderId) {

		return orderDao.getOrderById(orderId);

	}

	public void deleteOrders() {
		orderDao.deleteOrders();
		orderDao.deleteCatchBestOrders();
	}
}
