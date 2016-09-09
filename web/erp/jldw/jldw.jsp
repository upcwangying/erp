<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %><%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-09-01
  Time: 14:13
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
    <title>用户查询</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/jldw/js/jldw.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#jldw-query").datagrid('hideColumn', "jldwId");

        });
    </script>

</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="jldw-query" class="easyui-datagrid" title="计量单位明细" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				url:'<%= request.getContextPath()%>/JldwServlet?param=query',
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
                            queryJldw();
				        }
				    },
				    '-',
				    {
				        text: '添加',
				        iconCls: 'icon-add',
				        handler: function () {
                            addJldw();
				        }
				    },
				    '-',
				    {
				        text: '修改',
				        iconCls: 'icon-edit',
				        handler: function () {
                            updateJldw();
				        }
				    },
				    '-',
				    {
				        text: '删除',
				        iconCls: 'icon-remove',
				        handler: function () {
                            deleteJldw();
				        }
				    },
				    '-'
				]
			">
    <thead>
    <tr>
        <th data-options="field:'jldwId',width:80"></th>
        <th data-options="field:'jldwmc',width:100">计量单位名称</th>
        <th data-options="field:'jldwms',width:100">计量单位描述</th>
    </tr>
    </thead>
</table>

<div id="jldw-dlg" class="easyui-dialog" title="计量单位增加" style="width:450px;height:300px;padding:10px"
     data-options="
				iconCls: 'icon-save',
				maximizable:false,
				collapsible:false,
				modal: true,
				closed: true,
				toolbar: [
				    '-',
				    {
				        text: '保存',
				        iconCls: 'icon-save',
				        handler: function () {
				            saveJldwForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeJldwDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="jldw-form" method="post" style="width:auto">
        <input type="hidden" id="jldwId" />
        <table cellpadding="5">
            <tr>
                <td>计量单位名称:</td>
                <td>
                    <input class="easyui-textbox" type="text" id="jldwmc" name="jldwmc" data-options="required:true,validType:'remotejldwmc[\'jldwId\', \'seq\']'">
                    </input>
                </td>
            </tr>
            <tr>
                <td>计量单位描述:</td>
                <td><input class="easyui-textbox" type="text" id="jldwms" name="jldwms">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
