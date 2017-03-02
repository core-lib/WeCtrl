<%--
  Created by IntelliJ IDEA.
  User: yangchangpei
  Date: 17/3/2
  Time: 下午3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav navbar-nav navbar-right navbar-user">
    <li class="dropdown messages-dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages <span class="badge">7</span> <b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li class="dropdown-header">7 New Messages</li>
            <li class="message-preview">
                <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                </a>
            </li>
            <li class="divider"></li>
            <li class="message-preview">
                <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                </a>
            </li>
            <li class="divider"></li>
            <li class="message-preview">
                <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                </a>
            </li>
            <li class="divider"></li>
            <li><a href="#">View Inbox <span class="badge">7</span></a></li>
        </ul>
    </li>
    <li class="dropdown alerts-dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> Alerts <span class="badge">3</span> <b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li><a href="#">Default <span class="label label-default">Default</span></a></li>
            <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
            <li><a href="#">Success <span class="label label-success">Success</span></a></li>
            <li><a href="#">Info <span class="label label-info">Info</span></a></li>
            <li><a href="#">Warning <span class="label label-warning">Warning</span></a></li>
            <li><a href="#">Danger <span class="label label-danger">Danger</span></a></li>
            <li class="divider"></li>
            <li><a href="#">View All</a></li>
        </ul>
    </li>
    <li class="dropdown user-dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${merchant.name} <b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
            <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
            <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
            <li class="divider"></li>
            <li><a href="/auth/logout"><i class="fa fa-power-off"></i> Log Out</a></li>
        </ul>
    </li>
</ul>
