package com.erp.chart.enums;

import com.erp.chart.dataset.*;

/**
 * Created by wang_ on 2016-08-13.
 */
public enum DataSetEnum {

    CHARTPIEDATASET("chart_pie", new ChartPieDataSet()),
    CHARTBARDATASET("chart_bar", new ChartBarDataSet()),
    CHARTLINEDATASET("chart_line", new ChartLineDataSet()),

    CHARTINDEXPIEDATASET("chart_index_pie", new ChartIndexPieDataSet()),
    CHARTINDEXBARDATASET("chart_index_bar", new ChartIndexBarDataSet()),
    CHARTINDEXLINEDATASET("chart_index_line", new ChartIndexLineDataSet()),

    YDJSPIEDATASET("ydjs_pie", new YdjsPieDataSet()),
    YDJSBARDATASET("ydjs_bar", new YdjsBarDataSet()),
    YDJSLINEDATASET("ydjs_line", new YdjsLineDataSet());

    private String dataSetName;
    private BaseDataSet baseDataSet;

    DataSetEnum(String dataSetName, BaseDataSet baseDataSet) {
        this.dataSetName = dataSetName;
        this.baseDataSet = baseDataSet;
    }

    public static DataSetEnum getEnumByName(String dataSetName) {
        for (DataSetEnum dataSetEnum : values()) {
            if (dataSetEnum.getDataSetName().equals(dataSetName)) {
                return dataSetEnum;
            }
        }
        return null;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public BaseDataSet getBaseDataSet() {
        return baseDataSet;
    }

    public void setBaseDataSet(BaseDataSet baseDataSet) {
        this.baseDataSet = baseDataSet;
    }
}
