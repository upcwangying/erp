package com.erp.dao;

import com.erp.entity.Gys;
import com.erp.entity.WL;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-06-29.
 */
public interface IZSJDataDao {

    /**
     * ��ȡ��������������
     * @return
     * @throws DAOException
     */
    List<WL> queryWlList() throws DAOException;

    /**
     * ��������������
     * @param wl
     * @throws DAOException
     */
    void insertWl(WL wl) throws DAOException;

    /**
     * ��������������
     * @param wl
     * @throws DAOException
     */
    void updateWl(WL wl) throws DAOException;

    /**
     * ɾ����������
     * @param ids
     * @throws DAOException
     */
    void deleteWl(String[] ids) throws DAOException;

    /**
     * ��ȡ��Ӧ������������
     * @return
     * @throws DAOException
     */
    List<Gys> queryGysList() throws DAOException;

    /**
     * ���ӹ�Ӧ��������
     * @param gys
     * @throws DAOException
     */
    void insertGys(Gys gys) throws DAOException;

    /**
     * ���¹�Ӧ��������
     * @param gys
     * @throws DAOException
     */
    void updateGys(Gys gys) throws DAOException;

    /**
     * ɾ����Ӧ������
     * @param ids
     * @throws DAOException
     */
    void deleteGys(String[] ids) throws DAOException;

}
