<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 上午11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>应用环境 - WeCtrl</title>

    <!-- Bootstrap core CSS -->
    <link href="/res/css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="/res/css/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet" href="/res/font-awesome/css/font-awesome.min.css">
    <!-- Page Specific CSS -->
    <link rel="stylesheet" href="/res/css/morris-0.4.3.min.css">

    <link rel="stylesheet" href="/jq-weui/lib/weui.min.css">
    <link rel="stylesheet" href="/jq-weui/css/jquery-weui.css">
</head>

<body>
<div id="wrapper">
    <!-- Sidebar -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="/">WeCtrl Merchant</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <c:set var="menu" value="application" scope="request"/>
            <jsp:include page="/view/include/menu.jsp"/>
            <jsp:include page="/view/include/user.jsp"/>
        </div><!-- /.navbar-collapse -->
    </nav>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1>应用环境
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"> Dashboard</a></li>
                    <li><a href="/applications"> 我的应用</a></li>
                    <li><a href="/applications/${app.appID}"> ${app.appName}</a></li>
                    <li class="active"> 应用环境</li>
                    <%--<li><a href="/applications/${app.appID}/environments/new"> 添加环境</a></li>--%>
                </ol>
            </div>
        </div><!-- /.row -->
        <div class="col-lg-12">
            <div class="table-responsive">
                <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                    <c:forEach items="${page.entities}" var="env" varStatus="status">
                        <li envKey="${env.envKey}" ${status.index == 0 ? 'class="active"' : ''}><a href="#${env.envKey}" data-toggle="tab">${env.envName}</a></li>
                    </c:forEach>
                    <li envKey="new" ${fn:length(page.entities) == 0 ? 'class="active"' : ''}><a href="#new" data-toggle="tab">新建环境</a></li>
                </ul>
            </div>
            <div class="tab-content">
                <c:forEach items="${page.entities}" var="env" varStatus="status">
                    <div envKey="${env.envKey}" class="tab-pane fade ${status.index == 0 ? 'active in' : ''}" id="${env.envKey}">
                        <form role="form" action="/applications/${app.appID}/environments/${env.envKey}" method="PUT" onsubmit="javascript: return onPutButtonTap(this);">
                            <div class="col-lg-4">
                                <h3>基本配置</h3>
                                <div class="form-group">
                                    <label>环境名称</label>
                                    <input class="form-control" name="envName" value="${env.envName}">
                                </div>
                                <div class="form-group">
                                    <label>环境 Key</label>
                                    <input class="form-control" name="envKey" value="${env.envKey}">
                                </div>
                                <div class="form-group">
                                    <label>网页授权域名</label>
                                    <input class="form-control" name="domain" value="${env.domain}">
                                    <p class="help-block">http://this-environment-domain 或 https://this-environment-domain</p>
                                </div>
                                <div class="form-group">
                                    <label>消息推送URL</label>
                                    <input class="form-control" name="pushURL" value="${env.pushURL}">
                                    <p class="help-block">http://this-environment-domain/path/to/receive/messages 或 https://this-environment-domain/path/to/receive/messages</p>
                                </div>
                                <div class="form-group">
                                    <label class="radio-inline">
                                        <input type="radio" name="acquiescent" value="true" ${env.acquiescent ? 'checked="checked"' : ''}> 默认环境
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="acquiescent" value="false" ${not env.acquiescent ? 'checked="checked"' : ''}> 非默认环境
                                    </label>
                                </div>
                                <button type="submit" class="btn btn-primary">确定</button>
                                <button type="reset" class="btn btn-default">重置</button>
                                <button type="button" class="btn btn-danger" onclick="javascript: onDeleteButtonTap('${app.appID}', '${env.envKey}')">删除</button>
                                <div class="form-group">
                                </div>
                            </div>
                            <div class="col-lg-1"></div>
                            <div class="col-lg-4">

                            </div>
                            <div class="col-lg-3 error-container">
                            </div>
                        </form>
                    </div>
                </c:forEach>
                <div envKey="new" class="tab-pane fade ${fn:length(page.entities) == 0 ? 'active in' : ''}" id="new">
                    <form role="form" action="/applications/${app.appID}/environments" method="POST" onsubmit="javascript: return onPostButtomTap(this);">
                        <div class="col-lg-4">
                            <h3>基本配置</h3>
                            <div class="form-group">
                                <label>环境名称</label>
                                <input class="form-control" name="envName">
                            </div>
                            <div class="form-group">
                                <label>环境 Key</label>
                                <input class="form-control" name="envKey">
                            </div>
                            <div class="form-group">
                                <label>网页授权域名</label>
                                <input class="form-control" name="domain">
                                <p class="help-block">http://this-environment-domain 或 https://this-environment-domain</p>
                            </div>
                            <div class="form-group">
                                <label>消息推送URL</label>
                                <input class="form-control" name="pushURL">
                                <p class="help-block">http://this-environment-domain/path/to/receive/messages 或 https://this-environment-domain/path/to/receive/messages</p>
                            </div>
                            <div class="form-group">
                                <label class="radio-inline">
                                    <input type="radio" name="acquiescent" value="true"> 默认环境
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="acquiescent" value="false" checked="checked"> 非默认环境
                                </label>
                            </div>
                            <button type="submit" class="btn btn-primary">新建</button>
                            <button type="reset" class="btn btn-default">重置</button>
                            <div class="form-group">
                            </div>
                        </div>
                        <div class="col-lg-1"></div>
                        <div class="col-lg-4">

                        </div>
                        <div class="col-lg-3 error-container">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div><!-- /.row -->
</div><!-- /#page-wrapper -->
</div><!-- /#wrapper -->
<!-- JavaScript -->
<script src="/res/js/jquery-1.10.2.js"></script>
<script src="/res/js/bootstrap.js"></script>
<!-- Page Specific Plugins -->
<script src="/res/js/raphael-min.js"></script>
<script src="/res/js/morris-0.4.3.min.js"></script>
<script src="/res/js/morris/chart-data-morris.js"></script>
<script src="/res/js/tablesorter/jquery.tablesorter.js"></script>
<script src="/res/js/tablesorter/tables.js"></script>
<script src="/jq-weui/lib/jquery-2.1.4.js"></script>
<script src="/jq-weui/lib/fastclick.js"></script>
<script src="/jq-weui/js/jquery-weui.js"></script>
<script src="/mustache/mustache.js"></script>
<div id="error-tpl" class="col-lg-12" style="display: none;">
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        {{.}}
    </div>
</div>
<div id="success-tpl" class="col-lg-12" style="display: none;">
    <div class="alert alert-dismissable alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        {{.}}
    </div>
</div>
</body>
<script src="/view/base/environment/index.js"></script>
</html>
