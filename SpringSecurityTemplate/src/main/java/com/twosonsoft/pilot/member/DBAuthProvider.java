package com.twosonsoft.pilot.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("dbAuthProvider")
public class DBAuthProvider implements AuthenticationProvider
{
	private static final Logger logger = LoggerFactory.getLogger(DBAuthProvider.class);

	@Autowired
	SqlSession sqlSession;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		PasswordEncoder encoder = new StandardPasswordEncoder();
		String user_id = (String) authentication.getPrincipal();
		String user_pw = (String) authentication.getCredentials();

		logger.info("사용자가 입력한 로그인정보입니다. {}", user_id + "/" + user_pw);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("id", user_id);

		MemberInfo memberInfo = sqlSession.selectOne("Member.selectMemberInfo", parameterMap);

		if (memberInfo != null)
		{
			String db_pwd = memberInfo.getPwd();
			logger.info("사용자가 입력한 로그인정보입니다. {}", user_id + "/" + db_pwd);

			if (encoder.matches(user_pw, db_pwd))
			{
				logger.info("정상 로그인입니다.");
				List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
				roles.add(new SimpleGrantedAuthority(memberInfo.getRole()));

				UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(memberInfo, null, roles);
				result.setDetails(memberInfo);
				return result;
			}
		}

		return null;

	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
