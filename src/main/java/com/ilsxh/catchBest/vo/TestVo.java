package com.ilsxh.catchBest.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.ilsxh.catchBest.validator.IsMobile;
import com.ilsxh.catchBest.validator.IsTestedSuccessful;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年6月30日 上午9:29:44
 *
 */
public class TestVo extends BaseVo{
	@NotNull
	@IsTestedSuccessful
	private String mobile;

	@NotNull
	@Length(min = 32)
	private String password;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
	}
}
