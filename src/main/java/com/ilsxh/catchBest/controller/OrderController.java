package com.ilsxh.catchBest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.domain.OrderInfo;
import com.ilsxh.catchBest.result.CodeMsg;
import com.ilsxh.catchBest.result.Result;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.GoodsService;
import com.ilsxh.catchBest.service.OrderService;
import com.ilsxh.catchBest.service.RedisService;
import com.ilsxh.catchBest.vo.GoodsVo;
import com.ilsxh.catchBest.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	CatchBestUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    // 写一个拦截器 @NeedLogin
    public Result<OrderDetailVo> info(Model model,CatchBestUser user,
    		@RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}
