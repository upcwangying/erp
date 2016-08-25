package com.erp.service;

import com.erp.bean.SequenceBean;

/**
 * Created by wang_ on 2016-07-26.
 */
public class SequenceService {

    /**
     *
     * @param name
     * @param len
     * @return
     * @throws Exception
     */
    public static String initSequence(String name, int len) throws Exception {
        long sn = SequenceBean.getNextSequence(name);

        String result = sn + "";
        if (len <= 0) {
            return result;
        }

        while (result.length() < len) {
            result = "0" + result;
        }

        return result;
    }
}
