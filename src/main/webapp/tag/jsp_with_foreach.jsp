<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
<%@ taglib prefix="t" uri="http://127.0.0.1:8080/JavaWeb_Tag_war/t" %>
<%
    Collection<String> names = new ArrayList<>();
    names.add("zhangsan");
    names.add("lisi");
    names.add("wangwu");
    request.setAttribute("names", names);
%>
<html>
<head>
    <title>jsp with foreach</title>
</head>
<body>
<t:forEach items="${requestScope.names}" var="s">
    <h1>${s}</h1>
</t:forEach>
</body>
</html>
