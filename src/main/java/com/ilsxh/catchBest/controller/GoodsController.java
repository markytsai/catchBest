package com.ilsxh.catchBest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.RedisService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	CatchBestUserService userService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/to_list") 
	public String list(Model model, CatchBestUser user) {
		model.addAttribute("user", user);
		return "goods_list";
	}

}
