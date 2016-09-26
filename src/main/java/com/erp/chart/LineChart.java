package com.erp.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;

/**
 * Created by wang_ on 2016-06-29.
 */
public class LineChart extends BaseChart{

    /**
     * 折线图
     * @return
     */
    public JFreeChart createChart() {

        ChartFactory.setChartTheme(this.getStandardChartTheme());
        JFreeChart chart = ChartFactory.createLineChart(
                this.getChartBean().getChartTitle(),
                this.getChartBean().getXLabel(),
                this.getChartBean().getYLabel(),
                (CategoryDataset) this.getChartBean().getDataset(),
                this.getChartBean().getPlotOrientation(),
                this.getChartBean().isLegend(),
                this.getChartBean().isTooltips(),
                this.getChartBean().isUrls()
        );

        /*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        /*------------配置图表属性--------------*/
        // 1,设置整个图表背景颜色
        chart.setBackgroundPaint(Color.white);
        /*------------设定Plot参数-------------*/
        CategoryPlot plot = chart.getCategoryPlot();
        // 2,设置详细图表的显示细节部分的背景颜色
        plot.setBackgroundPaint(Color.white);
        // 3,设置垂直网格线颜色
        plot.setDomainGridlinePaint(Color.black);
        // 4,设置是否显示垂直网格线
        plot.setDomainGridlinesVisible(true);
        // 5,设置水平网格线颜色
        plot.setRangeGridlinePaint(Color.blue);
        // 6,设置是否显示水平网格线
        plot.setRangeGridlinesVisible(true);

        // 7,设置无数据时的信息显示、显示颜色
        plot.setNoDataMessage(this.getChartBean().getNoDataMessage());
        plot.setNoDataMessagePaint(this.getChartBean().getNoDataMessageColor());

        //显示折点数据
        CategoryItemRenderer categoryItemRenderer = plot.getRenderer();
        categoryItemRenderer.setBaseItemLabelsVisible(this.getChartBean().isShowChartData());
        categoryItemRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));

        categoryItemRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        categoryItemRenderer.setBaseItemLabelFont(new Font("Dialog", 1, 14));
        plot.setRenderer(categoryItemRenderer);

        return chart;
    }

}
