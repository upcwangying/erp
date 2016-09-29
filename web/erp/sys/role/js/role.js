/**
 * 查询角色
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
 * 增加角色
 */
function addRole() {
    openRoleDialog('add');
    $('#roleId').val('');
}

/**
 * 更新角色
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
    $('#roleGroupCode').combobox('setValue', row.groupId);
}

/**
 * 删除角色
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
 * 窗口保存方法
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

    if (!$("#role-form").form("validate")) {
        $.messager.alert('警告', '输入框显示红色,数据不符合要求！', 'warning');
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
 * 打开角色编辑保存窗口
 * @type {undefined}
 */
var flag=undefined;
function openRoleDialog(type) {
    flag = type;
    $('#role-dlg').dialog('open');
    $('#roleCode').textbox('setValue', '');
    $('#roleName').textbox('setValue', '');
    $('#roleDesc').textbox('setValue', '');
    $('#roleGroupCode').combobox('setValue', '');
}

/**
 * 关闭角色编辑保存窗口
 */
function closeRoleDialog() {
    $('#roleCode').textbox('setValue', '');
    $('#roleName').textbox('setValue', '');
    $('#roleDesc').textbox('setValue', '');
    $('#roleGroupCode').combobox('setValue', '');
    $('#role-dlg').dialog('close');
}

/**
 * 右键菜单按钮
 */
function addRolePermission() {
    openRolePermissionDialog();
    $("#role-permission-query").datagrid('hideColumn', "permissionId");
    $("#role-permission-query").datagrid('hideColumn', "moduleId");
    $("#role-permission-role-query").datagrid('hideColumn', "dbid");
    $("#role-permission-role-query").datagrid('hideColumn', "permissionId");

    var rows = $('#role-query').datagrid('getSelections');
    var modules = rows[0].modules;
    console.log(modules);
    $('#role-permission-tree').tree({
        url: root+'/RolePermissionServlet?param=query-modules',
        animate: true,
        method: 'post',
        queryParams: {
            modules: modules
        },
        onSelect: function(node) {
            var leaf = node.leaf;
            if (leaf == 'file') {
                var id = node.id;
                queryPermission(id);
            } else {
                var state = node.state;
                if(state == 'open') {
                    $(this).tree('collapse', node.target);
                } else {
                    $(this).tree('expand', node.target);
                }
            }
        }
    });

    queryRolePermission();
}

/**
 * 根据模块Id查询权限
 * @param id 模块Id
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
        }
    });
}

/**
 *
 */
function addPermissionToRole() {
    var rows = $('#role-permission-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '请选择要添加的数据!', 'info');
        return;
    }

    if (hasRolePermission()) {
        var msg = "添加的权限数据中,某些权限已经添加过;选择'是',系统会过滤已添加过的数据,确定继续添加数据吗?";
        $.messager.confirm('添加确认框', msg, function (r) {
            if (r) {
                for (var i=0;i<rows.length;i++) {
                    var row = rows[i];
                    if (!hasRolePermissionByRow(row)) {
                        $('#role-permission-role-query').datagrid('insertRow',{
                            index: 0,	// index start with 0
                            row: {
                                dbid: '',
                                permissionId: row.permissionId,
                                permissionCode: row.permissionCode,
                                permissionName: row.permissionName,
                                permissionDesc: row.permissionDesc
                            }
                        });
                    } else {
                        console.log(i);
                    }

                }
            }
        });
    } else {
        for (var i=0;i<rows.length;i++) {
            var row = rows[i];
            $('#role-permission-role-query').datagrid('insertRow',{
                index: 0,	// index start with 0
                row: {
                    dbid: '',
                    permissionId: row.permissionId,
                    permissionCode: row.permissionCode,
                    permissionName: row.permissionName,
                    permissionDesc: row.permissionDesc
                }
            });
        }
    }

}

/**
 * 查询角色对应的权限
 */
function queryRolePermission() {
    var rows = $('#role-query').datagrid('getSelections');
    var roleId = rows[0].roleId;
    $("#role-permission-role-query").datagrid({
        url:root + '/RolePermissionServlet?param=query',
        queryParams:{
            roleId: roleId
        },
        method:'post'
    });
}

/**
 * 保存角色对应的权限
 */
