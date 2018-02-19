package com.twosonsoft.pilot.action;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Parameter;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ActionTemplate extends ActionSupport{

	private static final long serialVersionUID = 2410159605796691772L;

	/*
	 * return json string from java object
	 */
	public String getJsonString(Object obj) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(obj);
		
		return jsonString;
	}
	/*
	 * add key-value data to session
	 * return modified session
	 */
	public Map<String, Object> addSessionValue(ActionContext context, String key, String value) {
		Map<String, Object> session = context.getSession();
		
		session.put(key, value);
		return session;
	}
	/*
	 * get session value by key : return string
	 */
	public String getSessionValue(ActionContext context, String key) {
		Map<String, Object> session = context.getSession();
		String value = (String)session.get(key);
		
		return value;
		
	}
	public void removeSessionValue(ActionContext context, String key) {
		Map<String, Object> session = context.getSession();
		session.remove(key);
		
	}
	/*
	 * Write json string directly to http servlet response
	 */
	public void writeJsonToHttpServletResponse(Object obj) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		String jsonString = getJsonString(obj);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}
	/*
	 * Get Http Parameter value
	 */
	public String getHttpParameter(ActionContext context, String key) {
		Parameter param = context.getParameters().get(key);
		String value = param.getValue();
		return value;
	}
	
}
