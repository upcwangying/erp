package com.erp.dao.impl;

import com.erp.dao.IProductDao;
import com.erp.entity.Product;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import com.erp.util.StringUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-08-26.
 */
public class ProductDaoImpl implements IProductDao {
    private static Logger logger = Logger.getLogger(ProductDaoImpl.class);

    /**
     * ��ѯ��Ʒ�б�
     *
     * @return
     * @throws DAOException
     */
    @Override
    public List<Product> queryProduct() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Product> productList = new ArrayList<>();
        try {
            String sql = "select p.productid, p.productname, p.productdesc,p.productdesc1,p.productdesc2," +
                    "p.productdesc3,p.productdesc4,p.productdesc5,p.create_staffid,p.create_date,p.update_staffid," +
                    "p.update_date,s.staffname as create_staffname,s1.staffname as update_staffname " +
                    "from t_product p " +
                    "left join staffinfo s on p.create_staffid=s.staffid " +
                    "left join staffinfo s1 on p.update_staffid = s1.staffid " +
                    "where p.is_del='0' and s.is_del='0' and s1.is_del='0' ";
            ps = connection.prepareStatement(sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Product product = new Product();
                product.setProductId(rst.getLong("productid"));
                product.setProductName(rst.getString("productname"));
                product.setProductDesc(rst.getString("productdesc"));
                product.setProductDesc1(rst.getString("productdesc1"));
                product.setProductDesc2(rst.getString("productdesc2"));
                product.setProductDesc3(rst.getString("productdesc3"));
                product.setProductDesc4(rst.getString("productdesc4"));
                product.setProductDesc5(rst.getString("productdesc5"));
                product.setDelete(false);
                product.setCreate_StaffId(rst.getLong("create_staffid"));
                product.setCreate_staffName(rst.getString("create_staffname"));
                product.setCreate_date(new Date(rst.getDate("create_date").getTime()));
                product.setUpdate_staffId(rst.getLong("update_staffid"));
                product.setUpdate_staffName(rst.getString("update_staffname"));
                product.setUpdate_date(new Date(rst.getDate("update_date").getTime()));
                productList.add(product);
            }
        } catch (Exception e) {
            logger.error("��ȡ��Ʒ�б�ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("��ȡ��Ʒ�б�ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

        return productList;
    }

    /**
     * ����������Ʒ
     *
     * @param product
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateProduct(Product product) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (StringUtil.isEmpty(product.getProductId())) {
                // ��ƷproductId Ϊ�գ�Ϊ����ҵ��
                insertProduct(connection, product);
            } else {
                // ��ƷproductId ��Ϊ�գ�Ϊ����ҵ��
                updateProduct(connection, product);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("����������Ʒʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("����������Ʒʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

    }

    /**
     * ������Ʒ
     *
     * @param connection
     * @param product
     * @throws DAOException
     */
    public void insertProduct(Connection connection, Product product) throws SQLException {
        String insert_sql = "insert into t_product (productname,productdesc,productdesc1,productdesc2," +
                "productdesc3,productdesc4,productdesc5,is_del,create_staffid,create_date,update_staffid,update_date) " +
                "values (?,?,?,?,?,?,?,'0',?,getdate(),?,getdate()) ";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        ps.setString(1, product.getProductName());
        ps.setString(2, product.getProductDesc());
        ps.setString(3, StringUtil.isEmptyDo1(product.getProductDesc1()));
        ps.setString(4, StringUtil.isEmptyDo1(product.getProductDesc2()));
        ps.setString(5, StringUtil.isEmptyDo1(product.getProductDesc3()));
        ps.setString(6, StringUtil.isEmptyDo1(product.getProductDesc4()));
        ps.setString(7, StringUtil.isEmptyDo1(product.getProductDesc5()));
        ps.setLong(8, product.getCreate_StaffId());
        ps.setLong(9, product.getCreate_StaffId());
        ps.execute();
    }

    /**
     * ������Ʒ
     *
     * @param connection
     * @param product
     * @throws DAOException
     */
    public void updateProduct(Connection connection, Product product) throws SQLException {
        String update_sql = "update t_product set productname=?,productdesc=?,productdesc1=?,productdesc2=?," +
                "productdesc3=?,productdesc4=?,productdesc5=?,update_staffid=?,update_date=getdate() " +
                "where productid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, product.getProductName());
        ps.setString(2, product.getProductDesc());
        ps.setString(3, StringUtil.isEmptyDo1(product.getProductDesc1()));
        ps.setString(4, StringUtil.isEmptyDo1(product.getProductDesc2()));
        ps.setString(5, StringUtil.isEmptyDo1(product.getProductDesc3()));
        ps.setString(6, StringUtil.isEmptyDo1(product.getProductDesc4()));
        ps.setString(7, StringUtil.isEmptyDo1(product.getProductDesc5()));
        ps.setLong(8, product.getUpdate_staffId());
        ps.setLong(9, product.getProductId());
        ps.execute();
    }

    /**
     * ɾ����Ʒ
     *
     * @param ids
     * @param staffId
     * @throws DAOException
     */
    @Override
    public void deleteProduct(String[] ids, String staffId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        JdbcUtil.beginTranaction();
        try {
            String delete_sql = "update t_product set is_del='1',update_staffid=?,update_date=getdate() " +
                    "where productid=? ";
            ps = connection.prepareStatement(delete_sql);
            for (String id: ids) {
                ps.setLong(1, Long.valueOf(staffId));
                ps.setLong(2, Long.valueOf(id));
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("ɾ����Ʒʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("ɾ����Ʒʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }
}
