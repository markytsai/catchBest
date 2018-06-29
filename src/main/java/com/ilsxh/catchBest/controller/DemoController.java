package com.ilsxh.catchBest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilsxh.catchBest.domain.User;
import com.ilsxh.catchBest.redis.UserKey;
import com.ilsxh.catchBest.result.CodeMsg;
import com.ilsxh.catchBest.result.Result;
import com.ilsxh.catchBest.service.RedisService;
import com.ilsxh.catchBest.service.UserService;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年6月26日 下午5:08:32
 *
 */
@Controller
public class DemoController {

	@Autowired
	UserService userService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/hello")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("successs");
	}

	@RequestMapping("/helloError")
	@ResponseBody
	public Result<String> helloError() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}

	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "thymeleaf");
		return "hello";
	}

	@RequestMapping("/db/get")
	@ResponseBody
	public Result<User> dbGet() {
		User user = userService.getById(1);
		return Result.success(user);
	}

	@RequestMapping("/db/tx")
	@ResponseBody
	public Result<Boolean> dbTx() {
		userService.tx();
		return Result.success(true);
	}

	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<User> redisGet() {
		User user = redisService.get(UserKey.getById, "" + 1, User.class);
		return Result.success(user);
	}

	@RequestMapping("/redis/set")
	@ResponseBody
	public Result<Boolean> redisSet() {
		User user = new User();
		user.setId(1);
		user.setName("1111");
		redisService.set(UserKey.getById, "" + 1, user);// UserKey:id1
		return Result.success(true);
	}

	@RequestMapping("/redis/exist")
	@ResponseBody
	public Result<Boolean> redisIsExist() {
		User user = new User();
		user.setId(1);
		user.setName("1111");
		boolean isExist = redisService.exists(UserKey.getById, "" + 1);// UserKey:id1
		return Result.success(isExist);
	}
}