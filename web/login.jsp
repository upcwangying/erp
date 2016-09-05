<%@ page import="com.erp.util.SystemConfig" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loginInIframe = SystemConfig.getValue("login.jsp.in.iframe");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
        <title>登陆</title>
        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/reset.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/supersized.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/supersized.3.2.7.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/supersized-init.js"></script>

        <script type="text/javascript">
            $(document).ready(function() {
                $('.page-container form').submit(function(){
                    var username = $(this).find('.username').val();
                    var password = $(this).find('.password').val();
                    if(username == '') {
                        alert("请输入用户名！");
                        $(this).find('.error').fadeOut('fast', function(){
                            $(this).css('top', '27px');
                        });
                        $(this).find('.error').fadeIn('fast', function(){
                            $(this).parent().find('.username').focus();
                        });
                        return false;
                    }
                    if(password == '') {
                        alert("请输入密码！");
                        $(this).find('.error').fadeOut('fast', function(){
                            $(this).css('top', '96px');
                        });
                        $(this).find('.error').fadeIn('fast', function(){
                            $(this).parent().find('.password').focus();
                        });
                        return false;
                    }
                });

                $('.page-container form .username, .page-container form .password').keyup(function(){
                    $(this).parent().find('.error').fadeOut('fast');
                });
            });

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

        </script>
    </head>
    <body>
        <div class="page-container">
            <h1>登录</h1>
            <form action="<%=request.getContextPath()%>/LoginServlet?param=login" method="post">
                <input type="text" id="username" name="username" class="username" placeholder="用户名">
                <input type="password" id="password" name="password" class="password" placeholder="密码">
                <button type="submit">Login</button>
                <div class="error"><span>+</span></div>
                <br>
                <div>${msg}</div>
            </form>
        </div>
    </body>
</html>


