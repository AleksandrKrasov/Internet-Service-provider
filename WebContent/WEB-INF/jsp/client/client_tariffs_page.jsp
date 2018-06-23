<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="tariff.tariffs"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jsp/client/client_head.jsp"%>

	<c:set var="tariffs" value="${requestScope.tariffs}" />
	<c:set var="tariffNames" value="${requestScope.tariffNames}" />
	<c:set var="tariffDesc" value="${requestScope.tariffDesc}" />
	<c:set var="serviceName" value="${param['serviceName']}" />

	<c:if test="${not empty tariffs && not empty tariffNames && not empty tariffDesc}">
		<table class="table_blur">
			<tr>
				<th><fmt:message key="tariff.tariff"/></th>
				<th><fmt:message key="tariff.description"/></th>
				<th><fmt:message key="tariff.price"/></th>
				<th></th>
			</tr>
			<c:forEach begin="0" end="${tariffs.size() - 1}" var="i">
				<tr>
					<td>${tariffNames[i]}</td>
					<td>${tariffDesc[i]}</td>
					<td>${tariffs[i].price} <fmt:message key="head.currency"/></td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientTariffs" /> <input
								type="hidden" name="tariffName" value="${tariffs[i].name}" /> <input
								type="hidden" name="order" value="true" />
							<button><fmt:message key="tariff.order"/></button>
						</form>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4">
					<form class="login-form" action="Controller" method="get">
						<input type="hidden" name="command" value="clientTariffs" />
						<input type="hidden" name="serviceName" value="${serviceName}" />
						<button><fmt:message key="tariff.sortBy"/></button>
						<select name="sortBy">
							<option value="price"><fmt:message key="tariff.sortBy.price"/></option>
							<option value="alphabet"><fmt:message key="tariff.sortBy.alphabet"/></option>
						</select>
					</form>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>