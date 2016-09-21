package com.erp.dao;

import com.erp.entity.Group;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-09-20.
 */
public interface IGroupDao {

    /**
     * ��ѯ
     * @return
     * @throws DAOException
     */
    List<Group> queryGroups() throws DAOException;

    /**
     * ���ӻ��޸�
     * @param group
     * @throws DAOException
     */
    void insertOrUpdateGroup(Group group) throws DAOException;

    /**
     * ɾ��
     * @param groupId
     * @param update_staffId
     * @throws DAOException
     */
    void deleteGroup(String[] groupId, String update_staffId) throws DAOException;

}
