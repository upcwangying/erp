<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="java.security.SecureRandom" %>
<%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-08-24
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    long seq = secureRandom.nextLong();
    session.setAttribute("random_session", seq+"");
    String version = SystemConfig.getValue("project.version");
    StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/kindeditor-4.1.10/themes/default/default.css" />
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script charset="utf-8" type="text/javascript" src="<%= request.getContextPath()%>/kindeditor-4.1.10/kindeditor-all.js"></script>
    <script charset="utf-8" type="text/javascript" src="<%= request.getContextPath()%>/kindeditor-4.1.10/lang/zh_CN.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/product/js/product.js?version=<%= version%>"></script>
    <title>商品填报</title>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';
        var maxWidth = 0 , maxHeight = 0;
        $(document).ready(function () {
            $("#product").datagrid('hideColumn', "productId");
            $("#product").datagrid('hideColumn', "productDesc");

            if (window.screen) {
                maxWidth = screen.availWidth;
                maxHeight = screen.availHeight;
            }
        });

        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="content"]', {
                allowFileManager : true,
                resizeType: 0,
                basePath: '<%= request.getContextPath()%>/kindeditor-4.1.10/',
                uploadJson: '<%= request.getContextPath()%>/UploadJsonServlet',
                fileManagerJson : '<%= request.getContextPath()%>/FileManagerJsonServlet',
                cssPath : '<%= request.getContextPath()%>/kindeditor-4.1.10/plugins/code/prettify.css',
                imageSizeLimit: '3MB',
                imageUploadLimit: 10,
                items:[
                    'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist',
                    'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', '|',
                    'formatblock', 'fontname', 'fontsize', 'forecolor', 'hilitecolor', 'bold', 'italic',
                    'underline', 'strikethrough', 'lineheight', 'removeformat', '/',
                    'undo', 'redo', 'multiimage', 'hr', 'pagebreak', 'fullscreen'
                ]
            });
        });
    </script>
</head>
<body>
<table id="product" class="easyui-datagrid" title="商品填报" style="width:100%;height:100%;"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				rownumbers:true,
				url:'<%= request.getContextPath()%>/ProductServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				toolbar: '#product-tb'
			">
    <thead>
    <tr>
        <th data-options="field:'productId',width:100,align:'right'"></th>
        <th data-options="field:'productName',width:150">商品名</th>
        <th data-options="field:'productMS',width:150">商品简介</th>
        <th data-options="field:'productDesc',width:150"></th>
        <th data-options="field:'create_staffName',width:100">创建人</th>
        <th data-options="field:'create_date',width:100">创建时间</th>
        <th data-options="field:'update_staffName',width:100">更新人</th>
        <th data-options="field:'update_date',width:100">更新时间</th>
    </tr>
    </thead>
</table>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<div id="product-tb" style="height:auto">
    <a href="javascript:void(0)" id="queryProdtct" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryProdtct()">查询</a>
    <a href="javascript:void(0)" id="lookProdtct" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="lookProdtct()">预览</a>
    <a href="javascript:void(0)" id="addProduct" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProduct()">添加</a>
    <a href="javascript:void(0)" id="updateProduct" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateProduct()">编辑</a>
    <a href="javascript:void(0)" id="deleteProduct" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProduct()">删除</a>
</div>

<div id="product-dlg" class="easyui-dialog" title="初始化" style="padding:10px"
     data-options="
				iconCls: 'icon-save',
				modal: true,
				fit: true,
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
    <form id="product-form" method="post">
        <table cellpadding="5" style="width: 100%; height: 100%;">
            <tr style="width: 100%;height: 20px">
                <td style="width: 100px;">商品名:</td>
                <td>
                    <input class="easyui-textbox" id="productName" name="productName" style="width: 50%;" data-options="required:true">
                    </input>
                </td>
            </tr>
            <tr style="width: 100%;height: 50px">
                <td style="width: 100px;">商品简介:</td>
                <td>
                    <input class="easyui-textbox" id="productMs" name="productMs" style="width: 50%;height: 50px" data-options="required:true, multiline:true">
                    </input>
                </td>
            </tr>
            <tr style="width: 100%;height: 100%">
                <td style="width: 100px;">商品详情:</td>
                <td>
                    <textarea id="content" name="content" style="width:100%;height:800px;visibility:hidden;"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>
