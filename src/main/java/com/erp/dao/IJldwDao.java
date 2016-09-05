package com.erp.dao;

import com.erp.entity.Jldw;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-09-01.
 */
public interface IJldwDao {

    /**
     * 查询
     * @return
     * @throws DAOException
     */
    List<Jldw> queryJldw() throws DAOException;

    /**
     * 查询
     * @param jldwmc
     * @param jldwId
     * @return
     * @throws DAOException
     */
    Jldw queryJldwByJldwId(String jldwmc, String jldwId) throws DAOException;

    /**
     * 插入、更新数据
     * @param jldw
     * @throws DAOException
     */
    void insertOrUpdateJldw(Jldw jldw) throws DAOException;

    /**
     * 删除数据
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    void deleteJldw(String[] ids, long update_staffId) throws DAOException;

}
