package com.erp.service;

import com.erp.dao.IJldwDao;
import com.erp.dao.impl.JldwDaoImpl;
import com.erp.entity.Jldw;
import com.erp.entity.Product;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-09-02.
 */
public class JldwService {
    /**
     * 查询
     *
     * @return
     * @throws ServiceException
     */
    public static List<Jldw> queryJldw() throws ServiceException {
        IJldwDao jldwDao = new JldwDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<Jldw> jldwList = jldwDao.queryJldw();
            if (jldwList != null && jldwList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

                for (Jldw jldw : jldwList) {
                    JSONObject object = JSONObject.fromObject(jldw, config);
                    array.add(object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return array;
    }

    /**
     * 插入、更新数据
     *
     * @param jldw
     * @throws ServiceException
     */
    public static void insertOrUpdateJldw(Jldw jldw) throws ServiceException {
        IJldwDao jldwDao = new JldwDaoImpl();
        try {
            jldwDao.insertOrUpdateJldw(jldw);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 恢复数据
     *
     * @param ids
     * @param update_staffId
     * @throws ServiceException
     */
    public static void resumeJldw(String[] ids, long update_staffId) throws ServiceException {
        IJldwDao jldwDao = new JldwDaoImpl();
        try {
            jldwDao.resumeJldw(ids, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除数据
     *
     * @param ids
     * @param update_staffId
     * @throws ServiceException
     */
    public static void deleteJldw(String[] ids, long update_staffId) throws ServiceException {
        IJldwDao jldwDao = new JldwDaoImpl();
        try {
            jldwDao.deleteJldw(ids, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
