package com.twosonsoft.pilot;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController
{
	@RequestMapping("/hello.do")
	public @ResponseBody String hello()
	{
		return "Just hello - but must have ADMIN role";
	}
}
