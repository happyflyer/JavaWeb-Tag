<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/t" %>
<html>
<head>
    <title>jsp with if</title>
</head>
<body>
<t:if test="${param.password=='123456'}">
    <h1>你的秘密数据在此！</h1>
</t:if>
</body>
</html>
