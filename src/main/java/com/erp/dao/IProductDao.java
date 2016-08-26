package com.erp.dao;

import com.erp.entity.Product;
import com.erp.exception.DAOException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-26.
 */
public interface IProductDao {

    /**
     * ��ѯ��Ʒ�б�
     * @return
     * @throws DAOException
     */
    List<Product> queryProduct() throws DAOException;

    /**
     * ����������Ʒ
     * @param product
     * @throws DAOException
     */
    void insertOrUpdateProduct(Product product) throws DAOException;

    /**
     * ɾ����Ʒ
     * @param ids
     * @param staffId
     * @throws DAOException
     */
    void deleteProduct(String[] ids, String staffId) throws DAOException;
}
