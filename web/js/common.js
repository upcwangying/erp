
$.extend($.fn.tabs.methods, {
    allTabs:function(jq){
        var tabs = $(jq).tabs('tabs');
        var all = [];
        all = $.map(tabs,function(n,i){
            return $(n).panel('options')
        });
        return all;
    },
    closeCurrent: function(jq){ // 关闭当前
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex',currentTab);
        $(jq).tabs('close',currentTabIndex);
    },
    closeAll:function(jq){ //关闭全部
        var tabs = $(jq).tabs('allTabs');
        $.each(tabs,function(i, n){
            $(jq).tabs('close', n.title);
        })
    },
    closeOther:function(jq){ //关闭除当前标签页外的tab页
        var tabs =$(jq).tabs('allTabs');
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex',currentTab);

        $.each(tabs,function(i,n){
            if(currentTabIndex != i) {
                $(jq).tabs('close', n.title);
            }
        })
    },
    closeLeft:function(jq){ // 关闭当前页左侧tab页
        var tabs = $(jq).tabs('allTabs');
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex',currentTab);
        var i = currentTabIndex-1;

        while(i > -1){
            $(jq).tabs('close', tabs[i].title);
            i--;
        }
    },
    closeRight: function(jq){ //// 关闭当前页右侧tab页
        var tabs = $(jq).tabs('allTabs');
        var currentTab = $(jq).tabs('getSelected'),
            currentTabIndex = $(jq).tabs('getTabIndex',currentTab);
        var i = currentTabIndex+ 1,len = tabs.length;
        while(i < len){
            $(jq).tabs('close', tabs[i].title);
            i++;
        }
    }
});

$.extend($.fn.validatebox.defaults.rules, {
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    minLength: {
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length: { validator: function (value, param) {
        var len = $.trim(value).length;
        return len >= param[0] && len <= param[1];
    },
        message: "输入内容长度必须介于{0}和{1}之间."
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    integer: {// 验证整数 可正负数
        validator: function (value) {
            //return /^[+]?[1-9]+\d*$/i.test(value);

            return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    age: {// 验证年龄
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    },

    chinese: {// 验证中文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    username: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno: {// 验证传真
        validator: function (value) {
            //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
            return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    date: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            //格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
        },
        message: '清输入合适的日期格式'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致！'
    },
    remote: {
        validator: function (_46, _47) {
            var _48 = $.ajax({
                url: root + '/UserServlet',
                dataType: "json",
                data: {
                    param: 'valid',
                    staffcode: _46,
                    staffId: $("#" + _47[0]).val()
                },
                async: false,
                cache: false,
                type: "post"
            }).responseJSON.success;
            // console.log(_48);
            return _48;
        },
        message: '该用户名已被占用！'
    }

});

function commonPaging(id) {
    var pager = $('#' + id).datagrid('getPager');	// get the pager of datagrid
    pager.pagination({
        buttons:[{
            iconCls:'icon-search',
            handler:function(){
                alert('search');
            }
        },{
            iconCls:'icon-add',
            handler:function(){
                alert('add');
            }
        },{
            iconCls:'icon-edit',
            handler:function(){
                alert('edit');
            }
        }]
    });
}

/**
 * 设置datagrid的编辑器的值
 * @param editor
 * @param value
 */
function setValueToEditor(editor, value) {
    switch (editor.type) {
        case "combobox":
            editor.target.combobox("setValue", value);
            break;
        case "combotree":
            editor.target.combotree("setValue", value);
            break;
        case "textbox":
            editor.target.textbox("setValue", value);
            break;
        case "numberbox":
            editor.target.numberbox("setValue", value);
            break;
        case "datebox":
            editor.target.datebox("setValue", value);
            break;
        case "datetimebox":
            editor.target.datebox("setValue", value);
            break;
        default:
            editor.html = value;
            break;
    }
}


/**
 * 对Date的扩展，将Date转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * 例子：
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 *
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}

/**
 * 获取系统当前时间
 * @param type
 * @returns {*}
 */
function getSysDate(type) {
    if (type != null) return (new Date()).Format(type);
    else return (new Date()).Format('yyyy-MM-dd');
}

/**
 *
 * @param clock
 */
function getSysTime(){
    var now = new Date(); //创建Date对象
    var year = now.getFullYear(); //获取年份
    var month = now.getMonth(); //获取月份
    var date = now.getDate(); //获取日期
    var day = now.getDay(); //获取星期
    var hour = now.getHours(); //获取小时
    var minute = now.getMinutes(); //获取分钟
    if (minute < 10) minute = "0" + minute;
    var sec = now.getSeconds(); //获取秒钟
    if (sec < 10) sec = "0" + sec;
    month = month + 1;
    var arr_week = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
    var week = arr_week[day]; //获取中文的星期
    return year + "年" + month + "月" + date + "日 " +
        week + " " + hour + ":" + minute + ":" + sec; //组合系统时间
}

/**
 * 获取系统当前时间
 * Author:LYoung
 * return:'yyyy-MM-dd hh:mm:ss'
 */
function getSysDateTime() {
    return (new Date()).Format('yyyy-MM-dd hh:mm:ss');
}

/**
 * 获取系统本月第一天
 * Author:LYoung
 * return:'yyyy-MM-dd'
 */
function getMonthFirstDay() {
    var nowdate = new Date();
    var monthFirstDay = new Date(nowdate.getFullYear(), nowdate.getMonth(), 1);
    return monthFirstDay.Format('yyyy-MM-dd');
}