/**
 * 查询
 */
function queryProdtct() {
    $("#product").datagrid({
        url:root + '/ProductServlet?param=query',
        queryParams:{
            seq: $("#seq").val()
        },
        method:'post'
    });
}

/**
 * 预览
 */
function lookProdtct() {
    if (!checkData()) {
        return;
    }
    var rows = $('#product').datagrid('getSelections');
    var row = rows[0];
    var productDesc = row.productDesc;

    openProductDialog('look');
    $('#product-save').linkbutton('disable');

    var productName = row.productName;
    var productMS = row.productMS;
    var productDesc = row.productDesc;

    $('#productName').textbox('setValue', productName);
    $('#productName').textbox('disable');
    $('#productMs').textbox('setValue', productMS);
    $('#productMs').textbox('disable');
    editor.insertHtml(decodeURI(productDesc));
    editor.readonly(true);

}

/**
 *
 * @returns {boolean}
 */
function checkData() {
    var rows = $('#product').datagrid('getSelections');
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
    $('#product-save').linkbutton('enable');
    $('#productName').textbox('enable');
    $('#productMs').textbox('enable');
    editor.readonly(false);
}

/**
 * 编辑更新
 */
function updateProduct() {
    if (!checkData()) {
        return;
    }
    var rows = $('#product').datagrid('getSelections');
    var row = rows[0];
    openProductDialog('edit');
    $('#product-save').linkbutton('enable');

    var productName = row.productName;
    var productMS = row.productMS;
    var productDesc = row.productDesc;

    $('#productName').textbox('setValue', productName);
    $('#productName').textbox('enable');
    $('#productMs').textbox('setValue', productMS);
    $('#productMs').textbox('enable');
    editor.insertHtml(decodeURI(productDesc));
    editor.readonly(false);
}

/**
 * 删除
 */
function deleteProduct() {
    var rows = $('#product').datagrid('getSelections');
    if (!rows || rows.length == 0) {
        $.messager.alert('提示','未选中数据!','info');
        return false;
    }

    var dbids = [];
    for (var i=0;i<rows.length;i++) {
        dbids.push(rows[i].productId);
    }

    $.ajax({
        url: root + "/ProductServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        traditional: true,
        data: {
            param: 'delete',
            seq: $("#seq").val(),
            productId: dbids,
            staffId: staffId
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
 *
 */
function saveProductForm() {
    var html = editor.html();
    var productName = $("#productName").val();
    var productMs = $("#productMs").val();

    if (productName == null || $.trim(productName) == "") {
        $.messager.alert('提示','“商品名称”不允许为空!','info');
        return;
    }

    if (productMs == null || $.trim(productMs) == "") {
        $.messager.alert('提示','“商品简介”不允许为空!','info');
        return;
    }

    if (html == null || $.trim(html) == "") {
        $.messager.alert('提示','“商品详情”不允许为空!','info');
        return;
    }

    if (flag == "add") {
        ajaxSubmit("", productName, productMs, html);
    } else if (flag == "edit") {
        var rows = $('#product').datagrid('getSelections');
        var productId = rows[0].productId;
        $.messager.confirm('确认框', '确定修改数据吗?', function (r) {
            if (r) {
                ajaxSubmit(productId, productName, productMs, html);
            }
        });
    }
}

/**
 *
 * @param productId
 * @param productName
 * @param productMs
 * @param html
 */
function ajaxSubmit(productId, productName, productMs, html) {
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
            productMs: productMs,
            html: encodeURI(html),
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
    $('#product-dlg').dialog('open');
}

/**
 * 关闭窗口
 */
function closeProductDialog() {
    $('#productName').textbox('setValue', '');
    $('#productMs').textbox('setValue', '');
    editor.html('');
    $('#product-dlg').dialog('close');
}
