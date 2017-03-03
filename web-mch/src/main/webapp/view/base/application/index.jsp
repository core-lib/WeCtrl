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
                    <h1>我的应用 <small></small></h1>
                    <ol class="breadcrumb">
                        <li><a href="/"><i class="fa fa-dashboard"></i> Dashboard</a></li>
                        <li class="active"><i class="fa fa-table"></i> 我的应用</li>
                        <li><a href="/applications/new">添加应用</a></li>
                    </ol>
                </div>
            </div><!-- /.row -->
            <div class="col-lg-12">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped tablesorter">
                        <thead>
                            <tr>
                                <th>App ID</th>
                                <th>应用名称</th>
                                <th>类型</th>
                                <th>消息加解密方式</th>
                                <th>已验证</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${page.entities}" var="application">
                                <tr id="application-tr-${application.appID}">
                                    <td><a href="/applications/${application.appID}">${application.appID}</a></td>
                                    <td>${application.appName}</td>
                                    <td>${application.type.name}</td>
                                    <td>${application.encoding.mode.name}</td>
                                    <td>${application.verified ? '是' : '否'}</td>
                                    <td>
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-danger" onclick="javascript: onDeleteButtonTap('${application.appID}');">删除</button>
                                            <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                                            <ul class="dropdown-menu">
                                                <li><a href="/applications/${application.appID}">编辑</a></li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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
    function onDeleteButtonTap(appID) {
        $.confirm("确定删除该应用?", "注意", function () {
            $.showLoading("正在删除...");
            $.ajax({
                type: "DELETE",
                url: "/applications/" + appID,
                success: function (res) {
                    $.hideLoading();
                    if (res.success) {
                        $("#application-tr-" + appID).remove();
                    } else {
                        $.alert(res.message ? res.message : "删除失败", "注意");
                    }
                },
                error: function (res) {
                    $.alert("删除失败", "注意");
                }
            })
        }, function () {

        });
    }
</script>
</html>
