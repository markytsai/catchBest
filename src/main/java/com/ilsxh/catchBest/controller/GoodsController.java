package com.ilsxh.catchBest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.GoodsService;
import com.ilsxh.catchBest.service.RedisService;
import com.ilsxh.catchBest.vo.GoodsVo;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	CatchBestUserService userService;

	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;

	@RequestMapping("/to_list")
	public String list(Model model, CatchBestUser user) {
		model.addAttribute("user", user);
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
		return "goods_list";
	}

	@RequestMapping("/to_detail/{goodsId}")
	public String detail(Model model, CatchBestUser user, @PathVariable("goodsId") long goodsId) {
		model.addAttribute("user", user);

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		long startAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int catchbestStatus = 0;
		int remainSeconds = 0;
		if (now < startAt) {// 秒杀还没开始，倒计时
			catchbestStatus = 0;
			remainSeconds = (int) ((startAt - now) / 1000);
		} else if (now > endAt) {// 秒杀已经结束
			catchbestStatus = 2;
			remainSeconds = -1;
		} else {// 秒杀进行中
			catchbestStatus = 1;
			remainSeconds = 0;
		}
		model.addAttribute("catchbestStatus", catchbestStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
	}

}
