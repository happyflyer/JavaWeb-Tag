<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/f" %>
<html>
<head>
    <title>jsp with if</title>
</head>
<body>
<f:if test="${param.password=='123456'}">
    <h1>你的秘密数据在此！</h1>
</f:if>
</body>
</html>
