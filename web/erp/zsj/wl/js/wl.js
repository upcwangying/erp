function queryWl() {
    $("#wl-query").datagrid({
        url:root + '/combo/ComboBoxServlet?param=wl-query',
        method:'post'
    });
}

function queryAddWl() {
    $("#wl-add").datagrid({
        url:root + '/combo/ComboBoxServlet?param=wl-query',
        method:'post'
    });
}

var wlEditIndex = undefined;
function endWlEditing(){
    if (wlEditIndex == undefined){return true}
    if ($('#wl-add').datagrid('validateRow', wlEditIndex)){
        $('#wl-add').datagrid('endEdit', wlEditIndex);
        wlEditIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickWlCell(index, field){
    if (wlEditIndex != index){
        if (endWlEditing()){
            $('#wl-add').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#wl-add').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            wlEditIndex = index;
        } else {
            setTimeout(function(){
                $('#wl-add').datagrid('selectRow', wlEditIndex);
            },0);
        }
    }
}

function appendWl(){
    if (endWlEditing()){
        $('#wl-add').datagrid('appendRow',{wlId:''});
        wlEditIndex = $('#wl-add').datagrid('getRows').length-1;
        $('#wl-add').datagrid('selectRow', wlEditIndex).datagrid('beginEdit', wlEditIndex);
    }
}

function removeWl(){
    if (wlEditIndex == undefined){return}
    $('#wl-add').datagrid('cancelEdit', wlEditIndex).datagrid('deleteRow', wlEditIndex);
    wlEditIndex = undefined;
}

function acceptWl(){
    if (endWlEditing()){
        $('#wl-add').datagrid('acceptChanges');
    }
}

function rejectWl(){
    $('#wl-add').datagrid('rejectChanges');
    editIndex = undefined;
}

function getWlChanges(){
    var rows = $('#wl-add').datagrid('getChanges');
    alert(rows.length+' rows are changed!');
}

/**
 * 打开对话窗口
 */
var flag = 'add';
function openWlDialog(type) {
    flag = type;
    $('#wl-dlg').dialog('open');
    $('#wlmc').textbox('setValue', '');
    $('#wlms').textbox('setValue', '');

}

/**
 * 关闭对话窗口
 */
function closeWlDialog() {
    $('#wlmc').textbox('setValue', '');
    $('#wlms').textbox('setValue', '');
    $('#wl-dlg').dialog('close');
}

/**
 * 保存物料数据
 */

function saveWlForm() {
    if (flag == 'add') {
        $('#wl-form').form({
            url: root + '/zsj/WlServlet?param=insert',
            onSubmit: function () {
                if ($("#wl-form").form("validate"))
                    return true;
                else
                    return false;
            },
            success: function (data) {
                // console.log(data);
                // data = JSON.parse(data);
                data = $.parseJSON(data);
                alert(data.msg);
                if (data.success) {
                    closeWlDialog();
                    queryAddWl();
                }
            }
        });
        //提交表单
        $('#wl-form').submit();
    } else if (flag == 'edit') {
        saveWl();
    }
}

/**
 * 更新物料数据
 */
function updateWl() {
    var rows = $('#wl-add').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return;
    }
    if (rows.length > 1) {
        $.messager.alert('提示','只能修改一条数据!','info');
        return;
    }
    // alert(rows.length);
    openWlDialog('edit');

    $('#wlmc').textbox('setValue', rows[0].wlmc);
    $('#wlms').textbox('setValue', rows[0].wlms);
}

function saveWl() {
    var rows = $('#wl-add').datagrid('getSelections');
    var id = rows[0].wlId;

    $.messager.confirm('修改确认框', '你确定修改选中的数据吗?', function(r){
        if (r){
            $.ajax({
                url: root + "/zsj/WlServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'update',
                    dbid: id,
                    wlmc: $('#wlmc').textbox('getValue'),
                    wlms: $('#wlms').textbox('getValue')
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        // $('#wlmc').textbox('setValue', '');
                        // $('#wlms').textbox('setValue', '');
                        closeWlDialog();
                        queryAddWl();
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
 * 删除物料数据
 */
function deleteWl() {
    var ids = [];
    var rows = $('#wl-add').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中删除的数据!','info');
        return;
    }
    // alert(rows.length);
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i].wlId);
    }
    // console.log(ids.join(','));
    $.messager.confirm('删除确认框', '你确定删除选中的数据吗?', function(r){
        if (r){
            $.ajax({
                url: root + "/zsj/WlServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'delete',
                    dbids: ids
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryAddWl();
                    }
                },
                error: function () {
                    alert("网络错误！")
                }
            });
        }
    });

}