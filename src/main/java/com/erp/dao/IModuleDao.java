package com.erp.dao;

import com.erp.entity.Module;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-07-02.
 */
public interface IModuleDao {

    /**
     * 查询所有模块
     * @return
     * @throws DAOException
     */
    List<Module> queryModules() throws DAOException;

    /**
     * 插入模块
     * @param module
     * @throws DAOException
     */
    void insertModule(Module module) throws DAOException;

    /**
     * 更新模块
     * @param module
     * @throws DAOException
     */
    void updateModule(Module module) throws DAOException;

    /**
     * 删除节点
     * @param ids
     * @throws DAOException
     */
    void deleteModule(String[] ids) throws DAOException;
}
