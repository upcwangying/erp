package com.erp.dao.impl;

import com.erp.dao.IRoleDao;
import com.erp.entity.Permission;
import com.erp.entity.Role;
import com.erp.entity.RolePermission;
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
import java.util.Iterator;
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
                    deleteRolePermissionByRoleId(connection, role.getRoleId()+"");
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
            deleteRolePermissionByRoleId(connection, roleId);
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
     * 查询角色对应的权限
     *
     * @param roleId
     * @throws DAOException
     */
    @Override
    public List<RolePermission> queryRolePermission(String roleId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<RolePermission> rolePermissionList = new ArrayList<>();
        try {
            String query_sql = "select rp.dbid,rp.roleid,rp.permissionid,rp.is_del," +
                    "p.permissioncode,p.permissionname,p.permissiondesc " +
                    "from "+TableNameConstant.T_SYS_ROLE_PERMISSION+" rp " +
                    "left join "+TableNameConstant.T_SYS_PERMISSION +" p " +
                    "on rp.permissionid=p.permissionid "+
                    "where rp.is_del='0' and p.is_del='0' ";
            ps = connection.prepareStatement(query_sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setDbid(rst.getLong("dbid"));
                rolePermission.setRoleId(rst.getLong("roleid"));
                rolePermission.setPermissionId(rst.getLong("permissionid"));
                rolePermission.setPermissionCode(rst.getString("permissioncode"));
                rolePermission.setPermissionName(rst.getString("permissionname"));
                rolePermission.setPermissionDesc(rst.getString("permissiondesc"));
                rolePermission.setIs_del(rst.getString("is_del"));
                rolePermissionList.add(rolePermission);
            }
        } catch (Exception e) {
            logger.error("查询角色权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询角色权限数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return rolePermissionList;
    }

    /**
     * 增加角色对应的权限
     *
     * @param rolePermissionList
     * @throws DAOException
     */
    @Override
    public void insertRolePermission(List<RolePermission> rolePermissionList) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            long roleId = insertRolePermission(connection, rolePermissionList);
            updateRoleByRoleId(connection, roleId, "1");
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("增加角色权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("增加角色权限数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     *
     * @param connection
     * @param rolePermissionList
     * @return roleId
     * @throws SQLException
     */
    private long insertRolePermission(Connection connection, List<RolePermission> rolePermissionList) throws SQLException {
        String insert_sql = "insert into "+TableNameConstant.T_SYS_ROLE_PERMISSION+
                "(roleid,permissionid,is_del) values (?,?,'0')";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        long roleId = 0L;
        for (int i=0;i<rolePermissionList.size();i++) {
            RolePermission rolePermission = rolePermissionList.get(i);
            if (i==0) {
                roleId = rolePermission.getRoleId();
            }
            ps.setLong(1, rolePermission.getRoleId());
            ps.setLong(2, rolePermission.getPermissionId());
            ps.addBatch();
        }
        ps.executeBatch();
        return roleId;
    }

    /**
     * 删除权限
     *
     * @param roleId
     * @param dbid
     * @param flag
     * @throws DAOException
     */
    @Override
    public void deleteRolePermission(String roleId, String[] dbid, boolean flag) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            deleteRolePermissionByDbid(connection, dbid);
            if (flag) {
                updateRoleByRoleId(connection, Long.valueOf(roleId), "0");
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除角色权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除角色权限数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 删除权限
     *
     * @param connection
     * @param dbid
     * @throws SQLException
     */
    private void deleteRolePermissionByDbid(Connection connection, String[] dbid) throws SQLException {
        String delete_sql = "update "+TableNameConstant.T_SYS_ROLE_PERMISSION+" set "+
                "is_del='1' where dbid=? ";
        PreparedStatement ps = connection.prepareStatement(delete_sql);
        for (int i=0;i<dbid.length;i++) {
            ps.setLong(1, Long.valueOf(dbid[i]));
            ps.addBatch();
        }
        ps.executeBatch();
    }

    /**
     * 更新 role 的 is_init_permission字段
     * @param connection
     * @param roleId
     * @param is_init_permission
     * @throws SQLException
     */
    private void updateRoleByRoleId(Connection connection, long roleId, String is_init_permission) throws SQLException {
        String update_sql = "update "+TableNameConstant.T_SYS_ROLE+" set is_init_permission=? " +
                "where roleid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, is_init_permission);
        ps.setLong(2, roleId);
        ps.execute();
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
    private void deleteRolePermissionByRoleId(Connection connection, String[] roleId) throws SQLException {
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
    private void deleteRolePermissionByRoleId(Connection connection, String roleId) throws SQLException {
        String[] roleIds = {roleId};
        deleteRolePermissionByRoleId(connection, roleIds);
    }

}
