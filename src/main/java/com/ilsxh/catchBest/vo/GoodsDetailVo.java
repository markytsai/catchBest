package com.ilsxh.catchBest.vo;

import com.ilsxh.catchBest.domain.CatchBestUser;

public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private GoodsVo goods;
	private CatchBestUser user;

	public int getMiaoshaStatus() {
		return miaoshaStatus;
	}

	public void setMiaoshaStatus(int miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}

	public int getRemainSeconds() {
		return remainSeconds;
	}

	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}

	public GoodsVo getGoods() {
		return goods;
	}

	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}

	public CatchBestUser getUser() {
		return user;
	}

	public void setUser(CatchBestUser user) {
		this.user = user;
	}
}
