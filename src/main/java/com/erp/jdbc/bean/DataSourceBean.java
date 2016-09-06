package com.erp.jdbc.bean;

import java.util.Properties;

/**
 * Created by wang_ on 2016-09-03.
 */
public class DataSourceBean {

    private String driverClass;
    private String username;
    private String password;
    private String url;
    private boolean encrypt;

    public DataSourceBean(Properties properties) {
        this.driverClass = properties.getProperty("driverClass", "");
        this.username = properties.getProperty("username", "");
        this.password = properties.getProperty("password", "");
        this.url = properties.getProperty("url", "");
        this.encrypt = Boolean.valueOf(properties.getProperty("encrypt", "false"));
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public String toString() {
        return "DataSourceBean{" +
                "driverClass='" + driverClass + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", encrypt=" + encrypt +
                '}';
    }
}
