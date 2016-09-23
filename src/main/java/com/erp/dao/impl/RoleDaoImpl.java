package com.erp.dao.impl;

import com.erp.dao.IRoleDao;
import com.erp.entity.Permission;
import com.erp.entity.Role;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import com.erp.util.StringUtil;
import com.erp.util.TableNameConstant;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016-09-22.
 */
public class RoleDaoImpl implements IRoleDao {
    private static Logger logger = Logger.getLogger(RoleDaoImpl.class);

    /**
     * ��ѯ
     *
     * @return
     * @throws DAOException
     */
    @Override
    public List<Role> queryRole() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Role> roleList = new ArrayList<>();
        try {
            String query_sql = "select r.roleid,r.rolecode,r.rolename,r.roledesc," +
                    "r.groupid,r.is_del as r_is_del,r.is_init_permission,g.groupname," +
                    "p1.permissionids from " + TableNameConstant.T_SYS_ROLE + " r " +
                    "left join " +TableNameConstant.T_SYS_MODULE_GROUP+ " g on r.groupid=g.groupid " +
                    "left join (select count(p.permissionid) as permissionids,p.roleid from "+
                    TableNameConstant.T_SYS_ROLE_PERMISSION+" p where p.is_del='0' group by p.roleid) p1 " +
                    "on r.roleid=p1.roleid where r.is_del='0' and g.is_del='0' ";
            ps = connection.prepareStatement(query_sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Role role = new Role();
                role.setRoleId(rst.getLong("roleid"));
                role.setRoleCode(rst.getString("rolecode"));
                role.setRoleName(rst.getString("rolename"));
                role.setRoleDesc(rst.getString("roledesc"));
                role.setGroupId(rst.getLong("groupid"));
                role.setGroupName(rst.getString("groupname"));
                role.setIs_del(rst.getString("r_is_del"));
                role.setIs_init_permission(rst.getString("is_init_permission"));
                role.setPermissionCount(rst.getInt("permissionids"));
                roleList.add(role);
            }
        } catch (Exception e) {
            logger.error("��ѯȨ������ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("��ѯȨ������ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return roleList;
    }

    /**
     * @param roleCode
     * @param roleId
     * @return
     * @throws DAOException
     */
    @Override
    public Role queryRoleByRoleId(String roleCode, String roleId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        Role role = null;
        try {
            String query_sql = "select r.roleid,r.rolecode,r.rolename,r.roledesc," +
                    "r.groupid,r.is_del as r_is_del,r.is_init_permission,g.groupname " +
                    "from " + TableNameConstant.T_SYS_ROLE + " r " +
                    "left join " +TableNameConstant.T_SYS_MODULE_GROUP+ " g on r.groupid=g.groupid " +
                    "where r.is_del='0' and g.is_del='0' and r.rolecode=? and (0=? or r.roleid!=?) ";
            ps = connection.prepareStatement(query_sql);
            ps.setString(1, roleCode);
            ps.setInt(2, StringUtil.isEmpty(roleId)?0:1);
            ps.setString(3, StringUtil.isEmpty(roleId)?"-1":roleId);
            rst = ps.executeQuery();
            while (rst.next()) {
                role = new Role();
                role.setRoleId(rst.getLong("roleid"));
                role.setRoleCode(rst.getString("rolecode"));
                role.setRoleName(rst.getString("rolename"));
                role.setRoleDesc(rst.getString("roledesc"));
                role.setGroupId(rst.getLong("groupid"));
                role.setGroupName(rst.getString("groupname"));
                role.setIs_del(rst.getString("r_is_del"));
                role.setIs_init_permission(rst.getString("is_init_permission"));
            }
        } catch (Exception e) {
            logger.error("��ѯȨ������ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("��ѯȨ������ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return role;
    }

    /**
     * ���ӻ��޸�
     *
     * @param role
     * @param flag
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateRole(Role role, boolean flag) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (role.getRoleId() == 0) {
                insertRole(connection, role);
            } else {
                updateRole(connection, role, flag);
                if (flag) {
                    deleteRolePermission(connection, role.getRoleId()+"");
                }
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("���ӻ���½�ɫ����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("���ӻ���½�ɫ����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ���ӽ�ɫ
     *
     * @param connection
     * @param role
     * @throws SQLException
     */
    private void insertRole(Connection connection, Role role) throws SQLException {
        String insert_sql = "insert into "+TableNameConstant.T_SYS_ROLE+
                "(rolecode,rolename,roledesc,groupid,is_del,is_init_permission) " +
                "values (?,?,?,?,'0','0')";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        ps.setString(1, role.getRoleCode());
        ps.setString(2, role.getRoleName());
        ps.setString(3, role.getRoleDesc());
        ps.setLong(4, role.getGroupId());
        ps.execute();
    }

    /**
     * ���ӽ�ɫ��Ӧ��Ȩ��
     *
     * @param permission
     * @throws DAOException
     */
    @Override
    public void insertRolePermission(Permission permission) throws DAOException {
        // TODO: 2016-09-22  ����Ȩ�ޣ����½�ɫ is_init_permission=1

    }

    /**
     * �޸Ľ�ɫ
     *
     * @param connection
     * @param role
     * @param flag
     * @throws SQLException
     */
    private void updateRole(Connection connection, Role role, boolean flag) throws SQLException {
        String update_sql = "update "+TableNameConstant.T_SYS_ROLE+" set "+
                "rolecode=?,rolename=?,roledesc=?,groupid=?,is_init_permission=? " +
                "where roleid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, role.getRoleCode());
        ps.setString(2, role.getGroupName());
        ps.setString(3, role.getRoleDesc());
        ps.setLong(4, role.getGroupId());
        ps.setString(5, flag?"0":role.getIs_init_permission());
        ps.setLong(6, role.getRoleId());
        ps.execute();
    }

    /**
     * ɾ��
     *
     * @param roleId
     * @throws DAOException
     */
    @Override
    public void deleteRole(String[] roleId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            deleteRole(connection, roleId);
            deleteRolePermission(connection, roleId);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("ɾ����ɫ����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("ɾ����ɫ����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ɾ��Ȩ��
     *
     * @param dbid
     * @param flag
     * @throws DAOException
     */
    @Override
    public void deleteRolePermission(String[] dbid, boolean flag) throws DAOException {
        // TODO: 2016-09-22 flagΪtrue,���� is_init_permission=0

    }

    /**
     * ɾ����ɫ
     *
     * @param connection
     * @param roleId
     * @throws SQLException
     */
    private void deleteRole(Connection connection, String[] roleId) throws SQLException {
        String delete_sql = "update " + TableNameConstant.T_SYS_ROLE + " set " +
                "is_del='1',is_init_permission='0' where roleid=? ";
        PreparedStatement ps = connection.prepareStatement(delete_sql);
        for (String roleID : roleId) {
            ps.setLong(1, Long.valueOf(roleID));
            ps.addBatch();
        }
        ps.executeBatch();

    }

    /**
     * ɾ����ɫ��Ӧ��Ȩ��
     *
     * @param connection
     * @param roleId
     * @throws SQLException
     */
    private void deleteRolePermission(Connection connection, String[] roleId) throws SQLException {
        String delete_sql = "update " + TableNameConstant.T_SYS_ROLE_PERMISSION + " set " +
                "is_del='1' where roleid=? ";
        PreparedStatement ps = connection.prepareStatement(delete_sql);
        for (String roleID : roleId) {
            ps.setLong(1, Long.valueOf(roleID));
            ps.addBatch();
        }
        ps.executeBatch();

    }

    /**
     * ɾ����ɫ��Ӧ��Ȩ��
     *
     * @param connection
     * @param roleId
     * @throws SQLException
     */
    private void deleteRolePermission(Connection connection, String roleId) throws SQLException {
        String[] roleIds = {roleId};
        deleteRolePermission(connection, roleIds);
    }

}
