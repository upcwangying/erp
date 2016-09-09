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
     * �����½�����
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
            logger.error("������ʼ���½�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("������ʼ���½�����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * �����½�����
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
             * ���ݵ�ǰ�½��·ݣ�����С�ڵ�ǰ�½��·ݵ����ݣ��ҵ��½��·�����һ������ΪA��
             * ��ȡA��yjye(�½����), ���ϵ�ǰ���ݵ�yjye��Ϊ����ĸ������ݵ�yjye��
             *
             */
            double last_yjye = queryLastDataBeforeCurrent(connection, yj.getYjyf());
            double current_yjye = yj.getYjye();

            /**
             * ���ݵ�ǰ�½��·ݣ����ҵ��ڵ�ǰ�½��·ݵ�����ΪB�����û���ҵ��򷵻�0D,
             * ���򷵻�B��yjye(�½����)�����ݵ�ǰ���½����-B���½���Ϊ�½�������
             */
            YJ yj_before = queryCurrentData(connection, yj.getYjyf(), null);
            double yjye_before = yj_before == null ? 0d : yj_before.getYjye();
            double yjye_cy = last_yjye + current_yjye - yjye_before;

            /**
             * ������������
             */
            yj.setYjye(last_yjye + current_yjye);
            updateYJData(connection, yj);

            /**
             * ���ݵ�ǰ�½��·ݣ����Ҵ��ڵ�ǰ�½��·ݵ����ݣ������������ݵ��½���
             */
            updateYJDataAfterCurrent(connection, yj.getYjyf(), yjye_cy);

            JdbcUtil.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback();
            logger.error("�����½�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("�����½�����ʧ�ܣ�" + e.getMessage(), e);
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
            logger.error("�����½�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("�����½�����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ����dbid����ɾ���½�����
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
            logger.error("ɾ���½�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("ɾ���½�����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ��ѯ�����½�����
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
            logger.error("��ѯ�½�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("��ѯ�½�����ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return yjList;
    }

    /**
     * ��ҳ��ѯ�½�����
     *
     * @param page ��ǰҳ��
     * @param rows ÿҳ����
     * @return
     * @throws DAOException
     */
    @Override
    public List<YJ> queryYJDataByPage(int page, int rows) throws DAOException {
        return null;
    }

    /**
     * ��ѯ���һ������
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
     * ��ѯ���һ������
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
     * �޸ĵ�ǰ�½��·�֮����������
     *
     * @param connection
     * @param yjyf
     * @param yjye_cy    �޸ĺ��yjye-�޸�֮ǰ��yjye
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
     * �����½�����
     *
     * @param connection
     * @param yj
     * @throws SQLException
     */
    private void insertYJData2(Connection connection, YJ yj) throws SQLException {
        /**
         * ���ݵ�ǰ�½��·ݣ�����С�ڵ�ǰ�½��·ݵ����ݣ��ҵ��½��·�����һ������ΪA��
         * ��ȡA��yjye(�½����), ���ϵ�ǰ���ݵ�yjye��Ϊ����ĸ������ݵ�yjye��
         *
         */
        double last_yjye = queryLastDataBeforeCurrent(connection, yj.getYjyf());
        double current_yjye = yj.getYjye();

        /**
         * ���ݵ�ǰ�½��·ݣ����ҵ��ڵ�ǰ�½��·ݵ�����ΪB�����û���ҵ��򷵻�0D,
         * ���򷵻�B��yjye(�½����)�����ݵ�ǰ���½����-B���½���Ϊ�½�������
         */
        YJ yj_before = queryCurrentData(connection, yj.getYjyf(), null);
        double yjye_before = yj_before == null ? 0d : yj_before.getYjye();
        double yjye_cy = current_yjye - yjye_before;

        /**
         * ��������
         */
        yj.setYjye(last_yjye + current_yjye);
        insertYJData(connection, yj);

        /**
         * ���ݵ�ǰ�½��·ݣ����Ҵ��ڵ�ǰ�½��·ݵ����ݣ������������ݵ��½���
         */
        updateYJDataAfterCurrent(connection, yj.getYjyf(), yjye_cy);

    }

    /**
     * ����dbid����ɾ���½�����
     *
     * @param connection
     * @param dbid
     * @throws SQLException
     */
    public void deleteYJData2(Connection connection, String dbid) throws SQLException {
        /**
         * ���ݵ�ǰ�½��·ݣ����ҵ��ڵ�ǰ�½��·ݵ�����ΪB�����û���ҵ��򷵻�0D,
         * ���򷵻�B��yjye(�½����)�����ݵ�ǰ���½����-B���½���Ϊ�½�������
         */
        YJ yj_before = queryCurrentData(connection, null, dbid);
        double yjye_before = yj_before == null ? 0d : yj_before.getYjhz() - yj_before.getYjzc();
        double yjye_cy = 0 - yjye_before;

        /**
         * �߼�ɾ��������
         */
        deleteYJData(connection, dbid);

        /**
         * ���ݵ�ǰ�½��·ݣ����Ҵ��ڵ�ǰ�½��·ݵ����ݣ������������ݵ��½���
         */
        updateYJDataAfterCurrent(connection, yj_before.getYjyf(), yjye_cy);

    }

    /**
     * �����½�����
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
     * �����½�����
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
     * ����dbid����ɾ���½�����
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
     * ��ѯ�����½�����
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
