<%@page import="ua.khpi.krasov.db.entity.User"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="head.head"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="head">	
		<fieldset>
			<c:set var="login" value="${sessionScope.user.login}"/>
			<c:set var="bill" value="${sessionScope.user.bill}"/>
			<c:set var="status" value="${sessionScope.status}"/>
			
			<c:if test="${not empty login}">
				<fmt:message key="head.login"/>: <b>${login} | </b>
			</c:if>	
			
			<c:if test="${not empty bill}">
				<fmt:message key="head.bill"/>: <b><t:colerfulBill value="${bill}">
     				<jsp:attribute name="even_fragment">
         				<span style="color: black;">${i}</span>
     				</jsp:attribute>
     				<jsp:attribute name="odd_fragment">
         			<span style="color: red;">${i}</span>
     				</jsp:attribute>
 					</t:colerfulBill> <fmt:message key="head.currency"/> | </b>
			</c:if>
			
			<c:if test="${not empty status}">
				<fmt:message key="head.status"/>: <b>${status}</b>
			</c:if>
			<div class="logout" align="right">
			
				<form class="login-form" action="PdfDownloader" method="get">
      				<button ><fmt:message key="head.dtp"/></button>
    			</form>
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="clientOrders"/>
      				<button><fmt:message key="head.myOrders"/></button>
    			</form>
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="clientServices"/>
      				<button><fmt:message key="head.services"/></button>
    			</form>
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="billRefill"/>
      				<button><fmt:message key="head.refillBill"/></button>
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