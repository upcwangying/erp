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
    <title>供应商增加</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/zsj/gys/js/gys.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#gys-add").datagrid('hideColumn', "gysId");
        });
    </script>
</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="gys-add" class="easyui-datagrid" title="供应商明细" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: false,
				rownumbers:true,
				<%--toolbar: '#gys-add-bar',--%>
				url:'<%= request.getContextPath()%>/combo/ComboBoxServlet?param=gys-query',
				method: 'post',
				<%--onClickCell: onClickGysCell,--%>
				toolbar: [
				    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
				            queryAddGys();
				        }
				    },
				    '-',
				    {
				        text: '添加',
				        iconCls: 'icon-add',
				        handler: function () {
                            openGysDialog('add');
				        }
				    },
				    '-',
				    {
				        text: '修改',
				        iconCls: 'icon-edit',
				        handler: function () {
				            updateGys();
				        }
				    },
				    '-',
				    {
				        text: '删除',
				        iconCls: 'icon-remove',
				        handler: function () {
				            deleteGys();
				        }
				    },
				    '-'
				]
			">
    <thead>
    <tr>

        <th data-options="field:'gysId',width:80"></th>
        <%--<th data-options="field:'gysbm',width:80">供应商编码</th>--%>
        <th data-options="field:'gysmc',width:100,editor:'textbox'">供应商名称</th>
        <th data-options="field:'gysms',width:100,editor:'textbox'">供应商描述</th>

    </tr>
    </thead>
</table>

<div id="gys-dlg" class="easyui-dialog" title="供应商增加" style="width:400px;height:200px;padding:10px"
     data-options="
				iconCls: 'icon-save',
				maximizable:true,
				collapsible:true,
				closed: true,
				modal: true,
				toolbar: [
				    '-',
				    {
				        text: '保存',
				        iconCls: 'icon-save',
				        handler: function () {
				            saveGysForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeGysDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="gys-form" method="post">
        <table cellpadding="5">
            <tr>
                <td>供应商名称:</td>
                <td><input class="easyui-textbox" type="text" id="gysmc" name="gysmc" data-options="required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>供应商描述:</td>
                <td><input class="easyui-textbox" type="text" id="gysms" name="gysms" data-options="required:true">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>