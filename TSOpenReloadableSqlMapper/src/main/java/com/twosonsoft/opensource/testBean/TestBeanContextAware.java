package com.twosonsoft.opensource.testBean;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

// 목표
// 1. 스프링의 ApplicationContextAware 인터페이스에 관한 테스트를 진행한다
// 2. ApplicationContextAware를 통해 얻을 수 있는 정보들이 무엇인지, 어떻게 얻을 수 있는지를 알아본다

// 테스트 진행 순서
// 1. classpath 밑에 sqlMapper 폴더를 생성하고 샘플 xml 파일을 생성한다
// 2. 현재의 클래스를 servlet-context.xml에서 스프링빈으로 등록한다
// 3. Resource파일에 접근해본다 


public class TestBeanContextAware implements ApplicationContextAware
{
	ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context = context;
		///////////////////////////////////////////////////////////
		// 기본적 정보를 몇가지 추출해 본다
		Resource[] allResource;
		try
		{
			// JSP가 있는 폴더의 파일을 모두 출력해 본다
			allResource = this.context.getResources("/WEB-INF/views/**/*.jsp");
			System.out.println("JSP Files");
			for(Resource resource : allResource)
			{
				System.out.println(resource.getFilename());
			}
			
			// sqlMapper 폴더에 존재하는 모든 XML 파일을 출력해본다
			System.out.println("Sql Mapper Files");
			allResource = this.context.getResources("classpath:sqlMapper/**/*.xml");
			for(Resource resource : allResource)
			{
				System.out.println(resource.getFilename());
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	
}
