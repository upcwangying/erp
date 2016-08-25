package com.erp.dao;

import com.erp.entity.StaffInfo;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * �û���½�ӿ�
 * Created by wang_ on 2016-06-28.
 */
public interface IUserDao {

    /**
     * ���Staff
     * @param staffCode
     * @return
     * @throws DAOException
     */
    StaffInfo queryStaffByCode (String staffId, String staffCode) throws DAOException;

    /**
     * ����STAFF
     * @param staffCode
     * @throws DAOException
     */
    void updateStaffByCode (String staffCode) throws DAOException;

    /**
     * ��ѯ�û���Ϣ�����dbidΪ�գ����ѯȫ���û�
     * @param dbid
     * @return
     * @throws DAOException
     */
    List<StaffInfo> queryUserData(String dbid) throws DAOException;

    /**
     * �����û�
     * @param staffInfo
     * @throws DAOException
     */
    void insertUserData(StaffInfo staffInfo) throws DAOException;

    /**
     * �����û�
     * @param staffInfo
     * @throws DAOException
     */
    void updateUserData(StaffInfo staffInfo) throws DAOException;

    /**
     * ɾ���û�
     * @param ids
     * @throws DAOException
     */
    void deleteUserData(String[] ids) throws DAOException;

}
