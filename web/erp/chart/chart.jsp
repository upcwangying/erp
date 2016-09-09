<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %><%--
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
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/chart/js/chart.js?version=<%= version%>"></script>
    <title>图表</title>

    <script type="text/javascript">
        var root = '<%= request.getContextPath()%>';
    </script>
</head>
<body bgcolor="#ffffff">
<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>
<div style="margin:10px 10px;">
    <label>物料：</label>
    <input class="easyui-combobox" data-options="
				url: '<%= request.getContextPath()%>/combo/ComboBoxServlet?param=wl-combo',
				method: 'post',
				valueField: 'wlbm',
				textField: 'wlmc',
				panelWidth: 180,
				panelHeight: 'auto',
				formatter: formatItem,
				onSelect: select
			">
</div>
<table id="table" width="1500" border="0" cellspacing="0" cellpadding="0">
</table>
</body>
</html>
