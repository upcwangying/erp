package com.erp.chart.util;

import com.erp.chart.BaseChart;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.servlet.ServletUtilities;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wang_ on 2016-06-29.
 */
public class ChartsUtil {

    //折线图
    public String parseChart(BaseChart baseChart, HttpSession session, int w, int h) {

        JFreeChart chart = baseChart.createChart();
        String filename = null;
        /**
         * 图表标题, 目录轴的显示标签, 数值轴的显示标签, 数据集, 图表方向：水平、垂直
         * 是否显示图例(对于简单的柱状图必须是false), 是否生成工具, 是否生成URL链接
         */
        try {
            // 得到chart的保存路径
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            // 使用printWriter将文件写出
            filename = ServletUtilities.saveChartAsPNG(chart, w, h, info, session);
//            ChartUtilities.writeImageMap(pw, filename, info, true);
            filename = ChartsUtilities.getImageJSON(filename, info, true);
//            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

}
