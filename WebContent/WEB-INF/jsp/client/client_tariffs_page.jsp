<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tariffs</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jsp/client/client_head.jsp"%>

	<c:set var="tariffs" value="${sessionScope.tariffs}" />

	<c:if test="${not empty tariffs}">
		<table class="table_blur">
			<tr>
				<th>Tariff</th>
				<th>Description</th>
				<th>Price</th>
			</tr>
			<c:forEach items="${tariffs}" var="i">
				<tr>
					<td>${i.name}</td>
					<td>${i.description}</td>
					<td>${i.price}</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientTariffs" /> <input
								type="hidden" name="tariffName" value="${i.name}" /> <input
								type="hidden" name="order" value="true" />
							<button>Order</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>