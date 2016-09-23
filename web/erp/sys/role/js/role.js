/**
 *
 */
function queryRole() {
    $("#role-query").datagrid({
        url:root + '/RoleServlet?param=query',
        // queryParams:{
        //     seq: $("#seq").val()
        // },
        method:'post'
    });
}

/**
 *
 */
function addRole() {
    openRoleDialog('add');
    $('#roleId').val('');
}

/**
 *
 */
function updateRole() {
    var rows = $('#role-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }
    var row = rows[0];

    openRoleDialog('edit');
    $('#role-dlg').dialog('setTitle','角色修改');

    $('#roleId').val(row.roleId);
    $('#groupId').val(row.groupId);
    $('#roleCode').textbox('setValue', row.roleCode);
    $('#roleName').textbox('setValue', row.roleName);
    $('#roleDesc').textbox('setValue', row.roleDesc);
    $('#roleGroupCode').combobox('setValues', row.groupId);
}

/**
 *
 */
function deleteRole() {
    var rows = $('#role-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }

    var ids=[];
    for (var i=0;i<rows.length;i++) {
        ids.push(rows[i].roleId);
    }

    $.messager.confirm('删除确认框', '确定删除选中的数据吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/RoleServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: "delete",
                    roleId: ids,
                    seq: $('#seq').val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryRole();
                    }
                },
                error: function () {
                    alert("网络错误！")
                }
            });
        }
    });
}

/**
 *
 */
function saveRoleForm() {
    var roleCode = $('#roleCode').textbox('getValue');
    var roleName = $('#roleName').textbox('getValue');
    var roleDesc = $('#roleDesc').textbox('getValue');
    var groupId = $('#roleGroupCode').combobox('getValue');

    if (roleCode == '' || roleCode == undefined) {
        $.messager.alert('提示', '‘角色编码’不允许为空!', 'info');
        return;
    }

    if (roleName == '' || roleName == undefined) {
        $.messager.alert('提示', '‘角色名称’不允许为空!', 'info');
        return;
    }

    if (roleDesc == '' || roleDesc == undefined) {
        $.messager.alert('提示', '‘角色描述’不允许为空!', 'info');
        return;
    }

    if (groupId == '' || groupId == undefined) {
        $.messager.alert('提示', '‘组编码’不允许为空!', 'info');
        return;
    }

    if (flag == 'add') {
        $.messager.confirm('保存确认框', '确定保存数据吗?', function (r) {
            if (r) {
                saveRole('', '0', false);
            }
        });
    } else if (flag == 'edit') {
        var flag_ = groupId != $('#groupId').val();
        var rows = $('#role-query').datagrid('getSelections');
        var roleId = rows[0].roleId;
        var is_init_permission = rows[0].is_init_permission;
        $.messager.confirm('修改确认框', '确定修改数据吗?', function (r) {
            if (r) {
                saveRole(roleId, is_init_permission, flag_);
            }
        });
    }

}

/**
 *
 * @param roleId
 * @param flag
 */
function saveRole(roleId, is_init_permission, flag_) {
    $.ajax({
        url: root + "/RoleServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: "add",
            roleId: roleId,
            roleCode: $('#roleCode').textbox('getValue'),
            roleName: $('#roleName').textbox('getValue'),
            roleDesc: $('#roleDesc').textbox('getValue'),
            groupId: $('#roleGroupCode').combobox('getValue'),
            is_init_permission: is_init_permission,
            flag: flag_,
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeRoleDialog();
                queryRole();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 *
 */
function addRolePermission() {
    openRolePermissionDialog();
    $("#role-permission-query").datagrid('hideColumn', "permissionId");
    $("#role-permission-query").datagrid('hideColumn', "moduleId");
    $("#role-permission-role-query").datagrid('hideColumn', "dbid");
    $("#role-permission-role-query").datagrid('hideColumn', "permissionId");
}

function saveRolePermission() {
    
}

/**
 *
 * @type {undefined}
 */
var flag=undefined;
function openRoleDialog(type) {
    flag = type;
    $('#role-dlg').dialog('open');
    $('#roleCode').textbox('setValue', '');
    $('#roleName').textbox('setValue', '');
    $('#roleDesc').textbox('setValue', '');
}

/**
 *
 */
function closeRoleDialog() {
    $('#roleCode').textbox('setValue', '');
    $('#roleName').textbox('setValue', '');
    $('#roleDesc').textbox('setValue', '');
    $('#role-dlg').dialog('close');
}

/**
 *
 * @param id
 */
function queryPermission(id) {
    $("#role-permission-query").datagrid({
        url:root + '/PermissionServlet?param=query',
        queryParams:{
            moduleId: id
        },
        method:'post',
        onLoadSuccess: function (data) {
            console.log(data);
            // $("#role-permission-query").datagrid('selectRow', 0);
        }
    });
}

/**
 *
 */
function openRolePermissionDialog() {
    $('#role-permission-dlg').dialog('open');
}

/**
 *
 */
function closeRolePermissionDialog() {
    $('#role-permission-dlg').dialog('close');
}
