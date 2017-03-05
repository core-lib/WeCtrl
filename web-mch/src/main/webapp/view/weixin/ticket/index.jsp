<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    <title>应用票据 - WeCtrl</title>

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
                <h1>应用票据
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"> Dashboard</a></li>
                    <li><a href="/applications"> 我的应用</a></li>
                    <li><a href="/applications/${app.appID}"> ${app.appName}</a></li>
                    <li class="active"> 应用票据</li>
                    <li><a onclick="javascript: onRefreshButtonTap('${app.appID}', '${param.type}');" href="javascript:"> 刷新</a></li>
                </ol>
            </div>
        </div><!-- /.row -->
        <div class="col-lg-12">
            <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li ${empty param.type or param.type eq 'JSAPI' ? 'class="active"' : ''}><a href="/applications/${app.appID}/tickets?type=JSAPI">JSAPI</a></li>
                <li ${param.type eq 'WX_CARD' ? 'class="active"' : ''}><a href="/applications/${app.appID}/tickets?type=WX_CARD">WXCard</a></li>
            </ul>
            <div class="table-responsive">
                <table class="table table-bordered table-hover table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>票据</th>
                        <th>过期时间</th>
                        <th>已失效</th>
                        <th>失效时间</th>
                        <th>失效原因</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.entities}" var="ticket">
                        <tr>
                            <td>
                                <div style="width: 240px; word-break: break-all;">
                                    ${ticket.value}
                                </div>
                            </td>
                            <td>${ticket.timeExpired}</td>
                            <td>${ticket.invalid ? '是' : '否'}</td>
                            <td><fmt:formatDate value="${ticket.dateInvalid}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${ticket.whyInvalid.name}</td>
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
    function onRefreshButtonTap(appID, type) {
        if (type == '') {
            type = "JSAPI";
        }
        $.confirm("确定刷新应用票据?", "注意", function () {
            $.showLoading("正在刷新...");
            $.ajax({
                type: "POST",
                url: "/applications/" + appID + "/tickets",
                data: {
                    type: type
                },
                success: function (res) {
                    $.hideLoading();
                    if (res.success) {
                        location.href = "/applications/" + appID + "/tickets?type=" + type;
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
</script>
</html>
