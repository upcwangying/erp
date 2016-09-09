package com.erp.dao.impl;

import com.erp.dao.IStyleDao;
import com.erp.entity.Style;
import com.erp.exception.DAOException;
import com.erp.util.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016-08-18.
 */
public class StyleDaoImpl implements IStyleDao {
    private static Logger logger = Logger.getLogger(StyleDaoImpl.class);

    /**
     * 获取样式列表
     *
     * @return
     * @throws DAOException
     */
    @Override
    public List<Style> queryStyleList() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Style> styleList = new ArrayList<>();
        try {
            String sql = "select styleid,style,styledesc from " + TableNameConstant.STYLE;
            ps = connection.prepareStatement(sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Style style = new Style();
                style.setStyleId(rst.getLong("styleid"));
                style.setStyle(rst.getString("style"));
                style.setStyleDesc(rst.getString("styledesc"));
                styleList.add(style);
            }
        } catch (Exception e) {
            logger.error("获取界面样式数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取界面样式数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

        return styleList;
    }

    /**
     * 更新样式
     *
     * @param style
     * @throws DAOException
     */
    @Override
    public void updateStyle(Style style) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update " + TableNameConstant.STYLE + " set styledesc=? where styleid=? ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, style.getStyleDesc());
            ps.setLong(2, style.getStyleId());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("更新界面样式数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("更新界面样式数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }
}
