<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/t" %>
<html>
<head>
    <title>jsp with choose</title>
</head>
<body>
<t:choose>
    <t:when test="${param.user == 'zhangsan'}">
        <h1>${param.user}登录成功！</h1>
    </t:when>
    <t:otherwise>
        <h1>登录失败</h1>
    </t:otherwise>
</t:choose>
</body>
</html>
