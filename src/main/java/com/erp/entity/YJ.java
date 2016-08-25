package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 月结
 * Created by wang_ on 2016-08-04.
 */
public class YJ implements Serializable{

    // 主键
    private long dbid;

    // 月结月份
    private String yjyf;

    // 月结支出
    private double yjzc;

    // 月结划转
    private double yjhz;

    // 月结余额
    private double yjye;

    // 填报人
    private long staffId;

    // 填报人姓名
    private String staffName;

    // 月结类型：1为初始化数据，0为增加的数据
    private String yjlx;

    // 是否删除
    private boolean delete;

    // 创建时间
    private Date createDate;

    // 更新时间
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
