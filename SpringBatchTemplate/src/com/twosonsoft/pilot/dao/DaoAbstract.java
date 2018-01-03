package com.twosonsoft.pilot.dao;

import org.apache.ibatis.session.SqlSession;

public class DaoAbstract
{
	SqlSession sqlSession;
	
	public SqlSession getSqlSession()
	{
		return sqlSession;
	}
	public void setSqlSession(SqlSession sqlSession)
	{
		this.sqlSession = sqlSession;
	}
}
