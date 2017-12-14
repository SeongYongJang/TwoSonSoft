<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<sec:authorize access="!isAuthenticated()">
		<li><a href="#none">회원가입</a></li>
		<li><a href="./member/login.do">로그인</a></li>
	</sec:authorize>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.id" var="id" />
		<sec:authentication property="principal.role" var="role" />
		
			사용자 권한 : <p>${role}</p>

		<li><a href="#none">${id} 회원</a></li>
		<li><a href="j_spring_security_logout.do">로그아웃</a></li>
	</sec:authorize>

	<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_KING')">
		사용자 관리자 권한 허용
	</sec:authorize>
</body>
</html>
