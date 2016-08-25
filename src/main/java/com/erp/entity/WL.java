package com.erp.entity;

import java.io.Serializable;

/**
 * ����
 * Created by wang_ on 2016-06-29.
 */
public class WL implements Serializable {

    // ����DBID
    private long wlId;

    // ���ϱ���
    private String wlbm;

    // ��������
    private String wlmc;

    // ��������
    private String wlms;

    // �Ƿ�ɾ��
    private boolean delete;

    // ����ʱ��
    private java.util.Date createDate;

    // ����ʱ��
    private java.util.Date updateDate;

    public long getWlId() {
        return wlId;
    }

    public void setWlId(long wlId) {
        this.wlId = wlId;
    }

    public String getWlbm() {
        return wlbm;
    }

    public void setWlbm(String wlbm) {
        this.wlbm = wlbm;
    }

    public String getWlmc() {
        return wlmc;
    }

    public void setWlmc(String wlmc) {
        this.wlmc = wlmc;
    }

    public String getWlms() {
        return wlms;
    }

    public void setWlms(String wlms) {
        this.wlms = wlms;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public int hashCode() {
        return this.wlbm.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof WL) {
            WL wl = (WL) obj;
            if (this.wlbm.equals(wl.getWlbm())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "WL{" +
                "wlId=" + wlId +
                ", wlbm='" + wlbm + '\'' +
                ", wlmc='" + wlmc + '\'' +
                ", wlms='" + wlms + '\'' +
                ", delete=" + delete +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
