package com.erp.enums;

import com.erp.util.ResourcePathUtil;

/**
 * Created by wang_ on 2016-09-02.
 */
public enum ResourceXmlEnum {

    path_chart("charts.xml"),
    path_datasource("datasource.xml"),
    path_beanconfig("beanconfig.xml");

    private String value;

    ResourceXmlEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return ResourcePathUtil.getResourcePath(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
