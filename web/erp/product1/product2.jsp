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
    <title>商品填报</title>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';
        $(document).ready(function () {
            $("#product1").datagrid('hideColumn', "productId");
            $("#product1").datagrid('hideColumn', "productDesc");

        });

    </script>
</head>
<body>
<table id="product1" class="easyui-datagrid" title="商品填报" style="width:100%;height:100%;"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				rownumbers:true,
				url:'<%= request.getContextPath()%>/ProductServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				toolbar: '#product1-tb'
			">
    <thead>
    <tr>
        <th data-options="field:'productId',width:100,align:'right'"></th>
        <th data-options="field:'productName',width:150">商品名</th>
        <th data-options="field:'productMS',width:250">商品简介</th>
        <th data-options="field:'jldw',width:150">计量单位</th>
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
        <th data-options="field:'productDesc',width:150"></th>
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
    <a href="javascript:void(0)" id="deleteProduct" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProduct()">删除</a>
</div>

<div id="product1-dlg" class="easyui-dialog" title="初始化" style="padding:10px"
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
    <form id="product1-form" method="post">
        <div class="easyui-panel" style="width:100%;height:100%; padding:30px 70px 50px 70px">
            <div style="margin-bottom:20px">
                <div>商品名:</div>
                <input class="easyui-textbox" id="productName" name="productName" style="width: 100%;"
                       data-options="required:true">
                </input>
            </div>
            <div style="margin-bottom:20px">
                <div>商品简介:</div>
                <input class="easyui-textbox" id="productMs" name="productMs" style="width: 100%; height: 100px"
                       data-options="required:true, multiline:true">
                </input>
            </div>
            <div style="margin-bottom:20px">
                <div>图片上传:</div>
                <input class="easyui-filebox" id="files" name="files" style="width: 100%;height: 50px"
                       data-options="multiple:true,accept: 'image/*',required:true">
                </input>
            </div>
            <div>
                <%--<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100%" onclick="upload()">Upload</a>--%>

            </div>
        </div>
    </form>
</div>
</body>
</html>

