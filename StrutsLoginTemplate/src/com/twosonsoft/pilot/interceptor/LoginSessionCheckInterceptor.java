package com.twosonsoft.pilot.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

public class LoginSessionCheckInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8643066927989531551L;

	private static Log log = LogFactory.getLog(LoginSessionCheckInterceptor.class);
	protected Set<String> excludeActions = null;

	String redirectAction = "/login.do"; // default page if not setted by param.

	public void setExcludeActions(String excludeActions) {
		 this.excludeActions = TextParseUtil.commaDelimitedStringToSet(excludeActions);
	}

	@Override
	public void destroy() {
		log.info("Destroying Interceptor");
		log.info("-=-=-=-=-=-=-=-=-=-=-=-=-");

	}

	@Override
	public void init() {
		log.info("Intialising Interceptor");
		log.info("-=-=-=-=-=-=-=-=-=-=-=-=-");

	}
	
	@Override
	public  String intercept(ActionInvocation invocation) throws Exception {
		log.info("intercept");
		
		if (!isLoginRequiredAction()) { // check if action is available action without login
			return invocation.invoke(); // execute action
		} else {
			if (isLogin()) { // check if logged in or not
				return invocation.invoke(); // execute action
			} else {
				proccessDeniedResponse(); // redirect to login page, or send denial xml message
				return null;
			}
		}

	}


	void proccessDeniedResponse() throws Exception {
		String redirectUrl = null;
		if (redirectAction.startsWith("/") || redirectAction.startsWith("\\")) {
			redirectUrl = ServletActionContext.getRequest().getContextPath() + redirectAction;
		} else {
			redirectUrl = ServletActionContext.getRequest().getContextPath() + "/" + redirectAction;
		}
		ServletActionContext.getResponse().sendRedirect(redirectUrl);

	}

	/*
	 * check login or not
	 */
	boolean isLogin() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();

		String id = (String) session.get("id");
		String token = (String) session.get("token");

		if (null != id && !"".equals(id)) {
			if (null != token && !"".equals(token)) {
				return true;
			}
		}
		return false;

	}
	/*
	 * check requested action require login session
	 */

	boolean isLoginRequiredAction() {
		String requestedResouece = getRequestedResource(ServletActionContext.getRequest());
		String action = null;
		Iterator<String> excludeActionsIter = excludeActions.iterator();
		while (excludeActionsIter.hasNext()) {
			action = (String) excludeActionsIter.next();
			if (requestedResouece.startsWith(action))
				return false;
		}

		return true;
	}

	private String getRequestedResource(HttpServletRequest request) {
		String requestedResouece = null;

		String contextPath = request.getContextPath();
		String requestedUri = request.getRequestURI();

		if ("/".equals(contextPath)) {
			requestedResouece = requestedUri;
		} else {
			requestedResouece = requestedUri.substring(contextPath.length());
		}
		return requestedResouece;
	}

}
