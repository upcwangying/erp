package com.erp.dao;

import com.erp.entity.Jldw;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-09-01.
 */
public interface IJldwDao {

    /**
     * ��ѯ
     * @return
     * @throws DAOException
     */
    List<Jldw> queryJldw() throws DAOException;

    /**
     * ��ѯ
     * @param jldwmc
     * @param jldwId
     * @return
     * @throws DAOException
     */
    Jldw queryJldwByJldwId(String jldwmc, String jldwId) throws DAOException;

    /**
     * ���롢��������
     * @param jldw
     * @throws DAOException
     */
    void insertOrUpdateJldw(Jldw jldw) throws DAOException;

    /**
     * ɾ������
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    void deleteJldw(String[] ids, long update_staffId) throws DAOException;

}
