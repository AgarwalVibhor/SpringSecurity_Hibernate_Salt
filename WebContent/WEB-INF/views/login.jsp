<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<style type="text/css">
	.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
</style>
</head>
<body>

	<h1 align="center">Login With Valid Username and Password</h1>
	<br />
	<br />
	
	<c:if test="${not empty error_message}">
		<h1 class="error" align="center">${error_message}</h1>
	</c:if>
	<c:if test="${not empty logout_message}">
		<h1 align="center" class="msg">${logout_message}</h1>
	</c:if>
	
	<c:url value="/login_check" var="loginUrl" />
	<form name="loginForm" action="${loginUrl}" method="POST">
	
		<table border="0" align="center">
			
			<tr>
				<td>USERNAME : </td>
				<td><input type="text" name="username" /></td>
			</tr>
			<tr>
				<td>PASSWORD : </td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="Login" /></td>
			</tr>
			
		</table>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	</form>
	<br />
	<br />
	
	<h2 align="center">New User? Click <a href='<c:url value="/registerGet" />'>Here</a> to Register.</h2>

</body>
</html>