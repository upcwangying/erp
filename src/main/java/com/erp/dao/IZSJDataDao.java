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
     * 获取物料下拉框数据
     * @return
     * @throws DAOException
     */
    List<WL> queryWlList() throws DAOException;

    /**
     * 增加、更新物料主数据
     * @param wl
     * @throws DAOException
     */
    void insertOrUpdateWl(WL wl) throws DAOException;

    /**
     * 删除物料数据
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    void deleteWl(String[] ids, long update_staffId) throws DAOException;

    /**
     * 获取供应商下拉框数据
     * @return
     * @throws DAOException
     */
    List<Gys> queryGysList() throws DAOException;

    /**
     * 增加供应商主数据
     * @param gys
     * @throws DAOException
     */
    void insertOrUpdateGys(Gys gys) throws DAOException;

    /**
     * 删除供应商数据
     * @param ids
     * @param update_staffId
     * @throws DAOException
     */
    void deleteGys(String[] ids, long update_staffId) throws DAOException;

}
