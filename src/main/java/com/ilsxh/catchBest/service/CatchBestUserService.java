package com.ilsxh.catchBest.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilsxh.catchBest.dao.CatchBestUserDao;
import com.ilsxh.catchBest.domain.CatchBestUser;
import com.ilsxh.catchBest.exception.GlobalException;
import com.ilsxh.catchBest.redis.CatchBestUserKey;
import com.ilsxh.catchBest.result.CodeMsg;
import com.ilsxh.catchBest.util.MD5Util;
import com.ilsxh.catchBest.util.UUIDUtil;
import com.ilsxh.catchBest.vo.BaseVo;
import com.ilsxh.catchBest.vo.LoginVo;
import com.ilsxh.catchBest.vo.TestVo;

@Service
public class CatchBestUserService {

	public static final String COOKI_NAME_TOKEN = "token";

	@Autowired
	CatchBestUserDao catchBestUserDao;

	@Autowired
	RedisService redisService;

	public CatchBestUser getById(long id) {
		return catchBestUserDao.getById(id);
	}

	public CatchBestUser getByToken(HttpServletResponse response, String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		CatchBestUser user = redisService.get(CatchBestUserKey.token, token, CatchBestUser.class);
		// 延长有效期
		if (user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if (loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}

		// 获取前台传来的手机号和密码
		String mobile = loginVo.getMobile();
		System.out.println(mobile);
		String formPass = loginVo.getPassword();
		// 判断手机号是否存在
		CatchBestUser user = getById(Long.parseLong(mobile));
		if (user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		System.out.println(user);
		// 验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if (!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		// 生成cookie
		String token = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}

	/**
	 * 
	 * @param response
	 * @param token 随机生成的ID
	 * @param user
	 */
	private void addCookie(HttpServletResponse response, String token, CatchBestUser user) {
		redisService.set(CatchBestUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
		cookie.setMaxAge(CatchBestUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
