package com.erp.entity;

import java.io.Serializable;

/**
 * 供应商
 * Created by wang_ on 2016-06-29.
 */
public class Gys implements Serializable {

    // 供应商DBID
    private long gysId;

    // 供应商编码
    private String gysbm;

    // 供应商名称
    private String gysmc;

    // 供应商描述
    private String gysms;

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

    public long getGysId() {
        return gysId;
    }

    public void setGysId(long gysId) {
        this.gysId = gysId;
    }

    public String getGysbm() {
        return gysbm;
    }

    public void setGysbm(String gysbm) {
        this.gysbm = gysbm;
    }

    public String getGysmc() {
        return gysmc;
    }

    public void setGysmc(String gysmc) {
        this.gysmc = gysmc;
    }

    public String getGysms() {
        return gysms;
    }

    public void setGysms(String gysms) {
        this.gysms = gysms;
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
        return this.getGysbm().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Gys) {
            Gys gys = (Gys) obj;
            if (this.gysbm.equals(gys.getGysbm())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Gys{" +
                "gysId=" + gysId +
                ", gysbm='" + gysbm + '\'' +
                ", gysmc='" + gysmc + '\'' +
                ", gysms='" + gysms + '\'' +
                ", delete=" + delete +
                ", create_staffId=" + create_staffId +
                ", createDate=" + createDate +
                ", update_staffId=" + update_staffId +
                ", updateDate=" + updateDate +
                '}';
    }
}
