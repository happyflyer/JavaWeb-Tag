<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags" %>
<html:Html title="使用懒人标签注册">
    <html:Errors2 headline="用户注册失败"/>
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
</html:Html>
