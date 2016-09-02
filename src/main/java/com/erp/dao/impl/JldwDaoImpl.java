package com.erp.dao.impl;

import com.erp.dao.IJldwDao;
import com.erp.entity.Jldw;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-09-01.
 */
public class JldwDaoImpl implements IJldwDao {
    private static final Logger logger = Logger.getLogger(JldwDaoImpl.class);

    /**
     * 查询
     *
     * @return
     * @throws DAOException
     */
    @Override
    public List<Jldw> queryJldw() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Jldw> jldwList = new ArrayList<>();
        try {
            String query_sql = "select jldwid,jldwmc,jldwms,is_valid," +
                    "create_staffid,create_date,update_staffid,update_date " +
                    "from t_jldw ";
            ps = connection.prepareStatement(query_sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Jldw jldw = new Jldw();
                jldw.setJldwId(rst.getLong("jldwid"));
                jldw.setJldwmc(rst.getString("jldwmc"));
                jldw.setJldwms(rst.getString("jldwms"));
                jldw.setIs_valid(rst.getString("is_valid"));
                jldw.setCreate_staffId(rst.getLong("create_staffid"));
                jldw.setCreateDate(new Date(rst.getDate("create_date").getTime()));
                jldw.setUpdate_staffId(rst.getLong("update_staffid"));
                jldw.setUpdateDate(new Date(rst.getDate("update_date").getTime()));
                jldwList.add(jldw);
            }
        } catch (Exception e) {
            logger.error("查询计量单位数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询计量单位数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return jldwList;
    }

    /**
     * 插入、更新数据
     *
     * @param jldw
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateJldw(Jldw jldw) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (jldw.getJldwId() == 0) {
                insertJldw(connection, jldw);
            } else {
                updateJldw(connection, jldw);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入或更新计量单位数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入或更新计量单位数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 插入数据
     *
     * @param connection
     * @param jldw
     * @throws DAOException
     */
    private void insertJldw(Connection connection, Jldw jldw) throws SQLException {
        String insert_sql = "insert into t_jldw(jldwmc,jldwms,is_valid,create_staffid,create_date,update_staffid,update_date) " +
                "values (?,?,'1',?,getdate(),?,getdate()) ";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        ps.setString(1, jldw.getJldwmc());
        ps.setString(2, jldw.getJldwms());
        ps.setLong(3, jldw.getCreate_staffId());
        ps.setLong(4, jldw.getUpdate_staffId());
        ps.execute();
    }

    /**
     * 更新数据
     *
     * @param connection
     * @param jldw
     * @throws DAOException
     */
    private void updateJldw(Connection connection, Jldw jldw) throws SQLException {
        String update_sql = "update t_jldw set jldwmc=?,jldwms=?,update_staffid=?,update_date=getdate() " +
                "where jldwid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, jldw.getJldwmc());
        ps.setString(2, jldw.getJldwms());
        ps.setLong(3, jldw.getUpdate_staffId());
        ps.setLong(4, jldw.getJldwId());
        ps.execute();
    }

    /**
     * 恢复数据
     *
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    @Override
    public void resumeJldw(String[] ids, long update_staffId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            updateJldw(connection, ids, update_staffId, false);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("恢复计量单位数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("恢复计量单位数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 删除数据
     *
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    @Override
    public void deleteJldw(String[] ids, long update_staffId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            updateJldw(connection, ids, update_staffId, true);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除计量单位数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除计量单位数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 恢复数据
     *
     * @param connection
     * @param ids
     * @param update_staffId
     * @throws SQLException
     */
    public void updateJldw(Connection connection, String[] ids, long update_staffId, boolean del_flag) throws SQLException {
        String sql = "update t_jldw set is_valid=?, update_staffid=?, update_date=getdate() " +
                "where styleid=? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        for (String id : ids) {
            ps.setString(1, del_flag?"0":"1");
            ps.setLong(2, update_staffId);
            ps.setLong(3, Long.valueOf(id));
            ps.addBatch();
        }
        ps.executeBatch();
    }
}
