<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ attribute name="value"  required="true" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="even_fragment"  required="true"  fragment="true" %>
<%@ attribute name="odd_fragment"  required="true"  fragment="true" %>
 
<c:set value="${value}" var="i" scope="request" />
<c:choose>
	<c:when test="${value < 100}">
		 <jsp:invoke fragment="odd_fragment" />
	</c:when>
	<c:otherwise>
		<jsp:invoke fragment="even_fragment" />
	</c:otherwise>
</c:choose>
