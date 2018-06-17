<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Services</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<%@ include file="/WEB-INF/jsp/client/client_head.jsp"%>

	<c:set var="servicelist" value="${sessionScope.servicelist}" />
	<c:set var="message" value="${param['completed']}" />

	<c:if test="${not empty servicelist}">
		<table class="table_blur">
			<tr>
				<th>Service</th>
				<th>Tariffs</th>
			</tr>
			<c:forEach items="${servicelist}" var="i">
				<tr>
					<td>${i.name}</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientTariffs" /> <input
								type="hidden" name="serviceName" value="${i.name}" /> <input
								type="hidden" name="serviceId" value="${i.id}" />
							<button>Tariffs</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	
	<c:if test="${not empty message}">
		<div class="login-page">
  			<div class="form">
				<c:set var="message1" value="${'True'}" />
				<c:choose>
					<c:when test="${message == message1}">
						<p class="message">Operation completed</p>
					</c:when>
					<c:when test="${message != message1}">
						<p class="message">Operation was not completed</p>
					</c:when>
				</c:choose>
			</div>
		</div>
	</c:if>
</body>
</html>