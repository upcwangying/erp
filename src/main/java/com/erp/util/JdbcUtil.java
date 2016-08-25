package com.erp.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC������
 */
public class JdbcUtil {
	private static Logger logger = Logger.getLogger(JdbcUtil.class);
	private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();
	
	private static Connection getConn() {
		Connection connection = null;
		try {
			String driverClassName = SystemConfig.getValue("driver.classname", "");
			String url = SystemConfig.getValue("url", "");
			String username = SystemConfig.getValue("username", "");
			String password = SystemConfig.getValue("password", "");
			String encrypt = SystemConfig.getValue("encrypt", "false");
			if (Boolean.valueOf(encrypt)) {
				password = new String(AESEncrypt.getInstance().decrypt(ByteUtils.string2ByteArray(password)));
			}

			if (driverClassName.equals("") || url.equals("") || username.equals("") || password.equals("")) {
				String msg = "���ݿ�������Ϣ���������޷��������ݿ����ӣ��뵽system-config�����ļ����������Ϣ......";
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

	public static Connection getConnection() {
		Connection connection = connections.get();
		if (connection == null) {
			connection = getConn();
			connections.set(connection);
		}
		return connection;
		
	}
	
	public static void close() {
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

	public static void openTransactionIsolation(int level) {
		try {
			Connection connection = getConnection();
			connection.setTransactionIsolation(level);
		} catch (SQLException e) {
			logger.error("����������뼶��ʧ�ܣ�" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static void beginTranaction() {
		try {
			Connection connection = getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("��������ʧ�ܣ�" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static void commit() {
		try {
			Connection connection = getConnection();
			connection.commit();
		} catch (SQLException e) {
			logger.error("�ύ����ʧ�ܣ�" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static void rollback() {
		try {
			Connection connection = getConnection();
			connection.rollback();
		} catch (SQLException e) {
			logger.error("�ع�����ʧ�ܣ�" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
