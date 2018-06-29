package com.ilsxh.catchBest.result;

/**
 * @description
 * @author Caizhenya mail:tsaizhenya@gmail.com
 * @date 2018年6月26日 下午5:26:48
 *
 */
public class Result<T> {

	private int code;
	private String msg;
	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private Result(T data) {
		this.code = 0;
		this.msg = "success";
		this.data = data;
	}

	private Result(CodeMsg cm) {
		if (cm == null) {
			return;
		} 
		this.code = cm.getCode();
		this.msg = cm.getMsg();
	}

	/**
	 * 成功时候调用
	 * @return
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(data);
	}
	
	public static <T> Result<T> error(CodeMsg cm) {
		return new Result<T>(cm);  
	}

}
