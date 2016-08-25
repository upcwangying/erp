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
    <title>物料查询</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/zsj/wl/js/wl.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
    </script>

</head>
<body>

<table id="wl-query" class="easyui-datagrid" title="物料明细" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				url:'<%= request.getContextPath()%>/combo/ComboBoxServlet?param=wl-query',
				method: 'post',
				toolbar: [
				    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
				            queryWl();
				        }
				    },
				    '-'
				]
			">
    <thead>
    <tr>

        <%--<th data-options="field:'wlbm',width:80">物料编码</th>--%>
        <th data-options="field:'wlmc',width:100">物料名称</th>
        <th data-options="field:'wlms',width:100">物料描述</th>
        <th data-options="field:'createDate',width:100">创建时间</th>
        <th data-options="field:'updateDate',width:100">更新时间</th>
        <%--<th data-options="field:'delete',width:60,align:'center'">是否删除</th>--%>

    </tr>
    </thead>
</table>

<%--<div style="margin:10px 0;">--%>
    <%--<span>Selection Mode: </span>--%>
    <%--<select onchange="$('#wl-query').datagrid({singleSelect:(this.value==0)})">--%>
        <%--<option value="0">Single Row</option>--%>
        <%--<option value="1">Multiple Rows</option>--%>
    <%--</select>--%>
<%--</div>--%>
</body>
</html>