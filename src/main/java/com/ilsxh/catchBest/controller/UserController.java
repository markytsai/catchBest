package com.ilsxh.catchBest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.result.Result;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.RedisService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	CatchBestUserService userService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/info")
	@ResponseBody
	public Result<CatchBestUser> info(Model model, CatchBestUser user) {
		return Result.success(user);
	}

}
