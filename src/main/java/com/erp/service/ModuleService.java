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
    private static Map<Integer, Set<Module>> projectModuleMaps;
    private static Map<Integer, Module> moduleMaps;

    /**
     *
     * @param flag
     * @return
     * @throws ServiceException
     */
    public static List<Module> initModules(boolean flag) throws ServiceException{
        IModuleDao moduleDao = new ModuleDaoImpl();
        List<Module> modules = null;
        try {
            modules = moduleDao.queryModules(flag);

            if (projectModuleMaps == null) {
                projectModuleMaps = new HashMap<Integer, Set<Module>>();
                moduleMaps = new HashMap<Integer, Module>();
            } else {
                projectModuleMaps.clear();
                moduleMaps.clear();
            }

            for (Module m : modules) {
                if (m.getParentType().equalsIgnoreCase("P")) {
                    Set<Module> ms = projectModuleMaps.get(m.getParentId());
                    if (ms == null) {
                        ms = new TreeSet<Module>();
                        projectModuleMaps.put(m.getParentId(), ms);
                    }
                    ms.add(m);
                }
                moduleMaps.put(m.getModuleId(), m);
            }

            for (Module m : modules) {
                if (m.getParentType().equalsIgnoreCase("m")) {
                    moduleMaps.get(m.getParentId()).addChild(m);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return modules;
    }

    /**
     *
     * @param id
     * @return
     */
    public static Set<Module> getModuleByProjectId(int id) {
        Set<Module> children = projectModuleMaps.get(id);
        if (children == null) {
            children = new HashSet<Module>();
            projectModuleMaps.put(id, children);
        }
        return children;

    }

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
