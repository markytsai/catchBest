package com.ilsxh.catchBest.redis;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年7月6日 下午9:45:51
 *
 */
public class CatchBestKey extends BasePrefix {

	public CatchBestKey(String prefix) { // 设置为永不过期
		super(prefix);
	}

	public static CatchBestKey isGoodsOver = new CatchBestKey("go");
}
