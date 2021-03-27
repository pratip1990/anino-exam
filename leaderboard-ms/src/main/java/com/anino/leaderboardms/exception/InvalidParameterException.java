package com.anino.leaderboardms.exception;

public class InvalidParameterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7286848756558166818L;
	private String msg;

	public InvalidParameterException(String msg) {
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
