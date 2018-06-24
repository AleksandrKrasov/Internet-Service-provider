<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="tariff.addingTariff"/></title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>
	
	<c:set var="serviceId" value="${param['serviceId']}" />
	
	<div class="login-page">
		<div class="form">
			<form action="Controller" method="POST" >
				<input type="hidden" name="command" value="addTariff"/>
				<input type="hidden" name="serviceId" value="${serviceId}"/>
				<input type="text" name="price" placeholder="<fmt:message key="tariff.price"/>">
				--------------------------------------------------
				<input type="text" name="name" placeholder="<fmt:message key="tariff.name.en"/>">
				<textarea type="text" name="description" placeholder="<fmt:message key="tariff.description.en"/>"></textarea>
				--------------------------------------------------
				<input type="text" name="nameRu" placeholder="<fmt:message key="tariff.name.ru"/>">
				<textarea type="text" name="descriptionRu" placeholder="<fmt:message key="tariff.description.ru"/>"></textarea>
				<button><fmt:message key="tariff.addNew"/></button>
			</form>
		</div>
	</div>
</body>
</html>