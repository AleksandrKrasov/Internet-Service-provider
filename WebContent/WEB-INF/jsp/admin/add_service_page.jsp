<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>New Service</title>
<link href="style/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/admin/admin_head.jsp"%>
	
	<div class="login-page">
  		<div class="form">
    		<form class="login-form" action="Controller" method="POST">
    			<input type="hidden" name="command" value="addService"/>
      			<input type="text" name="nameEn" placeholder="<fmt:message key="service.nameEn"/>"/>
      			<input type="text" name="nameRu" placeholder="<fmt:message key="service.nameRu"/>"/>
      			<button><fmt:message key="service.add"/></button>
    		</form>
  		</div>
	</div>
</body>
</html>