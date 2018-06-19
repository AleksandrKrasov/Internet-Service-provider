<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client list</title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>
	
	<c:set var="clientList" value="${sessionScope.clientList}" />
	<c:set var="statusNames" value="${sessionScope.statusNames}" />
	<c:set var="status" value="${sessionScope.status}" />

	<c:if test="${not empty clientList && not empty statusNames && not empty status}">
		<table class="table_blur">
			<tr>
				<th>Login</th>
				<th>First name</th>
				<th>Last name</th>
				<th>Bill</th>
				<th>Status</th>
				<th>Change status</th>
				<th></th>
			</tr>
			<c:forEach begin="0" end="${clientList.size() - 1}" var="i">
				<tr>
					<td>${clientList[i].login}</td>
					<td>${clientList[i].firstName}</td>
					<td>${clientList[i].lastName}</td>
					<td>${clientList[i].bill}</td>
					<td>${statusNames[i]}</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientList" />
							<!-- <input type="hidden" name="changeStatus" value="true" /> -->
							<select name="changeStatus">
								<c:forEach begin="1" end="2" var="j">
									<option>${status[j].getName()}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="clientLogin" value="${clientList[i].login}" />
							<button>Change</button>
						</form>
					</td>
					<td>
						<form class="login-form" action="Controller" method="get">
							<input type="hidden" name="command" value="clientList" /> 
							<input type="hidden" name="delete" value="true" /> 
							<input type="hidden" name="clientLogin" value="${clientList[i].login}" />
							<button>Delete</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>