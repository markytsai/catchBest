package com.ilsxh.catchBest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ilsxh.catchBest.domain.CatchBestOrder;
import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.domain.OrderInfo;
import com.ilsxh.catchBest.result.CodeMsg;
import com.ilsxh.catchBest.service.CatchBestService;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.GoodsService;
import com.ilsxh.catchBest.service.OrderService;
import com.ilsxh.catchBest.service.RedisService;
import com.ilsxh.catchBest.vo.GoodsVo;

@Controller
@RequestMapping("/catchbest")
public class CatchBestController {

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

	@RequestMapping("/do_catchbest")
	public String list(Model model, CatchBestUser user, @RequestParam("goodsId") long goodsId) {
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
		CatchBestOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
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
}
