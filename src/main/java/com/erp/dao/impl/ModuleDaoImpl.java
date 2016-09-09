
package com.erp.dao.impl;

import com.erp.dao.IModuleDao;
import com.erp.entity.Module;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import com.erp.util.TableNameConstant;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016-07-02.
 */
public class ModuleDaoImpl implements IModuleDao {
    private static Logger logger = Logger.getLogger(ModuleDaoImpl.class);

    /**
     * ��ʼ��ģ��
     *
     * @return
     * @throws Exception
     */
    public List<Module> queryModules(boolean flag) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Module> moduleList = new ArrayList<Module>();
        try {
            String sql = "select module_id,module_name,href,parentid,parenttype,display,dis_order,icon " +
                    "from " + TableNameConstant.MODULE+ " where (0=? or display='0') " +
                    "order by parenttype desc,parentid asc, dis_order asc ";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, flag ? 0 : 1);
            rst = ps.executeQuery();
            while (rst.next()) {
                Module module = new Module();
                module.setModuleId(rst.getInt("module_id"));
                module.setModuleName(rst.getString("module_name"));
                module.setHref(rst.getString("href"));
                module.setParentId(rst.getInt("parentid"));
                module.setParentType(rst.getString("parenttype"));
                module.setDisplay(rst.getString("display"));
                module.setDisOrder(rst.getInt("dis_order"));
                module.setIcon(rst.getString("icon"));
                moduleList.add(module);
            }
        } catch (Exception e) {
            logger.error("��ȡģ��ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("��ȡģ��ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

        return moduleList;
    }

    /**
     * ����ģ��
     *
     * @param module
     * @throws DAOException
     */
    @Override
    public void insertModule(Module module) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "insert into " + TableNameConstant.MODULE + "(module_name,href,parentid,parenttype,display,dis_order,icon) " +
                    "values (?,?,?,?,'0',?,?) ";
            ps = connection.prepareStatement(sql);
            ps.setString(1, module.getModuleName());
            ps.setString(2, module.getHref());
            ps.setLong(3, module.getParentId());
            ps.setString(4, module.getParentType());
            ps.setInt(5, module.getDisOrder());
            ps.setString(6, module.getIcon());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("����ģ��ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("����ģ��ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ����ģ��
     *
     * @param module
     * @throws DAOException
     */
    @Override
    public void updateModule(Module module) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update " + TableNameConstant.MODULE + " set module_name=?, href=?, dis_order=? where module_id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, module.getModuleName());
            ps.setString(2, module.getHref());
            ps.setInt(3, module.getDisOrder());
            ps.setLong(4, module.getModuleId());
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("����ģ��ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("����ģ��ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ����ģ��
     *
     * @param id
     * @throws DAOException
     */
    @Override
    public void updateModule(String id) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update " + TableNameConstant.MODULE + " set display='0' where module_id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("�޸�ģ��ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("�޸�ģ��ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     * ɾ���ڵ�
     *
     * @param ids
     * @throws DAOException
     */
    @Override
    public void deleteModule(String[] ids) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String sql = "update " + TableNameConstant.MODULE + " set display='1' where module_id=?";
            ps = connection.prepareStatement(sql);
            for (String id : ids) {
                ps.setString(1, id);
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("ɾ��ģ��ʧ�ܣ�" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("ɾ��ģ��ʧ�ܣ�" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }
}
