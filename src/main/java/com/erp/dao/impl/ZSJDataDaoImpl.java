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
     * 增加物料主数据
     *
     * @param wl
     * @throws DAOException
     */
    @Override
    public void insertWl(WL wl) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String wl_sql = "insert into t_wl(wlbm,wlmc,wlms,is_del,create_date,update_date) " +
                    "values(?,?,?,'0',getdate(),getdate())";
            ps = connection.prepareStatement(wl_sql);
            ps.setString(1, wl.getWlbm());
            ps.setString(2, wl.getWlmc());
            ps.setString(3, wl.getWlms());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入物料数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入物料数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 更新物料主数据
     *
     * @param wl
     * @throws DAOException
     */
    @Override
    public void updateWl(WL wl) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String wl_sql = "update t_wl set wlmc=?,wlms=?,update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(wl_sql);
            ps.setString(1, wl.getWlmc());
            ps.setString(2, wl.getWlms());
            ps.setLong(3, wl.getWlId());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("更新物料数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新物料数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 删除物料数据
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void deleteWl(String[] ids) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String wl_sql = "update t_wl set is_del='1',update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(wl_sql);
            for (String id : ids) {
                ps.setLong(1, Long.valueOf(id));
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
    public void insertGys(Gys gys) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String gys_sql = "insert into t_gys(gysbm,gysmc,gysms,is_del,create_date,update_date) " +
                    "values(?,?,?,'0',getdate(),getdate())";
            ps = connection.prepareStatement(gys_sql);
            ps.setString(1, gys.getGysbm());
            ps.setString(2, gys.getGysmc());
            ps.setString(3, gys.getGysms());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入供应商数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入供应商数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 更新供应商主数据
     *
     * @param gys
     * @throws DAOException
     */
    @Override
    public void updateGys(Gys gys) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String gys_sql = "update t_gys set gysmc=?,gysms=?,update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(gys_sql);
            ps.setString(1, gys.getGysmc());
            ps.setString(2, gys.getGysms());
            ps.setLong(3, gys.getGysId());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("更新供应商数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新供应商数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 删除供应商数据
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void deleteGys(String[] ids) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String gys_sql = "update t_gys set is_del='1',update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(gys_sql);
            for (String id : ids) {
                ps.setLong(1, Long.valueOf(id));
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
