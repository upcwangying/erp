package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wang_ on 2016-08-26.
 */
public class Product implements Serializable, Comparable<Project> {

    // 商品ID
    private long productId;

    // 商品名称
    private String productName;

    // 商品描述
    private String productDesc;

    // 商品描述1
    private String productDesc1;

    // 商品描述2
    private String productDesc2;

    // 商品描述3
    private String productDesc3;

    // 商品描述4
    private String productDesc4;

    // 商品描述5
    private String productDesc5;

    // 是否删除
    private boolean delete;

    // 创建人
    private long create_StaffId;

    // 创建人姓名
    private String create_staffName;

    // 创建时间
    private Date create_date;

    // 更新人
    private long update_staffId;

    // 更新人姓名
    private String update_staffName;

    // 更新时间
    private Date update_date;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductDesc1() {
        return productDesc1;
    }

    public void setProductDesc1(String productDesc1) {
        this.productDesc1 = productDesc1;
    }

    public String getProductDesc2() {
        return productDesc2;
    }

    public void setProductDesc2(String productDesc2) {
        this.productDesc2 = productDesc2;
    }

    public String getProductDesc3() {
        return productDesc3;
    }

    public void setProductDesc3(String productDesc3) {
        this.productDesc3 = productDesc3;
    }

    public String getProductDesc4() {
        return productDesc4;
    }

    public void setProductDesc4(String productDesc4) {
        this.productDesc4 = productDesc4;
    }

    public String getProductDesc5() {
        return productDesc5;
    }

    public void setProductDesc5(String productDesc5) {
        this.productDesc5 = productDesc5;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public long getCreate_StaffId() {
        return create_StaffId;
    }

    public void setCreate_StaffId(long create_StaffId) {
        this.create_StaffId = create_StaffId;
    }

    public String getCreate_staffName() {
        return create_staffName;
    }

    public void setCreate_staffName(String create_staffName) {
        this.create_staffName = create_staffName;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public long getUpdate_staffId() {
        return update_staffId;
    }

    public void setUpdate_staffId(long update_staffId) {
        this.update_staffId = update_staffId;
    }

    public String getUpdate_staffName() {
        return update_staffName;
    }

    public void setUpdate_staffName(String update_staffName) {
        this.update_staffName = update_staffName;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productId != product.productId) return false;
        return productName.equals(product.productName);

    }

    @Override
    public int hashCode() {
        int result = (int) (productId ^ (productId >>> 32));
        result = 31 * result + productName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productDesc1='" + productDesc1 + '\'' +
                ", productDesc2='" + productDesc2 + '\'' +
                ", productDesc3='" + productDesc3 + '\'' +
                ", productDesc4='" + productDesc4 + '\'' +
                ", productDesc5='" + productDesc5 + '\'' +
                ", delete=" + delete +
                ", create_StaffId=" + create_StaffId +
                ", create_staffName='" + create_staffName + '\'' +
                ", create_date=" + create_date +
                ", update_staffId=" + update_staffId +
                ", update_staffName='" + update_staffName + '\'' +
                ", update_date=" + update_date +
                '}';
    }

    @Override
    public int compareTo(Project o) {
        return (int) (this.productId - o.getProjectId());
    }
}
