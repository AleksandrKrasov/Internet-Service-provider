<%@page import="ua.khpi.krasov.db.entity.User"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="head">	
		<fieldset>
			<c:set var="login" value="${sessionScope.user.login}"/>
			
			<c:if test="${not empty login}">
				<fmt:message key="head.login"/>: <b>${login} | </b>
			</c:if>	
				<b><fmt:message key="head.admin"/></b>
				
			<div class="logout" align="right">
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="clientList"/>
      				<button><fmt:message key="head.clients"/></button>
    			</form>
    			
    			<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="adminServices"/>
      				<button><fmt:message key="head.services"/></button>
    			</form>
    			
    			<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="settings"/>
      				<button><fmt:message key="head.settings"/></button>
    			</form>
    			
    			<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="logout"/>
      				<button><fmt:message key="head.logout"/></button>
    			</form>
			</div>
		</fieldset>
	</div>
	
</body>
</html>