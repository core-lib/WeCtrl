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

    <title>${app.appName} - WeCtrl</title>

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
                    <li class="active">${app.appName}</li>
                </ol>
            </div>
        </div><!-- /.row -->
        <div class="col-lg-4">
            <h3>基本配置</h3>
            <div class="form-group">
                <label>App ID</label>
                <p class="form-control-static">${app.appID}</p>
                <p class="help-block">公众号基本配置的AppID(应用ID)</p>
            </div>
            <div class="form-group">
                <label>App Secret</label>
                <p class="form-control-static">${app.appSecret}</p>
                <p class="help-block">公众号基本配置AppSecret(应用密钥)</p>
            </div>
            <div class="form-group">
                <label>URL</label>
                <p class="form-control-static">${app.pushURL}</p>
                <p class="help-block">请添加好应用后将该地址复制到微信公众号基本配置的URL(服务器地址)中</p>
            </div>
            <div class="form-group">
                <label>Token</label>
                <p class="form-control-static">${app.token}</p>
                <p class="help-block">公众号基本配置的Token(令牌)</p>
            </div>
            <div class="form-group">
                <label>消息加解密方式</label>
                <div>
                    <label class="radio-inline">
                        <input type="radio" name="mode" value="PLAIN" disabled="disabled" ${empty app.encoding.mode or app.encoding.mode eq 'PLAIN' ? 'checked="checked"' : ''}> 明文模式
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="mode" value="COMPATIBLE" disabled="disabled" ${app.encoding.mode eq 'COMPATIBLE' ? 'checked="checked"' : ''}> 兼容模式
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="mode" value="ENCRYPTED" disabled="disabled" ${app.encoding.mode eq 'ENCRYPTED' ? 'checked="checked"' : ''}> 安全模式（推荐）
                    </label>
                </div>
                <p class="help-block">公众号基本配置的消息加解密方式</p>
            </div>
            <div class="form-group">
                <label>EncodingAESKey</label>
                <p class="form-control-static">${app.encoding.password}</p>
                <p class="help-block">公众号基本配置的EncodingAESKey(消息加解密密钥)</p>
            </div>
        </div>
        <div class="col-lg-1"></div>
        <div class="col-lg-4">
            <h3>账号详情</h3>
            <div class="form-group">
                <label>头像地址</label>
                <p class="form-control-static">${app.portraitURL}</p>
                <p class="help-block">公众号账号详情的头像网络地址</p>
            </div>
            <div class="form-group">
                <label>二维码地址</label>
                <p class="form-control-static">${app['QRCodeURL']}</p>
                <p class="help-block">公众号账号详情的二维码网络地址</p>
            </div>
            <div class="form-group">
                <label>公众号名称</label>
                <p class="form-control-static">${app.appName}</p>
                <p class="help-block">公众号账号详情的名称</p>
            </div>
            <div class="form-group">
                <label>微信号</label>
                <p class="form-control-static">${app.appNumber}</p>
                <p class="help-block">公众号账号详情的微信号</p>
            </div>
            <div class="form-group">
                <label>公众号类型</label>
                <div>
                    <label class="radio-inline">
                        <input type="radio" name="type" value="SUBSCRIPTION" disabled="disabled" ${empty app.type or app.type eq 'SUBSCRIPTION' ? 'checked="checked"' : ''}> 订阅号
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="type" value="SERVICE" disabled="disabled" ${app.type eq 'SERVICE' ? 'checked="checked"' : ''}> 服务号
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="type" value="ENTERPRISE" disabled="disabled" ${app.type eq 'ENTERPRISE' ? 'checked="checked"' : ''}> 企业号
                    </label>
                </div>
                <p class="help-block">公众号账号详情的类型</p>
            </div>
            <div class="form-group">
                <label>原始 ID</label>
                <p class="form-control-static">${app.originalID}</p>
                <p class="help-block">公众号账号详情的原始ID</p>
            </div>
        </div>
        <div class="col-lg-3">
            <c:forEach items="${errors}" var="error">
                <div class="col-lg-12">
                    <div class="alert alert-dismissable alert-danger">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                            ${error}
                    </div>
                </div>
            </c:forEach>
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

<script>
    $("input[name='appID']").change(function(){
        $("input[name='pushURL']").val("http://" + this.value + ".wectrl.com/message");
    });
</script>
</body>
</html>