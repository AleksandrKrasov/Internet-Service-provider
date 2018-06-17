<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

	<c:set var="user" value="${sessionScope.user}"/>

	<c:if test="${not empty user}">
		<%@ include file="/WEB-INF/jsp/client/client_head.jsp" %>
	</c:if>		

	<div class="login-page">
  		<div class="form">
    		<form class="login-form" action="Controller" method="get">
    			<input type="hidden" name="command" value="login"/>
      			<input type="text" name="login" placeholder="login"/>
      			<input type="password" name="password" placeholder="password"/>
      			<button>login</button>
      			<p class="message">Not registered? <a href="/FinalTask/registration.jsp">Create an account</a></p>
    		</form>
  		</div>
	</div>
</body>
</html>