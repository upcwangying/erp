package com.erp.service;

import com.erp.dao.IProductDao;
import com.erp.dao.impl.ProductDaoImpl;
import com.erp.entity.Product;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-08-26.
 */
public class ProductService {

    /**
     * 查询商品列表
     *
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryProduct() throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<Product> productList = productDao.queryProduct();
            if (productList != null && productList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

                for (Product product : productList) {
                    JSONObject object = JSONObject.fromObject(product, config);
                    array.add(object);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return array;
    }

    /**
     * 插入或更新商品
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
     * 上架或下架
     *
     * @param ids
     * @param staffId
     * @param flag    下架标识: true执行下架操作
     * @throws ServiceException
     */
    public static void updateProductValid(String[] ids, String staffId, boolean flag) throws ServiceException {
        IProductDao productDao = new ProductDaoImpl();
        try {
            productDao.updateProductValid(ids, staffId, flag);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除商品
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
