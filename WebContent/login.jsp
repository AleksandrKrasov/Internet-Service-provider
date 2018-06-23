<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="login.login"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<c:set var="user" value="${sessionScope.user}"/>

	<c:if test="${not empty user && user.roleId == 1}">
		<%@ include file="/WEB-INF/jsp/client/client_head.jsp" %>
	</c:if>	
	<c:if test="${not empty user && user.roleId == 0}">
		<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp" %>
	</c:if>

	<div class="login-page">
  		<div class="form">
    		<form class="login-form" action="Controller" method="get">
    			<input type="hidden" name="command" value="login"/>
      			<input type="text" name="login" placeholder="<fmt:message key="login.login"/>"/>
      			<input type="password" name="password" placeholder="<fmt:message key="login.password"/>"/>
      			<button><fmt:message key="login.LogIn"/></button>
      			<p class="message"><fmt:message key="login.question"/> <a href="/FinalTask/registration.jsp"><fmt:message key="login.createAcc"/></a></p>
    		</form>
  		</div>
	</div>
</body>
</html>