<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %><%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-07-22
  Time: 13:49
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
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>填报查询</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/report/js/report.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
    </script>

</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="report-query" class="easyui-datagrid" title="填报明细" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				url:'<%= request.getContextPath()%>/ReportServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				toolbar: [
				    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
				            queryReport();
				        }
				    },
				    '-'
				]
			">
    <thead>
    <tr>

        <th data-options="field:'wlmc',width:100">物料名称</th>
        <th data-options="field:'gysmc',width:100">供应商名称</th>
        <th data-options="field:'price',width:100,align:'right'">价格</th>
        <th data-options="field:'number',width:100,align:'right'">购买数量</th>
        <th data-options="field:'staffName',width:100">采购人</th>
        <th data-options="field:'shoppingTime',width:100">购买时间</th>

    </tr>
    </thead>
</table>

</body>
</html>