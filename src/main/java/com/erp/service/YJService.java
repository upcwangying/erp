package com.erp.service;

import com.erp.dao.IYJDao;
import com.erp.dao.impl.YJDaoImpl;
import com.erp.entity.YJ;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-08-04.
 */
public class YJService {

    /**
     * �����½�����
     *
     * @param yj
     * @throws ServiceException
     */
    public static void insertYJData(YJ yj) throws ServiceException {
        IYJDao yjDao = new YJDaoImpl();
        try {
            yjDao.insertYJData(yj);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * �����½�����
     *
     * @param yj
     * @throws ServiceException
     */
    public static void updateYJData(YJ yj) throws ServiceException {
        IYJDao yjDao = new YJDaoImpl();
        try {
            yjDao.updateYJData(yj);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * �����½�����
     * @param dbid
     * @param yj
     * @throws ServiceException
     */
    public static void deleteOrInsert(String dbid, YJ yj) throws ServiceException {
        IYJDao yjDao = new YJDaoImpl();
        try {
            yjDao.deleteOrInsert(dbid, yj);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ����dbid����ɾ���½�����
     *
     * @param dbid
     * @throws ServiceException
     */
    public static void deleteYJData(String dbid) throws ServiceException {
        IYJDao yjDao = new YJDaoImpl();
        try {
            yjDao.deleteYJData(dbid);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ��ѯ�����½�����
     *
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryYJData() throws ServiceException {
        IYJDao yjDao = new YJDaoImpl();
        JSONArray yjArray = new JSONArray();
        try {
            List<YJ> yjList = yjDao.queryYJData(null);
            if (yjList != null && yjList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

                for (YJ yj : yjList) {
                    JSONObject object = JSONObject.fromObject(yj, config);
                    yjArray.add(object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return yjArray;
    }

    /**
     * ��ҳ��ѯ�½�����
     *
     * @param page ��ǰҳ��
     * @param rows ÿҳ����
     * @return
     * @throws ServiceException
     */
    public static List<YJ> queryYJDataByPage(int page, int rows) {
        return null;
    }

}
