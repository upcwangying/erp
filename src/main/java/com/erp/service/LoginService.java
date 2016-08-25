package com.erp.service;

import com.erp.dao.IUserDao;
import com.erp.dao.impl.UserDaoImpl;
import com.erp.entity.StaffInfo;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;

/**
 * Created by wang_ on 2016-07-02.
 */
public class LoginService {

    /**
     * ��½Service
     * @param staffCode
     * @return
     * @throws ServiceException
     */
    public static StaffInfo queryStaffByCode(String staffId, String staffCode) throws ServiceException {
        IUserDao loginDao = new UserDaoImpl();
        StaffInfo staffInfo = null;
        try {
            staffInfo = loginDao.queryStaffByCode(staffId, staffCode);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return staffInfo;
    }

    /**
     * ��������¼ʱ��
     * @param staffCode
     */
    public static void updateStaffByCode(String staffCode) throws ServiceException {
        IUserDao loginDao = new UserDaoImpl();
        try {
            loginDao.updateStaffByCode(staffCode);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
