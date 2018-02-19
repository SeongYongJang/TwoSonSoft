package com.twosonsoft.pilot.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class JsonAction extends ActionTemplate {

	private static final long serialVersionUID = -2849090947079965064L;

	public String getJson() throws Exception {
		JsonData jsonData = new JsonData();
		
		jsonData.setName("Json Data");
		jsonData.setDescription("Struts Json Return Test");
		
		this.writeJsonToHttpServletResponse(jsonData);

		return null;

	}
	public String getSessionInfo() throws Exception {
		
		///////////////////////////////////////////////////////////
		// get login info from session
		ActionContext context = ActionContext.getContext();
		
		String id = this.getSessionValue(context, "id");
		String token = this.getSessionValue(context, "token");
		////////////////////////////////////////////////////////////
		SessionInfo sessionInfo = new SessionInfo();
		
		sessionInfo.setId(id);
		sessionInfo.setToken(token);
		////////////////////////////////////////////////////////////
		this.writeJsonToHttpServletResponse(sessionInfo);

		
		return null;
		
	}

	class SessionInfo{
		String id;
		String token;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
	}
	class JsonData{
		String name;
		String description;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
	}
}
