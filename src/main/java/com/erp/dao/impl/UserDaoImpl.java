package com.erp.dao.impl;

import com.erp.dao.IUserDao;
import com.erp.entity.StaffInfo;
import com.erp.exception.DAOException;
import com.erp.util.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
            String sql = "select s.staffid,s.staffcode,s.staffname,s.password,s.telphone,s.is_init," +
                    "s.styleid,style.style,s.create_date,s.update_date,s.last_login_time " +
                    "from staffinfo s left join style style " +
                    "on s.styleid=style.styleid " +
                    "where s.is_del='0' and s.staffcode=? and (0=? or s.staffid!=?) ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, staffCode);
            ps.setInt(2, StringUtil.isEmpty(staffId)?0:1);
            ps.setString(3, StringUtil.isEmpty(staffId)?"-1":staffId);
            rst = ps.executeQuery();
            while (rst.next()) {
                staffInfo = new StaffInfo();
                staffInfo.setStaffId(rst.getLong("staffid"));
                staffInfo.setStaffCode(staffCode);
                staffInfo.setStaffName(rst.getString("staffname"));
                staffInfo.setPassword(rst.getString("password"));
                staffInfo.setTelephone(rst.getString("telphone"));
                staffInfo.setIsInit(rst.getString("is_init"));
                staffInfo.setDelete(true);
                staffInfo.setStyleId(rst.getLong("styleid"));
                staffInfo.setStyle(rst.getString("style"));
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
            String sql = "update staffinfo set last_login_time=getdate() where staffcode=? ";
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
            String sql = "select s.staffid,s.staffcode,s.staffname,s.password,s.telphone,s.is_init," +
                    "s.styleid,style.style,s.create_date,s.update_date,s.last_login_time " +
                    "from staffinfo s left join style style " +
                    "on s.styleid=style.styleid " +
                    "where s.is_del='0' and (0=? or s.staffid=?) " +
                    "order by s.staffid asc ";
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
                staffInfo.setIsInit(rst.getString("is_init"));
                staffInfo.setDelete(false);
                staffInfo.setStyleId(rst.getLong("styleid"));
                staffInfo.setStyle(rst.getString("style"));
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
            String sql = "update staffinfo set is_del='1',update_date=getdate() where staffid=? ";
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
        String sql = "insert into staffinfo(staffcode,staffname,password,telphone,is_init,is_del,styleid,create_date,update_date) " +
                "values (?,?,?,?,'0','0',?,getdate(),getdate()) ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, staffInfo.getStaffCode());
        ps.setString(2, staffInfo.getStaffName());
        ps.setString(3, staffInfo.getPassword());
        ps.setString(4, staffInfo.getTelephone());
        ps.setLong(5, staffInfo.getStyleId());
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
        String sql = "update staffinfo set staffcode=?,staffname=?,password=?,telphone=?,styleid=?,update_date=getdate() " +
                "where staffid=? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, staffInfo.getStaffCode());
        ps.setString(2, staffInfo.getStaffName());
        ps.setString(3, staffInfo.getPassword());
        ps.setString(4, staffInfo.getTelephone());
        ps.setLong(5, staffInfo.getStyleId());
        ps.setLong(6, staffInfo.getStaffId());
        ps.execute();
    }

}
