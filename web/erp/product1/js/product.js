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
    var rows = $('#product1').datagrid('getSelections');
    var productId = rows[0].productId;
    openUrl(root + '/erp/product1/addPic.jsp?productId='+productId);
}

/**
 * 查看图片
 */
function lookPic() {

}

