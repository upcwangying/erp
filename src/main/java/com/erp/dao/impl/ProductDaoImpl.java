package com.erp.dao.impl;

import com.erp.dao.IProductDao;
import com.erp.entity.Product;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
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
     * 查询商品列表
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
            String sql = "select p.productid, p.productname, p.productdesc,p.jldwid,j.jldwmc," +
                    "p.price,p.is_valid,p.create_staffid,p.create_date,p.update_staffid,p.update_date," +
                    "s.staffname as create_staffname,s1.staffname as update_staffname " +
                    "from t_product p " +
                    "left join staffinfo s on p.create_staffid=s.staffid " +
                    "left join staffinfo s1 on p.update_staffid = s1.staffid " +
                    "left join t_jldw j on p.jldwid=j.jldwid " +
                    "where p.is_del='0' and s.is_del='0' and s1.is_del='0' and j.is_del='0' ";
            ps = connection.prepareStatement(sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Product product = new Product();
                product.setProductId(rst.getLong("productid"));
                product.setProductName(rst.getString("productname"));
                product.setProductDesc(rst.getString("productdesc"));
                product.setJldwid(rst.getLong("jldwid"));
                product.setJldwmc(rst.getString("jldwmc"));
                product.setPrice(rst.getDouble("price"));
                product.setIs_valid(rst.getString("is_valid"));
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
            logger.error("获取商品列表失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("获取商品列表失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

        return productList;
    }

    /**
     * 插入或更新商品
     *
     * @param product
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateProduct(Product product) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (product.getProductId() == 0) {
                // 商品productId 为空，为新增业务
                insertProduct(connection, product);
            } else {
                // 商品productId 不为空，为更新业务
                updateProduct(connection, product);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("插入或更新商品失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("插入或更新商品失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

    }

    /**
     * 上架或下架
     *
     * @param ids
     * @param staffId
     * @param flag    下架标识: true执行下架操作
     * @throws DAOException
     */
    @Override
    public void updateProductValid(String[] ids, String staffId, boolean flag) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String update_sql = "update t_product set is_valid=?,update_staffid=?,update_date=getdate() " +
                    "where productid=? ";
            ps = connection.prepareStatement(update_sql);
            for (String id : ids) {
                ps.setString(1, flag?"0":"1");
                ps.setLong(2, Long.valueOf(staffId));
                ps.setLong(3, Long.valueOf(id));
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("上架或下架商品失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("上架或下架商品失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * 插入商品
     *
     * @param connection
     * @param product
     * @throws DAOException
     */
    public void insertProduct(Connection connection, Product product) throws SQLException {
        String insert_sql = "insert into t_product (productname,productdesc," +
                "jldwid,price,is_valid,is_del,create_staffid,create_date,update_staffid,update_date) " +
                "values (?,?,?,?,'0','0',?,getdate(),?,getdate()) ";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        ps.setString(1, product.getProductName());
        ps.setString(2, product.getProductDesc());
        ps.setLong(3, product.getJldwid());
        ps.setDouble(4, product.getPrice());
        ps.setLong(5, product.getCreate_StaffId());
        ps.setLong(6, product.getCreate_StaffId());
        ps.execute();
    }

    /**
     * 更新商品
     *
     * @param connection
     * @param product
     * @throws DAOException
     */
    public void updateProduct(Connection connection, Product product) throws SQLException {
        String update_sql = "update t_product set productname=?,productdesc=?," +
                "jldwid=?,price=?,update_staffid=?,update_date=getdate() where productid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, product.getProductName());
        ps.setString(2, product.getProductDesc());
        ps.setLong(3, product.getJldwid());
        ps.setDouble(4, product.getPrice());
        ps.setLong(5, product.getUpdate_staffId());
        ps.setLong(6, product.getProductId());
        ps.execute();
    }

    /**
     * 删除商品
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
            logger.error("删除商品失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除商品失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }
}
