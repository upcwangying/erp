package com.erp.product.bean;

import java.util.HashMap;

/**
 * Created by wang_ on 2016-08-25.
 */
public class TypeComparator extends SortComparator {
    public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {
        if (((Boolean)a.get("is_dir")) && !((Boolean)b.get("is_dir"))) {
            return -1;
        } else if (!((Boolean)a.get("is_dir")) && ((Boolean)b.get("is_dir"))) {
            return 1;
        } else {
            return ((String)a.get("filetype")).compareTo((String)b.get("filetype"));
        }
    }
}
