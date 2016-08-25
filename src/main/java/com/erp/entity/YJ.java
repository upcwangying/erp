package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * �½�
 * Created by wang_ on 2016-08-04.
 */
public class YJ implements Serializable{

    // ����
    private long dbid;

    // �½��·�
    private String yjyf;

    // �½�֧��
    private double yjzc;

    // �½Ữת
    private double yjhz;

    // �½����
    private double yjye;

    // ���
    private long staffId;

    // �������
    private String staffName;

    // �½����ͣ�1Ϊ��ʼ�����ݣ�0Ϊ���ӵ�����
    private String yjlx;

    // �Ƿ�ɾ��
    private boolean delete;

    // ����ʱ��
    private Date createDate;

    // ����ʱ��
    private Date updateDate;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getYjyf() {
        return yjyf;
    }

    public void setYjyf(String yjyf) {
        this.yjyf = yjyf;
    }

    public double getYjzc() {
        return yjzc;
    }

    public void setYjzc(double yjzc) {
        this.yjzc = yjzc;
    }

    public double getYjhz() {
        return yjhz;
    }

    public void setYjhz(double yjhz) {
        this.yjhz = yjhz;
    }

    public double getYjye() {
        return yjye;
    }

    public void setYjye(double yjye) {
        this.yjye = yjye;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getYjlx() {
        return yjlx;
    }

    public void setYjlx(String yjlx) {
        this.yjlx = yjlx;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "YJ{" +
                "dbid=" + dbid +
                ", yjyf='" + yjyf + '\'' +
                ", yjzc=" + yjzc +
                ", yjhz=" + yjhz +
                ", yjye=" + yjye +
                ", staffId=" + staffId +
                ", staffName=" + staffName +
                ", yjlx=" + yjlx +
                ", delete=" + delete +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
