<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 下午3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav navbar-nav side-nav">
    <li${empty menu or menu eq 'dashboard' ? ' class="active"' : ''}><a href="/"><i class="fa fa-dashboard"></i> Dashboard</a></li>
    <li${menu eq 'application' ? ' class="active"' : ''}><a href="/applications"><i class="fa fa-table"></i> 我的应用</a></li>
    <li${menu eq 'verification' ? ' class="active"' : ''}><a href="/verifications"><i class="fa fa-table"></i> 应用验证</a></li>
</ul>
