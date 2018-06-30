package com.ilsxh.catchBest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.domain.OrderInfo;
import com.ilsxh.catchBest.vo.GoodsVo;

@Service
public class CatchBestService {
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;

	/**
	 * 
	 * @param user
	 * @param goods
	 * @return 返回订单详情
	 */
	@Transactional
	public OrderInfo catchbest(CatchBestUser user, GoodsVo goods) {
		//减库存 下订单 写入秒杀订单
		goodsService.reduceStock(goods);
		//order_info maiosha_order
		return orderService.createOrder(user, goods);
	}
	
}
