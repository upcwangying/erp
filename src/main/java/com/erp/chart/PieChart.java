package com.erp.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by wang_ on 2016-06-29.
 */
public class PieChart extends BaseChart{

    //饼状图
    public JFreeChart createChart() {
        ChartFactory.setChartTheme(this.getStandardChartTheme());
        JFreeChart chart = ChartFactory.createPieChart3D(
                this.getChartBean().getChartTitle(),
                (PieDataset) this.getChartBean().getDataset(),
                this.getChartBean().isLegend(),
                this.getChartBean().isTooltips(),
                this.getChartBean().isUrls()
        );
        chart.setBackgroundPaint(Color.white);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();

        // 指定饼图轮廓线的颜色
        // plot.setBaseSectionOutlinePaint(Color.BLACK);
        // plot.setBaseSectionPaint(Color.BLACK);

        // 设置无数据时的信息显示、显示颜色
        plot.setNoDataMessage(this.getChartBean().getNoDataMessage());
        plot.setNoDataMessagePaint(this.getChartBean().getNoDataMessageColor());

        if (this.getChartBean().isShowChartData()) {
            // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                    "{0}={1}({2})", NumberFormat.getNumberInstance(),
                    new DecimalFormat("0.00%")));
            // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
//            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
        }

        plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));
        plot.setBackgroundPaint(Color.white);

        // 指定图片的透明度(0.0-1.0)
        plot.setForegroundAlpha(0.65f);
        // 指定显示的饼图上圆形(false)还椭圆形(true)
        plot.setCircular(false, true);

        // 设置第一个 饼块section 的开始位置，默认是12点钟方向
        plot.setStartAngle(90);

        // // 设置分饼颜色
//        plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
//        plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));

        return chart;
    }

}
