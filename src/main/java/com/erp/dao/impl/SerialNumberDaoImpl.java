package com.erp.dao.impl;

import com.erp.dao.ISerialNumberDao;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wang_ on 2016-07-25.
 */
public class SerialNumberDaoImpl implements ISerialNumberDao {
    private static Logger logger = Logger.getLogger(SerialNumberDaoImpl.class);

    /**
     * 获得SerialNumber，如果没有获取到，则插入一条新的，否则更新序列号
     * @param name
     * @return
     * @throws DAOException
     */
    @Override
    public long getSerialNumber(String name) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        long serialNumber = -1L;
        try {
            serialNumber = getSerialNumber(connection, name);
//            System.out.println("serialNumber: " + serialNumber);
            if (serialNumber == -1L) {
                insertSerialNumber(connection, name);
                serialNumber = 1L;
            } else {
                updateSerialNumber(connection, name);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("获取自增序列号失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取自增序列号失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return serialNumber;
    }

    /**
     *
     * @param connection
     * @param name
     * @return
     * @throws SQLException
     */
    private long getSerialNumber(Connection connection, String name) throws SQLException {
        long serialNumber = -1L;
        String serial_sql = "select name,value from serialnumber where name=?";
        PreparedStatement ps = connection.prepareStatement(serial_sql);
        ps.setString(1, name);
        ResultSet rst = ps.executeQuery();
        while (rst.next()) {
            serialNumber = rst.getLong("value");
        }
        return serialNumber;
    }

    /**
     *
     * @param connection
     * @param name
     * @throws SQLException
     */
    public void insertSerialNumber(Connection connection, String name) throws SQLException {
        String serial_sql = "insert into serialnumber(name,value,create_date,update_date) values (?, 1, getdate(), getdate()) ";
        PreparedStatement ps = connection.prepareStatement(serial_sql);
        ps.setString(1, name);
        ps.execute();
    }

    /**
     * 更新
     * @param connection
     * @param name
     * @throws SQLException
     */
    public void updateSerialNumber(Connection connection, String name) throws SQLException {
        String serial_sql = "update serialnumber set value=value+1, update_date=getdate() where name=?";
        PreparedStatement ps = connection.prepareStatement(serial_sql);
        ps.setString(1, name);
        ps.execute();
    }
}
