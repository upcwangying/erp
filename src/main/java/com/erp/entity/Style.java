package com.erp.entity;

import java.io.Serializable;

/**
 * Created by wang_ on 2016-08-18.
 */
public class Style implements Serializable{

    private long styleId;

    private String style;

    private String styleDesc;

    public long getStyleId() {
        return styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleDesc() {
        return styleDesc;
    }

    public void setStyleDesc(String styleDesc) {
        this.styleDesc = styleDesc;
    }

    @Override
    public String toString() {
        return "Style{" +
                "styleId=" + styleId +
                ", style='" + style + '\'' +
                ", styleDesc='" + styleDesc + '\'' +
                '}';
    }
}
