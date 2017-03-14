<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 上午11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>用户 - WeCtrl</title>

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
                <h1>用户
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"> Dashboard</a></li>
                    <li><a href="/applications"> 我的应用</a></li>
                    <li><a href="/applications/${app.appID}"> ${app.appName}</a></li>
                    <li class="active"> 用户</li>
                    <li><a onclick="javascript: onRefreshButtonTap('${app.appID}');" href="javascript:"> 刷新</a></li>
                </ol>
            </div>
        </div><!-- /.row -->
        <div class="row">
            <form role="form" action="/applications/${app.appID}/users" class="col-lg-12">
                <input type="hidden" name="pagination" value="0"/>
                <input type="hidden" name="capacity" value="20"/>
                <div class="form-group input-group col-lg-3" style="float: right;">
                    <input type="text" class="form-control" name="keyword" value="${param.keyword}" placeholder="关键字搜索">
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                    </span>
                </div>
            </form>
        </div>
        <div class="col-lg-12">
            <div class="table-responsive">
                <table class="table table-bordered table-hover table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>头像</th>
                        <th>昵称</th>
                        <th>openID</th>
                        <th>已关注</th>
                        <th>关注时间</th>
                        <th>归属环境</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.entities}" var="user">
                        <tr>
                            <td><img src="${user.portraitURL}" width="50px"/></td>
                            <td>${user.nickname}</td>
                            <td>${user.openID}</td>
                            <td>${user.subscribed ? '是' : '否'}</td>
                            <td><fmt:formatDate value="${user.dateSubscribed}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                        ${empty user.environment.envName ? '默认环境' : user.environment.envName}
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <c:forEach items="${environments}" var="environment">
                                            <li onclick="javascript: onEnvironmentButtonTap(this, '${app.appID}', '${user.openID}', '${environment.envKey}');" style="cursor: pointer;">
                                                <a>${environment.envName}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="float: right;"><jsp:include page="/view/include/page-control.jsp" /></div>
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
</body>
<script>
    function onRefreshButtonTap(appID) {
        $.confirm("确定刷新用户?", "注意", function () {
            $.showLoading("正在刷新...");
            $.ajax({
                type: "POST",
                url: "/applications/" + appID + "/users",
                success: function (res) {
                    $.hideLoading();
                    if (res.success) {
                        location.href = "/applications/" + appID + "/users";
                    } else {
                        $.alert(res.message ? res.message : "刷新失败", "注意");
                    }
                },
                error: function (res) {
                    $.hideLoading();
                    $.alert("刷新失败", "注意");
                }
            });
        }, function () {

        });
    }

    function onEnvironmentButtonTap(btn, appID, openID, envKey) {
        $.showLoading("请稍候...");
        $.ajax({
            type: "PUT",
            url: "/applications/" + appID + "/users/" + openID,
            data: {
                envKey: envKey
            },
            success: function (res) {
                $.hideLoading();
                $(btn).parent().prev().html($(btn).text() + '<span class="caret"></span>');
                $.toast("操作成功", "success", null);
            },
            error: function (res) {
                $.hideLoading();
                $.toast(res.message ? res.message : "操作失败", "cancel", null);
            }
        });
    }
</script>
</html>
