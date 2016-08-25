package com.erp.product.enums;

import com.erp.product.bean.NameComparator;
import com.erp.product.bean.SizeComparator;
import com.erp.product.bean.SortComparator;
import com.erp.product.bean.TypeComparator;

/**
 * Created by wang_ on 2016-08-25.
 */
public enum SortEnum {

    SIZE("size", new SizeComparator()),
    TYPE("type", new TypeComparator()),
    NAME("name", new NameComparator());

    private String name;
    private SortComparator comparator;

    SortEnum(String name, SortComparator comparator) {
        this.name = name;
        this.comparator = comparator;
    }

    public static SortEnum getSortEnumByName(String name) {
        for (SortEnum sortEnum: values()) {
            if (sortEnum.getName().equals(name)) {
                return sortEnum;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortComparator getComparator() {
        return comparator;
    }

    public void setComparator(SortComparator comparator) {
        this.comparator = comparator;
    }
}
