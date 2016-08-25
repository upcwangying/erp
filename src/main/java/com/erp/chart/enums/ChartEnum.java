package com.erp.chart.enums;

import com.erp.chart.BarChart;
import com.erp.chart.BaseChart;
import com.erp.chart.LineChart;
import com.erp.chart.PieChart;

/**
 * Created by wang_ on 2016-06-29.
 */
public enum ChartEnum {

    PIECHART("pie", new PieChart()),
    BARCHART("bar", new BarChart()),
    LINECHART("line", new LineChart());

    private String chartName;
    private BaseChart baseChart;

    ChartEnum(String chartName, BaseChart baseChart) {
        this.chartName = chartName;
        this.baseChart = baseChart;
    }

    public static ChartEnum getEnumByName(String chartName) {
        for (ChartEnum chartEnum : values()) {
            if (chartEnum.getChartName().equals(chartName)) {
                return chartEnum;
            }
        }
        return null;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public BaseChart getBaseChart() {
        return baseChart;
    }

    public void setBaseChart(BaseChart baseChart) {
        this.baseChart = baseChart;
    }

}
