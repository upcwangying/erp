<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %><%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-08-18
  Time: 15:50
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
    <title>界面样式维护</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/sys/style/js/style.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
        $(document).ready(function () {
            $("#style-query").datagrid('hideColumn', "styleId");
        });
    </script>

</head>
<body>
<table id="style-query" class="easyui-datagrid" title="界面样式" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: false,
				url:'<%= request.getContextPath()%>/StyleServlet?param=query',
				method: 'post',
				toolbar: [
				    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
                            queryStyle();
				        }
				    },
				    <%--'-',--%>
				    <%--{--%>
				        <%--text: '添加',--%>
				        <%--iconCls: 'icon-add',--%>
				        <%--handler: function () {--%>
				            <%--alert('方法未实现！');--%>
				        <%--}--%>
				    <%--},--%>
				    <%--'-',--%>
				    <%--{--%>
				        <%--text: '删除',--%>
				        <%--iconCls: 'icon-remove',--%>
				        <%--handler: function () {--%>
				            <%--alert('方法未实现！');--%>
				        <%--}--%>
				    <%--},--%>
				    '-',
				    {
				        text: '修改',
				        iconCls: 'icon-edit',
				        handler: function () {
				            updateStyle();
				        }
				    },
				    '-'
				]
			">
    <thead>
    <tr>
        <th data-options="field:'styleId',width:100"></th>
        <th data-options="field:'style',width:100">界面样式</th>
        <th data-options="field:'styleDesc',width:150">样式描述</th>
    </tr>
    </thead>
</table>

<div id="style-dlg" class="easyui-dialog" title="样式修改" style="width:450px;height:300px;padding:10px"
     data-options="
				iconCls: 'icon-save',
				maximizable:true,
				collapsible:true,
				modal: true,
				closed: true,
				toolbar: [
				    '-',
				    {
				        text: '保存',
				        iconCls: 'icon-save',
				        handler: function () {
				            saveStyleForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeStyleDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="style-form" method="post" style="width:auto">
        <table cellpadding="5">
            <tr>
                <td>样式:</td>
                <td>
                    <input class="easyui-textbox" id="style" name="style">
                    </input>
                </td>
            </tr>
            <tr>
                <td>样式描述:</td>
                <td><input class="easyui-textbox" id="styleDesc" name="styleDesc">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>

