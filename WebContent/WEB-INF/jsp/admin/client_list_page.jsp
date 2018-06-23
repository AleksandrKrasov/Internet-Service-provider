<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="clients.clients"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>
	
	<c:set var="clientList" value="${requestScope.clientList}" />
	<c:set var="statusNames" value="${requestScope.statusNames}" />
	<c:set var="status" value="${requestScope.status}" />
	<c:set var="radioStatusNames" value="${requestScope.radioStatusNames}" />

	<c:if test="${not empty clientList && not empty statusNames && not empty status}">
		<table class="table_blur">
			<tr>
				<th><fmt:message key="clients.login"/></th>
				<th><fmt:message key="clients.firstName"/></th>
				<th><fmt:message key="clients.lastName"/></th>
				<th><fmt:message key="clients.bill"/></th>
				<th><fmt:message key="clients.status"/></th>
				<th><fmt:message key="clients.changeStatus"/></th>
				<th></th>
			</tr>
			<c:forEach begin="0" end="${clientList.size() - 1}" var="i">
				<tr>
					<td>${clientList[i].login}</td>
					<td>${clientList[i].firstName}</td>
					<td>${clientList[i].lastName}</td>
					<td>${clientList[i].bill} <fmt:message key="head.currency"/></td>
					<td>${statusNames[i]}</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientList" />
							<select name="changeStatus">
								<c:forEach begin="1" end="2" var="j">
									<option value="${status[j].getName()}">${radioStatusNames[j - 1]}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="clientLogin" value="${clientList[i].login}" />
							<button><fmt:message key="clients.change"/></button>
						</form>
					</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientList" /> 
							<input type="hidden" name="delete" value="true" /> 
							<input type="hidden" name="clientLogin" value="${clientList[i].login}" />
							<button><fmt:message key="clients.delete"/></button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>