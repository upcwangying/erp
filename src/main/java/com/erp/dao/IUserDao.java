package com.erp.dao;

import com.erp.entity.StaffInfo;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * 用户登陆接口
 * Created by wang_ on 2016-06-28.
 */
public interface IUserDao {

    /**
     * 获得Staff
     * @param staffCode
     * @return
     * @throws DAOException
     */
    StaffInfo queryStaffByCode (String staffId, String staffCode) throws DAOException;

    /**
     * 更新STAFF
     * @param staffCode
     * @throws DAOException
     */
    void updateStaffByCode (String staffCode) throws DAOException;

    /**
     * 查询用户信息，如果dbid为空，则查询全部用户
     * @param dbid
     * @return
     * @throws DAOException
     */
    List<StaffInfo> queryUserData(String dbid) throws DAOException;

    /**
     * 增加用户
     * @param staffInfo
     * @throws DAOException
     */
    void insertUserData(StaffInfo staffInfo) throws DAOException;

    /**
     * 更新用户
     * @param staffInfo
     * @throws DAOException
     */
    void updateUserData(StaffInfo staffInfo) throws DAOException;

    /**
     * 删除用户
     * @param ids
     * @throws DAOException
     */
    void deleteUserData(String[] ids) throws DAOException;

}
