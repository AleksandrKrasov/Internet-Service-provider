<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="service.services"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<%@ include file="/WEB-INF/jsp/client/client_head.jsp"%>

	<c:set var="servicelist" value="${requestScope.servicelist}" />
	<c:set var="serviceNames" value="${requestScope.serviceNames}" />
	<c:set var="message" value="${param['completed']}" />

	<c:if test="${not empty servicelist && not empty serviceNames}">
		<table class="table_blur">
			<tr>
				<th><fmt:message key="service.services"/></th>
				<th><fmt:message key="service.tariffs"/></th>
			</tr>
			<c:forEach begin="0" end="${servicelist.size() - 1}" var="i">
				<tr>
					<td>${serviceNames[i]}</td>
					<td>
						<form class="login-form" action="Controller" method="POST">
							<input type="hidden" name="command" value="clientTariffs" /> <input
								type="hidden" name="serviceName" value="${servicelist[i].name}" /> <input
								type="hidden" name="serviceId" value="${servicelist[i].id}" />
							<button><fmt:message key="service.tariffs"/></button>
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
						<p class="message"><fmt:message key="refill.opComp"/></p>
					</c:when>
					<c:when test="${message != message1}">
						<p class="message"><fmt:message key="refill.opNotComp"/></p>
					</c:when>
				</c:choose>
			</div>
		</div>
	</c:if>
</body>
</html>