<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 下午1:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>登录 - WeCtrl</title>
    <!-- Custom Theme files -->
    <link href="/view/auth/style.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Custom Theme files -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
</head>
<body>
<div class="login">
    <h2>WeCtrl</h2>
    <div class="login-top">
        <h1>登录</h1>
        <form action="/auth/login" method="POST">
            <input type="hidden" name="redirectURI" value="${redirectURI}">
            <input type="text" name="username" value="用户名" onfocus="if (this.value == '用户名') {this.value = '';}" onblur="if (this.value == '') {this.value = '用户名';}">
            <input type="password" name="password" value="密码密码密码" onfocus="if (this.value == '密码密码密码') {this.value = '';}" onblur="if (this.value == '') {this.value = '密码密码密码';}">
            <div class="forgot">
                <span style="float: left; color: orangered; margin-top: 10px;">${error}</span>
                <a href="/auth/forgot">忘记密码</a>
                <input type="submit" value="登录">
                &nbsp;
            </div>
        </form>
    </div>
    <div class="login-bottom">
        <h3>新用户点这里&nbsp;&nbsp;<a href="/auth/register">注册</a>&nbsp;&nbsp;</h3>
    </div>
</div>
<jsp:include page="/view/include/copyright.jsp"/>
</body>
</html>