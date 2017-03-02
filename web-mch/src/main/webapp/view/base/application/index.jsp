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
                <jsp:include page="/view/include/menu.jsp"/>
                <jsp:include page="/view/include/user.jsp"/>
            </div><!-- /.navbar-collapse -->
        </nav>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1>我的应用 <small></small></h1>
                    <ol class="breadcrumb">
                        <li><a href="/"><i class="icon-dashboard"></i> Dashboard</a></li>
                        <li class="active"><i class="icon-file-alt"></i> 我的应用</li>
                    </ol>
                </div>
            </div><!-- /.row -->
            <div class="col-lg-12">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-striped tablesorter">
                        <thead>
                            <tr>
                                <th>Logo</th>
                                <th>AppName</th>
                                <th>AppNumber</th>
                                <th>QRCode</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${page.entities}" var="application">
                                <tr>
                                    <td><img height="60px" src="${application.portraitURL}"/></td>
                                    <td>${application.appName}</td>
                                    <td>${application.appNumber}</td>
                                    <td>${application['QRCodeURL']}</td>
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
</body>
</html>
