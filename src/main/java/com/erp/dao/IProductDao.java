package com.erp.dao;

import com.erp.entity.Product;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-26.
 */
public interface IProductDao {

    /**
     * 查询商品列表
     * @return
     * @throws DAOException
     */
    List<Product> queryProduct() throws DAOException;

    /**
     * 插入或更新商品
     * @param product
     * @throws DAOException
     */
    void insertOrUpdateProduct(Product product) throws DAOException;

    /**
     * 删除商品
     * @param ids
     * @param staffId
     * @throws DAOException
     */
    void deleteProduct(String[] ids, String staffId) throws DAOException;
}
