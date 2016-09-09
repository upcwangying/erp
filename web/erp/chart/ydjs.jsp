<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %>
<%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-06-28
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    long seq = secureRandom.nextLong();
    session.setAttribute("random_session", seq + "");
    String version = SystemConfig.getValue("project.version");
    StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <title>月度结算图表</title>

    <script type="text/javascript">
        var root = '<%= request.getContextPath()%>';

        function insertTable() {
            $.ajax({
                url: root + "/ChartsServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                data: {
                    chart_lx: "line",
                    module_lx: 'ydjs',
                    width: 1500,
                    height: 800,
                    seq: $('#seq').val()
                },
                success: function (data) {
                    console.log(data);
                    var graphURL = root + "/servlet/DisplayServlet?filename=" + data.id;
                    var html = "<tr><td><img src='" + graphURL + "' width=1500 height=800 border=0></td></tr>";
                    $("#table").empty();
                    $("#table").append(html);
                },
                error: function () {
                    alert("网络错误！")
                }
            });

        }
    </script>
</head>
<body bgcolor="#ffffff" onload="insertTable()">
<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>
<table id="table" width="1500" border="0" cellspacing="0" cellpadding="0">
</table>
</body>
</html>
