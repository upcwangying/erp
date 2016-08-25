package com.erp.dao.impl;

import com.erp.dao.IReportDao;
import com.erp.entity.YW;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import com.erp.util.StringUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016-06-30.
 */
public class ReportDaoImpl implements IReportDao {
    private static Logger logger = Logger.getLogger(ReportDaoImpl.class);

    /**
     * ���������
     * @param ywList
     * @throws DAOException
     */
    public void insertReportData(List<YW> ywList) throws DAOException{
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            insertReportData(connection, ywList);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("�����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ���������
     *
     * @param ywList
     * @throws DAOException
     */
    @Override
    public void updateReportData(List<YW> ywList) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            updateReportData(connection, ywList);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("���������ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("���������ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ���ӻ���������
     * �÷��������������ӣ������ӵ�Ӧ��Ϊ������룬���޸ĵ�Ϊ����ҵ��
     *
     * @param insertList
     * @param updateList
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateReportData(List<YW> insertList, List<YW> updateList) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            insertReportData(connection, insertList);
            updateReportData(connection, updateList);
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("�������������ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("�������������ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ɾ�������
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void deleteReportData(String[] ids) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update t_yw set is_del='1',update_date=getdate() where dbid=? ";
            ps = connection.prepareStatement(sql);
            for (String dbid : ids) {
                ps.setString(1, dbid);
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("ɾ�������ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("ɾ�������ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }


    /**
     * ���������
     * @param connection
     * @param ywList
     * @throws DAOException
     */
    private void insertReportData(Connection connection, List<YW> ywList) throws SQLException {
        String sql = "insert into t_yw (wlbm,gysbm,price,number,staffid,shoppingtime,is_del,create_date,update_date) " +
                "values (?,?,?,?,?,?,'0',getdate(),getdate())";
        PreparedStatement ps = connection.prepareStatement(sql);
        for (YW yw : ywList) {
            ps.setString(1, yw.getWlbm());
            ps.setString(2, yw.getGysbm());
            ps.setDouble(3, yw.getPrice());
            ps.setLong(4, yw.getNumber());
            ps.setLong(5, yw.getStaffId());
            ps.setDate(6, new Date(yw.getShoppingTime().getTime()));
            ps.addBatch();
        }
        ps.executeBatch();

    }

    /**
     * ���������
     *
     * @param connection
     * @param ywList
     * @throws DAOException
     */
    private void updateReportData(Connection connection, List<YW> ywList) throws SQLException {
        String sql = "update t_yw set wlbm=?,gysbm=?,price=?,number=?,staffid=?,shoppingtime=?,update_date=getdate() " +
                "where dbid=? ";
        PreparedStatement ps = connection.prepareStatement(sql);
        for (YW yw : ywList) {
            ps.setString(1, yw.getWlbm());
            ps.setString(2, yw.getGysbm());
            ps.setDouble(3, yw.getPrice());
            ps.setLong(4, yw.getNumber());
            ps.setLong(5, yw.getStaffId());
            ps.setDate(6, new Date(yw.getShoppingTime().getTime()));
            ps.setLong(7, yw.getDbid());
            ps.addBatch();
        }
        ps.executeBatch();

    }

    /**
     * ��ȡ�������ϸ(���dbidΪ���򷵻�ȫ����ϸ����)
     * @param dbid
     * @return
     * @throws DAOException
     */
    @Override
    public List<YW> queryReportDatas(String dbid) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<YW> ywList = new ArrayList<YW>();
        try {
            String sql = "select yw.dbid,yw.wlbm,w.wlmc,yw.gysbm,g.gysmc,yw.price," +
                    "yw.number,yw.staffid,s.staffname,yw.shoppingtime,yw.create_date,yw.update_date " +
                    "from t_yw yw " +
                    "left join staffinfo s on yw.staffid=s.staffid " +
                    "left join t_wl w on yw.wlbm=w.wlbm " +
                    "left join t_gys g on yw.gysbm=g.gysbm " +
                    "where yw.is_del='0' and (0=? or yw.dbid=?) and s.is_del='0' " +
                    "and w.is_del='0' and g.is_del='0' ";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, StringUtil.isEmpty(dbid) ? 0 : 1);
            ps.setInt(2, StringUtil.isEmpty(dbid) ? -1 : Integer.valueOf(dbid));
            rst = ps.executeQuery();
            while (rst.next()) {
                YW yw = new YW();
                yw.setDbid(rst.getLong("dbid"));
                yw.setWlbm(rst.getString("wlbm"));
                yw.setWlmc(rst.getString("wlmc"));
                yw.setGysbm(rst.getString("gysbm"));
                yw.setGysmc(rst.getString("gysmc"));
                yw.setPrice(rst.getDouble("price"));
                yw.setNumber(rst.getLong("number"));
                yw.setStaffId(rst.getLong("staffid"));
                yw.setStaffName(rst.getString("staffname"));
                yw.setShoppingTime(new java.util.Date(rst.getDate("shoppingtime").getTime()));
                yw.setDelete(false);
                yw.setCreateDate(new java.util.Date(rst.getDate("create_date").getTime()));
                yw.setUpdateDate(new java.util.Date(rst.getDate("update_date").getTime()));
                ywList.add(yw);
            }
        } catch (Exception e) {
            logger.error("��ѯ����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("��ѯ����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return ywList;
    }

}
