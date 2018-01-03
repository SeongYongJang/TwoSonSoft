package com.twosonsoft.pilot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchMainApplication
{

	@SuppressWarnings("resource")
	public static void main(String[] args)
	{

		ApplicationContext context;
		context = new ClassPathXmlApplicationContext("app-context.xml");

		while (true)
		{
			try
			{
				Thread.sleep(10000);
				System.out.println("Template Batch >> Watch dog ...");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
