package com.erp.dao;

import com.erp.entity.Style;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-18.
 */
public interface IStyleDao {

    /**
     * 获取样式列表
     * @return
     * @throws DAOException
     */
    List<Style> queryStyleList() throws DAOException;

    /**
     * 更新样式
     * @param style
     * @throws DAOException
     */
    void updateStyle(Style style) throws DAOException;
}
