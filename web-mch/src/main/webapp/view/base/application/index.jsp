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
                <c:set var="menu" value="index" scope="request"/>
                <jsp:include page="/view/include/menu.jsp"/>
                <jsp:include page="/view/include/user.jsp"/>
            </div><!-- /.navbar-collapse -->
        </nav>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1>${app.appName} <small></small></h1>
                    <ol class="breadcrumb">
                        <li><a href="/"> 我的应用</a></li>
                        <li class="active"> ${app.appName}</li>
                        <li><a href="/applications/${app.appID}"> 编辑</a></li>
                    </ol>
                </div>
            </div><!-- /.row -->
            <div class="row">
                <div class="col-lg-4">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Pie Chart Example with Tooltips</h3>
                        </div>
                        <div class="panel-body">
                            <div class="flot-chart">
                                <div class="flot-chart-content" id="flot-chart-pie"></div>
                            </div>
                            <div class="text-right">
                                <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-long-arrow-right"></i> Multiple Axes Line Graph Example with Tooltips and Raw Data</h3>
                        </div>
                        <div class="panel-body">
                            <div class="flot-chart">
                                <div class="flot-chart-content" id="flot-chart-multiple-axes"></div>
                            </div>
                            <div class="text-right">
                                <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- /.row -->
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
</html>
