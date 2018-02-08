package com.twosonsoft.pilot;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.twosonsoft.pilot.dao.DaoMemberInfo;
import com.twosonsoft.pilot.dao.DaoTemp;
import com.twosonsoft.pilot.dto.BeanMemberInfo;
import com.twosonsoft.pilot.dto.BeanTemp;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	// inject DAO object
	@Resource(name = "daoMemberInfo_DataSourceTransaction")
	DaoMemberInfo daoMemberInfo_DataSourceTransaction;
	
	@Resource(name = "daoTemp_DataSourceTransaction")
	DaoTemp daoTemp_DataSourceTransaction;
	
	@Value("${app.description}")
	private String appDescription;

		
		

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{
		logger.info("Welcome home! The client locale is {}.", locale);
		
		System.out.println("appDescription = " + appDescription);
		
		return "home";
	}

	@RequestMapping(value = "/selectMemberInfo.do", method = RequestMethod.GET)
	public @ResponseBody String selectMemberInfo(Locale locale, Model model, String id)
	{
		BeanMemberInfo info = daoMemberInfo_DataSourceTransaction.selectMemberInfo(id);

		Gson gson = new Gson();
		return gson.toJson(info);

	}
	@RequestMapping("insertMemberInfo.do")
	public @ResponseBody String insertMemberInfo(BeanMemberInfo memberInfo)
	{
		daoMemberInfo_DataSourceTransaction.insertMemberInfo(memberInfo);

		return "success";
	}
	@RequestMapping("insertMemberInfoComplex.do")
	public @ResponseBody String insertMemberInfoComplex(BeanMemberInfo memberInfo)
	{
		// Test Transaction propagation
		daoMemberInfo_DataSourceTransaction.insertMemberInfoComplex(memberInfo);
		
		return "success";
	}
	
	@RequestMapping("insertTempException.do")
	public @ResponseBody String insertTempComplex(BeanTemp temp)
	{
		// Test Transaction propagation
		daoTemp_DataSourceTransaction.insertTempRuntimeException(temp);
		
		return "success";
	}
}
