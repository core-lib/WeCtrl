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
            <c:set var="menu" value="application" scope="request"/>
            <jsp:include page="/view/include/menu.jsp"/>
            <jsp:include page="/view/include/user.jsp"/>
        </div><!-- /.navbar-collapse -->
    </nav>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1>我的应用
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"><i class="fa fa-dashboard"></i> Dashboard</a></li>
                    <li><a href="/applications"><i class="fa fa-table"></i> 我的应用</a></li>
                    <li class="active"><i class="fa fa-edit">添加应用</i></li>
                </ol>
            </div>
        </div><!-- /.row -->
        <div class="col-lg-6">
            <form role="form">
                <div class="form-group">
                    <label>App ID</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>App Secret</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>公众号类型</label>
                    <div>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="SUBSCRIPTION" checked> 订阅号
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="SERVICE"> 服务号
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="ENTERPRISE"> 企业号
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label>公众号账号</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>公众号名称</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>消息推送地址</label>
                    <p class="form-control-static">https://wectrl.qfoxy.com/message</p>
                </div>
                <div class="form-group">
                    <label>Token</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>头像地址</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>二维码地址</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>原始 ID</label>
                    <input class="form-control">
                </div>
                <div class="form-group">
                    <label>加密方式</label>
                    <div>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="SUBSCRIPTION" checked> 明文
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="SERVICE"> 密文
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="ENTERPRISE"> 兼容模式
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label>加密密钥</label>
                    <input class="form-control">
                </div>

                <button type="submit" class="btn btn-primary">确定</button>
                <button type="reset" class="btn btn-default">重置</button>
                <div class="form-group">
                </div>
            </form>
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
