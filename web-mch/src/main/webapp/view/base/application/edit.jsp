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
                <h1>${app.appName}
                    <small></small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="/"> 我的应用</a></li>
                    <li><a href="/applications/${app.appID}/index"> ${app.appName}</a></li>
                    <li class="active"> 详细</li>
                </ol>
            </div>
        </div><!-- /.row -->
        <form role="form" action="/applications/${app.appID}" method="PUT" onsubmit="javascript: return onSubmitButtomTap(this);">
            <div class="col-lg-12">
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        <h3 class="panel-title">中控服务器 App Secret</h3>
                    </div>
                    <div class="panel-body">
                        <span id="wxctrl-secret" class="text-danger">${app.secret}</span>
                        <a class="text-danger" href="javascript: onSecretResetButtonTap('${app.appID}');">重设</a>
                    </div>
                    <div class="panel-body">
                        <p class="text-danger">为了防止微信公众号的真正AppSecret在调用中控服务器API时造成泄露, 请使用该AppSecret代替.</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <h3>基本配置</h3>
                <div class="form-group">
                    <label>App ID</label>
                    <input class="form-control" name="appID" value="${app.appID}" disabled="disabled">
                    <p class="help-block">公众号基本配置的AppID(应用ID)</p>
                </div>
                <div class="form-group">
                    <label>App Secret</label>
                    <input type="password" class="form-control" name="appSecret" value="${app.appSecret}">
                    <p class="help-block">公众号基本配置AppSecret(应用密钥)</p>
                </div>
                <div class="form-group">
                    <label>URL</label>
                    <div class="form-group input-group">
                        <input class="form-control" value="${app.pushURL}" name="pushURL" readonly="readonly">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button"><i class="fa fa-copy"></i></button>
                        </span>
                    </div>
                    <p class="help-block">公众号基本配置的URL(服务器地址)</p>
                </div>
                <div class="form-group">
                    <label>Token</label>
                    <input class="form-control" name="token" value="${app.token}">
                    <p class="help-block">公众号基本配置的Token(令牌)</p>
                </div>
                <div class="form-group">
                    <label>消息加解密方式</label>
                    <div>
                        <label class="radio-inline">
                            <input type="radio" name="mode" value="PLAIN" ${empty app.encoding.mode or app.encoding.mode eq 'PLAIN' ? 'checked="checked"' : ''}> 明文模式
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="mode" value="COMPATIBLE" ${app.encoding.mode eq 'COMPATIBLE' ? 'checked="checked"' : ''}> 兼容模式
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="mode" value="ENCRYPTED"${app.encoding.mode eq 'ENCRYPTED' ? 'checked="checked"' : ''}> 安全模式
                        </label>
                    </div>
                    <p class="help-block">公众号基本配置的消息加解密方式</p>
                </div>
                <div class="form-group">
                    <label>EncodingAESKey</label>
                    <input class="form-control" name="password" value="${app.encoding.password}">
                    <p class="help-block">公众号基本配置的EncodingAESKey(消息加解密密钥)</p>
                </div>
                <button type="submit" class="btn btn-primary">确定</button>
                <button type="reset" class="btn btn-default">重置</button>
                <div class="form-group">
                </div>
            </div>
            <div class="col-lg-1"></div>
            <div class="col-lg-4">
                <h3>账号详情</h3>
                <div class="form-group">
                    <label>头像地址</label>
                    <input class="form-control" name="portraitURL" value="${app.portraitURL}">
                    <p class="help-block">公众号账号详情的头像网络地址</p>
                </div>
                <div class="form-group">
                    <label>二维码地址</label>
                    <input class="form-control" name="QRCodeURL" value="${app['QRCodeURL']}">
                    <p class="help-block">公众号账号详情的二维码网络地址</p>
                </div>
                <div class="form-group">
                    <label>公众号名称</label>
                    <input class="form-control" name="appName" value="${app.appName}">
                    <p class="help-block">公众号账号详情的名称</p>
                </div>
                <div class="form-group">
                    <label>微信号</label>
                    <input class="form-control" name="appNumber" value="${app.appNumber}">
                    <p class="help-block">公众号账号详情的微信号</p>
                </div>
                <div class="form-group">
                    <label>公众号类型</label>
                    <div>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="SUBSCRIPTION" ${empty app.type or app.type eq 'SUBSCRIPTION' ? 'checked="checked"' : ''}> 订阅号
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="SERVICE" ${app.type eq 'SERVICE' ? 'checked="checked"' : ''}> 服务号
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="type" value="ENTERPRISE" ${app.type eq 'ENTERPRISE' ? 'checked="checked"' : ''}> 企业号
                        </label>
                    </div>
                    <p class="help-block">公众号账号详情的类型</p>
                </div>
                <div class="form-group">
                    <label>原始 ID</label>
                    <input class="form-control" name="originalID" value="${app.originalID}" readonly="readonly">
                    <p class="help-block">公众号账号详情的原始ID</p>
                </div>
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
    $("input[name='appID']").change(function(){
        $("input[name='pushURL']").val("http://" + this.value + ".wectrl.com/message");
    });

    function onSecretResetButtonTap(appID) {
        $.confirm("确定重设AppSecret?这将使公众号暂时无法正常使用!", "注意", function () {
            $.showLoading("正在重设...");
            $.ajax({
                type: "POST",
                url: "/applications/" + appID + "/secret",
                success: function (res) {
                    $.hideLoading();
                    if (res.success) {
                        $("#wxctrl-secret").text(res.entity);
                        $.toast("重设成功", "success", null);
                    } else {
                        $.alert(res.message ? res.message : "重设失败", "注意");
                    }
                },
                error: function (res) {
                    $.hideLoading();
                    $.alert("重设失败", "注意");
                }
            });
        }, function () {

        });
    }

    function onSubmitButtomTap(form) {
        $.showLoading("请稍候...");
        $.ajax({
            type: "PUT",
            url: form.action,
            data: $(form).serialize(),
            success: function (res) {
                $.hideLoading();
                if (res.success) {
                    location.href = res.entity;
                } else {
                    var tpl = "{{#entity}}" + $("#error-tpl").html() + "{{/entity}}";
                    var html = Mustache.render(tpl, res);
                    $("#error-container").empty().html(html);
                }
            },
            error: function (res) {
                $.hideLoading();
                res = {
                    entity: ["未知错误"]
                };
                var tpl = "{{#entity}}" + $("#error-tpl").html() + "{{/entity}}";
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
