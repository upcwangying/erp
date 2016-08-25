<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %><%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-07-22
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String version = SystemConfig.getValue("project.version");
    StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>供应商查询</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/zsj/gys/js/gys.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
    </script>

</head>
<body>

<table id="gys-query" class="easyui-datagrid" title="供应商明细" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				url:'<%= request.getContextPath()%>/combo/ComboBoxServlet?param=gys-query',
				method: 'post',
				toolbar: [
				    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
				            queryGys();
				        }
				    },
				    '-'
				]
			">
    <thead>
    <tr>

        <%--<th data-options="field:'gysbm',width:80">供应商编码</th>--%>
        <th data-options="field:'gysmc',width:100">供应商名称</th>
        <th data-options="field:'gysms',width:100">供应商描述</th>

    </tr>
    </thead>
</table>

</body>
</html>