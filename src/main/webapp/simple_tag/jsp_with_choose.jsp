<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/f" %>
<html>
<head>
    <title>jsp with choose</title>
</head>
<body>
<f:choose>
    <f:when test="${param.user == 'zhangsan'}">
        <h1>${param.user}登录成功！</h1>
    </f:when>
    <f:otherwise>
        <h1>登录失败</h1>
    </f:otherwise>
</f:choose>
</body>
</html>
