package com.erp.bean;

import com.erp.service.SerialNumberService;

/**
 * –Ú¡–∫≈
 * Created by wang_ on 2016-07-26.
 */
public class SequenceBean {

    private String name;
    private long last;
    private long curent;

    public SequenceBean(String name) {
        this.name = name;
        this.curent = SerialNumberService.initSerialNumber(name);
        this.last = this.curent + 1;
    }

    public static synchronized long getNextSequence(String name) throws Exception {
        SequenceBean sequenceBean = new SequenceBean(name);
        return sequenceBean.curent;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public long getCurent() {
        return curent;
    }

    public void setCurent(long curent) {
        this.curent = curent;
    }
}
