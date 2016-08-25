package com.erp.dao;

import com.erp.entity.YW;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-06-30.
 */
public interface IReportDao {

    /**
     * ���������
     * @param ywList
     * @throws DAOException
     */
    void insertReportData(List<YW> ywList) throws DAOException;

    /**
     * ���������
     * @param ywList
     * @throws DAOException
     */
    void updateReportData(List<YW> ywList) throws DAOException;

    /**
     * ���ӻ���������
     * @param insertList
     * @param updateList
     * @throws DAOException
     */
    void insertOrUpdateReportData(List<YW> insertList, List<YW> updateList) throws DAOException;

    /**
     * ɾ�������
     * @param ids
     * @throws DAOException
     */
    void deleteReportData(String[] ids) throws DAOException;

    /**
     * ��ȡ�������ϸ(���dbidΪ���򷵻�ȫ����ϸ����)
     * @param dbid
     * @return
     * @throws DAOException
     */
    List<YW> queryReportDatas(String dbid) throws DAOException;

}
