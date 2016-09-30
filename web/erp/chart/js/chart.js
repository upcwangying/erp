function formatItem(row){
    var s = '<span style="font-weight:bold">' + row.wlmc + '</span><br/>' +
        '<span style="color:#888">' + row.wlms + '</span>';
    return s;
}

function select(row) {
    insertTable(row.wlbm);
}

function insertTable(wl) {
    $.ajax({
        url: root + "/ChartsServlet",
        type: 'post',
        cache: false,
        dataType: 'json',
        data: {
            wlbm: wl,
            chart_lx: "line",
            module_lx: 'chart',
            width: chart_width,
            height: chart_height,
            seq: $('#seq').val()
        },
        success: function (data) {
            // console.log(data);
            var graphURL = root + "/servlet/DisplayServlet?filename=" + data.id;
            var html = "<tr><td><img src='" + graphURL + "' width="+chart_width+" height="+chart_height+" border=0></td></tr>";
            $("#table").empty();
            $("#table").append(html);
        },
        error: function () {
            alert("网络错误！")
        }
    });

}
