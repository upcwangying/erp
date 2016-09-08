/**
 * 查询
 */
function queryProdtct() {
    $("#product1").datagrid({
        url:root + '/ProductServlet?param=query',
        queryParams:{
            seq: $("#seq").val()
        },
        method:'post'
    });
}

/**
 * 数据检查
 * @returns {boolean}
 */
function checkData() {
    var rows = $('#product1').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return false;
    }
    if (rows.length > 1) {
        $.messager.alert('提示','只能选择一条数据!','info');
        return false;
    }
    return true;
}

/**
 * 增加
 */
function addProduct() {
    openProductDialog('add');
}

/**
 * 编辑更新
 */
function updateProduct() {
    if (!checkData()) {
        return;
    }
    var rows = $('#product1').datagrid('getSelections');
    var row = rows[0];

    if (row.is_valid == '1') {
        $.messager.alert('提示','只能修改‘未上架’的商品数据，请重新选择!','info');
        return;
    }

    openProductDialog('edit');

    $('#product1-dlg').dialog('setTitle', '商品修改');
    $('#productName').textbox('setValue', row.productName);
    $('#productDesc').textbox('setValue', row.productDesc);
    $('#jldw').combobox('setValue', row.jldwid);
    $('#price').numberbox('setValue', row.price);

}

/**
 * 上架
 */
function upProduct() {
    var rows = $('#product1').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return false;
    }

    var ids = [];
    for (var i=0;i<rows.length;i++) {
        var row = rows[i];
        if (row.is_valid == '1') {
            $.messager.alert('提示','选中的数据中包含‘已上架’的产品，请重新选择!','info');
            return;
        }
        ids.push(row.productId);
    }

    $.messager.confirm('确认框', '确定上架选中的商品吗?', function (r) {
        if (r) {
            upOrDown('up', ids, false);
        }
    });

}

/**
 * 下架
 */
function downProduct() {
    var rows = $('#product1').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return false;
    }

    var ids = [];
    for (var i=0;i<rows.length;i++) {
        var row = rows[i];
        if (row.is_valid == '0') {
            $.messager.alert('提示','选中的数据中包含‘未上架’的产品，请重新选择!','info');
            return;
        }
        ids.push(row.productId);
    }

    $.messager.confirm('确认框', '确定下架选中的商品吗?', function (r) {
        if (r) {
            upOrDown('down', ids, true);
        }
    });

}

/**
 *
 * @param param
 * @param ids
 * @param flag :下架操作为true;
 */
