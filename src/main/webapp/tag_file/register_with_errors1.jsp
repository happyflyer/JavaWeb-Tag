<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet" href="../bootstrap-4.4.1-dist/css/bootstrap.min.css">
    <script src="../bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
    <script src="../jquery-3.5.1/jquery-3.5.1.min.js"></script>
    <script src="../popper-2.5.3/popper.min.js"></script>
</head>
<body>
<html:Errors1/>
<h1>会员注册</h1>
<form action="register.do" method="post">
    <div class="form-group">
        <label for="username">UserName</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="Enter username">
    </div>
    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</body>
</html>
