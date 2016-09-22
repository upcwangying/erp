package com.erp.dao;

import com.erp.entity.Permission;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-09-21.
 */
public interface IPermissionDao {

    /**
     * ��ѯ
     * @param moduleId
     * @return
     * @throws DAOException
     */
    List<Permission> queryPermissionByModuleId(String moduleId) throws DAOException;

    /**
     *
     * @param permissionCode
     * @param permissionId
     * @return
     * @throws DAOException
     */
    Permission queryPermissionById(String permissionCode, String permissionId) throws DAOException;

    /**
     * ���롢����
     * @param permission
     * @throws DAOException
     */
    void insertOrUpdatePermission(Permission permission) throws DAOException;

    /**
     * ɾ��
     * @param permissionId
     * @throws DAOException
     */
    void deletePermission(String[] permissionId) throws DAOException;

}
