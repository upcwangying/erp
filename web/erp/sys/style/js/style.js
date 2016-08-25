// 查询方法
function queryStyle() {
    $("#style-query").datagrid({
        url: root + '/StyleServlet?param=query',
        method: 'post'
    });
}

/**
 * 打开窗口
 */
function openStyleDialog() {
    $('#style-dlg').dialog('open');
    $('#style').textbox('setValue', '');
    $('#styleDesc').textbox('setValue', '');
}

/**
 * 关闭窗口
 */
function closeStyleDialog() {
    $('#style').textbox('setValue', '');
    $('#styleDesc').textbox('setValue', '');
    $('#style-dlg').dialog('close');
}

// 更新方法
function updateStyle() {
    var rows = $('#style-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中删除的数据!', 'info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('提示', '只能修改一条数据!', 'info');
        return;
    }

    openStyleDialog();
    $('#style').textbox('setValue', rows[0].style);
    $('#style').textbox('disable');
    $('#styleDesc').textbox('setValue', rows[0].styleDesc);
}

/**
 *
 */
function saveStyleForm() {
    var rows = $('#style-query').datagrid('getSelections');
    var id = rows[0].styleId;

    $.messager.confirm('修改确认框', '你确定修改选中的数据吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/StyleServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: "update",
                    styleid: id,
                    styleDesc: $('#styleDesc').textbox('getValue')
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        closeStyleDialog();
                        queryStyle();
                    }
                },
                error: function () {
                    alert("网络错误！")
                }
            });
        }
    });

}
