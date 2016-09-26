<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="java.security.SecureRandom" %>
<%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-08-30
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    long seq = secureRandom.nextLong();
    session.setAttribute("random_session", seq + "");
    String version = SystemConfig.getValue("project.version");
    StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>

    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/kindeditor-4.1.10/themes/default/default.css"/>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/product1/js/product.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>
    <title>商品填报</title>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#product1").datagrid('hideColumn', "productId");
            $("#product1").datagrid('hideColumn', "jldwid");
            $("#product1-grid").datagrid('hideColumn', "dbid");

            hasPermissionItems(["addProduct","updateProduct","deleteProduct","up","down","deletePics","resumePics"]);
        });
    </script>
</head>
<body>
<table id="product1" class="easyui-datagrid" title="商品填报" style="width:100%;height:100%;"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: false,
				rownumbers:true,
				emptyMsg: '没有符合条件的结果集',
				url:'<%= request.getContextPath()%>/ProductServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				toolbar: '#product1-tb',
				onLoadSuccess: function() {
				    $(this).datagrid('fixRownumber');
				},
				onRowContextMenu: function(e,index,row) {
				    e.preventDefault();
				    $(this).datagrid('unselectAll');
				    $(this).datagrid('selectRow',index);
				    var is_valid = row.is_valid;
				    $('#product1-tree-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });

                    if (is_valid == '1') {
				        $('#product1-tree-menu').menu('disableItem', $('#openWindow'));
				        $('#product1-tree-menu').menu('disableItem', $('#uploadPic'));
                    } else {
                        $('#product1-tree-menu').menu('enableItem', $('#openWindow'));
                        if(hasPermission($('#uploadPic').attr('permission'))) {
                            $('#product1-tree-menu').menu('enableItem', $('#uploadPic'));
                        } else {
                            $('#product1-tree-menu').menu('disableItem', $('#uploadPic'));
                        }

                    }
				}
			">
    <thead>
    <tr>
        <th data-options="field:'productId',width:100,align:'right'"></th>
        <th data-options="field:'productName',width:150">商品名</th>
        <th data-options="field:'productDesc',width:150">商品简介</th>
        <th data-options="field:'jldwid',width:150"></th>
        <th data-options="field:'jldwmc',width:150">计量单位</th>
        <th data-options="field:'price',width:150">商品单价</th>
        <th data-options="field:'is_valid',width:150,
            formatter:function(value){
                    if (value == '0') {
                        return '未上架';
                    } else {
                        return '已上架';
                    }
				}
        ">是否上架
        </th>
        <th data-options="field:'thumbnailUrl',width:150,
            formatter:function(value){
                if (value == '') {
                    return '无图片可显示,<br>请上传图片!';
                } else {
                    return '<img src='+ value +' />';
                }
			}
        ">商品图片</th>
    </tr>
    </thead>
</table>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>
<input type="hidden" id="productId" name="productId"/>

<div id="product1-tb" style="height:auto">
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="queryProdtct" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryProdtct()">查询</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="addProduct" permission="product_add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProduct()">添加</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="updateProduct" permission="product_update" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateProduct()">编辑</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="up" permission="product_up" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="upProduct()">上架</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="down" permission="product_down" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="downProduct()">下架</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="deleteProduct" permission="product_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProduct()">删除</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
</div>

<div id="product1-tree-menu" class="easyui-menu" style="width:120px;">
    <div onclick="lookPic()" id="lookPic" data-options="iconCls:'icon-remove'">查看图片</div>
    <div class="menu-sep"></div>
    <div onclick="uploadPic()" id="uploadPic" permission="product_pic_upload" data-options="iconCls:'icon-add'">上传图片</div>
    <div class="menu-sep"></div>
    <div onclick="openWindow()" id="openWindow" data-options="iconCls:'icon-remove'">删除图片</div>
</div>

<div id="product1-dlg" class="easyui-dialog" title="商品增加" style="width:500px;height:400px;padding:10px"
     data-options="
				iconCls: 'icon-save',
				modal: true,
				closed: true,
				toolbar: [
				    '-',
				    {
				        text: '保存',
				        id: 'product-save',
				        iconCls: 'icon-save',
				        handler: function () {
				            saveProductForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeProductDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="product1-form" method="post">
        <table cellpadding="5">
            <tr>
                <td>商品名称:</td>
                <td>
                    <input class="easyui-textbox" id="productName" name="productName" style="width: 300px;" data-options="required:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>商品描述:</td>
                <td>
                    <input class="easyui-textbox" id="productDesc" name="productDesc" style="width:300px;height: 150px;" data-options="required:true,multiline:true">
                    </input>
                </td>
            </tr>
            <tr>
                <td>计量单位:</td>
                <td>
                    <input class="easyui-combobox" id="jldw" name="jldw" style="width: 300px;" data-options="
                        required:true,
                        valueField: 'jldwId',
                        textField: 'jldwmc',
                        url: '<%= request.getContextPath()%>/JldwServlet?param=query-combo'
                        ">
                    </input>
                </td>
            </tr>
            <tr>
                <td>单价:</td>
                <td>
                    <input class="easyui-numberbox" id="price" name="price" style="width: 300px;" data-options="required:true,min:0,precision:2">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="product1-win" class="easyui-window" title="图片删除"
     data-options="modal:true,fit:true,closed:true,closable:false,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-edit'"
     style="width:500px;height:200px;padding:10px;">
    <table id="product1-grid" class="easyui-datagrid" style="width:100%;height:100%;"
           data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				rownumbers:true,
				emptyMsg: '没有上传过图片',
				<%--url:'<%= request.getContextPath()%>/FileUploadLogServlet?param=query',--%>
				<%--method: 'post',--%>
				<%--queryParams:{--%>
                    <%--seq: $('#seq').val(),--%>
                    <%--productId: $('#productId').val()--%>
                <%--},--%>
				toolbar: '#product1-tb-grid'
			">
        <thead>
        <tr>
            <th data-options="field:'dbid',width:100,align:'right'"></th>
            <th data-options="field:'name',width:300">图片名</th>
            <th data-options="field:'url',width:150">原始商品路径</th>
            <th data-options="field:'is_pic_valid',width:150,
                formatter:function(value){
                    if (value == '1') {
                        return '有效';
                    } else {
                        return '无效';
                    }
				}
                ">图片是否有效</th>
            <th data-options="field:'is_del',width:150,
                formatter:function(value){
                    if (value == '1') {
                        return '已删除';
                    } else {
                        return '未删除';
                    }
				}
                ">是否删除</th>
        </tr>
        </thead>
    </table>
</div>

<div id="product1-tb-grid" style="height:auto">
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="queryPics" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryPics()">查询</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="deletePics" permission="product_pic_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deletePics()">删除</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="resumePics" permission="product_pic_resume" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="resumePics()">恢复</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="closeWindow" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="closeWindow()">关闭窗口</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
</div>

</body>
</html>

