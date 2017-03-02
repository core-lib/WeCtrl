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
    <title>Home</title>
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
            <input type="text" name="username" placeholder="用户名">
            <input type="password" name="password" placeholder="密码">
            <div class="forgot">
                <span style="float: left; color: orangered; margin-top: 10px;">${error}</span>
                <a href="">忘记密码</a>
                <input type="submit" value="登录">
                &nbsp;
            </div>
        </form>
    </div>
    <div class="login-bottom">
        <h3>新用户点这里&nbsp;&nbsp;<a href="#">注册</a>&nbsp;&nbsp;</h3>
    </div>
</div>
<div class="copyright">
    <p>Copyright &copy; 2017.Company name All rights reserved.</p>
</div>
</body>
</html>