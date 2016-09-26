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
    <title>物料增加</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/zsj/wl/js/wl.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';
        $(document).ready(function () {
            $("#wl-add").datagrid('hideColumn', "wlId");

            hasPermissionItems(["wl-add-button","wl-update","wl-delete"]);
        });
    </script>
</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="wl-add" class="easyui-datagrid" title="物料明细" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: false,
				url:'<%= request.getContextPath()%>/combo/ComboBoxServlet?param=wl-query',
				method: 'post',
				toolbar: '#wl-tb',
				rownumbers:true,
				method: 'post'
			">
    <thead>
    <tr>
        <th data-options="field:'wlId',width:80">主键</th>
        <%--<th data-options="field:'wlbm',width:80">物料编码</th>--%>
        <th data-options="field:'wlmc',width:100,editor:'textbox'">物料名称</th>
        <th data-options="field:'wlms',width:100,editor:'textbox'">物料描述</th>
    </tr>
    </thead>
</table>

<div id="wl-tb" style="height:auto">
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="wl-search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryAddWl()">查询</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="wl-add-button" permission="wl-add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="openWlDialog('add')">添加</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="wl-update" permission="wl-update" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateWl()">编辑</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="wl-delete" permission="wl-delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteWl()">删除</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
</div>


<div id="wl-dlg" class="easyui-dialog" title="物料增加" style="width:400px;height:200px;padding:10px"
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
				            saveWlForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeWlDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="wl-form" method="post">
        <table cellpadding="5">
            <tr>
                <td>物料名称:</td>
                <td><input class="easyui-textbox" type="text" id="wlmc" name="wlmc" data-options="required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>物料描述:</td>
                <td><input class="easyui-textbox" type="text" id="wlms" name="wlms" data-options="required:true">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>