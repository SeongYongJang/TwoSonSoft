// Test Spring Security MD5 Module

package com.twosonsoft.pilot;

import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class TestSpringMD5
{

	@Test
	public void test()
	{
		int i;
		for (i = 0; i < 1; i++)
		{
			// SHA-256 hashing
			PasswordEncoder encoder = new StandardPasswordEncoder();
			String hashedPass = encoder.encode("1234");
			System.out.println("index = " + i + " sha256 => " + hashedPass);
		}

		BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();
		String encStr = encoder2.encode("1234");

		System.out.println(encStr);

		// $2a$10$shAEBOLOKWAl736t6XDY3.HnUnkeWnYuE47SkcKJV2DfESiihzu1q
		// $2a$10$AVtuIQAdnrDtLSev3QJ4z.Lpfreblh.ISh8r08wdfABlZb/0.UxcS

		// 사용자의 비밀번호
		String password = "1234";
		// 위 비밀번호의 BCrypt 알고리즘 해쉬 생성, passwordHashed 변수는 실제 데이터베이스에 저장될 60바이트의
		// 문자열이 된다.
		// String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt());
		// 위 문장은 아래와 같다. 숫자가 높아질수록 해쉬를 생성하고 검증하는 시간은 느려진다. 즉, 보안이 우수해진다. 하지만 그만큼
		// 응답 시간이 느려지기 때문에 적절한 숫자를 선정해야 한다. 기본값은 10이다.
		String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt(10));

		System.out.println(passwordHashed);

		boolean isValidPassword = BCrypt.checkpw("1234", "$2a$10$0qZCZHyaGvJDHlDzw.izjegI214ZyUbXxWyQ1jJV22ClU7YoXvf7O");

		if (isValidPassword)
		{
			System.out.println("Matched");
		}

	}


}
