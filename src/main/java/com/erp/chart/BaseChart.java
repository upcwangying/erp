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
    //创建主题样式
    private static StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
    private ChartBean chartBean;

    static {
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));
        //应用主题样式
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
