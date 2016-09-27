/**
 *
 */
function queryPermission() {
    var nodes = $('#permission-tree').tree('getChecked');
    if (!nodes || nodes.length == 0) {
        $.messager.alert('提示','未选中任何‘叶子节点’!','info');
        return;
    }

    if (nodes.length > 1) {
        $.messager.alert('提示','只能选择一个‘叶子节点’,请重新选择!','info');
        return;
    }
    var node = nodes[0];

    $("#permission-query").datagrid({
        url:root + '/PermissionServlet?param=query',
        queryParams:{
            moduleId: node.id
        },
        method:'post'
    });
}

/**
 *
 */
function addPermission() {
    var nodes = $('#permission-tree').tree('getChecked');
    if (!nodes || nodes.length == 0) {
        $.messager.alert('提示','未选中任何‘叶子节点’!','info');
        return;
    }

    if (nodes.length > 1) {
        $.messager.alert('提示','只能选择一个‘叶子节点’,请重新选择!','info');
        return;
    }
    var node = nodes[0];
    openPermissionDialog('add');
    $('#permissionId').val('');

}

/**
 *
 */
function updatePermission() {
    var rows = $('#permission-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }
    var row = rows[0];

    openPermissionDialog('edit');
    $('#permission-dlg').dialog('setTitle','权限修改');

    $('#permissionId').val(row.permissionId);
    $('#permissionCode').textbox('setValue', row.permissionCode);
    $('#permissionName').textbox('setValue', row.permissionName);
    $('#permissionDesc').textbox('setValue', row.permissionDesc);
}

/**
 *
 */
function deletePermission() {
    var rows = $('#permission-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }
    var permissionId=[];
    for (var i=0;i<rows.length;i++) {
        permissionId.push(rows[i].permissionId);
    }

    $.messager.confirm('删除确认框', '将同时删除角色中已分配的该权限信息，确定删除该权限吗?', function (r) {
        if (r) {
            deletePermissions(permissionId);
        }
    });
}

/**
 *
 * @param permissionId
 */
function deletePermissions(permissionId) {
    $.ajax({
        url: root + "/PermissionServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: "delete",
            permissionId: permissionId,
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                queryPermission();
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
function savePermissionForm() {
    var permissionCode = $('#permissionCode').textbox('getValue');
    var permissionName = $('#permissionName').textbox('getValue');
    var permissionDesc = $('#permissionDesc').textbox('getValue');

    if (permissionCode == '' || permissionCode == undefined) {
        $.messager.alert('提示','‘权限编码’不允许为空!','info');
        return;
    }

    if (permissionName == '' || permissionName == undefined) {
        $.messager.alert('提示','‘权限名称’不允许为空!','info');
        return;
    }

    if (permissionDesc == '' || permissionDesc == undefined) {
        $.messager.alert('提示','‘权限描述’不允许为空!','info');
        return;
    }

    if (!$("#permission-form").form("validate")) {
        $.messager.alert('警告', '输入框显示红色,数据不符合要求！', 'warning');
        return;
    }

    if (flag == 'add') {
        var nodes = $('#permission-tree').tree('getChecked');
        var node = nodes[0];
        var moduleId = node.id;
        $.messager.confirm('保存确认框', '确定保存数据吗?', function (r) {
            if (r) {
                savePermission('',moduleId);
            }
        });
    } else if (flag == 'edit') {
        var rows = $('#permission-query').datagrid('getSelections');
        var permissionId = rows[0].permissionId;
        var moduleId = rows[0].moduleId;
        $.messager.confirm('修改确认框', '确定修改数据吗?', function (r) {
            if (r) {
                savePermission(permissionId,moduleId);
            }
        });
    }

}

function savePermission(permissionId,moduleId) {
    $.ajax({
        url: root + "/PermissionServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: "add",
            permissionId: permissionId,
            moduleId: moduleId,
            permissionCode: $('#permissionCode').textbox('getValue'),
            permissionName: $('#permissionName').textbox('getValue'),
            permissionDesc: $('#permissionDesc').textbox('getValue'),
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closePermissionDialog();
                queryPermission();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 *
 * @type {undefined}
 */
var flag=undefined;
function openPermissionDialog(type) {
    flag = type;
    $('#permission-dlg').dialog('open');
    $('#permissionCode').textbox('setValue', '');
    $('#permissionName').textbox('setValue', '');
    $('#permissionDesc').textbox('setValue', '');
}

/**
 *
 */
function closePermissionDialog() {
    $('#permissionCode').textbox('setValue', '');
    $('#permissionName').textbox('setValue', '');
    $('#permissionDesc').textbox('setValue', '');
    $('#permission-dlg').dialog('close');
}