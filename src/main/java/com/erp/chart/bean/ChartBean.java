package com.erp.chart.bean;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;

import java.awt.*;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by wang_ on 2016-06-29.
 */
public class ChartBean implements Serializable {
    private String chartTitle; // ͼ�����
    private Dataset dataset; // ���ݼ�
    private PlotOrientation plotOrientation; // ͼ����ˮƽ����ֱ
    private String XLabel; // Ŀ¼��(X��)����ʾ��ǩ
    private String YLabel; // ��ֵ��(Y��)����ʾ��ǩ
    private String noDataMessage; // ������ʱ��ʾ���ַ���
    private Color noDataMessageColor; // ������ʱ��ʾ���ַ�����ɫ
    private boolean legend; // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
    private boolean tooltips; // �Ƿ����ɹ���
    private boolean urls; // �Ƿ�����URL����
    private boolean showChartData = true; // �Ƿ���ʾͼ�����ݣ��۵����ݣ�������״ͼ���ݣ�

    public ChartBean() {
    }

    public ChartBean(Properties properties) {
        this.chartTitle = properties.getProperty("chartTitle");
        this.dataset = null;
        this.plotOrientation = getPlotOrientation(properties.getProperty("plotOrientation"));
        this.XLabel = properties.getProperty("XLabel");
        this.YLabel = properties.getProperty("YLabel");
        this.noDataMessage = properties.getProperty("noDataMessage");
        this.noDataMessageColor = getColor(properties.getProperty("noDataMessageColor"));
        this.legend = Boolean.valueOf(properties.getProperty("legend"));
        this.tooltips = Boolean.valueOf(properties.getProperty("tooltips"));
        this.urls = Boolean.valueOf(properties.getProperty("urls"));
        this.showChartData = Boolean.valueOf(properties.getProperty("showChartData"));
    }

    /**
     *
     * @param name
     * @return
     */
    private PlotOrientation getPlotOrientation(String name) {
        return "HORIZONTAL".equals(name) ? PlotOrientation.HORIZONTAL : ("VERTICAL".equals(name) ? PlotOrientation.VERTICAL : null);
    }

    /**
     *
     * @param name
     * @return
     */
    private Color getColor(String name) {
        if (name == null) return Color.RED;
        String[] rgb = name.split(",");
        if (rgb.length != 3) {
            return Color.RED;
        } else {
            try {
                int x = Integer.valueOf("".equals(rgb[0].trim()) ? "255" : rgb[0].trim()),
                        y = Integer.valueOf("".equals(rgb[1].trim()) ? "0" : rgb[1].trim()),
                        z = Integer.valueOf("".equals(rgb[2].trim()) ? "0" : rgb[2].trim());
                if (x > 255) x = 255;
                if (x < 0) x = 0;
                if (y > 255) y = 255;
                if (y < 0) y = 0;
                if (z > 255) z = 255;
                if (z < 0) z = 0;
                return new Color(x, y, z);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return Color.RED;
            }
        }
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public PlotOrientation getPlotOrientation() {
        return plotOrientation;
    }

    public void setPlotOrientation(PlotOrientation plotOrientation) {
        this.plotOrientation = plotOrientation;
    }

    public String getXLabel() {
        return XLabel;
    }

    public void setXLabel(String XLabel) {
        this.XLabel = XLabel;
    }

    public String getYLabel() {
        return YLabel;
    }

    public void setYLabel(String YLabel) {
        this.YLabel = YLabel;
    }

    public String getNoDataMessage() {
        return noDataMessage;
    }

    public void setNoDataMessage(String noDataMessage) {
        this.noDataMessage = noDataMessage;
    }

    public Color getNoDataMessageColor() {
        return noDataMessageColor;
    }

    public void setNoDataMessageColor(Color noDataMessageColor) {
        this.noDataMessageColor = noDataMessageColor;
    }

    public boolean isLegend() {
        return legend;
    }

    public void setLegend(boolean legend) {
        this.legend = legend;
    }

    public boolean isTooltips() {
        return tooltips;
    }

    public void setTooltips(boolean tooltips) {
        this.tooltips = tooltips;
    }

    public boolean isUrls() {
        return urls;
    }

    public void setUrls(boolean urls) {
        this.urls = urls;
    }

    public boolean isShowChartData() {
        return showChartData;
    }

    public void setShowChartData(boolean showChartData) {
        this.showChartData = showChartData;
    }

    @Override
    public String toString() {
        return "ChartBean{" +
                "chartTitle='" + chartTitle + '\'' +
                ", dataset=" + dataset +
                ", plotOrientation=" + plotOrientation +
                ", XLabel='" + XLabel + '\'' +
                ", YLabel='" + YLabel + '\'' +
                ", noDataMessage='" + noDataMessage + '\'' +
                ", noDataMessageColor='" + noDataMessageColor + '\'' +
                ", legend=" + legend +
                ", tooltips=" + tooltips +
                ", urls=" + urls +
                ", showChartData=" + showChartData +
                '}';
    }
}
