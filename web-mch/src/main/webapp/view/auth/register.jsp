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
    <title>注册 - WeCtrl</title>
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
        <h1>注册</h1>
        <form action="/auth/register" method="POST">
            <input type="hidden" name="redirectURI" value="${redirectURI}">
            <input type="text" name="name" value="名称" onfocus="if (this.value == '名称') {this.value = '';}" onblur="if (this.value == '') {this.value = '名称';}">
            <input type="text" name="username" value="用户名" onfocus="if (this.value == '用户名') {this.value = '';}" onblur="if (this.value == '') {this.value = '用户名';}">
            <input type="password" name="password" value="密码密码密码" onfocus="if (this.value == '密码密码密码') {this.value = '';}" onblur="if (this.value == '') {this.value = '密码密码密码';}">
            <input type="text" name="email" value="邮箱" onfocus="if (this.value == '邮箱') {this.value = '';}" onblur="if (this.value == '') {this.value = '邮箱';}">
            <div class="forgot">
                <span style="float: left; color: orangered; margin-top: 10px;">${error}</span>
                <input type="submit" value="注册">
                &nbsp;
            </div>
        </form>
    </div>
    <div class="login-bottom">
        <h3>已有账户点这里&nbsp;&nbsp;<a href="/auth/login">登录</a>&nbsp;&nbsp;</h3>
    </div>
</div>
<jsp:include page="/view/include/copyright.jsp"/>
</body>
</html>