package com.erp.listener;

import com.erp.chart.factory.ChartBeanFactory;
import com.erp.util.JdbcUtil;
import com.erp.util.SystemConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wang_ on 2016-08-04.
 */
@WebListener
public class StartUpListener implements ServletContextListener {

    public StartUpListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        /**
         * 初始化配置文件
         */
        SystemConfig.init();

        /**
         * 初始化ChartBean
         */
        ChartBeanFactory.init();

        /**
         *
         */
        ServletContext context = servletContextEvent.getServletContext();
        String initYJ = initYJ();
        context.setAttribute("initYJ", initYJ);

    }

    /**
     *
     * @return
     */
    private String initYJ() {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        // 0为未初始化，1为初始化
        String initYJ = "0";
        String sql = "select dbid,yjyf,yjzc,yjhz,yjye,staffid, " +
                "yjlx,create_date,update_date " +
                "from t_yj where is_del='0' and yjlx='1' ";
        try {
            ps = connection.prepareStatement(sql);
            rst = ps.executeQuery();
            if (rst.next()) {
                initYJ = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return initYJ;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
