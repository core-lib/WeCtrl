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

    <title>应用消息 - WeCtrl</title>

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
            <c:set var="menu" value="message" scope="request"/>
            <jsp:include page="/view/include/menu.jsp"/>
            <jsp:include page="/view/include/user.jsp"/>
        </div><!-- /.navbar-collapse -->
    </nav>
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1>应用消息
                    <small></small>
                </h1>
                <ol class="breadcrumb">

                    <li><a href="/"> 我的应用</a></li>
                    <li><a href="/applications/${app.appID}/index"> ${app.appName}</a></li>
                    <li class="active"> 应用消息</li>
                </ol>
            </div>
        </div><!-- /.row -->
        <div class="col-lg-12">
            <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li class="active"><a href="#text" data-toggle="tab" onclick="javascript: loadTexts('${app.appID}');">文本消息</a></li>
                <li><a href="#image" data-toggle="tab" onclick="javascript: loadImages('${app.appID}');">图片消息</a></li>
            </ul>
            <div class="tab-content">
                <div id="text" class="tab-pane fade active in">
                    <table class="table table-bordered table-hover table-striped">
                        <tr id="text-row-tpl" style="display: none;">
                            <td><img src="{{user.portraitURL}}" width="50px" /></td>
                            <td>{{sender}}</td>
                            <td>{{user.nickname}}</td>
                            <td>{{content}}</td>
                            <td>{{dateCreated}}</td>
                        </tr>
                        <tbody id="text-tbody">

                        </tbody>
                    </table>
                </div>
                <div id="image" class="tab-pane fade">
                    <table class="table table-bordered table-hover table-striped">
                        <tr id="image-row-tpl" style="display: none;">
                            <td><img src="{{user.portraitURL}}" width="50px" /></td>
                            <td>{{sender}}</td>
                            <td>{{user.nickname}}</td>
                            <td><img src="{{picURL}}" width="100px" /></td>
                            <td>{{dateCreated}}</td>
                        </tr>
                        <tbody id="image-tbody">

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
    $(function () {
        loadTexts('${app.appID}');
    });

    function loadTexts(appID) {
        if ($("#text-tbody").children().length > 0) {
            return;
        }
        $.showLoading("请稍候...");
        $.get("/applications/" + appID + "/messages/texts", function (page) {
            var tpl =  "{{#entities}}<tr>" + $("#text-row-tpl").html() + "</tr>{{/entities}}";
            var html = Mustache.render(tpl, page);
            $("#text-tbody").empty().html(html);
            $.hideLoading();
        });
    }

    function loadImages(appID) {
        if ($("#image-tbody").children().length > 0) {
            return;
        }
        $.showLoading("请稍候...");
        $.get("/applications/" + appID + "/messages/images", function (page) {
            var tpl =  "{{#entities}}<tr>" + $("#image-row-tpl").html() + "</tr>{{/entities}}";
            var html = Mustache.render(tpl, page);
            $("#image-tbody").empty().html(html);
            $.hideLoading();
        });
    }


</script>
</html>
