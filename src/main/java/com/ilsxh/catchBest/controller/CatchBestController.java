package com.ilsxh.catchBest.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilsxh.catchBest.domain.CatchBestOrder;
import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.domain.OrderInfo;
import com.ilsxh.catchBest.rabbitmq.CatchBestMessage;
import com.ilsxh.catchBest.rabbitmq.MQSender;
import com.ilsxh.catchBest.redis.CatchBestKey;
import com.ilsxh.catchBest.redis.GoodsKey;
import com.ilsxh.catchBest.redis.OrderKey;
import com.ilsxh.catchBest.result.CodeMsg;
import com.ilsxh.catchBest.result.Result;
import com.ilsxh.catchBest.service.CatchBestService;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.GoodsService;
import com.ilsxh.catchBest.service.OrderService;
import com.ilsxh.catchBest.service.RedisService;
import com.ilsxh.catchBest.vo.GoodsVo;

@Controller
@RequestMapping("/catchbest")
public class CatchBestController implements InitializingBean {

	@Autowired
	CatchBestUserService userService;

	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	CatchBestService catchBestService;

	@Autowired
	MQSender sender;
	private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

	@RequestMapping("/catchbest")
	public String list2(Model model, CatchBestUser user, @RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return "login";
		}
		// 判断库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if (stock <= 0) {
			model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
			return "catchbest_fail";
		}
		// 判断是否已经秒杀到了
		CatchBestOrder order = orderService.getCatchBestOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
			return "catchbest_fail";
		}

		// 减库存 下订单 写入秒杀订单
		OrderInfo orderInfo = catchBestService.catchbest(user, goods);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
		return "order_detail";
	}

	@RequestMapping(value = "/do_catchbest", method = RequestMethod.POST)
	@ResponseBody
	public Result<Integer> doBest(Model model, CatchBestUser user, @RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}

		// 内存标记，减少redis访问
		boolean over = localOverMap.get(goodsId);
		if (over) {
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}

		long stock = RedisService.decr(GoodsKey.getCatchBestGoodsStock, "" + goodsId);// 10
		if (stock < 0) {
			localOverMap.put(goodsId, true);
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		// 判断是否已经秒杀到了
		CatchBestOrder order = orderService.getCatchBestOrderByUserIdGoodsId(user.getId(), goodsId);
		if (order != null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}

		// 入队
		CatchBestMessage mm = new CatchBestMessage();
		mm.setUser(user);
		mm.setGoodsId(goodsId);
		sender.sendMessage(mm);
		return Result.success(0);// 排队中

		/*
		 * // 判断库存 GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		 * int stock = goods.getStockCount(); if (stock <= 0) { return
		 * Result.error(CodeMsg.MIAO_SHA_OVER); } // 判断是否已经秒杀到了 CatchBestOrder
		 * order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),
		 * goodsId); if (order != null) { return
		 * Result.error(CodeMsg.REPEATE_MIAOSHA); }
		 * 
		 * // 减库存 下订单 写入秒杀订单 OrderInfo orderInfo =
		 * catchBestService.catchbest(user, goods); return
		 * Result.success(orderInfo);
		 */
	}

	/**
	 * orderId：成功 -1：秒杀失败 0： 排队中
	 */
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	@ResponseBody
	public Result<Long> catchbestResult(Model model, CatchBestUser user, @RequestParam("goodsId") long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		long result = catchBestService.getCatchBestResult(user.getId(), goodsId);
		return Result.success(result);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	@ResponseBody
	public Result<Boolean> reset(Model model) {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		for (GoodsVo goods : goodsList) {
			goods.setStockCount(10);
			redisService.set(GoodsKey.getCatchBestGoodsStock, "" + goods.getId(), 10);
			localOverMap.put(goods.getId(), false);
		}
		redisService.delete(OrderKey.getCatchBestOrderByUidGid);
		redisService.delete(CatchBestKey.isGoodsOver);
		catchBestService.reset(goodsList);
		return Result.success(true);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();

		if (goodsList == null) {
			return;
		}

		for (GoodsVo goodsVo : goodsList) {
			redisService.set(GoodsKey.getCatchBestGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
		}
	}
}
