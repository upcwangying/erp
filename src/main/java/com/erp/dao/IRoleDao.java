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
     * ��ѯ
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
     * ���ӻ��޸�
     * @param role
     * @param flag �޸�ʱ�Ƿ��޸�������룿 �޸���Ϊtrue
     * @throws DAOException
     */
    void insertOrUpdateRole(Role role, boolean flag) throws DAOException;

    /**
     * ���ӽ�ɫ��Ӧ��Ȩ��
     * @param permission
     * @throws DAOException
     */
    void insertRolePermission(Permission permission) throws DAOException;

    /**
     * ɾ��
     * @param roleId
     * @throws DAOException
     */
    void deleteRole(String[] roleId) throws DAOException;

    /**
     * ɾ��Ȩ��
     * @param dbid
     * @param flag ɾ����Ȩ���Ƿ�Ϊ���������Ϊtrue��ɾ���󣬸ý�ɫ���޶�ӦȨ��
     * @throws DAOException
     */
    void deleteRolePermission(String[] dbid, boolean flag) throws DAOException;

}
