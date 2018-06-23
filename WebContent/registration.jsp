<%@page import="ua.khpi.krasov.controller.Path"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="registration.registration"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="login-page">
		<div class="form">
			<form action="Controller" method="get" >
				<input type="hidden" name="command" value="registration"/>
				<input type="text" name="login" placeholder="<fmt:message key="registration.login"/>">
				<input type="password" name="password" placeholder="<fmt:message key="registration.password"/>">
				<input type="password" name="repeatPassword" placeholder="<fmt:message key="registration.passwordAgain"/>">
				<input type="text" name="firstName" placeholder="<fmt:message key="registration.firstName"/>">
				<input type="text" name="lastName" placeholder="<fmt:message key="registration.lastName"/>">
				<button><fmt:message key="registration.submit"/></button>
			</form>
		</div>
	</div>
</body>
</html>