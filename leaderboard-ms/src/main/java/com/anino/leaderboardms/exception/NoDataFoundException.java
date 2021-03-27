package com.anino.leaderboardms.exception;

public class NoDataFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7892049497827152456L;
	
	private String msg;

	public NoDataFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
