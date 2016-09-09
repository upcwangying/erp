<%@ page import="com.erp.util.SystemConfig" %>
<%@ page import="com.erp.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String projectId = SystemConfig.getValue("project.id");
    Project project = SystemConfig.getProjectById(Integer.valueOf(projectId));
    StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
    String version = SystemConfig.getValue("project.version");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/<%= staffInfo.getStyle()%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-easyui-1.4.5/themes/icon.css">
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath()%>/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>

    <script type="text/javascript" src="<%= request.getContextPath()%>/js/common.js?version=<%= version%>"></script>

    <script type="text/javascript">
        var root = '<%= request.getContextPath()%>';
        var staffId = '<%= staffInfo.getStaffId()%>';
        var is_init = '<%= staffInfo.getIsInit()%>';

        $(document).ready(function () {
            $('#tabs').tabs({
                onClose:function (title) {
                    idx--;
                },
                //监听右键事件，创建右键菜单
                onContextMenu:function(e, title,index){
                    e.preventDefault();
                    $('#menu-index').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    }).data("tabTitle", title);
                }
            });

            //右键菜单click
            $("#menu-index").menu({
                onClick : function (item) {
                    closeTab(this, item.name);
                }
            });
        });

        //删除Tabs
        function closeTab(menu, type){
            switch (type){
                case 1 :
                    $('#tabs').tabs('closeCurrent', '#tabs');
                    break;
                case 2 :
                    $('#tabs').tabs('closeAll', '#tabs');
                    break;
                case 3 :
                    $('#tabs').tabs('closeOther', '#tabs');
                    break;
                case 4 :
                    $('#tabs').tabs('closeRight', '#tabs');
                    break;
                case 5 :
                    $('#tabs').tabs('closeLeft', '#tabs');
                    break;
            }

        }

        var idx = 0;
        function addTab(tabId, url, moduleName){
            if ($('#tabs').tabs('exists', moduleName)){
                $('#tabs').tabs('select', moduleName);
            } else {
                idx++;
                var content = "页面还未实现！";
                if (url) {
                    url = '<%= request.getContextPath()%>' + url;
                    content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
                }
                $('#tabs').tabs('add',{
                    id: "tab" + tabId,
                    title: moduleName,
                    content: content,
                    closable:true
                });

                if (idx >= 10) {
                    $.messager.alert('警告','您打开的Tab已超过10个,请手动关闭以免影响您的体验!','warning');
                }

            }
        }

        function clickLeaf(tree, node) {
            var id = node.id;
            var moduleName = node.text;
            var url = node.url;
            addTab(id, url, moduleName);
        }
        
        function logout() {
            $.messager.confirm('确认框','确定退出<%= project.getProjectName()%>吗?',function(r){
                if (r){
                    window.location = "<%=request.getContextPath()%>/LoginServlet?param=loginout";
                }
            });
        }

        function setSysDateTime(id) {
            id.innerHTML = getSysDateTime();
        }

        $(function(){
            //初始化accordion
            $("#RightAccordion").accordion({
                fillSpace:true,
                fit:true,
                border:false,
                animate:false
            });
            $.ajax({
                url: root + "/ModuleServlet?param=query",
                type: 'post',
                cache: false,
                dataType: 'json',
                traditional: true,
                data: {
                    flag: 'false'
                },
                success: function (data) {
//                    if (data == "0") {
//                        window.location = "/Account";
//                    }
                    $.each(data, function (i, e) {//循环创建手风琴的项
                        var id = e.id;
                        $('#RightAccordion').accordion('add', {
                            title: e.text,
                            content: "<ul id='tree" + id + "' ></ul>",
//                            selected: true,
                            iconCls: e.iconCls
                        });
                        $.parser.parse();
                        $("#tree" + id).tree({
                            data: e.children,
                            onClick: function (node) {
                                var leaf = node.leaf;
                                if (node.state == 'closed') {
                                    $(this).tree('expand', node.target);
                                } else if (node.state == 'open') {
                                    $(this).tree('collapse', node.target);
                                }
                                if (leaf == 'file') {
                                    clickLeaf("#tree" + id, node);
                                }
                            }
                        });
                    });
                },
                error: function () {
                    alert("网络错误！")
                }
            });
        });

        function pageLoad() {
            window.setInterval("setSysDateTime(curtime)", 1000);
        }
    </script>
</head>
<body onload="pageLoad()">
<div class="easyui-layout" style="width:100%;height:100%">
    <!-- 顶部 -->
    <div data-options="region:'north'" style="height:50px; width: 100%;background-image: url('images/logo.png')">
        <span style="text-align: right;">
            <div>
                <label style="color: red"><%=staffInfo.getStaffName()%></label>
                <span>,&nbsp;您好,&nbsp;欢迎登录<%= project.getProjectName()%>! &nbsp;现在时间: &nbsp;</span>
                <span id="curtime"></span>&nbsp;&nbsp;
                <a href="javascript:logout();">退出系统</a>&nbsp;
            </div>
        </span>
    </div>

    <!-- 模块栏 -->
    <div data-options="region:'west',split:true" title="<%= project.getProjectName()%>" style="width:180px;">
            <div id="RightAccordion" class="easyui-accordion"></div>
    </div>

    <!-- 主界面 -->
    <div data-options="region:'center'">
        <div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
            <%--<div id="tab-main" title="主页面" data-options="" style="padding:10px">--%>
                <%--<h1>Welcome</h1>--%>
            <%--</div>--%>
        </div>
    </div>

</div>

<div id="menu-index" class="easyui-menu" style="width:150px;">
    <div id="menu-index-tabclose" data-options="name:1">关闭当前页面</div>
    <div id="menu-index-tabcloseall" data-options="name:2">全部关闭</div>
    <div id="menu-index-tabcloseother" data-options="name:3">关闭其他页面</div>
    <div class="menu-sep"></div>
    <div id="menu-index-tabcloseright" data-options="name:4">关闭右侧页面</div>
    <div id="menu-index-tabcloseleft" data-options="name:5">关闭左侧页面</div>
</div>

</body>
</html>