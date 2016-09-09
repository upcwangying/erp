package com.erp.dao.impl;

import com.erp.dao.IJldwDao;
import com.erp.entity.Jldw;
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
            String query_sql = "select jldwid,jldwmc,jldwms,is_del," +
                    "create_staffid,create_date,update_staffid,update_date " +
                    "from " + TableNameConstant.T_JLDW + " where is_del='0' ";
            ps = connection.prepareStatement(query_sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Jldw jldw = new Jldw();
                jldw.setJldwId(rst.getLong("jldwid"));
                jldw.setJldwmc(rst.getString("jldwmc"));
                jldw.setJldwms(rst.getString("jldwms"));
                jldw.setIs_del(rst.getString("is_del"));
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
     * 查询
     *
     * @param jldwmc
     * @param jldwId
     * @return
     * @throws DAOException
     */
    @Override
    public Jldw queryJldwByJldwId(String jldwmc, String jldwId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        Jldw jldw = null;
        try {
            String query_sql = "select jldwid,jldwmc,jldwms,is_del," +
                    "create_staffid,create_date,update_staffid,update_date " +
                    "from " + TableNameConstant.T_JLDW + " where is_del='0' and jldwmc=? and (0=? or jldwid!=?) ";
            ps = connection.prepareStatement(query_sql);
            ps.setString(1, jldwmc);
            ps.setInt(2, StringUtil.isEmpty(jldwId)?0:1);
            ps.setString(3, StringUtil.isEmpty(jldwId)?"-1":jldwId);
            rst = ps.executeQuery();
            while (rst.next()) {
                jldw = new Jldw();
                jldw.setJldwId(rst.getLong("jldwid"));
                jldw.setJldwmc(rst.getString("jldwmc"));
                jldw.setJldwms(rst.getString("jldwms"));
                jldw.setIs_del(rst.getString("is_del"));
                jldw.setCreate_staffId(rst.getLong("create_staffid"));
                jldw.setCreateDate(new Date(rst.getDate("create_date").getTime()));
                jldw.setUpdate_staffId(rst.getLong("update_staffid"));
                jldw.setUpdateDate(new Date(rst.getDate("update_date").getTime()));
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
        return jldw;
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
        String insert_sql = "insert into " + TableNameConstant.T_JLDW + "(jldwmc,jldwms,is_del,create_staffid,create_date,update_staffid,update_date) " +
                "values (?,?,'0',?,getdate(),?,getdate()) ";
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
        String update_sql = "update " + TableNameConstant.T_JLDW + " set jldwmc=?,jldwms=?,update_staffid=?,update_date=getdate() " +
                "where jldwid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, jldw.getJldwmc());
        ps.setString(2, jldw.getJldwms());
        ps.setLong(3, jldw.getUpdate_staffId());
        ps.setLong(4, jldw.getJldwId());
        ps.execute();
    }

    /**
     * 删数据
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
            String sql = "update " + TableNameConstant.T_JLDW + " set is_del='1', update_staffid=?, update_date=getdate() " +
                    "where jldwid=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            for (String id : ids) {
                ps.setLong(1, update_staffId);
                ps.setLong(2, Long.valueOf(id));
                ps.addBatch();
            }
            ps.executeBatch();
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

}
