<%@ tag description="HTML 懒人标签" language="java" pageEncoding="UTF-8" %>
<%@ attribute name="title" %>
<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <link rel="stylesheet" href="../bootstrap-4.4.1-dist/css/bootstrap.min.css">
    <script src="../bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
    <script src="../jquery-3.5.1/jquery-3.5.1.min.js"></script>
    <script src="../popper-2.5.3/popper.min.js"></script>
</head>
<body>
<jsp:doBody/>
</body>
</html>
