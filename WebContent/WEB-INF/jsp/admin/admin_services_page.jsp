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
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>

	<c:set var="serviceNames" value="${requestScope.serviceNames}" />
	<c:set var="servicelist" value="${requestScope.servicelist}" />
	<c:set var="message" value="${param['completed']}" />


	<table class="table_blur">
		<c:if test="${not empty servicelist && not empty serviceNames}">
			<tr>
				<th><fmt:message key="service.service"/></th>
				<th></th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach begin="0" end="${servicelist.size() - 1}" var="i">
				<tr>
					<td>${serviceNames[i]}</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="adminTariffs" /> <input
								type="hidden" name="serviceId" value="${servicelist[i].id}" />
							<button><fmt:message key="service.tariffs"/></button>
						</form>
					</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="renameService" /> <input
								type="hidden" name="serviceName" value="${servicelist[i].name}" />
							<button><fmt:message key="service.rename"/></button>
						</form>
					</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="adminServices" /> <input
								type="hidden" name="serviceName" value="${servicelist[i].name}" />
							<button><fmt:message key="service.delete"/></button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan="4">
				<form class="login-form" action="Controller" method="get">
					<input type="hidden" name="command" value="addService" />
					<button><fmt:message key="service.add"/></button>
				</form>
			</td>
		</tr>
	</table>

	<c:if test="${not empty message}">
		<div class="login-page">
			<div class="form">
				<p class="message"><fmt:message key="refill.opComp"/></p>
			</div>
		</div>
	</c:if>
</body>
</html>