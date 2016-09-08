package com.erp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wang_ on 2016-09-06.
 */
public class FileUploadLog implements Serializable {

    private long dbid;

    private long productId;

    private String name;

    private String url;

    private String thumbnailurl;

    private String deleteurl;

    private String is_pic_valid;

    private String is_del;

    private long create_staffId;

    private Date createDate;

    private long update_staffId;

    private Date updateDate;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailurl() {
        return thumbnailurl;
    }

    public void setThumbnailurl(String thumbnailurl) {
        this.thumbnailurl = thumbnailurl;
    }

    public String getDeleteurl() {
        return deleteurl;
    }

    public void setDeleteurl(String deleteurl) {
        this.deleteurl = deleteurl;
    }

    public String getIs_pic_valid() {
        return is_pic_valid;
    }

    public void setIs_pic_valid(String is_pic_valid) {
        this.is_pic_valid = is_pic_valid;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
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
    public String toString() {
        return "FileUploadLog{" +
                "dbid=" + dbid +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", thumbnailurl='" + thumbnailurl + '\'' +
                ", deleteurl='" + deleteurl + '\'' +
                ", is_pic_valid='" + is_pic_valid + '\'' +
                ", is_del='" + is_del + '\'' +
                ", create_staffId=" + create_staffId +
                ", createDate=" + createDate +
                ", update_staffId=" + update_staffId +
                ", updateDate=" + updateDate +
                '}';
    }
}
