package com.erp.service;

import com.erp.dao.IUserDao;
import com.erp.dao.impl.UserDaoImpl;
import com.erp.entity.StaffInfo;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-07-27.
 */
public class UserService {

    /**
     * ��ѯ�û���Ϣ�����dbidΪ�գ����ѯȫ���û�
     *
     * @param dbid
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryUserData(String dbid) throws ServiceException {
        IUserDao userDao = new UserDaoImpl();
        JSONArray user_array = new JSONArray();
        try {
            List<StaffInfo> staffInfoList = userDao.queryUserData(dbid);
            if (staffInfoList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

                for (StaffInfo staffInfo : staffInfoList) {
                    JSONObject user_object = JSONObject.fromObject(staffInfo, config);
                    user_array.add(user_object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

        return user_array;
    }

    /**
     * �����û�
     *
     * @param staffInfo
     * @throws ServiceException
     */
    public static void insertUserData(StaffInfo staffInfo) throws ServiceException {
        IUserDao userDao = new UserDaoImpl();
        try {
            userDao.insertUserData(staffInfo);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * �����û�
     *
     * @param staffInfo
     * @throws ServiceException
     */
    public static void updateUserData(StaffInfo staffInfo) throws ServiceException {
        IUserDao userDao = new UserDaoImpl();
        try {
            userDao.updateUserData(staffInfo);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ɾ���û�
     *
     * @param ids
     * @throws ServiceException
     */
    public static void deleteUserData(String[] ids) throws ServiceException {
        IUserDao userDao = new UserDaoImpl();
        try {
            userDao.deleteUserData(ids);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
