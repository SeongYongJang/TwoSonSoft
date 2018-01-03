package com.twosonsoft.pilot.batch;

import javax.annotation.Resource;

import com.twosonsoft.pilot.dao.DaoMemberInfo;
import com.twosonsoft.pilot.dao.DaoTemp;
import com.twosonsoft.pilot.dto.BeanTemp;

// Must be Spring Bean - Declare it in app-context.xml
public class TaskTemplate
{
	// inject DAO object
	@Resource(name = "daoMemberInfo_DataSourceTransaction")
	DaoMemberInfo daoMemberInfo_DataSourceTransaction;
	
	@Resource(name = "daoTemp_DataSourceTransaction")
	DaoTemp daoTemp_DataSourceTransaction;
	
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
		BeanTemp temp = new BeanTemp();
		temp.setF1("111");
		temp.setF2("222");
		temp.setF3("333");

		// Test Transaction propagation
		daoTemp_DataSourceTransaction.insertTempRuntimeException(temp);
		
	}
	
}
