<%@ page import="com.erp.util.SystemConfig" %><%--
Created by IntelliJ IDEA.
User: wang_
Date: 2016-06-28
Time: 10:11
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loginInIframe = SystemConfig.getValue("login.jsp.in.iframe");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <link type="text/css" rel="stylesheet" href="<%= request.getContextPath()%>/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
    <title>登陆</title>
    <script language="JavaScript">
        var loginInFrame = '<%= loginInIframe%>';
        console.log("loginInFrame:" + loginInFrame);
        if (loginInFrame == "true") {
            if (window != parent) {
                parent.location.href = location.href;
            }
        } else {
            if (window != top) {
                top.location.href = location.href;
            }
        }

        function login() {
            var userid = document.getElementById("userid").value;
            var pwd = document.getElementById("pwd").value;
            if (userid == "") {
                alert("请输入用户名！");
                document.loginForm.userid.focus();
                return false;
            }
            if (pwd == "") {
                alert("请输入密码！");
                document.loginForm.pwd.focus();
                return false;
            }
            document.loginForm.submit();
        }
    </script>
</head>
<body onload="document.getElementById('userid').focus()">
    <form class="form-horizontal" id="loginForm" name="loginForm" action="<%=request.getContextPath()%>/LoginServlet?param=login" method="post">
        <div class="form-group">
            <label for="userid" class="col-md-5 control-label">用户名：</label>
            <div class="col-md-2">
                <input type="text" class="form-control" id="userid" name="userid" placeholder="请输入用户名">
            </div>
        </div>
        <div class="form-group">
            <label for="pwd" class="col-md-5 control-label">密码：</label>
            <div class="col-md-2">
                <input type="password" class="form-control" id="pwd" name="pwd" placeholder="请输入密码">
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-5 col-md-7">
                <button class="btn btn-default" onclick="login()">登陆</button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-5 col-md-7">${msg}</div>
        </div>

    </form>
</body>
</html>