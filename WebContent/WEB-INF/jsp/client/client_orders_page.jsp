<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="orders.orders"/></title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/client/client_head.jsp"%>

	<c:set var="serviceNames" value="${requestScope.serviceNames}" />
	<c:set var="tariffNames" value="${requestScope.tariffNames}" />
	<c:set var="statusNames" value="${requestScope.statusNames}" />
	<c:set var="prices" value="${requestScope.prices}" />
	<c:set var="orders" value="${requestScope.orders}" />
	<c:set var="orderSize" value="${requestScope.orderSize}" />
	
	<c:if test="${orderSize == 0}">
		<div class="login-page">
  			<div class="form">
  				<p><fmt:message key="orders.message"/></p>
  			</div>
  		</div>
	</c:if>

	<c:if
		test="${not empty serviceNames && not empty tariffNames && not empty statusNames && not empty orderSize}">
		<table class="table_blur">
			<tr>
				<th><fmt:message key="service.service"/></th>
				<th><fmt:message key="tariff.tariff"/></th>
				<th><fmt:message key="tariff.status"/></th>
				<th><fmt:message key="tariff.price"/></th>
				<th></th>
			</tr>
			<c:forEach var="order_i" begin="0" end="${orderSize - 1}">
				<tr>
					<td>${serviceNames[order_i]}</td>
					<td>${tariffNames[order_i]}</td>
					<td>${statusNames[order_i]}</td>
					<td>${prices[order_i]} <fmt:message key="head.currency"/></td>
					<td>
						<form class="login-form" action="Controller" method="POST">
							<input type="hidden" name="command" value="clientOrders" /> <input
								type="hidden" name="orderId" value="${orders[order_i].id}" />
							<button><fmt:message key="orders.cancel"/></button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>