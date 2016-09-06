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
     * �ϼܻ��¼�
     * @param ids
     * @param staffId
     * @param flag �¼ܱ�ʶ: trueִ���¼ܲ���
     * @throws DAOException
     */
    void updateProductValid(String[] ids, String staffId, boolean flag) throws DAOException;

    /**
     * ɾ����Ʒ
     * @param ids
     * @param staffId
     * @throws DAOException
     */
    void deleteProduct(String[] ids, String staffId) throws DAOException;

    /**
     * ��ѯ����Ʒ���ϴ���ͼƬ
     * @param productId ��ƷID
     * @return
     * @throws DAOException
     */
    List<FileUploadLog> queryFileUploadLog(String productId) throws DAOException;

    /**
     * ����
     * @param fileUploadLog
     * @throws DAOException
     */
    long insertFileUploadLog(FileUploadLog fileUploadLog) throws DAOException;

    /**
     * ����
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
     * ����deleteUrl�ֶ�
     * @param dbid
     * @param deleteUrl
     * @param update_staffId
     * @throws DAOException
     */
    void updateFileUploadLog(long dbid, String deleteUrl, String update_staffId) throws DAOException;

    /**
     * ɾ����������
     * @param dbid ����
     * @param update_staffId
     * @throws DAOException
     */
    void deleteFileUploadLog(String dbid, String update_staffId) throws DAOException;

}
