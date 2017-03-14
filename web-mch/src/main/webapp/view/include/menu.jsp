<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 下午3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav navbar-nav side-nav">
    <li${empty menu or menu eq 'index' ? ' class="active"' : ''}><a href="/applications/${app.appID}/index"><i class="fa fa-dashboard"></i> ${app.appName}</a></li>
    <li${menu eq 'user' ? ' class="active"' : ''}><a href="/applications/${app.appID}/users"><i class="fa fa-table"></i> 用户</a></li>
    <li${menu eq 'message' ? ' class="active"' : ''}><a href="/applications/${app.appID}/messages"><i class="fa fa-table"></i> 消息</a></li>
    <li${menu eq 'token' ? ' class="active"' : ''}><a href="/applications/${app.appID}/tokens"><i class="fa fa-table"></i> Access Token</a></li>
    <li${menu eq 'ticket' ? ' class="active"' : ''}><a href="/applications/${app.appID}/tickets"><i class="fa fa-table"></i> 票据</a></li>
    <li${menu eq 'environment' ? ' class="active"' : ''}><a href="/applications/${app.appID}/environments"><i class="fa fa-table"></i> 环境</a></li>
    <li${menu eq 'verification' ? ' class="active"' : ''}><a href="/applications/${app.appID}/verifications"><i class="fa fa-table"></i> 验证</a></li>
</ul>
