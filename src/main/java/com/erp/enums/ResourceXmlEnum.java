package com.erp.enums;

import com.erp.util.ResourcePathUtil;

import java.io.File;

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
        return ResourcePathUtil.getResourcePath()+File.separatorChar+value;
    }

    @Override
    public String toString() {
        return value;
    }

}
