<%@ tag description="显示错误信息的标签" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="headline" %>
<c:if test="${requestScope.errors != null}">
    <h3>${headline}</h3>
    <ul style="color: rgb(255, 0, 0);">
        <c:forEach var="error" items="${requestScope.errors}">
            <li>${error}</li>
        </c:forEach>
    </ul>
</c:if>
