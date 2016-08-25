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

    //��״ͼ
    public JFreeChart createChart() {
        ChartFactory.setChartTheme(this.getStandardChartTheme());
        JFreeChart chart = ChartFactory.createPieChart3D(
                this.getChartBean().getChartTitle(),
                (PieDataset) this.getChartBean().getDataset(),
                this.getChartBean().isLegend(),
                this.getChartBean().isTooltips(),
                this.getChartBean().isUrls()
        );
        chart.setBackgroundPaint(Color.pink);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();

        // ָ����ͼ�����ߵ���ɫ
        // plot.setBaseSectionOutlinePaint(Color.BLACK);
        // plot.setBaseSectionPaint(Color.BLACK);

        // ����������ʱ����Ϣ��ʾ����ʾ��ɫ
        plot.setNoDataMessage(this.getChartBean().getNoDataMessage());
        plot.setNoDataMessagePaint(this.getChartBean().getNoDataMessageColor());

        if (this.getChartBean().isShowChartData()) {
            // ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                    "{0}={1}({2})", NumberFormat.getNumberInstance(),
                    new DecimalFormat("0.00%")));
            // ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����
//            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
        }

        plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));

        // ָ��ͼƬ��͸����(0.0-1.0)
        plot.setForegroundAlpha(0.65f);
        // ָ����ʾ�ı�ͼ��Բ��(false)����Բ��(true)
        plot.setCircular(false, true);

        // ���õ�һ�� ����section �Ŀ�ʼλ�ã�Ĭ����12���ӷ���
        plot.setStartAngle(90);

        // // ���÷ֱ���ɫ
//        plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
//        plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));

        return chart;
    }

}
