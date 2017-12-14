package com.twosonsoft.pilot;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/secured")
public class SecuredAPIController
{
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/admin_get.do")
	public @ResponseBody String admin_get()
	{
		
		return "Secured API Called - Admin";
	}
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/user_get.do")
	public @ResponseBody String user_get()
	{
		
		return "Secured API Called - User";
	}
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@RequestMapping(value = "/any_get.do")
	public @ResponseBody String any_get()
	{
		
		return "Secured API Called - any";
	}
	
	
	
}
