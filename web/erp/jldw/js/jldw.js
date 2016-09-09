/**
 * 查询
 */
function queryJldw() {
    $("#jldw-query").datagrid({
        url:root + '/JldwServlet?param=query',
        queryParams:{
            seq: $("#seq").val()
        },
        method:'post'
    });
}

/**
 * 添加
 */
function addJldw() {
    openJldwDialog("add");
    $('#jldwId').val('');

}

/**
 * 删除
 */
function deleteJldw() {
    var rows = $('#jldw-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return false;
    }

    $.messager.confirm('确认框', '确定删除选中的数据吗?', function (r) {
        if (r) {
            deleteOrResume();
        }
    });

}

function deleteOrResume() {
    var rows = $('#jldw-query').datagrid('getSelections');
    var dbids = [];
    for (var i=0;i<rows.length;i++) {
        dbids.push(rows[i].jldwId);
    }

    $.ajax({
        url: root + "/JldwServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'delete',
            seq: $("#seq").val(),
            jldwId: dbids,
            staffId: staffId
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                queryJldw();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 修改
 */
function updateJldw() {
    var rows = $('#jldw-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('提示','只能选择一条数据!','info');
        return;
    }

    var row = rows[0];
    openJldwDialog('edit');
    $('#jldwId').val(row.jldwId);
    $('#jldwmc').textbox('setValue', row.jldwmc);
    $('#jldwms').textbox('setValue', row.jldwms);
}

/**
 *
 */
function saveJldwForm() {
    var jldwmc = $('#jldwmc').textbox('getValue');
    if (jldwmc == null || jldwmc == "") {
        $.messager.alert('提示','‘计量单位名称’不能为空!','info');
        return;
    }

    if (flag == 'add') {
        ajaxSubmit("");
    } else if(flag == 'edit') {
        var rows = $('#jldw-query').datagrid('getSelections');
        var jldwId = rows[0].jldwId;
        $.messager.confirm('确认框', '确定修改数据吗?', function (r) {
            if (r) {
                ajaxSubmit(jldwId);
            }
        });
    }
}

function ajaxSubmit(dbid) {
    $.ajax({
        url: root + "/JldwServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'add',
            seq: $("#seq").val(),
            jldwId: dbid,
            jldwmc: $('#jldwmc').val(),
            jldwms: $('#jldwms').val(),
            create_by: staffId,
            update_by: staffId
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeJldwDialog();
                queryJldw();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 打开Dialog
 * @type {undefined}
 */
var flag=undefined;
function openJldwDialog(type) {
    flag = type;
    $('#jldw-dlg').dialog("open");
}

/**
 * 关闭对话框
 */
function closeJldwDialog() {
    $('#jldwmc').textbox('setValue', '');
    $('#jldwms').textbox('setValue', '');
    $('#jldw-dlg').dialog("close");
}
