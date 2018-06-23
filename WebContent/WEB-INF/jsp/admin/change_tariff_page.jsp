<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="tariff.changingTariff"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>
	
	<c:set var="tariff" value="${requestScope.tariff}"/>
	<c:set var="serviceId" value="${param['serviceId']}"/>
	
	<div class="login-page">
		<div class="form">
			<form action="Controller" method="get">
				<input type="hidden" name="command" value="changeTariff" />
				<input type="hidden" name="serviceId" value="${serviceId}" />
				<input type="hidden" name="tariffName" value="${tariff.name}" />
				<p><fmt:message key="tariff.newPrice"/></p>
				<input type="text" name="price" placeholder="${tariff.price}">
				--------------------------------------------------
				<p><fmt:message key="tariff.name.en"/></p>
				<input type="text" name="name" placeholder="${tariff.name}">
				<p><fmt:message key="tariff.description.en"/></p>
				<textarea type="text" name="description" placeholder="${tariff.description}"></textarea>
				--------------------------------------------------
				<p><fmt:message key="tariff.name.ru"/></p>
				<input type="text" name="nameRu" placeholder="${tariff.nameRu}">
				<p><fmt:message key="tariff.description.ru"/></p>
				<textarea type="text" name="descriptionRu" placeholder="${tariff.descriptionRu}"></textarea>
				<button><fmt:message key="tariff.change"/></button>
			</form>
		</div>
	</div>
</body>
</html>