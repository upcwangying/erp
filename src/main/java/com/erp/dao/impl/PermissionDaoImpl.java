package com.erp.dao.impl;

import com.erp.dao.IPermissionDao;
import com.erp.entity.Permission;
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
 * Created by wang_ on 2016-09-21.
 */
public class PermissionDaoImpl implements IPermissionDao {
    private static Logger logger = Logger.getLogger(PermissionDaoImpl.class);

    /**
     * 查询
     *
     * @param moduleId
     * @return
     * @throws DAOException
     */
    @Override
    public List<Permission> queryPermissionByModuleId(String moduleId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Permission> permissionList = new ArrayList<>();
        try {
            String query_sql = "select permissionid,moduleid,permissioncode," +
                    "permissionname,permissiondesc,is_del " +
                    "from " + TableNameConstant.T_SYS_PERMISSION + " " +
                    "where is_del='0' and moduleid=? ";
            ps = connection.prepareStatement(query_sql);
            ps.setLong(1, Long.valueOf(moduleId));
            rst = ps.executeQuery();
            while (rst.next()) {
                Permission permission = new Permission();
                permission.setPermissionId(rst.getLong("permissionid"));
                permission.setModuleId(rst.getLong("moduleid"));
                permission.setPermissionCode(rst.getString("permissioncode"));
                permission.setPermissionName(rst.getString("permissionname"));
                permission.setPermissionDesc(rst.getString("permissiondesc"));
                permission.setIs_del(rst.getString("is_del"));
                permissionList.add(permission);
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
        return permissionList;
    }

    /**
     * @param permissionCode
     * @param permissionId
     * @return
     * @throws DAOException
     */
    @Override
    public Permission queryPermissionById(String permissionCode, String permissionId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        Permission permission = null;
        try {
            String query_sql = "select permissionid,moduleid,permissioncode," +
                    "permissionname,permissiondesc,is_del " +
                    "from " + TableNameConstant.T_SYS_PERMISSION + " " +
                    "where is_del='0' and permissioncode=? and (0=? or permissionid!=?) ";
            ps = connection.prepareStatement(query_sql);
            ps.setString(1, permissionCode);
            ps.setInt(2, StringUtil.isEmpty(permissionId)?0:1);
            ps.setString(3, StringUtil.isEmpty(permissionId)?"-1":permissionId);
            rst = ps.executeQuery();
            while (rst.next()) {
                permission = new Permission();
                permission.setPermissionId(rst.getLong("permissionid"));
                permission.setModuleId(rst.getLong("moduleid"));
                permission.setPermissionCode(rst.getString("permissioncode"));
                permission.setPermissionName(rst.getString("permissionname"));
                permission.setPermissionDesc(rst.getString("permissiondesc"));
                permission.setIs_del(rst.getString("is_del"));
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
        return permission;
    }

    /**
     * 插入、更新
     *
     * @param permission
     * @throws DAOException
     */
    @Override
    public void insertOrUpdatePermission(Permission permission) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (permission.getPermissionId() == 0) {
                insertPermission(connection, permission);
            } else {
                updatePermission(connection, permission);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除权限数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 插入
     *
     * @param connection
     * @param permission
     * @throws SQLException
     */
    private void insertPermission(Connection connection, Permission permission) throws SQLException {
        String insert_sql = "insert into " + TableNameConstant.T_SYS_PERMISSION+
                "(moduleid,permissioncode,permissionname,permissiondesc,is_del) " +
                "values (?,?,?,?,'0')";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        ps.setLong(1, permission.getModuleId());
        ps.setString(2, permission.getPermissionCode());
        ps.setString(3, permission.getPermissionName());
        ps.setString(4, permission.getPermissionDesc());
        ps.execute();
    }

    /**
     * 更新
     *
     * @param connection
     * @param permission
     * @throws SQLException
     */
    private void updatePermission(Connection connection, Permission permission) throws SQLException {
        String update_sql = "update " + TableNameConstant.T_SYS_PERMISSION+" set " +
                "permissioncode=?,permissionname=?,permissiondesc=? where permissionid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, permission.getPermissionCode());
        ps.setString(2, permission.getPermissionName());
        ps.setString(3, permission.getPermissionDesc());
        ps.setLong(4, permission.getPermissionId());
        ps.execute();
    }

    /**
     * 删除
     *
     * @param permissionId
     * @throws DAOException
     */
    @Override
    public void deletePermission(String[] permissionId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String delete_sql = "update " + TableNameConstant.T_SYS_PERMISSION + " set " +
                    "is_del='1' where permissionid=? ";
            ps = connection.prepareStatement(delete_sql);
            for (String permissionID : permissionId) {
                ps.setLong(1, Long.valueOf(permissionID));
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除权限数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除权限数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }
}
