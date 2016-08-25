package com.erp.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * ģ��
 * Created by wang_ on 2016-07-02.
 */
public class Module implements Serializable, Comparable<Module> {

    // ģ��DBID
    private int moduleId;

    // ģ������
    private String moduleName;

    // ģ���ַ
    private String href;

    // ���ڵ�
    private int parentId;

    // ���ڵ����ͣ�p:һ��Ŀ¼��m:������������Ŀ¼��
    private String parentType;

    // �Ƿ���ʾ
    private char display;

    // ����
    private int disOrder;

    // �ڵ����ͣ���file��ΪҶ�ӽ�㣻��folder��Ϊһ�������ڵ�
    private String icon;

    // ��ģ��
    private Set<Module> children = new TreeSet<Module>();

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public char getDisplay() {
        return display;
    }

    public void setDisplay(char display) {
        this.display = display;
    }

    public int getDisOrder() {
        return disOrder;
    }

    public void setDisOrder(int disOrder) {
        this.disOrder = disOrder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<Module> getChildren() {
        return children;
    }

    public void setChildren(Set<Module> children) {
        this.children = children;
    }

    public void addChild(Module module) {
        this.children.add(module);
    }

    public boolean hasChildren() {
        return this.children.size() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;

        Module module = (Module) o;

        if (moduleId != module.getModuleId()) return false;
        if (parentId != module.getParentId()) return false;
        if (moduleName != null ? !moduleName.equals(module.getModuleName()) : module.getModuleName() != null)
            return false;
        return !(parentType != null ? !parentType.equals(module.getParentType()) : module.getParentType() != null);

    }

    @Override
    public int hashCode() {
        return this.moduleId;
    }

    @Override
    public int compareTo(Module m) {
        int result = this.disOrder - m.getDisOrder();
        return result == 0 ? this.moduleId - m.getModuleId() : result;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", href='" + href + '\'' +
                ", parentId=" + parentId +
                ", display=" + display +
                ", disOrder=" + disOrder +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }

}
