package com.erp.chart.bean;

import java.io.Serializable;

/**
 * Created by wang_ on 2016-06-30.
 */
public class BaseBean implements Serializable {
    private String wlbm;

    public BaseBean() {
    }

    public BaseBean(String wlbm) {
        this.wlbm = wlbm;
    }

    public String getWlbm() {
        return wlbm;
    }

    public void setWlbm(String wlbm) {
        this.wlbm = wlbm;
    }

}
