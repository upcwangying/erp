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
     * ���������
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
     * ���������
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
     * ���ӻ���������
     * �÷��������������ӣ������ӵ�Ӧ��Ϊ������룬���޸ĵ�Ϊ����ҵ��
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
     * ɾ�������
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
     * ��ȡ�������ϸ(���dbidΪ���򷵻�ȫ����ϸ����)
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
