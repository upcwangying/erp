package com.erp.dao;

import com.erp.entity.FileUploadLog;
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
     * 上架或下架
     * @param ids
     * @param staffId
     * @param flag 下架标识: true执行下架操作
     * @throws DAOException
     */
    void updateProductValid(String[] ids, String staffId, boolean flag) throws DAOException;

    /**
     * 删除商品
     * @param ids
     * @param staffId
     * @throws DAOException
     */
    void deleteProduct(String[] ids, String staffId) throws DAOException;

    /**
     * 查询该商品下上传的图片
     * @param productId 商品ID
     * @return
     * @throws DAOException
     */
    List<FileUploadLog> queryFileUploadLog(String productId) throws DAOException;

    /**
     * 增加
     * @param fileUploadLog
     * @throws DAOException
     */
    long insertFileUploadLog(FileUploadLog fileUploadLog) throws DAOException;

    /**
     * 增加
     * @param productId
     * @param name
     * @param url
     * @param thumbnailUrl
     * @param staffId
     * @return
     * @throws DAOException
     */
    long insertFileUploadLog(String productId, String name, String url,
                             String thumbnailUrl, String staffId) throws DAOException;

    /**
     * 更新deleteUrl字段
     * @param dbid
     * @param deleteUrl
     * @param update_staffId
     * @throws DAOException
     */
    void updateFileUploadLog(long dbid, String deleteUrl, String update_staffId) throws DAOException;

    /**
     * 删除该条数据
     * @param dbid 主键
     * @param update_staffId
     * @throws DAOException
     */
    void deleteFileUploadLog(String dbid, String update_staffId) throws DAOException;

}
