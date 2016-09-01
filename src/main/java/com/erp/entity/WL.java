package com.erp.entity;

import java.io.Serializable;

/**
 * 物料
 * Created by wang_ on 2016-06-29.
 */
public class WL implements Serializable {

    // 物料DBID
    private long wlId;

    // 物料编码
    private String wlbm;

    // 物料名称
    private String wlmc;

    // 物料描述
    private String wlms;

    // 是否删除
    private boolean delete;

    // 创建人
    private long create_staffId;

    // 创建时间
    private java.util.Date createDate;

    // 更新人
    private long update_staffId;

    // 更新时间
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

    public long getCreate_staffId() {
        return create_staffId;
    }

    public void setCreate_staffId(long create_staffId) {
        this.create_staffId = create_staffId;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    public long getUpdate_staffId() {
        return update_staffId;
    }

    public void setUpdate_staffId(long update_staffId) {
        this.update_staffId = update_staffId;
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
                ", create_staffId=" + create_staffId +
                ", createDate=" + createDate +
                ", update_staffId=" + update_staffId +
                ", updateDate=" + updateDate +
                '}';
    }
}
