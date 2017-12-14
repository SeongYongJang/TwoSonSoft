package com.twosonsoft.pilot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twosonsoft.pilot.member.MemberInfo;

@Controller
@RequestMapping("/member")
public class MemberController
{
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("/login.do")
	public String login()
	{

		return "/login/login";
	}

	@RequestMapping("/loginProcess.do")
	public String loginProcess(HttpSession session)
	{
		System.out.println("Welcome login_success! " + session.getId());

		return "";
	}
	@RequestMapping("/access_denied.do")
	public String accessDenied(HttpSession session)
	{
		System.out.println("access denied");
		
		return "access_denied";
	}
	@RequestMapping("/loginSuccess.do")
	public String loginSuccess(HttpSession session, Authentication authentication)
	{
		System.out.println("Welcome login_success! " + session.getId());
		MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal();
		session.setAttribute("memberInfo", memberInfo);
		System.out.println("User ID = " + memberInfo.getId());
		return "redirect:/home.do";
	}
}
