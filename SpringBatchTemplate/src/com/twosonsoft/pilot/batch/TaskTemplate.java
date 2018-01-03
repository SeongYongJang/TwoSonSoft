package com.twosonsoft.pilot.batch;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

// Must be Spring Bean - Declare it in app-context.xml
public class TaskTemplate
{
	// autowire sqlSession
	@Autowired
	SqlSession sqlSession;
	
	
	String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	// Task main - must be called by quartz scheduler
	public void act()
	{
		// 
	}
	
}
