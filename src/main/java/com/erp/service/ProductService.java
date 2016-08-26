package com.erp.service;

import com.erp.dao.IProductDao;
import com.erp.dao.impl.ProductDaoImpl;
import com.erp.entity.Product;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;

import java.util.List;

/**
 * Created by wang_ on 2016-08-26.
 */
public class ProductService {

    /**
     * ��ѯ��Ʒ�б�
     *
     * @return
     * @throws ServiceException
     */
    public static List<Product> queryProduct() throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        List<Product> productList = null;
        try {
            productList = productDao.queryProduct();
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return productList;
    }

    /**
     * ����������Ʒ
     *
     * @param product
     * @throws ServiceException
     */
    public static void insertOrUpdateProduct(Product product) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        try {
            productDao.insertOrUpdateProduct(product);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * ɾ����Ʒ
     *
     * @param ids
     * @param staffId
     * @throws ServiceException
     */
    public static void deleteProduct(String[] ids, String staffId) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        try {
            productDao.deleteProduct(ids, staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
