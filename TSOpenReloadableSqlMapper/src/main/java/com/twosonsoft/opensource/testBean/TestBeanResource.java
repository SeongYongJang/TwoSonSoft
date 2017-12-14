package com.twosonsoft.opensource.testBean;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

// 테스트
// 1. Resource 를 데이터로 받는 빈을 테스트한다
// 2. Resource 를 표현했던 spEL을 추출할 수 있는지 알아본다

public class TestBeanResource implements ApplicationContextAware
{
	ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context = context;
		System.out.println("setApplicationContext");
		try
		{
			Resource[] resources = this.context.getResources("/WEB-INF/views/**/*.jsp");
			doSomethingWithResource(resources);
			DefaultListableBeanFactory factory = (DefaultListableBeanFactory)context.getParentBeanFactory();
			


			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	Resource[] resources;

	public Resource[] getResources()
	{
		return resources;
	}

	public void setResources(Resource[] resources)
	{
		this.resources = resources;
		System.out.println("TestBeanResource");
		doSomethingWithResource(resources);
		

		

	}

	void doSomethingWithResource(Resource[] resources)
	{
		for (Resource resource : resources)
		{
			System.out.println(resource.getFilename());
			System.out.println(resource.getDescription());
		}
	}

}
