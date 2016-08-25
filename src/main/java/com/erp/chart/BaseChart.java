package com.erp.chart;

import com.erp.chart.bean.ChartBean;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by wang_ on 2016-06-29.
 */
public class BaseChart implements Serializable {
    //����������ʽ
    private static StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
    private ChartBean chartBean;

    static {
        //���ñ�������
        standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));
        //����ͼ��������
        standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));
        //�������������
        standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));
        //Ӧ��������ʽ
//        ChartFactory.setChartTheme(standardChartTheme);
    }

    public BaseChart() {
    }

    public BaseChart(ChartBean chartBean) {
        this.chartBean = chartBean;
    }

    public JFreeChart createChart() {
        return null;
    }

    public StandardChartTheme getStandardChartTheme() {
        return standardChartTheme;
    }

    public void setStandardChartTheme(StandardChartTheme standardChartTheme) {
        BaseChart.standardChartTheme = standardChartTheme;
    }

    public ChartBean getChartBean() {
        return chartBean;
    }

    public void setChartBean(ChartBean chartBean) {
        this.chartBean = chartBean;
    }

}
