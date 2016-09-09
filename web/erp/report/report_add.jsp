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
				onRowContextMenu: function(e, rowIndex, rowData) {
                    e.preventDefault();
                    $('#report-menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
                toolbar: [
                    '-',
				    {
				        text: '查询',
				        iconCls: 'icon-search',
				        handler: function () {
				            queryAddReport();
				        }
				    },
				    '-',
				    {
				        text: '添加',
				        iconCls: 'icon-add',
				        handler: function () {
                            appendReport();
				        }
				    },
				    '-',
				    {
				        text: '保存',
				        iconCls: 'icon-save',
				        handler: function () {
                            saveReport();
				        }
				    },
				    '-',
				    {
				        text: '确认修改',
				        iconCls: 'icon-ok',
				        handler: function () {
                            endReportEdit();
				        }
				    },
				    '-',
				    {
				        text: '取消修改',
				        iconCls: 'icon-undo',
				        handler: function () {
                            rejectReport();
				        }
				    },
				    '-',
				    {
				        text: '删除',
				        iconCls: 'icon-remove',
				        handler: function () {
                            deleteReport();
				        }
				    },
				    '-'
                ]
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

<div id="report-menu" class="easyui-menu" style="width:120px;">
    <div onclick="endReportEdit()" data-options="iconCls:'icon-ok'">确认修改</div>
</div>

</body>
</html>