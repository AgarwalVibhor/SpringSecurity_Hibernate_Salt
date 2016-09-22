<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
<style type="text/css">
	.error{
		color: red;
		font-weight: bold;
	}
	.errorMessage {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}
	.errorBlock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 50px;
}
</style>

</head>
<body onload="document.employeeForm.fname.focus();">

	<h1 align="center">Employee Registration</h1>
	<br />
	<br />
	<c:if test="${not empty message}">
		<h1 align="center" class="errorMessage">${message}</h1>
	</c:if>
	<br />
	
	<c:url value="/registerPost" var="registerUrl" />
	<form:form name="employeeForm" action="${registerUrl}" method="POST" commandName="employeeForm">
		<form:errors path="*" cssClass="errorBlock" element="div" />
		<table align="center" border="0">
		
			<tr>
				<td>FIRST NAME : </td>
				<td><form:input path="fname" /></td>
				<td><form:errors path="fname" cssClass="error" /></td>
			</tr>
			<tr>
				<td>LAST NAME : </td>
				<td><form:input path="lname" /></td>
				<td><form:errors path="lname" cssClass="error" /></td>
			</tr>
			<tr>
				<td>USER NAME : </td>
				<td><form:input path="username" /></td>
				<td><form:errors path="username" cssClass="error" /></td>
			</tr>
			<tr>
				<td>PASSWORD : </td>
				<td><form:password path="password" /></td>
				<td><form:errors path="password" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Are you an User ?</td>
				<td><input type="radio" name="userRole" id="userRole" value="Yes"
							onclick="document.getElementById('admin').style.display='none';document.getElementById('manager').style.display='none';" />Yes &nbsp;&nbsp;
				<input type="radio" name="userRole" id="userRole" value="No" 
							onclick="document.getElementById('admin').style.display='block';document.getElementById('manager').style.display='block';"/>No </td>
				<td class="error">${error_message}</td>
			</tr>
			<tr id="admin" style="display: none;">
				<td>Are you an Admin ?</td>
				<td><input type="radio" name="adminRole" id="adminRole" value="Yes" />Yes &nbsp;&nbsp;
				<input type="radio" name="adminRole" id="adminRole" value="No" />No</td>
				<td>&nbsp;</td>
			</tr>
			<tr id="manager" style="display: none;">
				<td>Are you a Manager ?</td>
				<td><input type="radio" name="managerRole" id="managerRole" value="Yes" />Yes &nbsp;&nbsp;
				<input type="radio" name="managerRole" id="managerRole" value="No" />No</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit" value="Register" /></td>
			</tr>
			
		</table>	
		
	</form:form>
	
</body>

</html>		

