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
     * ���ӡ���������������
     * @param wl
     * @throws DAOException
     */
    void insertOrUpdateWl(WL wl) throws DAOException;

    /**
     * ɾ����������
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    void deleteWl(String[] ids, long update_staffId) throws DAOException;

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
    void insertOrUpdateGys(Gys gys) throws DAOException;

    /**
     * ɾ����Ӧ������
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    void deleteGys(String[] ids, long update_staffId) throws DAOException;

}
