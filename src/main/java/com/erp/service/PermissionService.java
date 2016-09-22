package com.erp.service;

import com.erp.dao.IPermissionDao;
import com.erp.dao.impl.PermissionDaoImpl;
import com.erp.entity.Permission;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by wang_ on 2016-09-22.
 */
public class PermissionService {

    /**
     * 查询
     *
     * @param moduleId
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryPermissionByModuleId(String moduleId) throws ServiceException {
        IPermissionDao permissionDao = new PermissionDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<Permission> permissionList = permissionDao.queryPermissionByModuleId(moduleId);
            if (permissionList != null && permissionList.size() > 0) {
                for (Permission permission : permissionList) {
                    JSONObject object = JSONObject.fromObject(permission);
                    array.add(object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return array;
    }

    /**
     * @param permissionCode
     * @param permissionId
     * @return
     * @throws ServiceException
     */
    public static Permission queryPermissionById(String permissionCode, String permissionId) throws ServiceException {
        IPermissionDao permissionDao = new PermissionDaoImpl();
        Permission permission = null;
        try {
            permission = permissionDao.queryPermissionById(permissionCode, permissionId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return permission;
    }

    /**
     * 插入、更新
     *
     * @param permission
     * @throws ServiceException
     */
    public static void insertOrUpdatePermission(Permission permission) throws ServiceException {
        IPermissionDao permissionDao = new PermissionDaoImpl();
        try {
            permissionDao.insertOrUpdatePermission(permission);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除
     *
     * @param permissionId
     * @throws ServiceException
     */
    public static void deletePermission(String[] permissionId) throws ServiceException {
        IPermissionDao permissionDao = new PermissionDaoImpl();
        try {
            permissionDao.deletePermission(permissionId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
