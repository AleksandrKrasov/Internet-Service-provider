<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My orders</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/client/client_head.jsp"%>

	<c:set var="serviceNames" value="${sessionScope.serviceNames}" />
	<c:set var="tariffNames" value="${sessionScope.tariffNames}" />
	<c:set var="statusNames" value="${sessionScope.statusNames}" />
	<c:set var="prices" value="${sessionScope.prices}" />
	<c:set var="orders" value="${sessionScope.orders}" />
	<c:set var="orderSize" value="${sessionScope.orderSize}" />
	
	<c:if test="${orderSize == 0}">
		<div class="login-page">
  			<div class="form">
  				<p>You do not have any orders yet.</p>
  			</div>
  		</div>
	</c:if>

	<c:if
		test="${not empty serviceNames && not empty tariffNames && not empty statusNames && not empty orderSize}">
		<table class="table_blur">
			<tr>
				<th>Service</th>
				<th>Tariff</th>
				<th>Status</th>
				<th>Price</th>
				<th></th>
			</tr>
			<c:forEach var="order_i" begin="0" end="${orderSize - 1}">
				<tr>
					<td>${serviceNames[order_i]}</td>
					<td>${tariffNames[order_i]}</td>
					<td>${statusNames[order_i]}</td>
					<td>${prices[order_i]}</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientOrders" /> <input
								type="hidden" name="orderId" value="${orders[order_i].id}" />
							<button>Cancel</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>