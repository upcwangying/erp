<%@ page import="java.security.SecureRandom" %>
<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-09-19
  Time: 15:00
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
    <title>权限管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/sys/permission/js/permission.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>

    <script type="text/javascript">
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#permission-query").datagrid('hideColumn', "permissionId");
            $("#permission-query").datagrid('hideColumn', "moduleId");
        });

    </script>
</head>

<body class="easyui-layout">

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<div data-options="region:'west',title:'模块列表',split:true" style="width:200px;">
    <table id="permission-tree" class="easyui-tree" style="height: 100%" data-options="
				url: '<%= request.getContextPath()%>/ModuleServlet?param=query',
				onlyLeafCheck: true,
				checkbox: true,
				animate: true,
				method: 'post',
				queryParams: {
				    flag: 'false'
				},
				onSelect: function(node) {
				    var leaf = node.leaf;
				    if (leaf == 'file') {
				        if (node.checked) {
                            $(this).tree('uncheck', node.target);
				        } else {
				            $(this).tree('check', node.target);
				        }
				    } else {
				        var state = node.state;
				        if(state == 'open') {
				            $(this).tree('collapse', node.target);
				        } else {
				            $(this).tree('expand', node.target);
				        }
				    }
				},
                onCollapse: function(node) {
                    var children = $(this).tree('getChildren', node.target);
                    for(var i=0;i<children.length;i++) {
                        var child=children[i];
                        if(child.leaf == 'file' && child.checked) {
                            $(this).tree('uncheck', child.target);
                        }
                    }
                }
			">
    </table>
</div>
<div data-options="region:'center',title:'权限管理'" style="padding:5px;background:#eee;">
    <table id="permission-query" class="easyui-datagrid" title="权限管理"
           data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				rownumbers: true,
				fit: true,
				<%--url:'<%= request.getContextPath()%>/GroupServlet?param=query',--%>
				<%--method: 'post',--%>
				<%--queryParams:{--%>
                    <%--seq: $('#seq').val()--%>
                <%--},--%>
				toolbar: [
				    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
                            queryPermission();
				        }
				    },
				    '-',
				    {
				        text: '添加',
				        iconCls: 'icon-add',
				        handler: function () {
				            addPermission();
				        }
				    },
				    '-',
				    {
				        text: '修改',
				        iconCls: 'icon-edit',
				        handler: function () {
				            updatePermission();
				        }
				    },
				    '-',
				    {
				        text: '删除',
				        iconCls: 'icon-remove',
				        handler: function () {
				            <%--deletePermission();--%>
				        }
				    },
				    '-'
				]
			">
        <thead>
        <tr>
            <th data-options="field:'permissionId',width:100"></th>
            <th data-options="field:'moduleId',width:100"></th>
            <th data-options="field:'permissionCode',width:100">权限编码</th>
            <th data-options="field:'permissionName',width:100">权限名称</th>
            <th data-options="field:'permissionDesc',width:150">权限说明</th>
        </tr>
        </thead>
    </table>

    <div id="permission-dlg" class="easyui-dialog" title="权限增加" style="width:400px;height:300px;padding:10px"
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
                            savePermissionForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closePermissionDialog();
				        }
				    },
				    '-'
				]
			">
        <form id="permission-form" method="post">
            <input type="hidden" id="permissionId"/>
            <table cellpadding="5">
                <tr>
                    <td>权限编码:</td>
                    <td><input class="easyui-textbox" id="permissionCode" name="permissionCode" data-options="required:true,
                                validType:'remotepermissioncode[\'permissionId\', \'seq\']'">
                        </input>
                    </td>
                </tr>
                <tr>
                    <td>权限名称:</td>
                    <td><input class="easyui-textbox" id="permissionName" name="permissionName" data-options="required:true">
                        </input>
                    </td>
                </tr>
                <tr>
                    <td>权限描述:</td>
                    <td><input class="easyui-textbox" id="permissionDesc" name="permissionDesc" data-options="height:50,multiline:true,required:true">
                        </input>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>

</body>
</html>
