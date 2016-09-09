package com.erp.dao.impl;

import com.erp.dao.IYJDao;
import com.erp.entity.YJ;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
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
 * Created by wang_ on 2016-08-04.
 */
public class YJDaoImpl implements IYJDao {
    private static final Logger logger = Logger.getLogger(YJDaoImpl.class);

    /**
     * 插入月结数据
     *
     * @param yj
     * @throws DAOException
     */
    @Override
    public void insertYJData(YJ yj) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            insertYJData2(connection, yj);
            JdbcUtil.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback();
            logger.error("插入或初始化月结数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入或初始化月结数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 更新月结数据
     *
     * @param yj
     * @throws DAOException
     */
    @Override
    public void updateYJData(YJ yj) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            /**
             * 根据当前月结月份，查找小于当前月结月份的数据，找到月结月份最大的一条数据为A，
             * 获取A的yjye(月结余额), 加上当前数据的yjye即为插入的该条数据的yjye。
             *
             */
            double last_yjye = queryLastDataBeforeCurrent(connection, yj.getYjyf());
            double current_yjye = yj.getYjye();

            /**
             * 根据当前月结月份，查找等于当前月结月份的数据为B，如果没有找到则返回0D,
             * 否则返回B的yjye(月结余额)，根据当前的月结余额-B的月结余额即为月结余额差异
             */
            YJ yj_before = queryCurrentData(connection, yj.getYjyf(), null);
            double yjye_before = yj_before == null ? 0d : yj_before.getYjye();
            double yjye_cy = last_yjye + current_yjye - yjye_before;

            /**
             * 更新数据数据
             */
            yj.setYjye(last_yjye + current_yjye);
            updateYJData(connection, yj);

            /**
             * 根据当前月结月份，查找大于当前月结月份的数据，更新所有数据的月结余额。
             */
            updateYJDataAfterCurrent(connection, yj.getYjyf(), yjye_cy);

            JdbcUtil.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback();
            logger.error("更新月结数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新月结数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * @param dbid
     * @param yj
     * @throws DAOException
     */
    @Override
    public void deleteOrInsert(String dbid, YJ yj) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            deleteYJData2(connection, dbid);
            insertYJData2(connection, yj);
            JdbcUtil.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback();
            logger.error("更新月结数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新月结数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 根据dbid主键删除月结数据
     *
     * @param dbid
     * @throws DAOException
     */
    @Override
    public void deleteYJData(String dbid) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            deleteYJData2(connection, dbid);
            JdbcUtil.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback();
            logger.error("删除月结数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除月结数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 查询所有月结数据
     *
     * @param yjyf
     * @return
     * @throws DAOException
     */
    @Override
    public List<YJ> queryYJData(String yjyf) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        List<YJ> yjList = null;
        try {
            yjList = queryYJData(connection, yjyf, null);
        } catch (SQLException e) {
            logger.error("查询月结数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询月结数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return yjList;
    }

    /**
     * 分页查询月结数据
     *
     * @param page 当前页数
     * @param rows 每页行数
     * @return
     * @throws DAOException
     */
    @Override
    public List<YJ> queryYJDataByPage(int page, int rows) throws DAOException {
        return null;
    }

    /**
     * 查询最后一条数据
     *
     * @param connection
     * @param yjyf
     * @return
     * @throws SQLException
     */
    private double queryLastDataBeforeCurrent(Connection connection, String yjyf) throws SQLException {
        double yjye = 0D;
        String sql = "select top 1 yjyf,yjzc,yjhz,yjye,yjlx from " +
                TableNameConstant.T_YJ + " where is_del='0' and convert(varchar(7), yjyf,120)<? order by yjyf desc";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, yjyf);
        ResultSet rst = ps.executeQuery();
        if (rst.next()) {
            yjye = rst.getDouble("yjye");
        }

        return yjye;
    }

    /**
     * 查询最后一条数据
     *
     * @param connection
     * @param yjyf
     * @param dbid
     * @return
     * @throws DAOException
     */
    private YJ queryCurrentData(Connection connection, String yjyf, String dbid) throws SQLException {
        List<YJ> yjList = queryYJData(connection, yjyf, dbid);
        YJ yj = null;
        if (yjList != null && yjList.size() > 0) {
            yj = yjList.get(0);
        }
        return yj;
    }

    /**
     * 修改当前月结月份之后所有数据
     *
     * @param connection
     * @param yjyf
     * @param yjye_cy    修改后的yjye-修改之前的yjye
     * @return
     * @throws SQLException
     */
    private void updateYJDataAfterCurrent(Connection connection, String yjyf, double yjye_cy) throws SQLException {
        List<YJ> yjList = new ArrayList<>();
        String sql = "update "+TableNameConstant.T_YJ+" set yjye=yjye+?, update_date=getdate() " +
                "where is_del='0' and convert(varchar(7), yjyf,120)>? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, yjye_cy);
        ps.setString(2, yjyf);
        ps.execute();

    }

    /**
     * 插入月结数据
     *
     * @param connection
     * @param yj
     * @throws SQLException
     */
    private void insertYJData2(Connection connection, YJ yj) throws SQLException {
        /**
         * 根据当前月结月份，查找小于当前月结月份的数据，找到月结月份最大的一条数据为A，
         * 获取A的yjye(月结余额), 加上当前数据的yjye即为插入的该条数据的yjye。
         *
         */
        double last_yjye = queryLastDataBeforeCurrent(connection, yj.getYjyf());
        double current_yjye = yj.getYjye();

        /**
         * 根据当前月结月份，查找等于当前月结月份的数据为B，如果没有找到则返回0D,
         * 否则返回B的yjye(月结余额)，根据当前的月结余额-B的月结余额即为月结余额差异
         */
        YJ yj_before = queryCurrentData(connection, yj.getYjyf(), null);
        double yjye_before = yj_before == null ? 0d : yj_before.getYjye();
        double yjye_cy = current_yjye - yjye_before;

        /**
         * 插入数据
         */
        yj.setYjye(last_yjye + current_yjye);
        insertYJData(connection, yj);

        /**
         * 根据当前月结月份，查找大于当前月结月份的数据，更新所有数据的月结余额。
         */
        updateYJDataAfterCurrent(connection, yj.getYjyf(), yjye_cy);

    }

    /**
     * 根据dbid主键删除月结数据
     *
     * @param connection
     * @param dbid
     * @throws SQLException
     */
    public void deleteYJData2(Connection connection, String dbid) throws SQLException {
        /**
         * 根据当前月结月份，查找等于当前月结月份的数据为B，如果没有找到则返回0D,
         * 否则返回B的yjye(月结余额)，根据当前的月结余额-B的月结余额即为月结余额差异
         */
        YJ yj_before = queryCurrentData(connection, null, dbid);
        double yjye_before = yj_before == null ? 0d : yj_before.getYjhz() - yj_before.getYjzc();
        double yjye_cy = 0 - yjye_before;

        /**
         * 逻辑删除该数据
         */
        deleteYJData(connection, dbid);

        /**
         * 根据当前月结月份，查找大于当前月结月份的数据，更新所有数据的月结余额。
         */
        updateYJDataAfterCurrent(connection, yj_before.getYjyf(), yjye_cy);

    }

    /**
     * 插入月结数据
     *
     * @param connection
     * @param yj
     * @throws SQLException
     */
    private void insertYJData(Connection connection, YJ yj) throws SQLException {
        String sql = "insert into "+TableNameConstant.T_YJ+" (yjyf,yjzc,yjhz,yjye,staffid,is_del,yjlx,create_date,update_date) " +
                "values (?,?,?,?,?,'0',?,getdate(),getdate()) ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, yj.getYjyf());
        ps.setDouble(2, yj.getYjzc());
        ps.setDouble(3, yj.getYjhz());
        ps.setDouble(4, yj.getYjye());
        ps.setLong(5, yj.getStaffId());
        ps.setString(6, yj.getYjlx());
        ps.execute();
    }

    /**
     * 更新月结数据
     *
     * @param connection
     * @param yj
     * @throws SQLException
     */
    private void updateYJData(Connection connection, YJ yj) throws SQLException {
        String sql = "update "+TableNameConstant.T_YJ+" set yjyf=?,yjzc=?,yjhz=?,yjye=?,staffid=?,update_date=getdate() " +
                "where dbid=? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, yj.getYjyf());
        ps.setDouble(2, yj.getYjzc());
        ps.setDouble(3, yj.getYjhz());
        ps.setDouble(4, yj.getYjye());
        ps.setLong(5, yj.getStaffId());
        ps.setLong(6, yj.getDbid());
        ps.execute();

    }

    /**
     * 根据dbid主键删除月结数据
     *
     * @param connection
     * @param dbid
     * @throws SQLException
     */
    private void deleteYJData(Connection connection, String dbid) throws SQLException {
        String sql = "update "+TableNameConstant.T_YJ+" set is_del='1',update_date=getdate() where dbid=? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, Long.valueOf(dbid));
        ps.execute();

    }

    /**
     * 查询所有月结数据
     *
     * @param connection
     * @param yjyf
     * @return
     * @throws SQLException
     */
    private List<YJ> queryYJData(Connection connection, String yjyf, String dbid) throws SQLException {
        List<YJ> yjList = new ArrayList<>();
        String sql = "select yj.dbid,yj.yjyf,yj.yjzc,yj.yjhz,yj.yjye,yj.staffid," +
                "yj.yjlx,yj.create_date,yj.update_date,s.staffname " +
                "from "+TableNameConstant.T_YJ+" yj left join "+TableNameConstant.STAFFINFO+" s " +
                "on yj.staffid=s.staffid " +
                "where yj.is_del='0' and s.is_del='0' " +
                "and (0=? or convert(varchar(7), yj.yjyf,120)=?) " +
                "and (0=? or yj.dbid=?) " +
                "order by yj.yjyf asc";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, yjyf == null ? 0 : 1);
        ps.setString(2, yjyf == null ? "0000-00" : yjyf);
        ps.setInt(3, dbid == null ? 0 : 1);
        ps.setString(4, dbid == null ? "0" : dbid);
        ResultSet rst = ps.executeQuery();
        while (rst.next()) {
            YJ yj = new YJ();
            yj.setDbid(rst.getLong("dbid"));
            yj.setYjyf(rst.getString("yjyf"));
            yj.setYjzc(rst.getDouble("yjzc"));
            yj.setYjhz(rst.getDouble("yjhz"));
            yj.setYjye(rst.getDouble("yjye"));
            yj.setStaffId(rst.getLong("staffid"));
            yj.setStaffName(rst.getString("staffname"));
            yj.setDelete(false);
            yj.setYjlx(rst.getString("yjlx"));
            yj.setCreateDate(new Date(rst.getDate("create_date").getTime()));
            yj.setUpdateDate(new Date(rst.getDate("update_date").getTime()));
            yjList.add(yj);
        }

        return yjList;
    }

}
