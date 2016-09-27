package com.erp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wang_ on 2016-06-28.
 */
public class StaffInfo implements Serializable {

    // �û�ID
    private long staffId;

    // �û�����
    private String staffCode;

    // �û�����
    private String staffName;

    // ����
    private String password;

    // �ֻ���
    private String telephone;

    // �Ƿ�ɾ��
    private boolean delete;

    // ������ʽID
    private long styleId;

    // ������ʽ
    private String style;

    private long roleId;

    private String roleName;

    private String staffType;

    private String modules;

    // ����ʱ��
    private Date createDate;

    // ����ʱ��
    private Date updateDate;

    // ����¼ʱ��
    private Date lastLoginTime;

    private Set<Permission> permissions = new HashSet<>();

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public long getStyleId() {
        return styleId;
    }

    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public boolean is_Admin() {
        return "1".equals(this.staffType);
    }

    @Override
    public String toString() {
        return "StaffInfo{" +
                "staffId=" + staffId +
                ", staffCode='" + staffCode + '\'' +
                ", staffName='" + staffName + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", delete=" + delete +
                ", styleId=" + styleId +
                ", style='" + style + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", staffType='" + staffType + '\'' +
                ", modules='" + modules + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", lastLoginTime=" + lastLoginTime +
                ", permissions=" + permissions +
                '}';
    }

    @Override
    public int hashCode() {
        return this.staffCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof StaffInfo) {
            StaffInfo staffInfo = (StaffInfo) obj;
            if (this.staffId == staffInfo.getStaffId()
                    && this.staffCode.equals(staffInfo.getStaffCode())) {
                return true;
            }
        }
        return false;
    }
}
