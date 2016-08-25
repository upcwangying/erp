package com.erp.dao;

import com.erp.entity.Project;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-07-15.
 */
public interface IProjectDao {

    List<Project> queryProjects(String projectId) throws DAOException;
}
