package com.erp.service;

import com.erp.dao.ISerialNumberDao;
import com.erp.dao.impl.SerialNumberDaoImpl;
import com.erp.exception.DAOException;

/**
 * Created by wang_ on 2016-07-26.
 */
public class SerialNumberService {

    /**
     *
     * @param name
     * @return
     */
    public static long initSerialNumber(String name) {
        ISerialNumberDao serialNumberDao = new SerialNumberDaoImpl();
        long serialNumber = -1L;
        try {
            serialNumber = serialNumberDao.getSerialNumber(name);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return serialNumber;
    }
}
