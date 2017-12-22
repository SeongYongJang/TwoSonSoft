package com.twosonsoft.pilot.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class BeanMemberInfo
{
	String id;
	String pwd;
	String role;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		// encode sha256
		// SHA-256 hashing
		PasswordEncoder encoder = new StandardPasswordEncoder();
		String hashedPass = encoder.encode("1234");

		this.pwd = hashedPass;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

}
