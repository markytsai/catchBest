package com.ilsxh.catchBest.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilsxh.catchBest.result.Result;
import com.ilsxh.catchBest.service.CatchBestUserService;
import com.ilsxh.catchBest.service.RedisService;
import com.ilsxh.catchBest.vo.BaseVo;
import com.ilsxh.catchBest.vo.LoginVo;
import com.ilsxh.catchBest.vo.TestVo;


@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	CatchBestUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
    	//登录
    	userService.login(response, loginVo);
    	return Result.success(true);
    }
    
}
