package com.erp.service;

import com.erp.dao.IProjectDao;
import com.erp.dao.impl.ProjectDaoImpl;
import com.erp.entity.Project;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;

import java.util.List;

/**
 * Created by wang_ on 2016-07-15.
 */
public class ProjectService {

    /**
     * ≥ı ºªØProject
     * @param projectId
     * @return
     * @throws ServiceException
     */
    public static List<Project> initProjects(String projectId) throws ServiceException{
        IProjectDao projectDao = new ProjectDaoImpl();
        List<Project> projects = null;
        try {
            projects = projectDao.queryProjects(projectId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return projects;
    }

}
