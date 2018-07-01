<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Error</title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

	<c:set var="user" value="${sessionScope.user}"/>

	<c:if test="${not empty user && user.roleId == 1}">
		<%@ include file="/WEB-INF/jsp/client/client_head.jsp" %>
	</c:if>	
	<c:if test="${not empty user && user.roleId == 0}">
		<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp" %>
	</c:if>		

	<div class="error">
		<table id="main-container">

			<tr >
				<td class="content">
				<%-- CONTENT --%>
				
					<h2 class="error"><fmt:message key="erorr.name"/></h2>
			
					<%-- this way we get the error information (error 404)--%>
					<c:set var="message" value="${requestScope['errorMessage']}"/>
					<c:set var="loginMessage" value="${requestScope['loginMessage']}"/>
					<c:set var="passwordMessage" value="${requestScope['passwordMessage']}"/>
					<c:set var="nameMessage" value="${requestScope['nameMessage']}"/>
				
					<%-- this way we get the exception --%>
					<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
				
					<c:if test="${not empty code}">
						<h3>Error code: ${code}</h3>
					</c:if>			
				
					<c:if test="${not empty message}">
						<h3><fmt:message key="error.message.message"/> ${message}</h3>
					</c:if>
					
					<c:if test="${not empty loginMessage}">
						<h3><fmt:message key="error.message.warn"/> ${loginMessage}</h3>
					</c:if>
					<c:if test="${not empty passwordMessage}">
						<h3><fmt:message key="error.message.warn"/> ${passwordMessage}</h3>
					</c:if>
					<c:if test="${not empty nameMessage}">
						<h3><fmt:message key="error.message.warn"/> ${nameMessage}</h3>
					</c:if>
				
						<%-- this way we print exception stack trace --%>
						<c:if test="${not empty exception}">
						<hr/>
						<h3>EROR 404: page not found</h3>
						<%-- <c:forEach var="stackTraceElement" items="${exception.stackTrace}">
							${stackTraceElement}
						</c:forEach> --%>
					</c:if>	
				</td>
			</tr>
		</table>
	</div>
</body>
</html>