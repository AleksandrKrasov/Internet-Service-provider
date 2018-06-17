<%@page import="ua.khpi.krasov.controller.Path"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	
	<div class="login-page">
	<div class="form">
		<form action="Controller" method="get" >
		
			<input type="hidden" name="command" value="registration"/>
			<input type="text" name="login" placeholder="Login">
			<input type="password" name="password" placeholder="Password">
			<input type="password" name="repeatPassword" placeholder="Password again">
			<input type="text" name="firstName" placeholder="First name">
			<input type="text" name="lastName" placeholder="Last name">
			<button>submit</button>
		</form>
		</div>
	</div>
</body>
</html>