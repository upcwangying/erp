package com.erp.service;

import com.erp.dao.IZSJDataDao;
import com.erp.dao.impl.ZSJDataDaoImpl;
import com.erp.entity.Gys;
import com.erp.entity.WL;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-06-29.
 */
public class ZSJService {

    /**
     * 获得物料下拉框数据
     *
     * @return
     */
    public static JSONArray initWlComboData() throws ServiceException{
        IZSJDataDao comboBoxDataDao = new ZSJDataDaoImpl();
        JSONArray wl_array = new JSONArray();
        try {
            List<WL> wlList = comboBoxDataDao.queryWlList();
            if (wlList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

                for (WL wl : wlList) {
                    JSONObject wl_object = JSONObject.fromObject(wl, config);
                    wl_array.add(wl_object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

        return wl_array;
    }

    /**
     * 获得供应商下拉框数据
     *
     * @return
     */
    public static JSONArray initGysComboData() throws ServiceException{
        IZSJDataDao comboBoxDataDao = new ZSJDataDaoImpl();
        JSONArray gys_array = new JSONArray();
        try {
            List<Gys> gysList = comboBoxDataDao.queryGysList();
            if (gysList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

//              JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd" }));
                for (Gys gys : gysList) {
                    JSONObject gys_object = JSONObject.fromObject(gys, config);
                    gys_array.add(gys_object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

        return gys_array;
    }

    /**
     * 插入、更新物料数据
     *
     * @param wl
     * @throws ServiceException
     */
    public static void insertOrUpdateWl(WL wl) throws ServiceException {
        IZSJDataDao zsjDataDao = new ZSJDataDaoImpl();
        try {
            zsjDataDao.insertOrUpdateWl(wl);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除物料数据
     *
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    public static void deleteWl(String[] ids, long update_staffId) throws ServiceException {
        IZSJDataDao zsjDataDao = new ZSJDataDaoImpl();
        try {
            zsjDataDao.deleteWl(ids, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 增加、更新供应商主数据
     *
     * @param gys
     * @throws ServiceException
     */
    public static void insertOrUpdateGys(Gys gys) throws ServiceException {
        IZSJDataDao zsjDataDao = new ZSJDataDaoImpl();
        try {
            zsjDataDao.insertOrUpdateGys(gys);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除供应商数据
     *
     * @param ids
     * @param update_staffId
     * @throws ServiceException
     */
    public static void deleteGys(String[] ids, long update_staffId) throws ServiceException {
        IZSJDataDao zsjDataDao = new ZSJDataDaoImpl();
        try {
            zsjDataDao.deleteGys(ids, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
