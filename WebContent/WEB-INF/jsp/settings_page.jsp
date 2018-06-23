<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="settings.settings"/></title>
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
					<p><fmt:message key="settings.saved"/></p>
				</c:when>
				<c:otherwise>
					<form action="Controller" method="get" >
						<input type="hidden" name="command" value="settings"/>
						<input type="hidden" name="submit" value="true"/>
						<p><fmt:message key="settings.language"/></p>
						<select name="language">
							<option value="ru">Русский</option>
							<option value="en">English</option>
						</select>
						<p><button><fmt:message key="settings.save"/></button></p>
						--------------------------------------------------
					</form>
					<form action="Controller" method="get" >
						<input type="hidden" name="command" value="settings"/>
						<input type="hidden" name="submit" value="true"/>
						<p><fmt:message key="settings.newLogin"/></p>
						<input type="text" name="login" placeholder="${user.login}">
						<p><fmt:message key="settings.newPassword"/></p>
						<input type="password" name="password" placeholder="<fmt:message key="settings.newPassword"/>">
						<p><fmt:message key="settings.passwordAgain"/></p>
						<input type="password" name="repeatPassword" placeholder="<fmt:message key="settings.passwordAgain"/>">
						<p><fmt:message key="settings.newFirstName"/></p>
						<input type="text" name="firstName" placeholder="${user.firstName}">
						<p><fmt:message key="settings.newLastName"/></p>
						<input type="text" name="lastName" placeholder="${user.lastName}">
						<button><fmt:message key="settings.save"/></button>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>