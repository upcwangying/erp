function queryGys() {
    $("#gys-query").datagrid({
        url: root + '/combo/ComboBoxServlet?param=gys-query',
        method: 'post'
    });
}

function queryAddGys() {
    $("#gys-add").datagrid({
        url: root + '/combo/ComboBoxServlet?param=gys-query',
        method: 'post'
    });
}

var gysEditIndex = undefined;
function endGysEditing() {
    if (gysEditIndex == undefined) {
        return true
    }
    if ($('#gys-add').datagrid('validateRow', gysEditIndex)) {
        $('#gys-add').datagrid('endEdit', gysEditIndex);
        gysEditIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickGysCell(index, field) {
    if (gysEditIndex != index) {
        if (endGysEditing()) {
            $('#gys-add').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#gys-add').datagrid('getEditor', {index: index, field: field});
            if (ed) {
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            gysEditIndex = index;
        } else {
            setTimeout(function () {
                $('#gys-add').datagrid('selectRow', gysEditIndex);
            }, 0);
        }
    }
}

function appendGys() {
    if (endGysEditing()) {
        $('#gys-add').datagrid('appendRow', {gysId: ''});
        gysEditIndex = $('#gys-add').datagrid('getRows').length - 1;
        $('#gys-add').datagrid('selectRow', gysEditIndex)
            .datagrid('beginEdit', gysEditIndex);
    }
}

function removeGys() {
    if (gysEditIndex == undefined) {
        return
    }
    $('#gys-add').datagrid('cancelEdit', gysEditIndex)
        .datagrid('deleteRow', gysEditIndex);
    gysEditIndex = undefined;
}

function acceptGys() {
    if (endGysEditing()) {
        $('#gys-add').datagrid('acceptChanges');
    }
}

function rejectGys() {
    $('#gys-add').datagrid('rejectChanges');
    editIndex = undefined;
}

function getGysChanges() {
    var rows = $('#gys-add').datagrid('getChanges');
    alert(rows.length + ' rows are changed!');
}

var flag = 'add';
function openGysDialog(type) {
    flag = type;
    $('#gys-dlg').dialog('open');
    $('#gysmc').textbox('setValue', '');
    $('#gysms').textbox('setValue', '');
}

function closeGysDialog() {
    $('#gysmc').textbox('setValue', '');
    $('#gysms').textbox('setValue', '');
    $('#gys-dlg').dialog('close');
}

function saveGysForm() {
    if (flag == 'add') {
        saveGys("");
    } else if (flag == 'edit') {
        var rows = $('#gys-add').datagrid('getSelections');
        var id = rows[0].gysId;
        $.messager.confirm('修改确认框', '你确定修改选中的数据吗?', function (r) {
            if (r) {
                saveGys(id);
            }
        });
    }

}

/**
 *
 */
function updateGys() {
    var rows = $('#gys-add').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中数据!', 'info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('提示', '只能修改一条数据!', 'info');
        return;
    }
    // alert(rows.length);
    openGysDialog('edit');
    $('#gys-dlg').dialog('setTitle', '供应商修改');

    $('#gysmc').textbox('setValue', rows[0].gysmc);
    $('#gysms').textbox('setValue', rows[0].gysms);
}

function saveGys(dbid) {
    $.ajax({
        url: root + "/zsj/GysServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'add',
            dbid: dbid,
            gysmc: $('#gysmc').textbox('getValue'),
            gysms: $('#gysms').textbox('getValue'),
            create_staffid: staffId,
            update_staffid: staffId,
            seq: $("#seq").val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeGysDialog();
                queryAddGys();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });

}

function deleteGys() {
    var ids = [];
    var rows = $('#gys-add').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中删除的数据!', 'info');
        return;
    }
    // alert(rows.length);
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].gysId);
    }
    // console.log(ids.join(','));
    $.messager.confirm('删除确认框', '你确定删除选中的数据吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/zsj/GysServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'delete',
                    dbids: ids,
                    update_staffid: staffId,
                    seq: $("#seq").val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryAddGys();
                    }
                },
                error: function () {
                    alert("网络错误！")
                }
            });
        }
    });
}