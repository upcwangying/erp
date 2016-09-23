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
     * 查询
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
            logger.error("查询权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询权限数据失败：" + e.getMessage(), e);
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
            logger.error("查询权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询权限数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return role;
    }

    /**
     * 增加或修改
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
            logger.error("增加或更新角色数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("增加或更新角色数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 增加角色
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
     * 增加角色对应的权限
     *
     * @param permission
     * @throws DAOException
     */
    @Override
    public void insertRolePermission(Permission permission) throws DAOException {
        // TODO: 2016-09-22  增加权限，更新角色 is_init_permission=1

    }

    /**
     * 修改角色
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
     * 删除
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
            logger.error("删除角色数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除角色数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 删除权限
     *
     * @param dbid
     * @param flag
     * @throws DAOException
     */
    @Override
    public void deleteRolePermission(String[] dbid, boolean flag) throws DAOException {
        // TODO: 2016-09-22 flag为true,更新 is_init_permission=0

    }

    /**
     * 删除角色
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
     * 删除角色对应的权限
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
     * 删除角色对应的权限
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
