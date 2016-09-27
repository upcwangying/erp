/**
 * 查询页面查询方法
 */
function queryReport() {
    $("#report-query").datagrid({
        url:root + '/ReportServlet?param=query',
        queryParams:{
            seq: $('#seq').val()
        },
        method:'post'
    });
}

/**
 * 增加页面查询方法
 */
function queryAddReport() {
    $("#report-add").datagrid({
        url:root + '/ReportServlet?param=query',
        queryParams:{
            seq: $('#seq').val()
        },
        method:'post'
    });
}

/**
 * 停止编辑
 * @returns {boolean}
 */
var editReportIndex = undefined;
function endReportEditing(){
    if (editReportIndex == undefined){return true}
    if ($('#report-add').datagrid('validateRow', editReportIndex)){
        $('#report-add').datagrid('endEdit', editReportIndex);
        editReportIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function endReportEdit() {
    var flag = endReportEditing();
    if (!flag) {
        $.messager.alert('错误','校验指定行数据失败!','error');
        return;
    }
}

/**
 * 双击事件
 * @param index
 * @param field
 */
function onClickReportCell(index, field) {
    if (!hasPermission('report_update')) {
        $.messager.alert('权限提示','没有编辑权限,请找有此权限的人操作或者联系管理员分配权限！', 'info');
        return;
    }
    if (editReportIndex != index){
        if (endReportEditing()) {
            $('#report-add').datagrid('selectRow', index)
                .datagrid('beginEdit', index);
            var ed = $('#report-add').datagrid('getEditor', {index:index,field:field});
            if (ed){
                ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
            }
            editReportIndex = index;
        } else {
            setTimeout(function(){
                $('#report-add').datagrid('selectRow', editReportIndex);
            }, 0);
        }
    }
}

function onEndReportEdit(index, row){
    var wl = $(this).datagrid('getEditor', {
        index: index,
        field: 'wlbm'
    });
    var gys = $(this).datagrid('getEditor', {
        index: index,
        field: 'gysbm'
    });
    row.wlmc = $(wl.target).combobox('getText');
    row.gysmc = $(gys.target).combobox('getText');
}

/**
 * 增加一行
 */
function appendReport(){
    if (endReportEditing()) {
        $('#report-add').datagrid('appendRow', {shoppingTime: getSysDate(null)});
        editReportIndex = $('#report-add').datagrid('getRows').length-1;
        $('#report-add').datagrid('selectRow', editReportIndex)
            .datagrid('beginEdit', editReportIndex);
        // var editor = $('#report-add').datagrid('getEditor', { index: editReportIndex, field: "shoppingTime" });
        // if (editor != null) {
        //     this.setValueToEditor(editor, getSysDate(null));
        // }
    } else {
        $.messager.alert('错误','校验指定行数据失败!','error');
        return;
    }
}

// function removeReport(){
//     if (editReportIndex == undefined){return}
//     $('#report-add').datagrid('cancelEdit', editReportIndex)
//         .datagrid('deleteRow', editReportIndex);
//     editReportIndex = undefined;
// }

/**
 *取消修改方法
 */
function rejectReport(){
    $('#report-add').datagrid('rejectChanges');
    editReportIndex = undefined;
}

/**
 *获得修改的行数据
 * @returns {jQuery}
 */
function getReportChanges(){
    if (endReportEditing()){
        var rows = $('#report-add').datagrid('getChanges');
        // console.log(rows.length + ' rows are changed!');
        return rows;
    }
    return null;
}

/**
 *获得选中的行数据
 * @returns {jQuery}
 */
function getReportSelections(){
    if (endReportEditing()) {
        var rows = $('#report-add').datagrid('getSelections');
        // console.log(rows.length + ' rows are Selected!');
        return rows;
    }
    return null;
}

/**
 * 删除
 */
function deleteReport() {
    var changerows = getReportChanges();
    if (changerows == null) {
        $.messager.alert('错误','校验指定行数据失败!','error');
        return;
    }
    if (changerows.length > 0) {
        $.messager.alert('警告','有未保存的数据，请‘保存数据’或‘查询列表数据’后重新选择!','warning');
        return;
    }
    var delrows = getReportSelections();
    if (delrows == null) {
        $.messager.alert('错误','校验指定行数据失败!','error');
        return;
    }
    if (delrows.length == 0) {
        $.messager.alert('提示','没有删除的数据!','info');
        return;
    }

    var ids = [];
    for (var i=0; i<delrows.length; i++) {
        // console.log(delrows[i].dbid);
        ids.push(delrows[i].dbid);
    }

    $.messager.confirm('删除确认框', '你确定删除选中的数据吗?', function(r){
        if (r){
            $.ajax({
                url: root + "/ReportServlet",
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
                        queryAddReport();
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
 * 保存
 */
function saveReport() {
    var rows = getReportChanges();
    if (rows == null) {
        $.messager.alert('错误','校验指定行数据失败!','error');
        return;
    }

    if (rows.length == 0) {
        $.messager.alert('提示','没有修改的数据!','info');
        return;
    }

    var checkResult = checkRepeat();
    // console.log(checkResult);
    if (checkResult != null) {
        $.messager.alert('警告','同一物料同一天只能提交一条数据,第'+checkResult+'行数据可能重复!','warning');
        return;
    }

    var dbid=[],wlbm=[],gysbm=[],price=[],number=[],shoppingtime=[];
    for (var i=0; i<rows.length; i++) {
        var row = rows[i];
        dbid.push(row.dbid == undefined ? '0' : row.dbid);
        wlbm.push(row.wlbm);
        gysbm.push(row.gysbm);
        price.push(row.price);
        number.push(row.number);
        shoppingtime.push(row.shoppingTime);
    }
    // alert(dbid.length);

    $.ajax({
        url: root + "/ReportServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'add',
            dbid: dbid,
            wlbm: wlbm,
            gysbm: gysbm,
            price: price,
            number: number,
            shoppingtime: shoppingtime,
            staffId: staffId,
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                queryAddReport();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });

}

/**
 * 检查行数据是否重复
 * @returns {*}
 */
function checkRepeat() {
    var total = $('#report-add').datagrid('getRows');
    if (total != null) {
        // console.log(total.length);
        var map = new Map();
        for (var i=0; i<total.length; i++) {
            var row = total[i];
            var key = row.wlbm + ',' + row.shoppingTime;
            if (map.containsKey(key)) {
                var  value = map.get(key) + ',' + (i+1);
                map.put(key, value);
                return value;
            } else {
                map.put(key, i+1);
            }

        }
        return null;
    }
}

