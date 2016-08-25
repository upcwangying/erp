package com.erp.dao.impl;

import com.erp.dao.IProjectDao;
import com.erp.entity.Project;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import com.erp.util.StringUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016-07-15.
 */
public class ProjectDaoImpl implements IProjectDao {
    private static Logger logger = Logger.getLogger(ProjectDaoImpl.class);

    /**
     *
     * @return
     * @throws DAOException
     */
    public List<Project> queryProjects(String projectId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Project> projectList = new ArrayList<Project>();
        try {
            String sql ="select projectid,projectcode,projectname,styleid,projectdesc,status,url from project " +
                    "where status='0' and ('0'=? or projectid=?) ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, projectId == null ? "0" : "1");
            ps.setInt(2, StringUtil.isEmpty(projectId) ? -1 : Integer.valueOf(projectId));
            rst = ps.executeQuery();
            while (rst.next()) {
                Project project = new Project();
                project.setProjectId(rst.getInt("projectid"));
                project.setProjectCode(rst.getString("projectcode"));
                project.setProjectName(rst.getString("projectname"));
                project.setStyleId(rst.getInt("styleid"));
                project.setProjectDesc(rst.getString("projectdesc"));
                project.setStatus("0");
                project.setUrl(rst.getString("url"));
                projectList.add(project);

            }
        } catch (Exception e) {
            logger.error("获取项目工程失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取项目工程失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

        return projectList;
    }
}
