package com.erp.dao;

import com.erp.entity.Module;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-07-02.
 */
public interface IModuleDao {

    /**
     * ��ѯ����ģ��
     * @return
     * @throws DAOException
     */
    List<Module> queryModules() throws DAOException;

    /**
     * ����ģ��
     * @param module
     * @throws DAOException
     */
    void insertModule(Module module) throws DAOException;

    /**
     * ����ģ��
     * @param module
     * @throws DAOException
     */
    void updateModule(Module module) throws DAOException;

    /**
     * ɾ���ڵ�
     * @param ids
     * @throws DAOException
     */
    void deleteModule(String[] ids) throws DAOException;
}
