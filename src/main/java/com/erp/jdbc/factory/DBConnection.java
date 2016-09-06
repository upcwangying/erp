package com.erp.jdbc.factory;

import com.erp.jdbc.bean.DataSourceBean;
import com.erp.util.AESEncrypt;
import com.erp.util.ByteUtils;
import com.erp.util.JdbcUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wang_ on 2016-09-03.
 */
public class DBConnection {
    private static Logger logger = Logger.getLogger(JdbcUtil.class);
    private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();
    private String name;
    private DataSourceBean dataSourceBean;

    public DBConnection(String name) {
        this.name = name;
    }

    public DBConnection(String name, DataSourceBean dataSourceBean) {
        this.name = name;
        this.dataSourceBean = dataSourceBean;
    }

    private Connection getConn() {
        Connection connection = null;
        try {
            String driverClassName = dataSourceBean.getDriverClass();
            String url = dataSourceBean.getUrl();
            String username = dataSourceBean.getUsername();
            String password = dataSourceBean.getPassword();
            boolean encrypt = dataSourceBean.isEncrypt();
            if (encrypt) {
                password = new String(AESEncrypt.getInstance().decrypt(ByteUtils.string2ByteArray(password)));
            }

            if (driverClassName.equals("") || url.equals("") || username.equals("") || password.equals("")) {
                String msg = "tb-connection named: " + name + " ������Ϣ������,�޷��������ݿ�����!�뵽datasource�м��������Ϣ......";
                logger.error(msg);
            }
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logger.error("��ȡ���ݿ�����ʧ�ܣ���������", e);
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection() {
        Connection connection = connections.get();
        if (connection == null) {
            connection = getConn();
            connections.set(connection);
        }
        return connection;

    }

    public void close() {
        Connection connection = connections.get();
        if(connection != null){
            try {
                connection.close();
                connections.set(null);
            } catch (SQLException e) {
                logger.error("�ر�����ʧ�ܣ�" + e.getMessage(), e);
                e.printStackTrace();
            }
        }
    }

    public void openTransactionIsolation(int level) {
        try {
            Connection connection = getConnection();
            connection.setTransactionIsolation(level);
        } catch (SQLException e) {
            logger.error("����������뼶��ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public void beginTranaction() {
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("��������ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            Connection connection = getConnection();
            connection.commit();
        } catch (SQLException e) {
            logger.error("�ύ����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            Connection connection = getConnection();
            connection.rollback();
        } catch (SQLException e) {
            logger.error("�ع�����ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public DataSourceBean getDataSourceBean() {
        return dataSourceBean;
    }

    public void setDataSourceBean(DataSourceBean dataSourceBean) {
        this.dataSourceBean = dataSourceBean;
    }
}
