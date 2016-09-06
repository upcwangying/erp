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

        });
    </script>
</head>
<body>
<table id="product1" class="easyui-datagrid" title="商品填报" style="width:100%;height:100%;"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: false,
				rownumbers:true,
				url:'<%= request.getContextPath()%>/ProductServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				toolbar: '#product1-tb',
				onRowContextMenu: function(e,index,row) {
				    e.preventDefault();
				    $(this).datagrid('unselectAll');
				    $(this).datagrid('selectRow',index);
				    $('#product1-tree-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
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
        <th data-options="field:'create_staffName',width:100">创建人</th>
        <th data-options="field:'create_date',width:100">创建时间</th>
        <th data-options="field:'update_staffName',width:100">更新人</th>
        <th data-options="field:'update_date',width:100">更新时间</th>
    </tr>
    </thead>
</table>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<div id="product1-tb" style="height:auto">
    <a href="javascript:void(0)" id="queryProdtct" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryProdtct()">查询</a>
    <a href="javascript:void(0)" id="addProduct" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProduct()">添加</a>
    <a href="javascript:void(0)" id="updateProduct" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateProduct()">编辑</a>
    <a href="javascript:void(0)" id="up" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="upProduct()">上架</a>
    <a href="javascript:void(0)" id="down" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="downProduct()">下架</a>
    <a href="javascript:void(0)" id="deleteProduct" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProduct()">删除</a>
</div>

<div id="product1-tree-menu" class="easyui-menu" style="width:120px;">
    <div onclick="uploadPic()" id="uploadPic" data-options="iconCls:'icon-add'">上传图片</div>
    <div class="menu-sep"></div>
    <div onclick="lookPic()" id="lookPic" data-options="iconCls:'icon-remove'">查看图片</div>
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

</body>
</html>
