<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %><%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-07-22
  Time: 13:56
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
    <title>模块修改</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/sys/module/js/module.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>

    <script>
        var root = '<%= request.getContextPath()%>';
        $(document).ready(function () {
            $("#module-tree").treegrid('hideColumn', "id");
            $("#module-tree").treegrid('hideColumn', "parentId");

//            hasPermissionItems(["addItem","deleteItem","resumeItem"], 'menu', "module-tree-menu");
        });
    </script>
</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="module-tree" class="easyui-treegrid" style="height: 100%" data-options="
				url: '<%= request.getContextPath()%>/ModuleServlet?param=query',
				method: 'post',
				queryParams: {
				    flag: 'true'
				},
				rownumbers: true,
				idField: 'id',
				treeField: 'text',
				onDblClickRow: function(row) {
				    $(this).treegrid('select',row.id);
				    if(hasPermission('module_update')) {
				        editNode(row);
				    }
				},
				onContextMenu: function(e, row){
				    e.preventDefault();
				    var leaf = row.leaf;
				    var display = row.display;
				    $(this).treegrid('select',row.id);
				    $('#module-tree-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
				    if (leaf == 'file') { <%--叶子结点--%>
				        $('#module-tree-menu').menu('disableItem', $('#addItem'));
                    } else {
                        if(hasPermission($('#addItem').attr('permission'))) {
                            $('#module-tree-menu').menu('enableItem', $('#addItem'));
                        }
                    }

                    if (display == '0') {
                        $('#module-tree-menu').menu('disableItem', $('#resumeItem'));
                        if(hasPermission($('#deleteItem').attr('permission'))) {
                            $('#module-tree-menu').menu('enableItem', $('#deleteItem'));
                        } else {
                            $('#module-tree-menu').menu('disableItem', $('#deleteItem'));
                        }
                    } else {
                        $('#module-tree-menu').menu('disableItem', $('#deleteItem'));
                        if(hasPermission($('#resumeItem').attr('permission'))) {
                            $('#module-tree-menu').menu('enableItem', $('#resumeItem'));
                        } else {
                            $('#module-tree-menu').menu('disableItem', $('#resumeItem'));
                        }
                    }
				}
			">
    <thead>
    <tr>
        <th data-options="field:'id'" width="220"></th>
        <th data-options="field:'parentId'" width="220"></th>
        <th data-options="field:'text'" width="220">模块名</th>
        <th data-options="field:'url'" width="250">链接</th>
        <th data-options="field:'leaf',
                formatter:function(value){
                    if (value == 'file') {
                        return '叶子结点';
                    } else {
                        return '非叶子结点';
                    }
				},
				styler: function(value,row,index){
				    if (value == 'file'){
					    return 'background-color:#99d8c9;color:red;';
					    // the function can return predefined css class and inline style
					    // return {class:'c1',style:'color:red'}
				    }
			    }
            " width="150">节点类型</th>
        <th data-options="field:'order'" width="100" align="right">排序</th>
        <th data-options="field:'display',
                formatter:function(value){
                    if (value == '0') {
                        return '显示';
                    } else {
                        return '不显示';
                    }
				},
				styler: function(value,row,index){
				    if (value != '0'){
					    return 'color:red;';
				    }
			    }
            " width="150">是否显示</th>
    </tr>
    </thead>
</table>

<div id="module-tree-menu" class="easyui-menu" style="width:120px;">
    <div onclick="addNode()" id="addItem" permission="module_add" data-options="iconCls:'icon-add'">增加节点</div>
    <div class="menu-sep"></div>
    <div onclick="deleteNode()" id="deleteItem" permission="module_delete" data-options="iconCls:'icon-remove'">删除节点</div>
    <div class="menu-sep"></div>
    <div onclick="resumeModule()" id="resumeItem" permission="module_resume" data-options="iconCls:'icon-remove'">恢复显示节点</div>
</div>

<div id="module-dlg" class="easyui-dialog" title="模块增加" style="width:400px;height:300px;padding:10px"
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
                            saveModuleNode();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeModuleDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="module-form" method="post">
        <table cellpadding="5">
            <tr>
                <td>模块名称:</td>
                <td><input class="easyui-textbox" type="text" id="modulename" name="modulename" data-options="required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>URL路径:</td>
                <td><input class="easyui-textbox" type="text" id="moduleurl" name="moduleurl">
                    </input>
                </td>
            </tr>
            <tr>
                <td>显示排序:</td>
                <td><input class="easyui-numberbox" type="number" id="disorder" name="disorder" data-options="min:1">
                    </input>
                </td>
            </tr>
            <tr>
                <td>节点类型:</td>
                <td>
                    <select id="select" class="easyui-combobox" name="select" style="width: 180px" data-options="
                            onSelect: function(record) {

                            }
                        ">
                        <option value="1">一级节点</option>
                        <option value="2">二级节点</option>
                        <option value="3">叶子节点</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
