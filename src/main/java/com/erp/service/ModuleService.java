package com.erp.service;

import com.erp.dao.IModuleDao;
import com.erp.dao.impl.ModuleDaoImpl;
import com.erp.entity.Module;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;

import java.util.*;

/**
 * Created by wang_ on 2016-07-02.
 */
public class ModuleService {

    /**
     * 插入模块
     *
     * @param module
     * @throws ServiceException
     */
    public static void insertModule(Module module) throws ServiceException {
        IModuleDao moduleDao = new ModuleDaoImpl();
        try {
            moduleDao.insertModule(module);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 更新模块
     *
     * @param module
     * @throws ServiceException
     */
    public static void updateModule(Module module) throws ServiceException {
        IModuleDao moduleDao = new ModuleDaoImpl();
        try {
            moduleDao.updateModule(module);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 更新模块
     *
     * @param id
     * @throws ServiceException
     */
    public static void updateModule(String id) throws ServiceException {
        IModuleDao moduleDao = new ModuleDaoImpl();
        try {
            moduleDao.updateModule(id);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除节点
     *
     * @param ids
     * @throws ServiceException
     */
    public static void deleteModule(String[] ids) throws ServiceException {
        IModuleDao moduleDao = new ModuleDaoImpl();
        try {
            moduleDao.deleteModule(ids);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
