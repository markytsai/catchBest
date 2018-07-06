package com.ilsxh.catchBest.redis;

public class GoodsKey extends BasePrefix {

	private GoodsKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static GoodsKey getGoodsList = new GoodsKey(600, "gl");
	public static GoodsKey getGoodsDetail = new GoodsKey(600, "gd");
	public static GoodsKey getCatchBestGoodsStock = new GoodsKey(600, "gs");
}
