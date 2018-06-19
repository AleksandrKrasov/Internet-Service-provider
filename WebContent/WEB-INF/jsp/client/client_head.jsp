<%@page import="ua.khpi.krasov.db.entity.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="head">	
		<fieldset>
			<c:set var="login" value="${sessionScope.user.login}"/>
			<c:set var="bill" value="${sessionScope.user.bill}"/>
			<c:set var="status" value="${sessionScope.status}"/>
			
			<c:if test="${not empty login}">
				Login: <b>${login} | </b>
			</c:if>	
			
			<c:if test="${not empty login}">
				Bill: <b><t:colerfulBill value="${bill}">
     				<jsp:attribute name="even_fragment">
         				<span style="color: black;">${i}</span>
     				</jsp:attribute>
     				<jsp:attribute name="odd_fragment">
         			<span style="color: red;">${i}</span>
     				</jsp:attribute>
 					</t:colerfulBill> | </b>
			</c:if>
			
			<c:if test="${not empty status}">
				Status: <b>${status}</b>
			</c:if>
			<div class="logout" align="right">
			
				<form class="login-form" action="PdfDownloader" method="get">
      				<button >download tariff plans</button>
    			</form>
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="clientOrders"/>
      				<button>My orders</button>
    			</form>
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="clientServices"/>
      				<button>services</button>
    			</form>
			
				<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="billRefill"/>
      				<button>Refill bill</button>
    			</form>
    			
    			<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="settings"/>
      				<button>settings</button>
    			</form>
    			
    			<form class="login-form" action="Controller" method="get">
    				<input type="hidden" name="command" value="logout"/>
      				<button>logout</button>
    			</form>
			</div>
		</fieldset>
	</div>
	
</body>
</html>