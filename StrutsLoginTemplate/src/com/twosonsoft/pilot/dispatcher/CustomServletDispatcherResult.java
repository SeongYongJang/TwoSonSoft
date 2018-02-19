package com.twosonsoft.pilot.dispatcher;

import org.apache.struts2.result.ServletDispatcherResult;

import com.opensymphony.xwork2.inject.Inject;

public class CustomServletDispatcherResult extends  ServletDispatcherResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3237767413538737533L;
	private String resultPath;

	public String getResultPath() {
		return resultPath;
	}

	@Inject(value = "struts.result.path", required = false)
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}

	@Override
	public void setLocation(String location) {
		super.setLocation(resultPath + location);
	}
}
