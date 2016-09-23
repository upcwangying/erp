package com.erp.service;

import com.erp.dao.IRoleDao;
import com.erp.dao.impl.RoleDaoImpl;
import com.erp.entity.Permission;
import com.erp.entity.Role;
import com.erp.entity.RolePermission;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by wang_ on 2016-09-22.
 */
public class RoleService {

    /**
     * ��ѯ
     *
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryRole() throws ServiceException{
        IRoleDao roleDao = new RoleDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<Role> roleList = roleDao.queryRole();
            if (roleList != null && roleList.size() > 0) {
                for (Role role : roleList) {
                    JSONObject object = JSONObject.fromObject(role);
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
     * @param roleCode
     * @param roleId
     * @return
     * @throws ServiceException
     */
    public static Role queryRoleByRoleId(String roleCode, String roleId) throws ServiceException {
        IRoleDao roleDao = new RoleDaoImpl();
        Role role = null;
        try {
            role = roleDao.queryRoleByRoleId(roleCode, roleId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return role;
    }

    /**
     * ���ӻ��޸�
     *
     * @param role
     * @param flag
     * @throws ServiceException
     */
    public static void insertOrUpdateRole(Role role, boolean flag) throws ServiceException {
        IRoleDao roleDao = new RoleDaoImpl();
        try {
            roleDao.insertOrUpdateRole(role, flag);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ɾ��
     *
     * @param roleId
     * @throws ServiceException
     */
    public static void deleteRole(String[] roleId) throws ServiceException {
        IRoleDao roleDao = new RoleDaoImpl();
        try {
            roleDao.deleteRole(roleId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ��ѯ��ɫ��Ӧ��Ȩ��
     * @param roleId
     * @throws ServiceException
     */
    public static JSONArray queryRolePermission(String roleId) throws ServiceException {
        IRoleDao roleDao = new RoleDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<RolePermission> rolePermissionList = roleDao.queryRolePermission(roleId);
            if (rolePermissionList != null && rolePermissionList.size() > 0) {
                for (RolePermission rolePermission : rolePermissionList) {
                    JSONObject object = JSONObject.fromObject(rolePermission);
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
     * ���ӽ�ɫ��Ӧ��Ȩ��
     *
     * @param rolePermissionList
     * @throws ServiceException
     */
    public static void insertRolePermission(List<RolePermission> rolePermissionList) throws ServiceException {
        IRoleDao roleDao = new RoleDaoImpl();
        try {
            roleDao.insertRolePermission(rolePermissionList);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ɾ��Ȩ��
     *
     * @param roleId
     * @param dbid
     * @param flag
     * @throws ServiceException
     */
    public static void deleteRolePermission(String roleId, String[] dbid, boolean flag) throws ServiceException {
        IRoleDao roleDao = new RoleDaoImpl();
        try {
            roleDao.deleteRolePermission(roleId, dbid, flag);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
