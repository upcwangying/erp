function queryModule() {
    $("#module-tree").treegrid({
        url: root + '/ModuleServlet?param=query',
        queryParams: {
            flag: 'true'
        },
        method: 'post'
    });
}

var module_flag = true;
function moduleAll() {
    if (module_flag) {
        $('#module-tree').treegrid('expandAll');
        $('#module-a').linkbutton({text: '关闭节点'});
        module_flag = false;
    } else {
        $('#module-tree').treegrid('collapseAll');
        $('#module-a').linkbutton({text: '展开节点'});
        module_flag = true;
    }

}

/**
 * 打开窗口
 */
var flag = "add";
function openModuleDialog(type) {
    flag = type;
    $('#module-dlg').dialog('open');
    if (flag == "add") {
        $("#select").combobox("enable");
        $("#select").combobox("select", "2");
    } else {
        var row = $('#module-tree').treegrid('getSelected');
        if (row.leaf == "file") {
            $("#select").combobox("select", "3");
        } else {
            if (row.parentId == 200) {
                $("#select").combobox("select", "1");
            } else {
                $("#select").combobox("select", "2");
            }
        }
        $("#select").combobox("disable");
    }
    $('#modulename').textbox('setValue', '');
    $('#moduleurl').textbox('setValue', '');
    $('#disorder').textbox('setValue', '');
}

/**
 * 关闭窗口
 */
function closeModuleDialog() {
    $('#modulename').textbox('setValue', '');
    $('#moduleurl').textbox('setValue', '');
    $('#disorder').textbox('setValue', '');
    $('#module-dlg').dialog('close');
}

/**
 * 修改节点--双击事件
 */
function editNode(row) {
    var id = row.id;
    openModuleDialog("edit");
    $("#module-dlg").dialog('setTitle', '模块修改');
    var select = $("#select").combobox("getValue");
    $('#modulename').textbox('setValue', row.text);
    $('#moduleurl').textbox('setValue', row.url);
    $('#disorder').textbox('setValue', row.order);
}

/**
 * 添加子节点
 */
function addNode() {
    if ($('#module-tree-menu').menu("getItem", $('#addItem')).disabled) {
        return;
    }
    openModuleDialog("add");
}

/**
 * 删除节点
 */
function deleteNode() {
    var selected = $("#module-tree").treegrid('getSelected');
    var parentNode = $("#module-tree").treegrid('getParent', selected.id);
    var children = [];
    if (parentNode != null) {
        children = $("#module-tree").treegrid('getChildren', parentNode.id);
    }
    if (selected.leaf == 'folder') {
        $.messager.confirm('确认框', '删除父节点将同时删除父节点下的子节点,确定删除该节点吗?', function (r) {
            if (r) {
                deleteSelectedNodes();
            }
        });
    } else if (selected.leaf == 'file' && children.length == 1) {
        $.messager.confirm('确认框', '父节点下只有一个叶子节点,确定删除该节点吗?', function (r) {
            if (r) {
                deleteSelectedNodes();
            }
        });
    } else {
        $.messager.confirm('确认框', '确定删除该节点吗?', function (r) {
            if (r) {
                deleteSelectedNodes();
            }
        });
    }

}

/**
 * 删除节点
 */
function deleteSelectedNodes() {
    var nodes = getSelectedNode();
    var ids = [];
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        var id = node.id;
        ids.push(id);
    }
    $.ajax({
        url: root + "/ModuleServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'delete',
            ids: ids
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                queryModule();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });

}

function resumeModule() {
    var selected = $("#module-tree").treegrid('getSelected');
    if (selected.display == '0') {
        $.messager.alert('提示', '该数据已是“显示”状态', 'info');
        return;
    }

    var id = selected.id;
    $.messager.confirm('确认框', '确定恢复该节点吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/ModuleServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'resume',
                    id: id
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryModule();
                    }
                },
                error: function () {
                    alert("网络错误！")
                }
            });
        }
    });
}


function saveModuleNode() {
    var row = $("#module-tree").treegrid('getSelected');
    var select = $("#select").combobox("getValue");
    var modulename = $("#modulename").textbox("getValue");
    var moduleurl = $("#moduleurl").textbox("getValue");
    var disorder = $("#disorder").numberbox("getValue");

    if (modulename == "") {
        $.messager.alert('提示', '“模块名称”不允许为空', 'info');
        return;
    }

    if (disorder == "") {
        $.messager.alert('提示', '“显示排序”不允许为空', 'info');
        return;
    }

    if (flag == "edit") {
        $.messager.confirm('确认框', '确定修改该节点吗?', function (r) {
            if (r) {
                saveNode("update", row.id, modulename, moduleurl, "", "", disorder, "");
            }
        });
    } else if (flag == "add") {
        if (select == "3" && moduleurl == "") {
            $.messager.alert('提示', '节点类型选择‘叶子节点’时，必须填写URL路径!', 'info');
            return;
        }

        var parentId = row.id;
        var parentType = "m";
        var icon = "folder";

        if (select == "1") {
            parentId = 200;
            parentType = "p";
        }

        if (select == "3") {
            icon = "file";
        }

        $.messager.confirm('保存确认框', '确定保存数据吗?', function (r) {
            if (r) {
                saveNode("insert", "", modulename, moduleurl, parentId, parentType, disorder, icon);
            }
        });
    }
}

/**
 * 添加叶节点
 */
function saveNode(param, id, text, url, parentId, parentType, order, icon) {
    $.ajax({
        url: root + "/ModuleServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: param,
            id: id,
            modulename: text,
            moduleurl: url,
            parentId: parentId,
            parentType: parentType,
            disorder: order,
            icon: icon
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeModuleDialog();
                queryModule();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 获取所有修改的节点
 * @returns {Array}
 */
function getAllChangedNode() {
    var nodes = getAllNode();
    var node = [];
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].text != nodes[i].attributes.text) {
            node.push(nodes[i]);
        }
    }
    return node;
}

/**
 * 获取所有的节点
 * @returns {Array}
 */
function getAllNode() {
    var nodes = $("#module-tree").treegrid('getRoots');
    var node = [];
    for (var i = 0; i < nodes.length; i++) {
        node.push(nodes[i]);
        var children = $("#module-tree").treegrid('getChildren', nodes[i].id);
        if (children.length > 0) {
            for (var j = 0; j < children.length; j++) {
                node.push(children[j]);
            }
        }
    }
    return node;
}

/**
 * 获得选中节点下所有的节点（包含选中的节点）
 */
function getSelectedNode() {
    var selected = $("#module-tree").treegrid('getSelected');
    var node = [];
    node.push(selected);
    var children = $("#module-tree").treegrid('getChildren', selected.id);
    if (children.length > 0) {
        for (var i = 0; i < children.length; i++) {
            node.push(children[i]);
        }
    }
    return node;
}

