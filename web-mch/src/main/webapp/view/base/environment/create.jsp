<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>我的应用 - WeCtrl</title>

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
                    <li><a href="/applications/${app.appID}/environments"> 应用环境</a></li>
                    <li class="active"> 添加环境</li>
                </ol>
            </div>
        </div><!-- /.row -->
        <form role="form" action="/applications/${app.appID}/environments" method="POST" onsubmit="javascript: return onSubmitButtomTap(this);">
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
                    <label>网页授权URL</label>
                    <input class="form-control" name="authorizeURL">
                </div>
                <div class="form-group">
                    <label>消息推送URL</label>
                    <input class="form-control" name="pushURL">
                </div>
                <button type="submit" class="btn btn-primary">确定</button>
                <button type="reset" class="btn btn-default">重置</button>
                <div class="form-group">
                </div>
            </div>
            <div class="col-lg-1"></div>
            <div class="col-lg-4">

            </div>
            <div id="error-container" class="col-lg-3">
            </div>
        </form>
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

<script>
    function onSubmitButtomTap(form) {
        $.showLoading("请稍候...");
        $.ajax({
            type: "POST",
            url: form.action,
            data: $(form).serialize(),
            success: function (res) {
                $.hideLoading();
                if (res.success) {
                    location.href = res.entity;
                } else {
                    var tpl = "{{#entity}}" + $("#error-tpl").clone().show().html() + "{{/entity}}";
                    var html = Mustache.render(tpl, res);
                    $("#error-container").empty().html(html);
                }
            },
            error: function (res) {
                $.hideLoading();
                res = {
                    entity: ["未知错误"]
                };
                var tpl = "{{#entity}}" + $("#error-tpl").clone().show().html() + "{{/entity}}";
                var html = Mustache.render(tpl, res);
                $("#error-container").empty().html(html);
            }
        });
        return false;
    }
</script>
<div id="error-tpl" class="col-lg-12" style="display: none;">
    <div class="alert alert-dismissable alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        {{.}}
    </div>
</div>
</body>
</html>