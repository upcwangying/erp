/**
 * 查询
 */
function queryAddYdjs() {
    $("#ydjs-add").datagrid({
        url:root + '/YJServlet?param=query',
        queryParams:{
            seq: $('#seq').val()
        },
        method:'post'
    });
}

var ydjs_flag = true;
function ydjsAll() {
    if (ydjs_flag) {
        $('#filterYdjs').linkbutton({text: '取消过滤'});
        filterYdjs();
        ydjs_flag = false;
    } else {
        $('#filterYdjs').linkbutton({text: '过滤'});
        disableFilterYdjs();
        ydjs_flag = true;
    }
}

/**
 *过滤
 */
function filterYdjs() {
    var dg = $('#ydjs-add').datagrid({
        filterBtnIconCls:'icon-filter'
    });
    dg.datagrid('enableFilter', [{
        field:'yjyf',
        type:'textbox'
    },{
        field:'yjzc',
        type:'numberbox',
        options:{precision:2},
        op:['equal','notequal','less','greater']
    },{
        field:'yjhz',
        type:'numberbox',
        options:{precision:2},
        op:['equal','notequal','less','greater']
    },{
        field:'yjye',
        type:'numberbox',
        options:{precision:2},
        op:['equal','notequal','less','greater']
    },{
        field:'staffName',
        type:'textbox'
    },{
        field:'yjlx',
        type:'textbox'
    }/*,{
     field:'status',
     type:'combobox',
     options:{
     panelHeight:'auto',
     data:[{value:'',text:'All'},{value:'P',text:'P'},{value:'N',text:'N'}],
     onChange:function(value){
     if (value == ''){
     dg.datagrid('removeFilterRule', 'status');
     } else {
     dg.datagrid('addFilterRule', {
     field: 'status',
     op: 'equal',
     value: value
     });
     }
     dg.datagrid('doFilter');
     }
     }
     }*/]);
}

/**
 * 取消过滤
 */
function disableFilterYdjs() {
    var fields = ['yjyf','yjzc','yjhz','yjye','staffName','yjlx'];
    for (var i=0;i<fields.length;i++) {
        $('#ydjs-add').datagrid('removeFilterRule', fields[i]);
    }
    $('#ydjs-add').datagrid('disableFilter');
}

/**
 * 增加月度结算
 */
function addYdjs() {
    openYjDialog('insert');
}

/**
 * 编辑月度结算
 */
function updateYdjs() {
    var rows = $('#ydjs-add').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return;
    }

    openYjDialog("edit");

    $('#yjyf').datebox('setValue', rows[0].yjyf);
    $('#yjzc').numberbox('setValue', rows[0].yjzc);
    $('#yjhz').numberbox('setValue', rows[0].yjhz);
}

/**
 * 删除月度结算
 */
function deleteYdjs() {
    var rows = $('#ydjs-add').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return;
    }

    var dbid = rows[0].dbid;
    var yjlx = rows[0].yjlx;
    if (yjlx == "1") {
        $.messager.alert('提示','不能删除初始化数据!','error');
        return;
    }
    $.messager.confirm('删除确认框', '你确定删除选中的数据吗?', function(r){
        if (r){
            $.ajax({
                url: root + "/YJServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'delete',
                    dbid: dbid,
                    yjlx: yjlx,
                    seq: $('#seq').val()
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryAddYdjs();
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
 * 窗口保存或初始化
 */
function saveYjForm() {
    var checkResult = checkRepeatYjyf();
    if (checkResult != null) {
        $.messager.alert('提示',"与第" + checkResult + "行“月结月份”重复,请重新选择“月结月份”！",'info');
        return;
    }
    if (flag == 'insert' || flag == "init") {
        if ($("#yj-form").form("validate")) {
            saveYJ(flag, null, null, null);
        }
    } else if (flag == 'edit') {
        var rows = $('#ydjs-add').datagrid('getSelections');
        var dbid = rows[0].dbid;
        var yjyf = rows[0].yjyf;
        var yjlx = rows[0].yjlx;
        $.messager.confirm('修改确认框', '你确定修改选中的数据吗?', function(r){
            if (r){
                saveYJ("update", dbid, yjyf, yjlx);
            }
        });
    }
}

/**
 * 保存
 * @param param
 * @param dbid
 * @param yjyf
 * @param yjlx
 */
function saveYJ(param, dbid, yjyf, yjlx) {
    $.ajax({
        url: root + "/YJServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: param,
            dbid: dbid,
            yjlx: yjlx,
            del_flag: yjyf != $('#yjyf').datebox('getValue'),
            yjyf: $('#yjyf').datebox('getValue'),
            yjzc: $('#yjzc').numberbox('getValue'),
            yjhz: $('#yjhz').numberbox('getValue'),
            staffId: staffId,
            seq: $('#seq').val()
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                if (param == "init") {
                    sendMessage("请刷新页面！");
                }
                closeYjDialog();
                queryAddYdjs();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

var flag = "init";
function openYjDialog(type) {
    flag = type;
    $('#yj-dlg').dialog('open');
    if (flag == 'insert') {
        $('#yj-dlg').dialog('setTitle', '月度结算增加');
    } else if ('edit') {
        $('#yj-dlg').dialog('setTitle', '月度结算修改');
    }
    $("#yjyf").datebox("setValue", getSysDate("yyyy-MM"));
    $('#yjzc').textbox('setValue', '');
    $('#yjhz').textbox('setValue', '');
}

function closeYjDialog() {
    $('#yjzc').textbox('setValue', '');
    $('#yjhz').textbox('setValue', '');
    $('#yj-dlg').dialog('close');
}

/**
 *
 * @returns {*}
 */
function checkRepeatYjyf() {
    var selected = $('#ydjs-add').datagrid('getSelected');
    var rows = $('#ydjs-add').datagrid('getRows');
    var yjyf = $('#yjyf').datebox('getValue');
    if (selected != null) {
        var index = $('#ydjs-add').datagrid('getRowIndex', selected);
        for (var i=0; i<rows.length; i++) {
            if (i != index && rows[i].yjyf == yjyf) {
                return (i+1);
            }
        }
    } else {
        for (var i=0; i<rows.length; i++) {
            if (rows[i].yjyf == yjyf) {
                return (i+1);
            }
        }
    }
    return null;
}
