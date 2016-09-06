package com.erp.jdbc.factory;

import com.erp.enums.ResourceXmlEnum;
import com.erp.jdbc.bean.DataSourceBean;
import com.erp.util.XmlUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wang_ on 2016-09-03.
 */
public class DBConnFactory {
    private static Logger logger = Logger.getLogger(DBConnFactory.class);

    private Map<String, DBConnection> dbConnections = new HashMap<>();
    private static String fileSource;
    private static DBConnFactory instance;
    private DBConnection defaultDBConnection;

    public DBConnFactory() {
        this.rebulid();
    }

    public static DBConnFactory getInstance() {
        if (instance == null) {
            instance = new DBConnFactory();
        }
        return instance;
    }

    public static void init() {
        init(ResourceXmlEnum.path_datesource.getValue());
    }

    public static void init(String file) {
        fileSource = file;
        getInstance();
    }

    private void rebulid() {
        Document doc = null;
        if (dbConnections != null) {
            dbConnections.clear();
        }
        try {
            logger.info("--------The data source begin init---------");
            doc = XmlUtil.getDocument(fileSource);
            Element root = doc.getRootElement();
            List connections = root.elements("tb-connection");
            if (connections != null) {
                for (Object conn : connections) {
                    Element connection = (Element) conn;
                    this.loadDataSourceProperties(connection);
                }
            }
            logger.info("--------The data source init success---------");
        } catch (Exception e) {
            logger.error("--------The data source init failure:" +e.getMessage()+ "---------");
            e.printStackTrace();
        }
    }

    private void loadDataSourceProperties(Element connection) {
        String name = connection.attributeValue("name");
        String def = connection.attributeValue("default");
        Properties properties = this.loadProperties(connection);
        DataSourceBean dataSourceBean = new DataSourceBean(properties);
        DBConnection dbConnection = new DBConnection(name, dataSourceBean);
        if (defaultDBConnection == null && Boolean.valueOf(def)) {
            defaultDBConnection = dbConnection;
        }
        dbConnections.put(name, dbConnection);
    }

    /**
     *
     * @param connection
     * @return
     */
    private Properties loadProperties(Element connection) {
        Properties properties = new Properties();
        List pros = connection.elements("property");
        if (pros != null) {
            for (Object pro: pros) {
                Element property = (Element) pro;
                properties.put(property.attributeValue("name"), property.getTextTrim());
            }
        }
        return properties;
    }

    public DBConnection getDBConnection() {
        if (defaultDBConnection != null) {
            return defaultDBConnection;
        }
        return getDBConnection("erp");
    }

    public DBConnection getDBConnection(String name) {
        DBConnection dbConnection = dbConnections.get(name);
        if (dbConnection == null) {
            logger.error("not found tb-connection named '" + name + "'...........");
            return null;
        }
        return dbConnection;
    }

    public Connection getConnection() {
        if (defaultDBConnection != null) {
            return defaultDBConnection.getConnection();
        }
        return getConnection("erp");
    }

    public Connection getConnection(String name) {
        DBConnection dbConnection = dbConnections.get(name);
        if (dbConnection == null) {
            logger.error("not found tb-connection named '" + name + "'...........");
            return null;
        }
        return dbConnection.getConnection();
    }

}
