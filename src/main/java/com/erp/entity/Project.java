package com.erp.entity;

import java.io.Serializable;

/**
 *
 * Created by wang_ on 2016-07-15.
 */
public class Project implements Serializable{

    // 工程项目ID
    private int projectId;

    // 工程项目编码
    private String projectCode;

    // 工程项目名称
    private String projectName;

    private int styleId;

    // 项目描述
    private String projectDesc;

    // 状态：“0”为有效；“1”为无效
    private String status;

    // 项目路径
    private String url;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (projectId != project.getProjectId()) return false;
        if (projectCode != null ? !projectCode.equals(project.getProjectCode()) : project.getProjectCode() != null)
            return false;
        return !(projectName != null ? !projectName.equals(project.getProjectName()) : project.getProjectName() != null);

    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + projectCode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectCode='" + projectCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", styleId=" + styleId +
                ", projectDesc='" + projectDesc + '\'' +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
