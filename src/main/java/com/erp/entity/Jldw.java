package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wang_ on 2016-09-01.
 */
public class Jldw implements Serializable {

    // ������λID������
    private long jldwId;

    // ������λ����
    private String jldwmc;

    // ������λ����
    private String jldwms;

    // �Ƿ���Ч
    private String is_valid;

    // ������
    private long create_staffId;

    // ����ʱ��
    private Date createDate;

    // ������
    private long update_staffId;

    // ����ʱ��
    private Date updateDate;

    public long getJldwId() {
        return jldwId;
    }

    public void setJldwId(long jldwId) {
        this.jldwId = jldwId;
    }

    public String getJldwmc() {
        return jldwmc;
    }

    public void setJldwmc(String jldwmc) {
        this.jldwmc = jldwmc;
    }

    public String getJldwms() {
        return jldwms;
    }

    public void setJldwms(String jldwms) {
        this.jldwms = jldwms;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public long getCreate_staffId() {
        return create_staffId;
    }

    public void setCreate_staffId(long create_staffId) {
        this.create_staffId = create_staffId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getUpdate_staffId() {
        return update_staffId;
    }

    public void setUpdate_staffId(long update_staffId) {
        this.update_staffId = update_staffId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jldw jldw = (Jldw) o;

        if (jldwId != jldw.jldwId) return false;
        return jldwmc.equals(jldw.jldwmc);

    }

    @Override
    public int hashCode() {
        int result = (int) (jldwId ^ (jldwId >>> 32));
        result = 31 * result + jldwmc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Jldw{" +
                "jldwId=" + jldwId +
                ", jldwmc='" + jldwmc + '\'' +
                ", jldwms='" + jldwms + '\'' +
                ", is_valid='" + is_valid + '\'' +
                ", create_staffId=" + create_staffId +
                ", createDate=" + createDate +
                ", update_staffId=" + update_staffId +
                ", updateDate=" + updateDate +
                '}';
    }
}
