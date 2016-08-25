package com.erp.dao;

import com.erp.exception.DAOException;

/**
 * Created by wang_ on 2016-07-25.
 */
public interface ISerialNumberDao {

    /**
     * »ñµÃSerialNumber
     * @param name
     * @return
     * @throws DAOException
     */
    long getSerialNumber(String name) throws DAOException;

}
