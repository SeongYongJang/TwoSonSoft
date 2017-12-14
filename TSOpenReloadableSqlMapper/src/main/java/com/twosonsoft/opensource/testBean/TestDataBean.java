package com.twosonsoft.opensource.testBean;

// 테스트빈 => singleton 객체
public class TestDataBean
{
	String name;
	String tel;
	static TestDataBean instance = null;
	
	public static TestDataBean getInstance()
	{
		if(instance == null)
		{
			instance = new TestDataBean();
		}
		return instance;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	
}
