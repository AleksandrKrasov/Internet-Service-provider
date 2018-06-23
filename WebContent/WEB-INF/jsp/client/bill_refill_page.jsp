<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="refill.bill"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/client/client_head.jsp" %>
	
	<div class="login-page">
	
		<c:set var="message" value="${param['completed']}"/>
		
  		<div class="form">
    		<form class="login-form" action="Controller" method="get">
    			<input type="hidden" name="command" value="billRefill"/>
      			<input type="text" name="summ" placeholder="<fmt:message key="refill.sum"/>"/>
      			<button><fmt:message key="refill.submit"/></button>
      			<c:if test="${not empty message}">
      				<p class="message"><fmt:message key="refill.opNotComp"/></p>
      			</c:if>
    		</form>
  		</div>
	</div>
</body>
</html>