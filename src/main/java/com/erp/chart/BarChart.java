package com.erp.chart;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;

import java.awt.*;

/**
 * Created by wang_ on 2016-06-29.
 */
public class BarChart extends BaseChart{

    // 柱状图
    public JFreeChart createChart() {

        ChartFactory.setChartTheme(this.getStandardChartTheme());
        JFreeChart chart = ChartFactory.createBarChart3D(
                this.getChartBean().getChartTitle(),
                this.getChartBean().getXLabel(),
                this.getChartBean().getYLabel(),
                (CategoryDataset) this.getChartBean().getDataset(),
                this.getChartBean().getPlotOrientation(),
                this.getChartBean().isLegend(),
                this.getChartBean().isTooltips(),
                this.getChartBean().isUrls()
        );
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

        // 设置无数据时的信息显示、显示颜色
        plot.setNoDataMessage(this.getChartBean().getNoDataMessage());
        plot.setNoDataMessagePaint(this.getChartBean().getNoDataMessageColor());

        // 设置x轴垂直显示
        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        CategoryItemRenderer categoryItemRenderer = plot.getRenderer();
        categoryItemRenderer.setBaseItemLabelsVisible(this.getChartBean().isShowChartData());
        categoryItemRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.TOP_LEFT));

        categoryItemRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        categoryItemRenderer.setBaseItemLabelFont(new Font("Dialog", 1, 14));
        plot.setRenderer(categoryItemRenderer);
        return chart;
    }

}
