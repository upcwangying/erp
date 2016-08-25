package com.erp.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 模块
 * Created by wang_ on 2016-07-02.
 */
public class Module implements Serializable, Comparable<Module> {

    // 模块DBID
    private int moduleId;

    // 模块名称
    private String moduleName;

    // 模块地址
    private String href;

    // 父节点
    private int parentId;

    // 父节点类型（p:一层目录；m:二层或二层以下目录）
    private String parentType;

    // 是否显示
    private char display;

    // 排序
    private int disOrder;

    // 节点类型：“file”为叶子结点；“folder”为一、二级节点
    private String icon;

    // 子模块
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
