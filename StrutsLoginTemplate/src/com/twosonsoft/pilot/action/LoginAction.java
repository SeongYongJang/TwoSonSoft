package com.twosonsoft.pilot.action;

import com.opensymphony.xwork2.ActionContext;
import com.twosonsoft.pilot.dto.BeanLoginCheck;

public class LoginAction extends ActionTemplate{
	
	private static final long serialVersionUID = -1557138749328887581L;
	
	// 요청된 파라미터를 세션에 저장한다 -> 로그인 여부는 세션의 값의 유무로 판단한다
	public String registLogin() throws Exception {
		ActionContext context = ActionContext.getContext();

		// get http request parameter
		String id = this.getHttpParameter(context, "id");
		String token = this.getHttpParameter(context, "token");
		String uuid = this.getHttpParameter(context, "uuid");
		
		// just put login data to session
		this.addSessionValue(context, "id", id);
		this.addSessionValue(context, "token", token);
		this.addSessionValue(context, "uuid", uuid);
		
//		BeanLoginCheck loginCheck = new BeanLoginCheck("true", "Y");
//		// write back to client as json
//		this.writeJsonToHttpServletResponse(loginCheck);
		
		return SUCCESS;
		
	}
	public String logout() {
		ActionContext context = ActionContext.getContext();
		this.removeSessionValue(context, "id");
		this.removeSessionValue(context, "token");
		this.removeSessionValue(context, "uuid");

		return SUCCESS;
	}
	
	public String isLogin() throws Exception {
		ActionContext context = ActionContext.getContext();
		String id = this.getSessionValue(context, "id");
		String token = this.getSessionValue(context, "token");
		String uuid = this.getSessionValue(context, "uuid");
		
		BeanLoginCheck loginCheck = new BeanLoginCheck("false", "N");
		
		if(null != id && !"".equals(id)) {
			if(null != token && !"".equals(token)) {
				// make login information
				loginCheck.setSuccess("true");
				loginCheck.setLoginYN("Y");
			}
		}
		// write back to client as json
		this.writeJsonToHttpServletResponse(loginCheck);
		return null;
	}
	
	public String getLoginInfo() {
		ActionContext context = ActionContext.getContext();
		
		String id = this.getSessionValue(context, "id");
		String token = this.getSessionValue(context, "token");
		
		return SUCCESS;
		
	}


}
