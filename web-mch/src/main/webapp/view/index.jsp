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

    <title>Dashboard - Merchant - WeCtrl</title>

    <!-- Bootstrap core CSS -->
    <link href="/res/css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="/res/css/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet" href="/res/font-awesome/css/font-awesome.min.css">
    <!-- Page Specific CSS -->
    <link rel="stylesheet" href="/res/css/morris-0.4.3.min.css">
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
                <jsp:include page="/view/include/user.jsp"/>
            </div><!-- /.navbar-collapse -->
        </nav>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1>Dashboard</h1>
                    <ol class="breadcrumb">
                        <li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
                    </ol>
                </div>
            </div><!-- /.row -->
            <div class="row">
                <form role="form" action="/" class="col-lg-3" style="float: right;">
                    <input type="hidden" name="pagination" value="0"/>
                    <input type="hidden" name="capacity" value="20"/>
                    <div class="form-group input-group">
                        <input type="text" class="form-control" name="keyword" value="${param.keyword}" placeholder="关键字搜索">
                            <span class="input-group-btn">
                          <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                        </span>
                    </div>
                </form>
            </div>
            <div class="row">
                <div class="col-lg-12"><h3>我的应用</h3></div>
                <c:forEach items="${page.entities}" var="app">
                    <div class="col-lg-4">
                        <div class="panel panel-success">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-2">
                                        <i class="fa fa-comments fa-5x"></i>
                                        <p class="announcement-heading"></p>
                                    </div>
                                    <div class="col-xs-10 text-right">
                                        <p class="announcement-heading">${app.appName}</p>
                                        <p class="announcement-text">${app.type.name}</p>
                                        <p class="announcement-text">${app.appID}</p>
                                        <p class="announcement-text">${app.verified ? '已验证' : '未验证'}</p>
                                    </div>
                                </div>
                            </div>
                            <a href="/applications/${app.appID}/index">
                                <div class="panel-footer announcement-bottom">
                                    <div class="row">
                                        <div class="col-xs-8">
                                            应用详情
                                        </div>
                                        <div class="col-xs-4 text-right">
                                            <i class="fa fa-arrow-circle-right"></i>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
                <div class="col-lg-4">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-2">
                                    <i class="fa fa-comments fa-5x"></i>
                                    <p class="announcement-heading"></p>
                                </div>
                                <div class="col-xs-10 text-right">
                                    <p class="announcement-heading">+新应用</p>
                                    <p class="announcement-text">&nbsp;</p>
                                    <p class="announcement-text">&nbsp;</p>
                                    <p class="announcement-text">&nbsp;</p>
                                </div>
                            </div>
                        </div>
                        <a href="/applications/new">
                            <div class="panel-footer announcement-bottom">
                                <div class="row">
                                    <div class="col-xs-8">
                                        添加应用
                                    </div>
                                    <div class="col-xs-4 text-right">
                                        <i class="fa fa-arrow-circle-right"></i>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <c:if test="${page.total > page.capacity}">
                <div style="float: right;"><jsp:include page="/view/include/page-control.jsp" /></div>
            </c:if>
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
</body>
</html>
