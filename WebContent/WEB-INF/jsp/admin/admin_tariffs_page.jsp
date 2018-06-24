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
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>

	<c:set var="tariffs" value="${requestScope.tariffs}" />
	<c:set var="serviceId" value="${param['serviceId']}" />
	<c:set var="tariffNames" value="${requestScope.tariffNames}" />
	<c:set var="tariffDescs" value="${requestScope.tariffDescs}" />
	
	<table class="table_blur">
		<tr>
			<th><fmt:message key="tariff.tariff"/></th>
			<th><fmt:message key="tariff.description"/></th>
			<th><fmt:message key="tariff.price"/></th>
			<th></th>
			<th></th>
		</tr>
		<c:if test="${not empty tariffs}">
			<c:forEach begin="0" end="${tariffs.size() - 1}" var="i">
				<tr>
					<td>${tariffNames[i]}</td>
					<td>${tariffDescs[i]}</td>
					<td>${tariffs[i].price} <fmt:message key="head.currency"/></td>
					<td>
						<form class="login-form" action="Controller" method="POST">
							<input type="hidden" name="command" value="changeTariff" />
							<input type="hidden" name="serviceId" value="${serviceId}" />
							<input type="hidden" name="tariffName" value="${tariffs[i].name}" /> 
							<button><fmt:message key="tariff.change"/></button>
						</form>
					</td>
					<td>
						<form class="login-form" action="Controller" method="POST">
							<input type="hidden" name="command" value="adminTariffs" />
							<input type="hidden" name="serviceId" value="${serviceId}" />
							<input type="hidden" name="tariffNameDelete" value="${tariffs[i].name}" />
							<button><fmt:message key="tariff.delete"/></button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan="5">
				<form class="login-form" action="Controller" method="POST">
					<input type="hidden" name="command" value="addTariff" />
					<input type="hidden" name="serviceId" value="${serviceId}" /> 
					<button><fmt:message key="tariff.add"/></button>
				</form>
			</td>
		</tr>
	</table>

</body>
</html>