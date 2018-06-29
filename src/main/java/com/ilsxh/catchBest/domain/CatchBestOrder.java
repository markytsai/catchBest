package com.ilsxh.catchBest.domain;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年6月28日 下午1:36:14
 *
 */
public class CatchBestOrder {
	private Long id;
	private Long userId;
	private Long orderId;
	private Long goodsId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}