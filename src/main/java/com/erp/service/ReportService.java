package com.erp.service;

import com.erp.dao.IReportDao;
import com.erp.dao.impl.ReportDaoImpl;
import com.erp.entity.YW;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-07-02.
 */
public class ReportService {

    /**
     * 增加填报数据
     * @param ywList
     * @throws ServiceException
     */
    public static void insertReportData(List<YW> ywList) throws ServiceException {
        IReportDao reportDao = new ReportDaoImpl();
        try {
            reportDao.insertReportData(ywList);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 更新填报数据
     *
     * @param ywList
     * @throws DAOException
     */
    public static void updateReportData(List<YW> ywList) throws ServiceException {
        IReportDao reportDao = new ReportDaoImpl();
        try {
            reportDao.updateReportData(ywList);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 增加或更新填报数据
     * 该方法适用于行增加，新增加的应该为插入插入，而修改的为更新业务
     *
     * @param insertList
     * @param updateList
     * @throws DAOException
     */
    public static void insertOrUpdateReportData(List<YW> insertList, List<YW> updateList) throws ServiceException {
        IReportDao reportDao = new ReportDaoImpl();
        try {
            reportDao.insertOrUpdateReportData(insertList, updateList);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除填报数据
     *
     * @param ids
     * @throws DAOException
     */
    public static void deleteReportData(String[] ids) throws ServiceException {
        IReportDao reportDao = new ReportDaoImpl();
        try {
            reportDao.deleteReportData(ids);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 获取填报数据明细(如果dbid为空则返回全部明细数据)
     * @param dbid
     * @throws ServiceException
     */
    public static JSONArray queryReportDatas(String dbid) throws ServiceException {
        IReportDao reportDao = new ReportDaoImpl();
        JSONArray ywArray = new JSONArray();
        try {
            List<YW> ywList = reportDao.queryReportDatas(dbid);
            if (ywList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

                for (YW yw : ywList) {
                    JSONObject ywObject = JSONObject.fromObject(yw, config);
                    ywArray.add(ywObject);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return ywArray;
    }

}
