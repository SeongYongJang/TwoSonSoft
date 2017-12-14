package com.twosonsoft.opensource.testBean;
// 목표
// 1. FactoryBean 인터페이스를 가진 객체를 생성해 본다
// 2. FactoryBean을 통해 생성된 객체를 스프링컨텍스트를 통해 접근해본다

// Test
// 1.getObject를 통해 해당 객체의 생성을 요청할 때 싱글톤 객체를 하나 만들어 리턴한다 

import org.springframework.beans.factory.FactoryBean;

public class TestBeanFactoryBean implements FactoryBean<TestDataBean>
{

	@Override
	public TestDataBean getObject() throws Exception
	{
		System.out.println("TestBeanFactoryBean requested getObject()");
		// create singleton instance
		TestDataBean instance = TestDataBean.getInstance();
		
		return instance;
	}

	@Override
	public Class<?> getObjectType()
	{
		// TODO Auto-generated method stub
		return TestDataBean.class;
	}

	@Override
	public boolean isSingleton()
	{
		// TODO Auto-generated method stub
		return true;
	}

}
