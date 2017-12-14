package com.twosonsoft.opensource;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.twosonsoft.opensource.comp.ReloadableSqlMapper;
import com.twosonsoft.opensource.testBean.TestBeanResource;
import com.twosonsoft.opensource.testBean.TestDataBean;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController implements ApplicationContextAware
{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


	@Autowired
	DefaultListableBeanFactory bf;

	@Autowired
	SqlSession sqlSession;
	@Autowired
	ReloadableSqlMapper sqlFactory; 
	
	
	ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context = context;

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{

		StaticApplicationContext sac = new StaticApplicationContext(this.context);
		XmlWebApplicationContext factory = (XmlWebApplicationContext) this.context.getParentBeanFactory();
		factory.getBeanFactory().getBeanDefinitionNames();
		// .getBeanDefinition("homeController");

		// BeanDefinition beanDefinition =
		// sac.getBeanFactory().getBeanDefinition("homeController");
		// BeanDefinition beanDefinition =
		// factory.getBeanFactory().getBeanDefinition("homeController");

		String[] names = this.context.getBeanDefinitionNames();// factory.getBeanFactory().getBeanDefinitionNames();;

		for (String name : names)
		{
			System.out.println("Name = " + name);
		}
		BeanDefinition d = bf.getBeanDefinition("sqlFactory");
		MutablePropertyValues values = d.getPropertyValues();
		List<PropertyValue> list = values.getPropertyValueList();
		for(PropertyValue val : list)
		{
			Object objVal = val.getValue();
			if(objVal instanceof TypedStringValue)
			{
				TypedStringValue str = (TypedStringValue) val.getValue();
				System.out.println("property value = " + str.getValue());
			}
			
		}
		
		String attr = (String)d.getAttribute("resources");
		System.out.println("attr = " + attr);
		

		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}
	@RequestMapping("reload.do")
	public @ResponseBody String reloadSqlMapper() throws Exception
	{
		sqlFactory.refresh();
		return "Reload Sql Mapper Complete";
	}

}
