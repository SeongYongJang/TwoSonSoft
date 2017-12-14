package com.twosonsoft.opensource.comp;
// SqlSesssionFactoryBean을 상속 받는다
// 스프링 컨텍스를 접근하기 위해 ApplicationContextAware을 구현한다
// 객체 생성 완료후 객체 프로퍼티의 설정값을 가져 오도록 한다
// xml이 존재하는 루트 디렉토리를 감시하는 Thread를 만들고 실행한다
// 객체소멸 시점시 디렉토리 감시 Thread를 종료하기 위해 DisposableBean 을 구현한다

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

public class ReloadableSqlMapper extends SqlSessionFactoryBean implements ApplicationContextAware, InfPathWatcher, DisposableBean
{
	private static final Logger logger = LoggerFactory.getLogger(ReloadableSqlMapper.class);

	ApplicationContext context;
	// 스프링빈의 생성 팩토리에 접근하기 위한 빈팩토리
	@Autowired
	DefaultListableBeanFactory listableBeanFactory;
	// 빈 생성시 사용된 프로퍼티 값을 저장
	String mapperLocationsPattern = null;
	// 디렉토리 변경 감시 서비스 객체
	PathWatcher pathWatcher = null;
	// Mapper 파일이 있는 최초 시작 디렉토리
	String mapperRootPath;

	String beanName = "sqlFactory";
	// 빈 라이프 사이클 플레그
	boolean isAlive = true;

	public String getBeanName()
	{
		return beanName;
	}

	public void setBeanName(String beanName)
	{
		this.beanName = beanName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.context = applicationContext;
	}
	// 디렉토리 변화가 감지되면 맵퍼를 재설정한다
	@Override
	public void nofitfyPathChange(String mapperPath, String targetFilename)
	{
		logger.info(targetFilename + " 변화 감지");
		try
		{
			logger.info("SqlMapper 재설정");
			refresh();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 객체가 소멸될때 디렉토리 감시 객체도 중단 시킨다
	@Override
	public void destroy() throws Exception
	{
		isAlive = false;
		if (pathWatcher != null)
		{
			logger.info("디렉토리 감시 쓰레드 종료");
			pathWatcher.closeWatcher();
		}		
	}

	// 객체 생성이 요청되었을때 mapper location pattern 을 추출한다
	@Override
	public SqlSessionFactory getObject() throws Exception
	{
		logger.debug("getObject... ");
		
		extractProperty();

		return super.getObject();
	}
	
	// mapper location 에 Resource 가 전달 되었을 때 디렉토리 감시 시스템을 동작 시킨다
	@Override
	public void setMapperLocations(Resource[] mapperLocations)
	{
		super.setMapperLocations(mapperLocations);
		try
		{
			if (pathWatcher != null)
			{
				logger.info("디렉토리 감시 쓰레드가 이미 실행중입니다");
				return;
			}
			else
			{
				logger.info("디렉토리 감시 쓰레드 실행 시작");
			}
			mapperRootPath = getSuffixAutomaton(mapperLocations);
			if (mapperRootPath != null && !mapperRootPath.equals(""))
			{
				// hook file system notification
				pathWatcher = PathWatcher.getInstance();
				pathWatcher.setPathParameter(mapperRootPath);
				pathWatcher.addObserver(this);
				pathWatcher.runWatcher();
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	// ////////////////////////////////////////////////////////////////////////////////////
	// 공통 패스 추출 알고리즘
	String getSuffixAutomaton(Resource[] mapperLocations) throws Exception
	{
		List<String> path = new ArrayList<String>();
		for (Resource res : mapperLocations)
		{
			path.add(res.getFile().getPath());
		}

		UtilSuffixAutomaton automaton = new UtilSuffixAutomaton();

		String commonStr = "";

		// 공통되는 문자열을 추출한다
		for (String baseStr : path)
		{
			if (commonStr.equals(""))
			{
				commonStr = baseStr;
				continue;
			}
			commonStr = automaton.lcs(baseStr, commonStr);
		}

		File file = new File(commonStr);
		if (!file.isDirectory())
		{
			// get parent directory
			commonStr = file.getParent();
		}
		logger.info("SqlSession xml 맵퍼 루트 디렉토리  = " + commonStr);
		return commonStr;
	}
	public void extractProperty()
	{
		if(this.mapperLocationsPattern != null )
		{
			return;
		}
		// 어플리케이션 컨텍스트로 부터 현재 빈을 세팅하기 위해 사용된 프로퍼티의 값을 가져와 저장한다
		BeanDefinition beanDefinition = listableBeanFactory.getBeanDefinition(beanName);
		MutablePropertyValues values = beanDefinition.getPropertyValues();

		List<PropertyValue> list = values.getPropertyValueList();

		for (PropertyValue propertyValue : list)
		{
			Object objValue = propertyValue.getValue();
			if (objValue instanceof TypedStringValue)
			{
				TypedStringValue str = (TypedStringValue) objValue;
				String value = str.getValue();
				String key = propertyValue.getName();
				if ("mapperLocations".equals(key))
				{
					this.mapperLocationsPattern = value;
				}
			}
		}
	}
	// 테스트용 외부 인터페이스
	public void refresh() throws Exception
	{
		Resource[] resources = context.getResources(mapperLocationsPattern);

		setMapperLocations(resources);
		this.afterPropertiesSet();
	}
}
