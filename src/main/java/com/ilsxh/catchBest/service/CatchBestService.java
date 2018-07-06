package com.ilsxh.catchBest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ilsxh.catchBest.domain.CatchBestOrder;
import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.domain.OrderInfo;
import com.ilsxh.catchBest.redis.CatchBestKey;
import com.ilsxh.catchBest.vo.GoodsVo;

@Service
public class CatchBestService {

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	RedisService redisService;

	/**
	 * 
	 * @param user
	 * @param goods
	 * @return 返回订单详情
	 */
	@Transactional
	public OrderInfo catchbest(CatchBestUser user, GoodsVo goods) {
		// 减库存 下订单 写入秒杀订单
		boolean success = goodsService.reduceStock(goods);
		// order_info maiosha_order
		if (success) {
			return orderService.createOrder(user, goods);
		} else {
			setGoodsOver(goods.getId());
			return null;
		}
	}

	public long getCatchBestResult(Long userId, long goodsId) {
		CatchBestOrder order = orderService.getCatchBestOrderByUserIdGoodsId(userId, goodsId);
		if (order != null) {
			return order.getOrderId();
		} else {
			boolean isOver = getGoodsOver(goodsId);
			if (isOver) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	private void setGoodsOver(Long goodsId) {
		redisService.set(CatchBestKey.isGoodsOver, "" + goodsId, true);
	}

	private boolean getGoodsOver(long goodsId) {
		return redisService.exists(CatchBestKey.isGoodsOver, "" + goodsId);

	}

	public void reset(List<GoodsVo> goodsList) {
		goodsService.resetStock(goodsList);
		orderService.deleteOrders();
	}

}
