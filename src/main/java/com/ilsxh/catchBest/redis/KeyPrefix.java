package com.ilsxh.catchBest.redis;

public interface KeyPrefix {

	public int expireSeconds();

	public String getPrefix(); 

}
