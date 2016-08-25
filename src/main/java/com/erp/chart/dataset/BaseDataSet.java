package com.erp.chart.dataset;

import com.erp.chart.bean.BaseBean;
import org.jfree.data.general.Dataset;

import java.io.Serializable;

/**
 * Created by wang_ on 2016-08-13.
 */
public abstract class BaseDataSet implements Serializable {
    private BaseBean baseBean;

    public abstract Dataset createDataSet();

    public BaseBean getBaseBean() {
        return baseBean;
    }

    public void setBaseBean(BaseBean baseBean) {
        this.baseBean = baseBean;
    }
}
