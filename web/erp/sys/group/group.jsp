<%@ page import="java.security.SecureRandom" %>
<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-09-19
  Time: 14:59
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
    <title>用户组管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/collections.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/sys/group/js/group.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>
    <script type="text/javascript">
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#group-query").datagrid('hideColumn', "groupId");
            $("#group-query").datagrid('hideColumn', "module");
            $("#group-query").datagrid('hideColumn', "modules");
//            console.log($('#group-search').attr('permission'));
            hasPermissionItems(["group-add","group-update","group-delete"]);
        });

    </script>

</head>
<body>
<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="group-query" class="easyui-datagrid" title="组管理" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				rownumbers: true,
				url:'<%= request.getContextPath()%>/GroupServlet?param=query',
				method: 'post',
				<%--queryParams:{--%>
                    <%--seq: $('#seq').val()--%>
                <%--},--%>
				toolbar: '#group-tb'
			">
    <thead>
    <tr>
        <th data-options="field:'groupId',width:100"></th>
        <th data-options="field:'groupCode',width:100">组编号</th>
        <th data-options="field:'groupName',width:100">组名称</th>
        <th data-options="field:'groupDesc',width:100">组说明</th>
        <th data-options="field:'module',width:100"></th>
        <th data-options="field:'modules',width:100"></th>
    </tr>
    </thead>
</table>

<div id="group-tb" style="height:auto">
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="group-search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryGroup()">查询</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="group-add" permission="group_add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addGroup()">添加</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="group-update" permission="group_update" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateGroup()">编辑</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="group-delete" permission="group_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteGroup()">删除</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
</div>

<%--<div id="toolbar">--%>
    <%--<table cellspacing="0" cellpadding="0">--%>
        <%--<tr>--%>
            <%--<td>--%>
                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onClick="javascript:addNew();">新增</a>--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<div class="datagrid-btn-separator"></div>--%>
            <%--</td>--%>
            <%--<td>--%>
                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onClick="javascript:updateInfo();">修改</a>--%>
            <%--</td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</div>--%>

<div id="group-dlg" class="easyui-dialog" title="增加组" style="width:500px;height:400px;padding:10px"
     data-options="
				iconCls: 'icon-save',
				maximizable:true,
				collapsible:false,
				modal: true,
				closed: true,
				toolbar: [
				    '-',
				    {
				        text: '保存',
				        iconCls: 'icon-save',
				        handler: function () {
				            saveGroupForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeGroupDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="group-form" method="post" style="width:auto">
        <input type="hidden" id="groupId" />
        <table cellpadding="5">
            <tr>
                <td>组编号:</td>
                <td>
                    <input class="easyui-textbox" id="groupCode" name="groupCode"
                           data-options="width:250,required:true,validType:'remotegroupcode[\'groupId\', \'seq\']'">
                    </input>
                </td>
            </tr>
            <tr>
                <td>组名称:</td>
                <td>
                    <input class="easyui-textbox" id="groupName" name="groupName" data-options="width:250,required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>组描述:</td>
                <td>
                    <input class="easyui-textbox" id="groupDesc" name="groupDesc" data-options="width:250,required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>模块选择:</td>
                <td>
                    <input class="easyui-combotree" id="module" name="module" data-options="
                        width: 250,
                        height: 50,
                        multiple: true,
                        multiline: true,
                        url:'<%= request.getContextPath()%>/ModuleServlet?param=query',
				        method: 'post',
				        required:true,
				        queryParams:{
                            <%--seq: $('#seq').val(),--%>
                            flag: 'false'
                        }
                        ">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
