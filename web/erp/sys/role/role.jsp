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
    <title>角色管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/collections.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/sys/role/js/role.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>
    <script type="text/javascript">
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#role-query").datagrid('hideColumn', "roleId");
            $("#role-query").datagrid('hideColumn', "groupId");
            $("#role-query").datagrid('hideColumn', "modules");

            hasPermissionItems(["role-add","role-update","role-delete","role_permission_add","role_permission_delete"]);
        });
//        $(function () {
//            $("#roleGroupCode").bind('click', function(){
//                alert("绑定单击事件");
//            });
//        });
    </script>
</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="role-query" class="easyui-datagrid" title="角色管理" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-search',
				singleSelect: true,
				rownumbers: true,
				url:'<%= request.getContextPath()%>/RoleServlet?param=query',
				method: 'post',
				<%--queryParams:{--%>
                    <%--seq: $('#seq').val()--%>
                <%--},--%>
                onRowContextMenu: function(e,index,row) {
                    e.preventDefault();
                    $(this).datagrid('selectRow', index);
                    $('#role-tree-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
				toolbar: '#role-tb'
			">
    <thead>
    <tr>
        <th data-options="field:'roleId',width:100"></th>
        <th data-options="field:'roleCode',width:100">角色编码</th>
        <th data-options="field:'roleName',width:100">角色名称</th>
        <th data-options="field:'groupId',width:100"></th>
        <th data-options="field:'groupName',width:100">组名称</th>
        <th data-options="field:'modules',width:100"></th>
        <th data-options="field:'permissionCount',width:100">权限数量</th>
        <th data-options="field:'is_init_permission',width:100,
            formatter:function(value){
                if (value == '1') {
                    return '已分配权限';
                } else {
                    return '未分配权限';
                }
			}
        ">是否分配权限</th>
    </tr>
    </thead>
</table>

<div id="role-tb" style="height:auto">
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="role-search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryRole()">查询</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="role-add" permission="role_add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRole()">添加</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="role-update" permission="role_update" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateRole()">编辑</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="role-delete" permission="role_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRole()">删除</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
</div>

<div id="role-tree-menu" class="easyui-menu" style="width:120px;">
    <div onclick="addRolePermission()" id="addRolePermission" data-options="iconCls:'icon-add'">分配权限</div>
</div>

<div id="role-dlg" class="easyui-dialog" title="增加角色" style="width:400px;height:330px;padding:10px"
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
				            saveRoleForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeRoleDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="role-form" method="post" style="width:auto">
        <input type="hidden" id="roleId" />
        <input type="hidden" id="groupId" />
        <table cellpadding="5">
            <tr>
                <td>角色编码:</td>
                <td>
                    <input class="easyui-textbox" id="roleCode" name="roleCode" data-options="width:250,required:true,
                           validType:'remoterolecode[\'roleId\', \'seq\']'">
                    </input>
                </td>
            </tr>
            <tr>
                <td>角色名称:</td>
                <td>
                    <input class="easyui-textbox" id="roleName" name="roleName" data-options="width:250,required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>角色描述:</td>
                <td>
                    <input class="easyui-textbox" id="roleDesc" name="roleDesc" data-options="height:50,width:250,multiline:true,required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>组编码:</td>
                <td>
                    <input class="easyui-combobox" id="roleGroupCode" name="roleGroupCode"
                           data-options="
                           width:250,
                           required:true,
                           url: '<%= request.getContextPath()%>/GroupServlet?param=query-combo',
				           method: 'post',
				           valueField: 'groupId',
				           textField: 'groupName'
                           ">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="role-permission-dlg" class="easyui-dialog" title="分配权限"
     data-options="
				iconCls: 'icon-save',
				maximizable: false,
				collapsible: false,
				fit: true,
				modal: true,
				closed: true
			">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'west',title:'模块列表',split:true" style="width:10%;height: 100%;">
            <table id="role-permission-tree" class="easyui-tree" style="height: 100%">
            </table>
        </div>
        <div data-options="region:'center',title:'模块权限管理'" style="width:40%;height: 100%;padding:5px;background:#eee;">
            <table id="role-permission-query" class="easyui-datagrid"
                   data-options="
				iconCls: 'icon-search',
				selectOnCheck: true,
				checkOnSelect: true,
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
				        text: '添加',
				        iconCls: 'icon-add',
				        handler: function () {
                            addPermissionToRole();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeRolePermissionDialog();
				        }
				    },
				    '-'
				]
			">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true"></th>
                    <th data-options="field:'permissionId',width:100"></th>
                    <th data-options="field:'moduleId',width:100"></th>
                    <th data-options="field:'permissionCode',width:100">权限编码</th>
                    <th data-options="field:'permissionName',width:100">权限名称</th>
                    <th data-options="field:'permissionDesc',width:150">权限说明</th>
                </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'east',title:'角色权限管理'" style="width:50%;height: 100%;padding:5px;background:#eee;">
            <table id="role-permission-role-query" class="easyui-datagrid"
                   data-options="
				iconCls: 'icon-search',
				rownumbers: true,
				fit: true,
				<%--url:'<%= request.getContextPath()%>/GroupServlet?param=query',--%>
				<%--method: 'post',--%>
				<%--queryParams:{--%>
                    <%--seq: $('#seq').val()--%>
                <%--},--%>
                rowStyler: function(index,row){
		            if (row.dbid==''){
			            return 'color:red;'; // return inline style
			            // the function can return predefined css class and inline style
			            // return {class:'r1', style:{'color:#fff'}};
		            }
	            },
				toolbar: '#role-permission-tb'
			">
                <thead>
                <tr>
                    <th data-options="field:'dbid',width:100"></th>
                    <th data-options="field:'permissionId',width:100"></th>
                    <th data-options="field:'permissionCode',width:100">权限编码</th>
                    <th data-options="field:'permissionName',width:100">权限名称</th>
                    <th data-options="field:'permissionDesc',width:100">权限描述</th>
                </tr>
                </thead>
            </table>

            <div id="role-permission-tb" style="height:auto">
                <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
                <a href="javascript:void(0)" id="role-permission-search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryRolePermission()">查询</a>
                <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
                <a href="javascript:void(0)" id="role-permission-add" permission="role_permission_add" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveRolePermission()">添加</a>
                <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
                <a href="javascript:void(0)" id="role-permission-delete" permission="role_permission_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRolePermission()">删除</a>
                <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
                <a href="javascript:void(0)" id="role-permission-undo" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="closeRolePermissionDialog()">取消</a>
                <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
            </div>

        </div>
    </div>
</div>
</body>
</html>
