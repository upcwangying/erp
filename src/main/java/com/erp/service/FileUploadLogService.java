package com.erp.service;

import com.erp.dao.IProductDao;
import com.erp.dao.impl.ProductDaoImpl;
import com.erp.entity.FileUploadLog;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-09-06.
 */
public class FileUploadLogService {

    /**
     * ��ѯ����Ʒ���ϴ���ͼƬ
     *
     * @param productId ��ƷID
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryFileUploadLog(String productId) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<FileUploadLog> fileUploadLogList = productDao.queryFileUploadLog(productId);
            if (fileUploadLogList != null && fileUploadLogList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

                for (FileUploadLog fileUploadLog : fileUploadLogList) {
                    JSONObject object = JSONObject.fromObject(fileUploadLog, config);
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
     * ����
     * @param fileUploadLog
     * @return
     * @throws ServiceException
     */
    public static long insertFileUploadLog(FileUploadLog fileUploadLog) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        long dbid = 0L;
        try {
            dbid = productDao.insertFileUploadLog(fileUploadLog);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

        return dbid;
    }

    /**
     * ����
     * @param productId
     * @param name
     * @param url
     * @param thumbnailUrl
     * @param staffId
     * @return
     * @throws ServiceException
     */
    public static long insertFileUploadLog(String productId, String name, String url,
                                           String thumbnailUrl, String staffId) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        long dbid = 0L;
        try {
            dbid = productDao.insertFileUploadLog(productId, name, url, thumbnailUrl, staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

        return dbid;
    }

    /**
     * ����deleteUrl�ֶ�
     *
     * @param dbid
     * @param deleteUrl
     * @param update_staffId
     * @throws ServiceException
     */
    public static void updateFileUploadLog(long dbid, String deleteUrl, String update_staffId) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        try {
            productDao.updateFileUploadLog(dbid, deleteUrl, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ɾ����������
     *
     * @param dbid ����
     * @param update_staffId
     * @throws ServiceException
     */
    public static void deleteFileUploadLog(String dbid, String update_staffId) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        try {
            productDao.deleteFileUploadLog(dbid, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
