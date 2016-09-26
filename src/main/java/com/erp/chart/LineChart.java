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
     * ����ͼ
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

        /*----------������������ľ����Ⱦ������������⣩--------------*/
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        /*------------����ͼ������--------------*/
        // 1,��������ͼ������ɫ
        chart.setBackgroundPaint(Color.white);
        /*------------�趨Plot����-------------*/
        CategoryPlot plot = chart.getCategoryPlot();
        // 2,������ϸͼ�����ʾϸ�ڲ��ֵı�����ɫ
        plot.setBackgroundPaint(Color.white);
        // 3,���ô�ֱ��������ɫ
        plot.setDomainGridlinePaint(Color.black);
        // 4,�����Ƿ���ʾ��ֱ������
        plot.setDomainGridlinesVisible(true);
        // 5,����ˮƽ��������ɫ
        plot.setRangeGridlinePaint(Color.blue);
        // 6,�����Ƿ���ʾˮƽ������
        plot.setRangeGridlinesVisible(true);

        // 7,����������ʱ����Ϣ��ʾ����ʾ��ɫ
        plot.setNoDataMessage(this.getChartBean().getNoDataMessage());
        plot.setNoDataMessagePaint(this.getChartBean().getNoDataMessageColor());

        //��ʾ�۵�����
        CategoryItemRenderer categoryItemRenderer = plot.getRenderer();
        categoryItemRenderer.setBaseItemLabelsVisible(this.getChartBean().isShowChartData());
        categoryItemRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));

        categoryItemRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        categoryItemRenderer.setBaseItemLabelFont(new Font("Dialog", 1, 14));
        plot.setRenderer(categoryItemRenderer);

        return chart;
    }

}
