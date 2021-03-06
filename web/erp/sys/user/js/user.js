/**
 * 查询用户信息
 */
function queryUser() {
    $("#user-query").datagrid({
        url: root + '/UserServlet?param=user-query',
        queryParams:{
            seq: $('#seq').val()
        },
        method: 'post'
    });
}

/**
 * 打开窗口
 */
var flag = 'add';
function openUserDialog(type) {
    flag = type;
    $('#user-dlg').dialog('open');
    $('#staffcode').textbox('setValue', '');
    $('#staffname').textbox('setValue', '');
    $('#pwd').textbox('setValue', '');
    $('#pwd-again').textbox('setValue', '');
    $('#telphone').textbox('setValue', '');
    $('#styleid').combobox('setValue', '');
    $('#staffType').combobox('setValue', '0');
}

/**
 * 关闭窗口
 */
function closeUserDialog() {
    $('#staffcode').textbox('setValue', '');
    $('#staffname').textbox('setValue', '');
    $('#pwd').textbox('setValue', '');
    $('#pwd-again').textbox('setValue', '');
    $('#telphone').textbox('setValue', '');
    $('#styleid').combobox('setValue', '');
    $('#staffType').combobox('setValue', '0');
    $('#user-dlg').dialog('close');
}

/**
 * 保存用户
 */
function saveUserForm() {
    var staffcode = $('#staffcode').textbox('getValue');
    var staffname = $('#staffname').textbox('getValue');
    var pwd = $('#pwd').textbox('getValue');
    var telphone = $('#telphone').textbox('getValue');
    var styleid = $('#styleid').combobox('getValue');
    var staffType = $('#staffType').combobox('getValue');

    if (staffcode==''||staffcode==null) {
        $.messager.alert('提示', '‘登录名’不允许为空!', 'info');
        return;
    }
    if (staffname==''||staffname==null) {
        $.messager.alert('提示', '‘用户名’有些数据不允许为空!', 'info');
        return;
    }
    if (pwd==''||pwd==null) {
        $.messager.alert('提示', '‘密码’有些数据不允许为空!', 'info');
        return;
    }
    if (styleid==''||styleid==null) {
        $.messager.alert('提示', '‘界面样式’有些数据不允许为空!', 'info');
        return;
    }
    if (staffType==''||staffType==null) {
        $.messager.alert('提示', '‘用户类型’不允许为空!', 'info');
        return;
    }

    if (!$("#user-form").form("validate")) {
        $.messager.alert('警告', '输入框显示红色,数据不符合要求！', 'warning');
        return;
    }

    if (flag == 'add') {
        saveUser("insert", "");
    } else if (flag == 'edit') {
        var rows = $('#user-query').datagrid('getSelections');
        var id = rows[0].staffId;
        $.messager.confirm('修改确认框', '你确定修改选中的数据吗?', function (r) {
            if (r) {
                saveUser("update", id);
            }
        });
    }
}

/**
 * 删除用户
 */
function deleteUser() {
    var ids = [];
    var rows = $('#user-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中删除的数据!', 'info');
        return;
    }
    // alert(rows.length);
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].staffId);
    }
    // console.log(ids.join(','));
    $.messager.confirm('删除确认框', '你确定删除选中的数据吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/UserServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'delete',
                    dbid: ids,
                    seq: $('#seq').val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryUser();
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
 * 更新用户
 */
function updateUser() {
    var rows = $('#user-query').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示', '未选中删除的数据!', 'info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('提示', '只能修改一条数据!', 'info');
        return;
    }
    // console.log(rows.length);
    var id = rows[0].staffId;

    openUserDialog('edit');
    $('#user-dlg').dialog('setTitle','用户修改');

    $('#staffId').val(rows[0].staffId);
    $('#staffcode').textbox('setValue', rows[0].staffCode);
    $('#staffname').textbox('setValue', rows[0].staffName);
    $('#pwd').textbox('setValue', rows[0].password);
    $('#pwd-again').textbox('setValue', rows[0].password);
    $('#telphone').textbox('setValue', rows[0].telephone);
    $('#styleid').combobox('setValue', rows[0].styleId);
    $('#staffType').combobox('setValue', rows[0].staffType);
    $('#roleId').combobox('setValue', rows[0].roleId=='0'?'':rows[0].roleId);

}

function saveUser(param, dbid) {
    $.ajax({
        url: root + "/UserServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: param,
            dbid: dbid,
            staffcode: $('#staffcode').textbox('getValue'),
            staffname: $('#staffname').textbox('getValue'),
            pwd: $('#pwd').textbox('getValue'),
            telphone: $('#telphone').textbox('getValue'),
            styleid: $('#styleid').combobox('getValue'),
            roleId: $('#roleId').combobox('getValue'),
            staffType: $('#staffType').combobox('getValue'),
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeUserDialog();
                queryUser();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}
