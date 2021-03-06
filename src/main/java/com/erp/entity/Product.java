package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wang_ on 2016-08-26.
 */
public class Product implements Serializable, Comparable<Product> {

    // 商品ID
    private long productId;

    // 商品名称
    private String productName;

    // 商品描述
    private String productDesc;

    // 计量单位
    private long jldwid;

    // 计量单位名称
    private String jldwmc;

    // 单价
    private double price;

    // 缩略图URL
    private String thumbnailUrl;

    // 是否上架 1为上架；0为下架
    private String is_valid;

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

    public long getJldwid() {
        return jldwid;
    }

    public void setJldwid(long jldwid) {
        this.jldwid = jldwid;
    }

    public String getJldwmc() {
        return jldwmc;
    }

    public void setJldwmc(String jldwmc) {
        this.jldwmc = jldwmc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
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
                ", jldwid=" + jldwid +
                ", jldwmc=" + jldwmc +
                ", price=" + price +
                ", thumbnailUrl=" + thumbnailUrl +
                ", is_valid=" + is_valid +
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
    public int compareTo(Product o) {
        return (int) (this.productId - o.getProductId());
    }
}
