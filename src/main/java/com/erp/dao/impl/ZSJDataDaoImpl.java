package com.erp.dao.impl;

import com.erp.dao.IZSJDataDao;
import com.erp.entity.Gys;
import com.erp.entity.WL;
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
 * Created by wang_ on 2016-06-29.
 */
public class ZSJDataDaoImpl implements IZSJDataDao {
    private static Logger logger = Logger.getLogger(ZSJDataDaoImpl.class);

    /**
     * 获取物料下拉框数据
     *
     * @return
     */
    public List<WL> queryWlList() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rst = null;
        List<WL> wlList = new ArrayList<WL>();
        try {
            String wl_sql = "select dbid,wlbm,wlmc,wlms,create_date,update_date " +
                    "from t_wl where is_del='0' order by dbid asc";
            preparedStatement = connection.prepareStatement(wl_sql);
            rst = preparedStatement.executeQuery();
            while (rst.next()) {
                WL wl = new WL();
                wl.setWlId(rst.getLong("dbid"));
                wl.setWlbm(rst.getString("wlbm"));
                wl.setWlmc(rst.getString("wlmc"));
                wl.setWlms(rst.getString("wlms"));
                wl.setCreateDate(new Date(rst.getDate("create_date").getTime()));
                wl.setUpdateDate(new Date(rst.getDate("update_date").getTime()));
                wl.setDelete(false);
                wlList.add(wl);
            }
        } catch (Exception e) {
            logger.error("获取物料下拉框数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取物料下拉框数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return wlList;
    }

    /**
     * 增加、更新物料主数据
     *
     * @param wl
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateWl(WL wl) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (wl.getWlId() == 0) {
                insertWl(connection, wl);
            } else {
                updateWl(connection, wl);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入或更新物料数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入或更新物料数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 增加物料主数据
     *
     * @param connection
     * @param wl
     * @throws SQLException
     */
    private void insertWl(Connection connection, WL wl) throws SQLException {
        String wl_sql = "insert into t_wl(wlbm,wlmc,wlms,is_del,create_staffid,create_date,update_staffid,update_date) " +
                "values(?,?,?,'0',?,getdate(),?,getdate())";
        PreparedStatement ps = connection.prepareStatement(wl_sql);
        ps.setString(1, wl.getWlbm());
        ps.setString(2, wl.getWlmc());
        ps.setString(3, wl.getWlms());
        ps.setLong(4, wl.getCreate_staffId());
        ps.setLong(5, wl.getUpdate_staffId());
        ps.execute();
    }

    /**
     * 更新物料主数据
     *
     * @param connection
     * @param wl
     * @throws SQLException
     */
    private void updateWl(Connection connection, WL wl) throws SQLException {
        String wl_sql = "update t_wl set wlmc=?,wlms=?,update_staffid=?,update_date=getdate() where dbid=? ";
        PreparedStatement ps = connection.prepareStatement(wl_sql);
        ps.setString(1, wl.getWlmc());
        ps.setString(2, wl.getWlms());
        ps.setLong(3, wl.getUpdate_staffId());
        ps.setLong(4, wl.getWlId());
        ps.execute();
    }

    /**
     * 删除物料数据
     *
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    @Override
    public void deleteWl(String[] ids, long update_staffId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String wl_sql = "update t_wl set is_del='1',update_staffid=?,update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(wl_sql);
            for (String id : ids) {
                ps.setLong(1, update_staffId);
                ps.setLong(2, Long.valueOf(id));
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除物料数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除物料数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 获取供应商下拉框数据
     *
     * @return
     */
    public List<Gys> queryGysList() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet rst = null;
        List<Gys> gysList = new ArrayList<Gys>();
        try {
            String gys_sql = "select dbid,gysbm,gysmc,gysms,create_date,update_date " +
                    "from t_gys where is_del='0' order by dbid asc";
            preparedStatement = connection.prepareStatement(gys_sql);
            rst = preparedStatement.executeQuery();
            while (rst.next()) {
                Gys gys = new Gys();
                gys.setGysId(rst.getLong("dbid"));
                gys.setGysbm(rst.getString("gysbm"));
                gys.setGysmc(rst.getString("gysmc"));
                gys.setGysms(rst.getString("gysms"));
                gys.setCreateDate(new Date(rst.getDate("create_date").getTime()));
                gys.setUpdateDate(new Date(rst.getDate("update_date").getTime()));
                gys.setDelete(false);
                gysList.add(gys);
            }
        } catch (Exception e) {
            logger.error("获取供应商下拉框数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取供应商下拉框数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return gysList;
    }

    /**
     * 增加供应商主数据
     *
     * @param gys
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateGys(Gys gys) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (gys.getGysId() == 0) {
                insertGys(connection, gys);
            } else {
                updateGys(connection, gys);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入或更新供应商数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入或更新供应商数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 增加供应商主数据
     *
     * @param connection
     * @param gys
     * @throws SQLException
     */
    private void insertGys(Connection connection, Gys gys) throws SQLException {
        String gys_sql = "insert into t_gys(gysbm,gysmc,gysms,is_del,create_staffid,create_date,update_staffid,update_date) " +
                "values(?,?,?,'0',?,getdate(),?,getdate())";
        PreparedStatement ps = connection.prepareStatement(gys_sql);
        ps.setString(1, gys.getGysbm());
        ps.setString(2, gys.getGysmc());
        ps.setString(3, gys.getGysms());
        ps.setLong(4, gys.getCreate_staffId());
        ps.setLong(5, gys.getUpdate_staffId());
        ps.execute();
    }

    /**
     * 更新供应商主数据
     *
     * @param connection
     * @param gys
     * @throws SQLException
     */
    private void updateGys(Connection connection, Gys gys) throws SQLException {
        String gys_sql = "update t_gys set gysmc=?,gysms=?,update_staffid=?,update_date=getdate() where dbid=? ";
        PreparedStatement ps = connection.prepareStatement(gys_sql);
        ps.setString(1, gys.getGysmc());
        ps.setString(2, gys.getGysms());
        ps.setLong(3, gys.getUpdate_staffId());
        ps.setLong(4, gys.getGysId());
        ps.execute();
    }

    /**
     * 删除供应商数据
     *
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    @Override
    public void deleteGys(String[] ids, long update_staffId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String gys_sql = "update t_gys set is_del='1',update_staffid=?,update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(gys_sql);
            for (String id : ids) {
                ps.setLong(1, update_staffId);
                ps.setLong(2, Long.valueOf(id));
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除供应商数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除供应商数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

}
