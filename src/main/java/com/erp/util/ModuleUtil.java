package com.erp.util;

import com.erp.dao.IModuleDao;
import com.erp.dao.impl.ModuleDaoImpl;
import com.erp.entity.Module;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;

import java.util.*;

/**
 * Created by wang_ on 2016-09-21.
 */
public class ModuleUtil {
    private static Map<Integer, Set<Module>> projectModuleMaps;
    private static Map<Integer, Module> moduleMaps;

    /**
     * @param flag
     * @param moduleId
     * @return
     * @throws ServiceException
     */
    public static List<Module> initModules(boolean flag, String[] moduleId) throws ServiceException {
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

            if (moduleId == null || moduleId.length == 0) {
                initWithoutArray(modules);
            } else {
                initByArray(modules, moduleId);
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return modules;
    }

    /**
     * @param modules
     */
    private static void initWithoutArray(List<Module> modules) {
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
    }

    /**
     * @param modules
     * @param moduleId
     */
    private static void initByArray(List<Module> modules, String[] moduleId) {
        for (Module m : modules) {
            if (Arrays.asList(moduleId).contains(m.getModuleId()+"")) {
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
        }

        for (Module m : modules) {
            if (Arrays.asList(moduleId).contains(m.getModuleId()+"")) {
                if (m.getParentType().equalsIgnoreCase("m")) {
                    moduleMaps.get(m.getParentId()).addChild(m);
                }
            }
        }

    }

    /**
     * @return
     */
    public static Set<Module> getModuleByProjectId() {
        return getModuleByProjectId(Integer.valueOf(SystemConfig.getValue("project.id")));
    }

    /**
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
}
