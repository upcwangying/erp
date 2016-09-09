<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.StaffInfo" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    long seq = secureRandom.nextLong();
    session.setAttribute("random_session", seq + "");
    String version = SystemConfig.getValue("project.version");
    String websocket_enable = SystemConfig.getValue("websocket.enable");
    StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
    String initYJ = (String) application.getAttribute("initYJ");
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

    <script type="text/javascript" src="<%= request.getContextPath()%>/erp/report/js/ydjs.js?version=<%= version%>"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>
    <title>月结填报</title>

    <script>
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';
        var is_init = '<%= staffInfo.getIsInit()%>';
        var initYJ = '<%= initYJ%>';
        var ws_enable = '<%= websocket_enable%>';

        $(document).ready(function () {
            $("#ydjs-add").datagrid('hideColumn', "dbid");

            if (initYJ == "1") {
                closeYjDialog();
            } else {
                if (is_init == "0") {
                    closeYjDialog();
                    $("#addYdjs").linkbutton("disable");
                    $("#updateYdjs").linkbutton("disable");
                    $("#deleteYdjs").linkbutton("disable");
                }
            }

        });

        var websocket = null;
        function yjpageLoad() {
            if (ws_enable != "true") {
                console.log("WebSocket服务未启用！");
                return;
            }
            var url = "ws://localhost:8080/erp/websocket/" + staffId + "/" + is_init;
            //判断当前浏览器是否支持WebSocket
            if('WebSocket' in window) {
                websocket = new WebSocket(url);
            } else {
                alert('Not support websocket');
                return;
            }

            //连接成功建立的回调方法
            websocket.onopen = function(event) {
                console.log("websocket连接开启！");
            };

            websocket.onmessage = function (event) {
                showMessage(event.data);
            };

            //连接发生错误的回调方法
            websocket.onerror = function() {
                console.log("websocket连接发生错误！");
            };

            //连接关闭的回调方法
            websocket.onclose = function(){
                console.log("websocket连接关闭！");
            };

        }

        function sendMessage(msg) {
            if (websocket != null) {
                console.log(msg);
                websocket.send(msg);
            }
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
        // 防止连接还没断开就关闭窗口，server端会抛异常。
        function yjpageBeforeUnload(){
            if (websocket != null) {
                websocket.close();
            }
        }

        //将消息显示在网页上
        function showMessage(msg){
            alert(msg);
        }

    </script>
</head>
<body onload="yjpageLoad()" onbeforeunload="yjpageBeforeUnload()" >

<input type="hidden" id="seq" name="seq" value="<%= seq%>"/>

<table id="ydjs-add" class="easyui-datagrid" title="月度结算" style="width:100%;height:100%;"
       data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				rownumbers:true,
				url:'<%= request.getContextPath()%>/YJServlet?param=query',
				method: 'post',
				queryParams:{
                    seq: $('#seq').val()
                },
				toolbar: '#tb'
			">
    <thead>
    <tr>
        <th data-options="field:'dbid',width:100,align:'right'"></th>
        <th data-options="field:'yjyf',width:150">月结月份</th>
        <th data-options="field:'yjzc',width:150">月结支出</th>
        <th data-options="field:'yjhz',width:150">月结划转</th>
        <th data-options="field:'yjye',width:150">月结余额</th>
        <th data-options="field:'staffName',width:100">姓名</th>
        <th data-options="field:'yjlx',width:200,
                formatter:function(value){
                    if (value == '1') {
                        return '初始化数据';
                    } else {
                        return '增加数据';
                    }
				}
            ">月结类型
        </th>
    </tr>
    </thead>
</table>

<div id="tb" style="height:auto">
    <a href="javascript:void(0)" id="queryAddYdjs" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="queryAddYdjs()">查询</a>
    <a href="javascript:void(0)" id="addYdjs" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addYdjs()">添加</a>
    <a href="javascript:void(0)" id="updateYdjs" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateYdjs()">编辑</a>
    <a href="javascript:void(0)" id="deleteYdjs" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteYdjs()">删除</a>
</div>

<div id="yj-dlg" class="easyui-dialog" title="初始化" style="width:450px;height:300px;padding:10px"
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
				            saveYjForm();
				        }
				    },
				    '-',
				    {
				        text: '取消',
				        iconCls: 'icon-undo',
				        handler: function () {
				            closeYjDialog();
				        }
				    },
				    '-'
				]
			">
    <form id="yj-form" method="post">
        <table cellpadding="5">
            <tr>
                <td>初始化月份:</td>
                <td><input class="easyui-datebox" type="text" id="yjyf" name="yjyf"
                           data-options="
                           required:true,
                           formatter: function (date) {
                               var y = date.getFullYear();
                               var m = date.getMonth() + 1;
                               var d = date.getDate();
                               return y + '-' + (m < 10 ? ('0' + m) : m);
                           },
                           parser: function (s) {
                               if (!s) return new Date();
                               var yStr = s.substr(0,4);
                               var mStr = s.substr(5,2)
                               var y = parseInt(yStr,10);
                               var m = parseInt(mStr,10);
                               if (!isNaN(y) && !isNaN(m)){
                                   return new Date(y,m-1);
                               } else {
                                   return new Date();
                               }
                           }
                    ">
                    </input>
                </td>
            </tr>
            <tr>
                <td>月结支出:</td>
                <td><input class="easyui-numberbox" type="number" id="yjzc" name="yjzc" data-options="required:true,min:0">
                    </input>
                </td>
            </tr>
            <tr>
                <td>月结划转:</td>
                <td><input class="easyui-numberbox" type="number" id="yjhz" name="yjhz" data-options="required:true,min:0">
                    </input>
                </td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>