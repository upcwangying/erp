<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="com.erp.util.SystemConfig" %>
<%--
  Created by IntelliJ IDEA.
  User: wang_
  Date: 2016-08-24
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
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
        $(document).ready(function () {
            $("#product").datagrid('hideColumn', "dbid");

        });

        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('textarea[name="content"]', {
                allowFileManager : true,
                basePath: '<%= request.getContextPath()%>/kindeditor-4.1.10/',
                <%--uploadJson: '<%= request.getContextPath()%>/kindeditor-4.1.10/jsp/upload_json.jsp',--%>
                <%--fileManagerJson : '<%= request.getContextPath()%>/kindeditor-4.1.10/jsp/file_manager_json.jsp',--%>
                uploadJson: '<%= request.getContextPath()%>/UploadJsonServlet',
                fileManagerJson : '<%= request.getContextPath()%>/FileManagerJsonServlet',
                cssPath : '<%= request.getContextPath()%>/kindeditor-4.1.10/plugins/code/prettify.css',
                imageSizeLimit: '3MB',
                imageUploadLimit: 10
            });
            K('input[name=getHtml]').click(function(e) {
                alert(editor.html());
            });
            K('input[name=isEmpty]').click(function(e) {
                alert(editor.isEmpty());
            });
            K('input[name=getText]').click(function(e) {
                alert(editor.text());
            });
            K('input[name=selectedHtml]').click(function(e) {
                alert(editor.selectedHtml());
            });
            K('input[name=setHtml]').click(function(e) {
                editor.html('<h3>Hello KindEditor</h3>');
            });
            K('input[name=setText]').click(function(e) {
                editor.text('<h3>Hello KindEditor</h3>');
            });
            K('input[name=insertHtml]').click(function(e) {
                editor.insertHtml('<strong>插入HTML</strong>');
            });
            K('input[name=appendHtml]').click(function(e) {
                editor.appendHtml('<strong>添加HTML</strong>');
            });
            K('input[name=clear]').click(function(e) {
                editor.html('');
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
				<%--url:'<%= request.getContextPath()%>/YJServlet?param=query',--%>
				method: 'post',
				toolbar: '#product-tb'
			">
    <thead>
    <tr>
        <th data-options="field:'dbid',width:100,align:'right'"></th>
        <th data-options="field:'yjyf',width:150">月结月份</th>
        <th data-options="field:'yjzc',width:150">月结支出</th>
        <th data-options="field:'yjhz',width:150">月结划转</th>
        <th data-options="field:'yjye',width:150">月结余额</th>
        <th data-options="field:'staffName',width:100">姓名</th>
    </tr>
    </thead>
</table>

<div id="product-tb" style="height:auto">
    <a href="javascript:void(0)" id="queryProdtct" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryProdtct()">查询</a>
    <a href="javascript:void(0)" id="addProduct" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProduct()">添加</a>
    <a href="javascript:void(0)" id="updateProduct" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateProduct()">编辑</a>
    <a href="javascript:void(0)" id="deleteProduct" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProduct()">删除</a>
</div>

<div id="product-dlg" class="easyui-dialog" title="初始化" style="width:800px;height:400px;padding:10px"
     data-options="
				iconCls: 'icon-save',
				maximizable:true,
				collapsible:true,
				modal: true,
				<%--closed: true,--%>
				toolbar: [
				    '-',
				    {
				        text: '保存',
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
        <textarea name="content" style="width:800px;height:400px;visibility:hidden;"></textarea>
        <p>
            <input type="button" name="getHtml" value="取得HTML" />
            <input type="button" name="isEmpty" value="判断是否为空" />
            <input type="button" name="getText" value="取得文本(包含img,embed)" />
            <input type="button" name="selectedHtml" value="取得选中HTML" />
            <br />
            <br />
            <input type="button" name="setHtml" value="设置HTML" />
            <input type="button" name="setText" value="设置文本" />
            <input type="button" name="insertHtml" value="插入HTML" />
            <input type="button" name="appendHtml" value="添加HTML" />
            <input type="button" name="clear" value="清空内容" />
            <input type="reset" name="reset" value="Reset" />
        </p>
    </form>
</div>

</body>
</html>