function upOrDown(param, ids, flag) {
    $.ajax({
        url: root + "/ProductServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: param,
            seq: $("#seq").val(),
            productId: ids,
            flag: flag,
            update_by: staffId
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                queryProdtct();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 删除
 */
function deleteProduct() {
    var rows = $('#product1').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return false;
    }

    var ids = [];
    for (var i=0;i<rows.length;i++) {
        var row = rows[i];
        if (row.is_valid == '1') {
            $.messager.alert('提示','选中的数据中包含‘已上架’的产品，请重新选择!','info');
            return false;
        }
        ids.push(row.productId);
    }

    $.messager.confirm('确认框', '确定删除选中的数据吗?', function (r) {
        if (r) {
            $.ajax({
                url: root + "/ProductServlet",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    param: 'delete',
                    seq: $("#seq").val(),
                    productId: ids,
                    update_by: staffId
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.success) {
                        queryProdtct();
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
 * Dialog保存
 */
function saveProductForm() {
    var productName = $('#productName').textbox('getValue');
    var productDesc = $('#productDesc').textbox('getValue');
    var jldw = $('#jldw').combobox('getValue');
    var price = $('#price').numberbox('getValue');

    if (productName == null || $.trim(productName) == "") {
        $.messager.alert('提示','“商品名称”不允许为空!','info');
        return;
    }

    if (productDesc == null || $.trim(productDesc) == "") {
        $.messager.alert('提示','“商品描述”不允许为空!','info');
        return;
    }

    if (jldw == null || $.trim(jldw) == "") {
        $.messager.alert('提示','“计量单位”不允许为空!','info');
        return;
    }

    if (price == null || $.trim(price) == "") {
        $.messager.alert('提示','“单价”不允许为空!','info');
        return;
    }

    if (flag == "add") {
        ajaxSubmit("", productName, productDesc, jldw, price);
    } else if (flag == "edit") {
        var rows = $('#product1').datagrid('getSelections');
        var productId = rows[0].productId;
        $.messager.confirm('确认框', '确定修改数据吗?', function (r) {
            if (r) {
                ajaxSubmit(productId, productName, productDesc, jldw, price);
            }
        });
    }
}

/**
 *
 * @param productId
 * @param productName
 * @param productDesc
 * @param jldw
 * @param price
 */
function ajaxSubmit(productId, productName, productDesc, jldw, price) {
    $.ajax({
        url: root + "/ProductServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'add',
            seq: $("#seq").val(),
            productId: productId,
            productName: productName,
            productDesc: productDesc,
            jldw: jldw,
            price: price,
            create_by: staffId,
            update_by: staffId
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                closeProductDialog();
                queryProdtct();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 打开窗口
 * @type {undefined}
 */
var flag = undefined;
function openProductDialog(type) {
    flag = type;
    $('#product1-dlg').dialog('open');
}

/**
 * 关闭窗口
 */
function closeProductDialog() {
    $('#productName').textbox('setValue', '');
    $('#productDesc').textbox('setValue', '');
    $('#jldw').combobox('setValue', '');
    $('#price').numberbox('setValue', '');
    $('#product1-dlg').dialog('close');
}

/**
 * 上传图片
 */
function uploadPic() {
    if ($('#product1-tree-menu').menu("getItem", $('#uploadPic')).disabled) {
        return;
    }
    var rows = $('#product1').datagrid('getSelections');
    var productId = rows[0].productId;
    openUrl(root + '/erp/product1/addPic.jsp?productId='+productId+"&staffId="+staffId);
}

/**
 * 查看图片
 */
function lookPic() {
    var rows = $('#product1').datagrid('getSelections');
    var productId = rows[0].productId;
    openUrl(root + '/erp/product1/lookPic.jsp?productId='+productId+"&staffId="+staffId);
}

function queryPics() {
    $("#product1-grid").datagrid({
        url:root + '/FileUploadLogServlet?param=query',
        queryParams:{
            seq: $("#seq").val(),
            productId: $('#productId').val()
        },
        method:'post'
    });
}

function deletePics() {
    var rows = $('#product1-grid').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return;
    }

    if (rows.length > 1) {
        $.messager.alert('提示','只能选择一条数据!','info');
        return;
    }

    var row = rows[0];
    if (row.is_del == '1') {
        $.messager.alert('提示','该数据‘已删除’,不能重复操作!','info');
        return;
    }

    var dbid = row.dbid;
    $.messager.confirm('确认框', '确定删除数据吗?', function (r) {
        if (r) {
            resumeOrDelPics('delete', dbid);
        }
    });
}

function resumePics() {
    var rows = $('#product1-grid').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return;
    }

    if (rows.length > 1) {
        $.messager.alert('提示','只能选择一条数据!','info');
        return;
    }

    var row = rows[0];
    if (row.is_del == '0') {
        $.messager.alert('提示','只能恢复‘已删除’的数据!','info');
        return;
    }

    var dbid = row.dbid;
    if (row.is_pic_valid == '0') {
        $.messager.confirm('确认框', '该数据对应的图片‘物理删除’,确定恢复数据吗?', function (r) {
            if (r) {
                resumeOrDelPics('resume', dbid);
            }
        });
    } else {
        $.messager.confirm('确认框', '确定恢复数据吗?', function (r) {
            if (r) {
                resumeOrDelPics('resume', dbid);
            }
        });
    }

}

function resumeOrDelPics(param, dbid) {
    $.ajax({
        url: root + "/FileUploadLogServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: param,
            seq: $("#seq").val(),
            dbid: dbid,
            update_staffId: staffId
        },
        success: function (data) {
            alert(data.msg);
            if (data.success) {
                queryPics();
            }
        },
        error: function () {
            alert("网络错误！")
        }
    });
}

/**
 * 打开窗口
 */
function openWindow() {
    if ($('#product1-tree-menu').menu("getItem", $('#openWindow')).disabled) {
        return;
    }
    $('#product1-win').window('open');
    var productId = $('#product1').datagrid('getSelections')[0].productId;
    $('#productId').val(productId);
    queryPics();
}

/**
 * 关闭窗口
 */
function closeWindow() {
    $('#productId').val('');
    $('#product1-win').window('close');
    queryProdtct();
}

