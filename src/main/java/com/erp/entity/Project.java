package com.erp.entity;

import java.io.Serializable;

/**
 *
 * Created by wang_ on 2016-07-15.
 */
public class Project implements Serializable{

    // ������ĿID
    private int projectId;

    // ������Ŀ����
    private String projectCode;

    // ������Ŀ����
    private String projectName;

    private int styleId;

    // ��Ŀ����
    private String projectDesc;

    // ״̬����0��Ϊ��Ч����1��Ϊ��Ч
    private String status;

    // ��Ŀ·��
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
