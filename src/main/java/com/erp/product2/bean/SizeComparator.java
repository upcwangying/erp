package com.erp.product2.bean;

import java.util.HashMap;

/**
 * Created by wang_ on 2016-08-25.
 */
public class SizeComparator extends SortComparator {
    public int compare(HashMap<String, Object> a, HashMap<String, Object> b) {
        if (((Boolean)a.get("is_dir")) && !((Boolean)b.get("is_dir"))) {
            return -1;
        } else if (!((Boolean)a.get("is_dir")) && ((Boolean)b.get("is_dir"))) {
            return 1;
        } else {
            if (((Long)a.get("filesize")) > ((Long)b.get("filesize"))) {
                return 1;
            } else if (((Long)a.get("filesize")) < ((Long)b.get("filesize"))) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
