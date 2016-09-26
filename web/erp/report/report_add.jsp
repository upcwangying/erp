<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %>
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
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/report/js/report.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/map.js?version=<%= version%>"></script>
    <title>填报</title>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';

        $(document).ready(function () {
            $("#report-add").datagrid('hideColumn', "dbid");

            hasPermissionItems(["report-add-button","report-save-button","report-delete"]);
        });

    </script>
</head>
<body>

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="report-add" class="easyui-datagrid" title="填报" style="width:100%;height:100%"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: false,
				rownumbers:true,
				url:'<%= request.getContextPath()%>/ReportServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				onDblClickCell: onClickReportCell,
				onEndEdit: onEndReportEdit,
                toolbar: '#report-tb'
			">
    <thead>
    <tr>
        <th data-options="field:'dbid',width:100,align:'right'"></th>
        <th data-options="field:'wlbm',width:150,
						formatter:function(value,row){
							return row.wlmc;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'wlbm',
								textField:'wlmc',
								method:'post',
								url:'<%= request.getContextPath()%>/combo/ComboBoxServlet?param=wl-combo',
								mode:'remote',
								required:true
							}
						}">物料</th>

        <th data-options="field:'gysbm',width:150,
						formatter:function(value,row){
							return row.gysmc;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'gysbm',
								textField:'gysmc',
								method:'post',
								url:'<%= request.getContextPath()%>/combo/ComboBoxServlet?param=gys-combo',
								mode:'remote',
								required:true
							}
						}">供应商</th>

        <th data-options="field:'price',width:100,align:'right',editor:{type:'numberbox',options:{precision:2,required:true}}">购买价格</th>
        <th data-options="field:'number',width:100,align:'right',editor:{type:'numberbox',options:{required:true}}">购买数量</th>
        <th data-options="field:'shoppingTime',width:200,editor:{type:'datebox',options:{required:true}}">购买时间</th>
    </tr>
    </thead>
</table>

<div id="report-tb" style="height:auto">
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="report-search" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryAddReport()">查询</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="report-add-button" permission="report_add" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendReport()">添加</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="report-save-button" permission="report_save" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveReport()">保存</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="report-update_ok" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="endReportEdit()">确认修改</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="report-update_undo" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectReport()">取消修改</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
    <a href="javascript:void(0)" id="report-delete" permission="report_delete" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteReport()">删除</a>
    <span class="datagrid-btn-separator" style="vertical-align: middle; height: 15px;display:inline-block;float:none"></span>
</div>

</body>
</html>