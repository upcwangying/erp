package com.erp.dao;

import com.erp.entity.YJ;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-04.
 */
public interface IYJDao {

    /**
     * �����½�����
     * @param yj
     * @throws DAOException
     */
    void insertYJData(YJ yj) throws DAOException;

    /**
     * �����½�����
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
     * ����dbid����ɾ���½�����
     * @param dbid
     * @throws DAOException
     */
    void deleteYJData(String dbid) throws DAOException;

    /**
     * ��ѯ�����½�����
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
