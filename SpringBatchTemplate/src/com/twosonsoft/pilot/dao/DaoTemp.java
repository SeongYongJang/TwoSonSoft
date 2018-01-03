package com.twosonsoft.pilot.dao;

import java.sql.SQLException;

import com.twosonsoft.pilot.dto.BeanTemp;

public class DaoTemp extends DaoAbstract
{

	public int insertTemp(BeanTemp temp)
	{
		return sqlSession.insert("ApiTemp.insertTemp", temp);

	}

	@SuppressWarnings("unused")
	public int insertTempException(BeanTemp temp) throws Exception
	{
		// 3개의 레코드를 넣은후 예외를 발생 시킨다면 입력된 3개의 레코드가 롤백이 될것인가?
		// 3개가 모두 들어가 있다
		// 일반 Exception 이 일어나면 롤백이 안되는것인가?

		sqlSession.insert("ApiTemp.insertTemp", temp);
		sqlSession.insert("ApiTemp.insertTemp", temp);
		sqlSession.insert("ApiTemp.insertTemp", temp);

		if (true)
		{
			Exception exception = new Exception();
			throw exception;
		}
		return 0;
	}
	@SuppressWarnings("unused")
	public int insertTempSqlException(BeanTemp temp) throws SQLException
	{
		// 3개의 레코드를 넣은후 예외를 발생 시킨다면 입력된 3개의 레코드가 롤백이 될것인가?
		// 3개가 모두 들어가 있다
		// 일반 Exception 이 일어나면 롤백이 안되는것인가?
		// rollbackFor = RuntimeException.class 추가 => 롤백이 된다 

		sqlSession.insert("ApiTemp.insertTemp", temp);
		sqlSession.insert("ApiTemp.insertTemp", temp);
		sqlSession.insert("ApiTemp.insertTemp", temp);

		if (true)
		{
			SQLException exception = new SQLException();
			throw exception;
		}
		return 0;

	}
	public int insertTempRuntimeException(BeanTemp temp) throws RuntimeException
	{
		// 3개의 레코드를 넣은후 예외를 발생 시킨다면 입력된 3개의 레코드가 롤백이 될것인가?
		// 3개가 모두 들어가 있다
		// 일반 Exception 이 일어나면 롤백이 안되는것인가?
		// rollbackFor = RuntimeException.class 추가 => 롤백이 된다 
		
		sqlSession.insert("ApiTemp.insertTemp", temp);
		sqlSession.insert("ApiTemp.insertTemp", temp);
		sqlSession.insert("ApiTemp.insertTemp", temp);
		
		if (true)
		{
			RuntimeException exception = new RuntimeException();
			throw exception;
		}
		return 0;
		
	}
}
