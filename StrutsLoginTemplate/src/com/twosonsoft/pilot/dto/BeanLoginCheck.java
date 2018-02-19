package com.twosonsoft.pilot.dto;

public class BeanLoginCheck {
	String success; // request success string
	String loginYN; // login Y or N

	public BeanLoginCheck(String success, String loginYN) {
		this.success = success;
		this.loginYN = loginYN;

	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getLoginYN() {
		return loginYN;
	}

	public void setLoginYN(String loginYN) {
		this.loginYN = loginYN;
	}

}