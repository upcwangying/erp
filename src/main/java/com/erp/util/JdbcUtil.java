package com.erp.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC工具类
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
				String msg = "数据库配置信息不完整，无法建立数据库连接！请到system-config配置文件检查配置信息......";
				logger.error(msg);
			}
			Class.forName(driverClassName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			logger.error("获取数据库连接失败！！！！！", e);
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
				logger.error("关闭连接失败：" + e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	public static void openTransactionIsolation(int level) {
		try {
			Connection connection = getConnection();
			connection.setTransactionIsolation(level);
		} catch (SQLException e) {
			logger.error("设置事务隔离级别失败：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static void beginTranaction() {
		try {
			Connection connection = getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("开启事务失败：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static void commit() {
		try {
			Connection connection = getConnection();
			connection.commit();
		} catch (SQLException e) {
			logger.error("提交事务失败：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public static void rollback() {
		try {
			Connection connection = getConnection();
			connection.rollback();
		} catch (SQLException e) {
			logger.error("回滚事务失败：" + e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
