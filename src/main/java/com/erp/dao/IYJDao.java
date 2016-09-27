package com.erp.dao;

import com.erp.entity.YJ;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-04.
 */
public interface IYJDao {

    /**
     * 插入月结数据
     * @param yj
     * @throws DAOException
     */
    void insertYJData(YJ yj) throws DAOException;

    /**
     * 更新月结数据
     * @param yj
     * @throws DAOException
     */
    void updateYJData(YJ yj) throws DAOException;

    /**
     *
     * @param dbid
     * @param yj
     * @throws DAOException
     */
    void deleteOrInsert(String dbid, YJ yj) throws DAOException;

    /**
     * 根据dbid主键删除月结数据
     * @param dbid
     * @throws DAOException
     */
    void deleteYJData(String dbid) throws DAOException;

    /**
     * 查询所有月结数据
     * @param yjyf
     * @return
     * @throws DAOException
     */
    List<YJ> queryYJData(String yjyf) throws DAOException;

    /**
     *
     * @param yjyf
     * @param dbid
     * @return
     * @throws DAOException
     */
    YJ queryYJDataByDbid(String yjyf, String dbid) throws DAOException;

}
