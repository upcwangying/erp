/**
 * 查询按钮
 */
function queryGroup() {
    $("#group-query").datagrid({
        url:root + '/GroupServlet?param=query',
        // queryParams:{
        //     seq: $("#seq").val()
        // },
        method:'post'
    });
}

/**
 * 添加按钮
 */
function addGroup() {
    openGroupDialog('add');
    $('#groupId').val('');
}

/**
 * 删除按钮
 */
function deleteGroup() {
    var rows = $('#group-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }

    var ids=[];
    for (var i=0;i<rows.length;i++) {
        ids.push(rows[i].groupId);
    }

    $.messager.confirm('删除确认框', '确定删除选中的数据吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/GroupServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: "delete",
                    groupId: ids,
                    staffId: staffId,
                    seq: $('#seq').val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryGroup();
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
 * 编辑按钮
 */
function updateGroup() {
    var rows = $('#group-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }
    var row = rows[0];

    openGroupDialog('edit');
    $('#group-dlg').dialog('setTitle','组修改');

    $('#groupId').val(row.groupId);
    $('#groupCode').textbox('setValue', row.groupCode);
    $('#groupName').textbox('setValue', row.groupName);
    $('#groupDesc').textbox('setValue', row.groupDesc);
    $('#module').combotree('setValues', row.module.split(","));
}

/**
 *
 */
function saveGroupForm() {
    var groupCode = $('#groupCode').textbox('getValue');
    var groupName = $('#groupName').textbox('getValue');
    var groupDesc = $('#groupDesc').textbox('getValue');
    var module = $('#module').combotree('getValues');
    var tree = $('#module').combotree('tree');

    if (groupCode == "" || groupCode == undefined) {
        $.messager.alert('提示', '‘组编号’不允许为空!', 'info');
        return;
    }

    if (groupName == "" || groupName == undefined) {
        $.messager.alert('提示', '‘组名称’不允许为空!', 'info');
        return;
    }

    if (groupDesc == "" || groupDesc == undefined) {
        $.messager.alert('提示', '‘组描述’不允许为空!', 'info');
        return;
    }

    if (module.length == 0) {
        $.messager.alert('提示', '‘模块选择’不允许为空!', 'info');
        return;
    }

    var nodes = tree.tree('getChecked', ['checked','indeterminate']);
    var modules=[];
    for (var i=0;i<nodes.length;i++) {
        modules.push(nodes[i].id);
    }

    console.log(modules);
    if (flag == 'add') {
        $.messager.confirm('保存确认框', '确定保存数据吗?', function (r) {
            if (r) {
                saveGroup("", module, modules);
            }
        });
    } else if (flag == 'edit') {
        var rows = $('#group-query').datagrid('getSelections');
        var id = rows[0].groupId;
        $.messager.confirm('修改确认框', '确定修改数据吗?', function (r) {
            if (r) {
                saveGroup(id, module, modules);
            }
        });
    }
}

/**
 *
 */
function saveGroup(dbid, module, modules) {
    // var tree = $('#module').combotree('tree');
    // var modules = getAllSelectedNode(tree);
    $.ajax({
        url: root + "/GroupServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: "add",
            groupId: dbid,
            groupCode: $('#groupCode').textbox('getValue'),
            groupName: $('#groupName').textbox('getValue'),
            groupDesc: $('#groupDesc').textbox('getValue'),
            module: module,
            modules: modules,
            staffId: staffId,
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeGroupDialog();
                queryGroup();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 获取所有选中的节点
 * @returns {Array}
 */
function getAllSelectedNode(tree) {
    var set = new Set();
    var roots = tree.tree('getRoots');
    for (var i = 0; i < roots.length; i++) {
        var root = roots[i];
        get2SelectedNode(tree, root, set);
    }
    return set.toArray();
}

/**
 *
 * @param tree
 * @param root
 * @param set
 */
function get2SelectedNode(tree, root, set) {
    var children2 = tree.tree('getChildren', root.target);
    if (children2.length > 0) {
        for (var i = 0; i < children2.length; i++) {
            var child2 = children2[i];
            if (child2.parentId == root.id) { // 2级节点
                var children3 = tree.tree('getChildren', child2.target);
                if (children3.length == 0) {
                    if (child2.checkState == 'checked') {
                        set.add(child2.parentId);
                        set.add(child2.id);
                    }
                } else if (children3.length > 0) {
                    get3SelectedNode(tree, child2, set);
                }
            }
        }
    }
}

/**
 *
 * @param tree
 * @param child2
 * @param set
 */
function get3SelectedNode(tree, child2, set) {
    var children3 = tree.tree('getChildren', child2.target);
    for (var i = 0; i < children3.length; i++) {
        var child3 = children3[i];
        if (child3.checkState == 'checked') {
            set.add(child2.parentId);
            set.add(child2.id);
            set.add(child3.id);
        }
    }
}

/**
 *
 * @type {undefined}
 */
var flag=undefined;
function openGroupDialog(type) {
    flag = type;
    $('#group-dlg').dialog('open');
    $('#groupCode').textbox('setValue', '');
    $('#groupName').textbox('setValue', '');
    $('#groupDesc').textbox('setValue', '');
    $('#module').combotree('setValues', '');
}

/**
 *
 */
function closeGroupDialog() {
    $('#groupCode').textbox('setValue', '');
    $('#groupName').textbox('setValue', '');
    $('#groupDesc').textbox('setValue', '');
    $('#module').combotree('setValues', '');
    $('#group-dlg').dialog('close');
}
