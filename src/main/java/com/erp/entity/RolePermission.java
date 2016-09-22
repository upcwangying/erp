package com.erp.entity;

import java.io.Serializable;

/**
 * Created by wang_ on 2016-09-22.
 */
public class RolePermission implements Serializable {

    private long dbid;

    private long roleId;

    private long permissionId;

    private String is_del;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "dbid=" + dbid +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                ", is_del='" + is_del + '\'' +
                '}';
    }
}
