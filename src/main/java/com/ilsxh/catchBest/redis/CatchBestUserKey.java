package com.ilsxh.catchBest.redis;

public class CatchBestUserKey extends BasePrefix {

	public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

	private CatchBestUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static CatchBestUserKey token = new CatchBestUserKey(TOKEN_EXPIRE, "tk");
	public static CatchBestUserKey getById= new CatchBestUserKey(0, "id");
}
