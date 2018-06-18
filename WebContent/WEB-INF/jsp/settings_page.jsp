<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Settings</title>
</head>
<body>
	
	<c:set var="user" value="${sessionScope.user}"/>
	
	<c:if test="${not empty user && user.roleId == 1}">
		<%@ include file="/WEB-INF/jsp/client/client_head.jsp" %>
	</c:if>	
	<c:if test="${not empty user && user.roleId == 0}">
		<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp" %>
	</c:if>		
	
	<c:set var="saved" value="${param['saved']}"/>
	
	<div class="login-page">
		<div class="form">
			<c:choose>
				<c:when test="${not empty saved}">
					<p>Changes saved</p>
				</c:when>
				<c:otherwise>
					<form action="Controller" method="get" >
						<input type="hidden" name="command" value="settings"/>
						<input type="hidden" name="submit" value="true"/>
						<p>New login</p>
						<input type="text" name="login" placeholder="${user.login}">
						<p>new Password</p>
						<input type="password" name="password" placeholder="New Password">
						<p>Password again</p>
						<input type="password" name="repeatPassword" placeholder="Password again">
						<p>New first name</p>
						<input type="text" name="firstName" placeholder="${user.firstName}">
						<p>New last name</p>
						<input type="text" name="lastName" placeholder="${user.lastName}">
						<button>save</button>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>