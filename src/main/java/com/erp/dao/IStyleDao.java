package com.erp.dao;

import com.erp.entity.Style;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-18.
 */
public interface IStyleDao {

    /**
     * ��ȡ��ʽ�б�
     * @return
     * @throws DAOException
     */
    List<Style> queryStyleList() throws DAOException;

    /**
     * ������ʽ
     * @param style
     * @throws DAOException
     */
    void updateStyle(Style style) throws DAOException;
}