function saveRolePermission() {
    var rows = $('#role-permission-role-query').datagrid('getRows');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', 'datagrid中，没有行数据!', 'info');
        return;
    }

    var unsavedPermissionid=[];
    for (var i=0;i<rows.length;i++) {
        var row = rows[i];
        if (row.dbid=='') {
            unsavedPermissionid.push(row.permissionId);
        }
    }
    if (unsavedPermissionid.length==0) {
        $.messager.alert('提示', '没有需要保存的数据!', 'info');
        return;
    }

    var roleRows = $('#role-query').datagrid('getSelections');
    var roleId = roleRows[0].roleId;
    $.messager.confirm('保存确认框', '确定保存数据吗？', function (r) {
        if (r) {
            $.ajax({
                url: root + "/RolePermissionServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: "insert",
                    roleId: roleId,
                    permissionId: unsavedPermissionid,
                    seq: $('#seq').val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryRolePermission();
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
 * 删除角色下的角色
 */
function deleteRolePermission() {
    var rows = $('#role-permission-role-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '请选择要删除的数据!', 'info');
        return;
    }

    if (!hasSavedData()) { // 没有已保存的数据
        console.log('!hasSavedData');
        $.messager.confirm('删除确认框', '选中的数据全是‘未保存’的数据，是否删除未保存的数据吗？', function (r) {
            if (r) {
                for (var i=rows.length-1;i>=0;i--) {
                    $('#role-permission-role-query').datagrid('deleteRow', i);
                }
            }
        });
    } else if (hasSavedData() && hasUnsavedData()) { // 有已保存和未保存的数据
        console.log('saveOrUnsave');
        var msg = "删除的数据中,有‘已保存’和‘未保存’的数据,请先执行‘保存’操作！";
        $.messager.alert('提示', msg, 'info');
        return;
    } else if (!hasUnsavedData()) { // 都是已保存的数据
        console.log('!hasUnsavedData');
        delRolePermission();
    }
}

function delRolePermission() {
    var rows = $('#role-permission-role-query').datagrid('getSelections');

    var roleRows = $('#role-query').datagrid('getSelections');
    var roleId = roleRows[0].roleId;

    var dbids=[];
    for (var i=0;i<rows.length;i++) {
        dbids.push(rows[i].dbid);
    }
    var count = getSavedDataCount();

    $.messager.confirm('删除确认框', '选中的数据全是‘已保存’的数据，是否删除‘已保存’的数据吗？', function (r) {
        if (r) {
            $.ajax({
                url: root + "/RolePermissionServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: "delete",
                    roleId: roleId,
                    dbid: dbids,
                    flag: (dbids.length==count),
                    seq: $('#seq').val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryRolePermission();
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
 * @returns {*}
 */
function getSavedDataCount() {
    var count=0;
    var rightRows = $('#role-permission-role-query').datagrid('getRows');
    for (var j=0;j<rightRows.length;j++) {
        var rightRow = rightRows[j];
        if (rightRow.permissionId != '') {
            count++;
        }
    }
    return count;
}

/**
 *
 * @returns {boolean}
 */
function hasRolePermission() {
    var rows = $('#role-permission-query').datagrid('getSelections');
    for (var i=0;i<rows.length;i++) {
        var row = rows[i];
        var rightRows = $('#role-permission-role-query').datagrid('getRows');
        for (var j=0;j<rightRows.length;j++) {
            var rightRow = rightRows[j];
            if (row.permissionId == rightRow.permissionId) {
                return true;
            }
        }
    }
    return false;
}

/**
 *
 * @param row
 * @returns {boolean}
 */
function hasRolePermissionByRow(row) {
    var rightRows = $('#role-permission-role-query').datagrid('getRows');
    for (var j = 0; j < rightRows.length; j++) {
        var rightRow = rightRows[j];
        if (row.permissionId == rightRow.permissionId) {
            return true;
        }
    }
    return false;
}

/**
 * 删除的数据中是否包含已保存的数据
 * @returns {boolean}
 */
function hasSavedData() {
    var rows = $('#role-permission-role-query').datagrid('getSelections');
    for (var i=0;i<rows.length;i++) {
        if (rows[i].dbid != '') {
            return true;
        }
    }
    return false;
}

/**
 * 删除的数据中是否包含未保存的数据
 * @returns {boolean}
 */
function hasUnsavedData() {
    var rows = $('#role-permission-role-query').datagrid('getSelections');
    for (var i=0;i<rows.length;i++) {
        if (rows[i].dbid == '') {
            return true;
        }
    }
    return false;
}

/**
 * 打开分配权限窗口
 */
function openRolePermissionDialog() {
    $('#role-permission-dlg').dialog('open');
}

/**
 * 关闭分配权限窗口
 */
function closeRolePermissionDialog() {
    $('#role-permission-dlg').dialog('close');
    queryRole();
}
