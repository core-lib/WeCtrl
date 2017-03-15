<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/15
  Time: 下午6:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>抱歉</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/jq-weui/lib/weui.min.css">
</head>
<body>
    <div align="center" style="padding-top: 5rem;">
        <i class="weui-icon-${not empty icon ? icon : 'info'} weui-icon_msg"></i>
        <div style="padding-top: 2rem;">
            <h3 style="font-weight: 400;">${not empty title ? title : '抱歉'}</h3>
            <p style="margin-top: 1rem; font-size: 12px; color: #888">${not empty description ? description : '公众号暂时无法提供该服务'}</p>
        </div>
    </div>
</body>
</html>
