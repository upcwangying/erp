package com.erp.dao;

import com.erp.entity.YW;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-06-30.
 */
public interface IReportDao {

    /**
     * 增加填报数据
     * @param ywList
     * @throws DAOException
     */
    void insertReportData(List<YW> ywList) throws DAOException;

    /**
     * 更新填报数据
     * @param ywList
     * @throws DAOException
     */
    void updateReportData(List<YW> ywList) throws DAOException;

    /**
     * 增加或更新填报数据
     * @param insertList
     * @param updateList
     * @throws DAOException
     */
    void insertOrUpdateReportData(List<YW> insertList, List<YW> updateList) throws DAOException;

    /**
     * 删除填报数据
     * @param ids
     * @throws DAOException
     */
    void deleteReportData(String[] ids) throws DAOException;

    /**
     * 获取填报数据明细(如果dbid为空则返回全部明细数据)
     * @param dbid
     * @return
     * @throws DAOException
     */
    List<YW> queryReportDatas(String dbid) throws DAOException;

}
