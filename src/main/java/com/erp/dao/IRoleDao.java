package com.erp.dao;

import com.erp.entity.Permission;
import com.erp.entity.Role;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-09-22.
 */
public interface IRoleDao {

    /**
     * 查询
     * @return
     * @throws DAOException
     */
    List<Role> queryRole() throws DAOException;

    /**
     *
     * @param roleCode
     * @param roleId
     * @return
     * @throws DAOException
     */
    Role queryRoleByRoleId(String roleCode, String roleId) throws DAOException;

    /**
     * 增加或修改
     * @param role
     * @param flag 修改时是否修改了组编码？ 修改了为true
     * @throws DAOException
     */
    void insertOrUpdateRole(Role role, boolean flag) throws DAOException;

    /**
     * 增加角色对应的权限
     * @param permission
     * @throws DAOException
     */
    void insertRolePermission(Permission permission) throws DAOException;

    /**
     * 删除
     * @param roleId
     * @throws DAOException
     */
    void deleteRole(String[] roleId) throws DAOException;

    /**
     * 删除权限
     * @param dbid
     * @param flag 删除的权限是否为最后几条，若为true：删除后，该角色下无对应权限
     * @throws DAOException
     */
    void deleteRolePermission(String[] dbid, boolean flag) throws DAOException;

}
