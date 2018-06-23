<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
<title><fmt:message key="head.head"/></title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp" %>
	<div class="login-page">
		<div class="form">
			<fmt:message key="admin.welcome"/> ${sessionScope.user.firstName}!
		</div>
	</div>
</body>
</html>