package com.erp.dao.impl;

import com.erp.dao.IUserDao;
import com.erp.entity.Permission;
import com.erp.entity.RolePermission;
import com.erp.entity.StaffInfo;
import com.erp.exception.DAOException;
import com.erp.util.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wang_ on 2016-06-28.
 */
public class UserDaoImpl implements IUserDao {
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    /**
     * 根据staffCode查找用户信息
     *
     * @param staffCode
     * @return
     * @throws Exception
     */
    public StaffInfo queryStaffByCode(String staffId, String staffCode) throws DAOException {
        if (StringUtil.isEmpty(staffCode)) return null;
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        StaffInfo staffInfo = null;
        try {
            String sql = "select s.staffid,s.staffcode,s.staffname,s.password,s.telphone,s.stafftype,s.styleid,style.style," +
                    "s.roleid,role.rolename,g.modules,s.create_date,s.update_date,s.last_login_time " +
                    "from "+TableNameConstant.STAFFINFO+" s " +
                    "left join "+TableNameConstant.STYLE+" style on s.styleid=style.styleid " +
                    "left join "+TableNameConstant.T_SYS_ROLE+" role on s.roleid=role.roleid and role.is_del='0' " +
                    "left join "+TableNameConstant.T_SYS_MODULE_GROUP+" g on role.groupid=g.groupid and g.is_del='0' " +
                    "where s.is_del='0' and s.staffcode=? and (0=? or s.staffid!=?) ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            ps.setInt(2, StringUtil.isEmpty(staffId)?0:1);
            ps.setString(3, StringUtil.isEmpty(staffId)?"-1":staffId);
            rst = ps.executeQuery();
            while (rst.next()) {
                staffInfo = new StaffInfo();
                long roleId = rst.getLong("roleid");
                Set<Permission> permissionSet = queryPermission(connection, roleId);
                staffInfo.setStaffId(rst.getLong("staffid"));
                staffInfo.setStaffCode(staffCode);
                staffInfo.setStaffName(rst.getString("staffname"));
                staffInfo.setPassword(rst.getString("password"));
                staffInfo.setTelephone(rst.getString("telphone"));
                staffInfo.setStaffType(rst.getString("stafftype"));
                staffInfo.setDelete(true);
                staffInfo.setStyleId(rst.getLong("styleid"));
                staffInfo.setStyle(rst.getString("style"));
                staffInfo.setRoleId(roleId);
                staffInfo.setRoleName(rst.getString("rolename"));
                staffInfo.setModules(rst.getString("modules"));
                staffInfo.setPermissions(permissionSet);
                staffInfo.setCreateDate(rst.getDate("create_date"));
                staffInfo.setUpdateDate(rst.getDate("update_date"));
                staffInfo.setLastLoginTime(rst.getDate("last_login_time"));
            }
        } catch (Exception e) {
            logger.error("查找用户信息失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查找用户信息失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return staffInfo;
    }

    /**
     * 更新STAFF
     *
     * @param staffCode
     * @throws DAOException
     */
    @Override
    public void updateStaffByCode(String staffCode) throws DAOException {
        if (staffCode == null || "".equals(staffCode)) return;
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update "+TableNameConstant.STAFFINFO+" set last_login_time=getdate() where staffcode=? ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("更新用户信息失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新用户信息失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 查询用户信息，如果dbid为空，则查询全部用户
     *
     * @param dbid
     * @return
     * @throws DAOException
     */
    @Override
    public List<StaffInfo> queryUserData(String dbid) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<StaffInfo> staffInfoList = new ArrayList<StaffInfo>();
        try {
            String sql = "select s.staffid,s.staffcode,s.staffname,s.password,s.telphone,s.stafftype,s.styleid,style.style," +
                    "s.roleid,role.rolename,g.modules,s.create_date,s.update_date,s.last_login_time " +
                    "from "+TableNameConstant.STAFFINFO+" s " +
                    "left join "+TableNameConstant.STYLE+" style on s.styleid=style.styleid " +
                    "left join "+TableNameConstant.T_SYS_ROLE+" role on s.roleid=role.roleid and role.is_del='0' " +
                    "left join "+TableNameConstant.T_SYS_MODULE_GROUP+" g on role.groupid=g.groupid and g.is_del='0' " +
                    "where s.is_del='0' and (0=? or s.staffid=?) order by s.staffid asc ";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, StringUtil.isEmpty(dbid) ? 0 : 1);
            ps.setString(2, StringUtil.isEmpty(dbid) ? "-1" : dbid);
            rst = ps.executeQuery();
            while (rst.next()) {
                StaffInfo staffInfo = new StaffInfo();
                staffInfo.setStaffId(rst.getLong("staffid"));
                staffInfo.setStaffCode(rst.getString("staffcode"));
                staffInfo.setStaffName(rst.getString("staffname"));
                staffInfo.setPassword(rst.getString("password"));
                staffInfo.setTelephone(rst.getString("telphone"));
                staffInfo.setStaffType(rst.getString("stafftype"));
                staffInfo.setDelete(false);
                staffInfo.setStyleId(rst.getLong("styleid"));
                staffInfo.setStyle(rst.getString("style"));
                staffInfo.setRoleId(rst.getLong("roleid"));
                staffInfo.setRoleName(rst.getString("rolename"));
                staffInfo.setModules(rst.getString("modules"));
                staffInfo.setCreateDate(DateUtil.getDateByType(rst.getString("create_date")));
                staffInfo.setUpdateDate(DateUtil.getDateByType(rst.getString("update_date")));
                if (!StringUtil.isEmpty(rst.getString("last_login_time"))) {
                    staffInfo.setLastLoginTime(DateUtil.getDateByType(rst.getString("last_login_time")));
                }

                staffInfoList.add(staffInfo);
            }
        } catch (Exception e) {
            logger.error("获取用户信息数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取用户信息数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

        return staffInfoList;
    }

    /**
     * 增加用户
     *
     * @param staffInfo
     * @throws DAOException
     */
    @Override
    public void insertUserData(StaffInfo staffInfo) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            insertUserData(connection, staffInfo);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入用户信息失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入用户信息失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 更新用户
     *
     * @param staffInfo
     * @throws DAOException
     */
    @Override
    public void updateUserData(StaffInfo staffInfo) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            updateUserData(connection, staffInfo);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("更新用户信息失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新用户信息失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 删除用户
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void deleteUserData(String[] ids) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update "+TableNameConstant.STAFFINFO+" set is_del='1',update_date=getdate() where staffid=? ";
            ps = connection.prepareStatement(sql);
            for (String id : ids) {
                ps.setString(1, id);
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除用户信息数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除用户信息数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

    }

    /**
     * 增加用户
     *
     * @param connection
     * @param staffInfo
     * @throws Exception
     */
    private void insertUserData(Connection connection, StaffInfo staffInfo) throws Exception {
        StringBuffer insert_sql = new StringBuffer("insert into ");
        insert_sql.append(TableNameConstant.STAFFINFO).append("(staffcode,staffname,password,telphone,stafftype,is_del,styleid,");
        if (staffInfo.getRoleId() != 0) {
            insert_sql.append("roleid,");
        }
        insert_sql.append("create_date,update_date) ");
        insert_sql.append("values (?,?,?,?,?,'0',?,");
        if (staffInfo.getRoleId() != 0) {
            insert_sql.append("?,");
        }
        insert_sql.append("getdate(),getdate()) ");
        PreparedStatement ps = connection.prepareStatement(insert_sql.toString());
        ps.setString(1, staffInfo.getStaffCode());
        ps.setString(2, staffInfo.getStaffName());
        ps.setString(3, staffInfo.getPassword());
        ps.setString(4, staffInfo.getTelephone());
        ps.setString(5, staffInfo.getStaffType());
        ps.setLong(6, staffInfo.getStyleId());
        if (staffInfo.getRoleId() != 0) {
            ps.setLong(7, staffInfo.getRoleId());
        }
        ps.execute();
    }

    /**
     * 更新用户
     *
     * @param connection
     * @param staffInfo
     * @throws Exception
     */
    private void updateUserData(Connection connection, StaffInfo staffInfo) throws Exception {
        StringBuffer update_sql = new StringBuffer("update ");
        update_sql.append(TableNameConstant.STAFFINFO).append(" set staffcode=?,staffname=?,password=?,telphone=?,stafftype=?,styleid=?,");
        if (staffInfo.getRoleId() != 0) {
            update_sql.append("roleid=?,");
        }
        update_sql.append("update_date=getdate() where staffid=? ");
        PreparedStatement ps = connection.prepareStatement(update_sql.toString());
        ps.setString(1, staffInfo.getStaffCode());
        ps.setString(2, staffInfo.getStaffName());
        ps.setString(3, staffInfo.getPassword());
        ps.setString(4, staffInfo.getTelephone());
        ps.setString(5, staffInfo.getStaffType());
        ps.setLong(6, staffInfo.getStyleId());
        if (staffInfo.getRoleId() != 0) {
            ps.setLong(7, staffInfo.getRoleId());
            ps.setLong(8, staffInfo.getStaffId());
        } else {
            ps.setLong(7, staffInfo.getStaffId());
        }
        ps.execute();
    }

    /**
     * 查询角色对应的权限
     *
     * @param connection
     * @param roleId
     * @throws SQLException
     */
    private Set<Permission> queryPermission(Connection connection, long roleId) throws SQLException {
        Set<Permission> permissionSet = new HashSet<>();
        String query_sql = "select rp.dbid,rp.roleid,rp.permissionid,rp.is_del," +
                "p.permissioncode,p.permissionname,p.permissiondesc " +
                "from " + TableNameConstant.T_SYS_ROLE_PERMISSION + " rp " +
                "left join " + TableNameConstant.T_SYS_PERMISSION + " p  " +
                "on rp.permissionid=p.permissionid and p.is_del='0' " +
                "where rp.is_del='0' and rp.roleid=? ";
        PreparedStatement ps = connection.prepareStatement(query_sql);
        ps.setLong(1, roleId);
        ResultSet rst = ps.executeQuery();
        while (rst.next()) {
            Permission permission = new Permission();
            permission.setPermissionId(rst.getLong("permissionid"));
            permission.setPermissionCode(rst.getString("permissioncode"));
            permission.setPermissionName(rst.getString("permissionname"));
            permission.setPermissionDesc(rst.getString("permissiondesc"));
            permissionSet.add(permission);
        }

        return permissionSet;
    }

}
