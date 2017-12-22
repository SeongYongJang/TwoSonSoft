package com.twosonsoft.pilot.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.twosonsoft.pilot.dto.BeanMemberInfo;

public class DaoMemberInfo extends DaoAbstract
{

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public BeanMemberInfo selectMemberInfo(String id)
	{
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("id",id);
		
		BeanMemberInfo info = sqlSession.selectOne("ApiMemberInfo.selectMemberInfo", parameterMap);
		
		return info;
		
	}
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public int insertMemberInfo(BeanMemberInfo info)
	{
		return sqlSession.insert("ApiMemberInfo.insertMemberInfo", info);
		
	}
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public int insertMemberInfoComplex(BeanMemberInfo info)
	{
		// 복합 트랜잭션 테스트 => Transaction Propagation Test
		BeanMemberInfo selectedInfo = selectMemberInfo(info.getId());
		Gson gson = new Gson();
		System.out.println(gson.toJson(selectedInfo));
		
		return sqlSession.insert("ApiMemberInfo.insertMemberInfo", info);
		
	}
}
