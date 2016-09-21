package com.erp.dao.impl;

import com.erp.dao.IGroupDao;
import com.erp.entity.Group;
import com.erp.exception.DAOException;
import com.erp.util.JdbcUtil;
import com.erp.util.StringUtil;
import com.erp.util.TableNameConstant;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang_ on 2016-09-20.
 */
public class GroupDaoImpl implements IGroupDao {
    private static Logger logger = Logger.getLogger(GroupDaoImpl.class);

    /**
     * 查询
     *
     * @return
     * @throws DAOException
     */
    @Override
    public List<Group> queryGroups() throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        List<Group> groupList = new ArrayList<>();
        try {
            String query_sql = "select groupid,groupcode,groupdesc,modules," +
                    "is_del,create_staffid,create_date,update_staffid,update_date " +
                    "from " + TableNameConstant.T_SYS_GROUP + " where is_del='0' " +
                    "order by create_date asc";
            ps = connection.prepareStatement(query_sql);
            rst = ps.executeQuery();
            while (rst.next()) {
                Group group = new Group();
                group.setGroupId(rst.getLong("groupid"));
                group.setGroupCode(rst.getString("groupcode"));
                group.setGroupDesc(rst.getString("groupdesc"));
                group.setModules(rst.getString("modules"));
                group.setIs_del(rst.getString("is_del"));
                group.setCreate_staffId(rst.getLong("create_staffid"));
                group.setCreate_date(new Date(rst.getDate("create_date").getTime()));
                group.setUpdate_staffId(rst.getLong("update_staffid"));
                group.setUpdate_date(new Date(rst.getDate("update_date").getTime()));
                groupList.add(group);
            }
        } catch (Exception e) {
            logger.error("查询组数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询组数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return groupList;
    }

    /**
     * @param groupCode
     * @param groupId
     * @return
     * @throws DAOException
     */
    @Override
    public Group queryGroupByGroupId(String groupCode, String groupId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rst = null;
        Group group = null;
        try {
            String query_sql = "select groupid,groupcode,groupdesc,modules,is_del," +
                    "create_staffid,create_date,update_staffid,update_date " +
                    "from " + TableNameConstant.T_SYS_GROUP + " " +
                    "where is_del='0' and groupcode=? and (0=? or groupid!=?) ";
            ps = connection.prepareStatement(query_sql);
            ps.setString(1, groupCode);
            ps.setInt(2, StringUtil.isEmpty(groupId)?0:1);
            ps.setString(3, StringUtil.isEmpty(groupId)?"-1":groupId);
            rst = ps.executeQuery();
            while (rst.next()) {
                group = new Group();
                group.setGroupId(rst.getLong("groupid"));
                group.setGroupCode(rst.getString("groupcode"));
                group.setGroupDesc(rst.getString("groupdesc"));
                group.setModules(rst.getString("modules"));
                group.setIs_del(rst.getString("is_del"));
                group.setCreate_staffId(rst.getLong("create_staffid"));
                group.setCreate_date(new Date(rst.getDate("create_date").getTime()));
                group.setUpdate_staffId(rst.getLong("update_staffid"));
                group.setUpdate_date(new Date(rst.getDate("update_date").getTime()));
            }
        } catch (Exception e) {
            logger.error("查询组数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("查询组数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
        return group;
    }

    /**
     * 增加或修改
     *
     * @param group
     * @throws DAOException
     */
    @Override
    public void insertOrUpdateGroup(Group group) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        try {
            if (group.getGroupId() == 0) {
                insertGroup(connection, group);
            } else {
                updateGroup(connection, group);
            }
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("增加或更新组数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("增加或更新组数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }
    }

    /**
     *
     * @param connection
     * @param group
     * @throws SQLException
     */
    private void insertGroup(Connection connection, Group group) throws SQLException {
        String insert_sql = "insert into " + TableNameConstant.T_SYS_GROUP +
                "(groupcode,groupdesc,modules,is_del,create_staffid,create_date,update_staffid,update_date) " +
                "values(?,?,?,'0',?,getdate(),?,getdate()) ";
        PreparedStatement ps = connection.prepareStatement(insert_sql);
        ps.setString(1, group.getGroupCode());
        ps.setString(2, group.getGroupDesc());
        ps.setString(3, group.getModules());
        ps.setLong(4, group.getCreate_staffId());
        ps.setLong(5, group.getUpdate_staffId());
        ps.execute();
    }

    /**
     *
     * @param connection
     * @param group
     * @throws SQLException
     */
    private void updateGroup(Connection connection, Group group) throws SQLException {
        String update_sql = "update " + TableNameConstant.T_SYS_GROUP + " set " +
                "groupdesc=?,modules=?,update_staffid=?,update_date=getdate() " +
                "where groupid=? ";
        PreparedStatement ps = connection.prepareStatement(update_sql);
        ps.setString(1, group.getGroupDesc());
        ps.setString(2, group.getModules());
        ps.setLong(3, group.getUpdate_staffId());
        ps.setLong(4, group.getGroupId());
        ps.execute();
    }

    /**
     * 删除
     *
     * @param groupId
     * @param update_staffId
     * @throws DAOException
     */
    @Override
    public void deleteGroup(String[] groupId, String update_staffId) throws DAOException {
        Connection connection = JdbcUtil.getConnection();
        JdbcUtil.beginTranaction();
        PreparedStatement ps = null;
        try {
            String delete_sql = "update " + TableNameConstant.T_SYS_GROUP + " set " +
                    "is_del='1',update_staffid=?,update_date=getdate() " +
                    "where groupid=? ";
            ps = connection.prepareStatement(delete_sql);
            for (String groupID : groupId) {
                ps.setLong(1, Long.valueOf(groupID));
                ps.setLong(2, Long.valueOf(update_staffId));
                ps.addBatch();
            }
            ps.executeBatch();
            JdbcUtil.commit();
        } catch (Exception e) {
            JdbcUtil.rollback();
            logger.error("删除组数据失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException("删除组数据失败：" + e.getMessage(), e);
        } finally {
            if (connection != null) {
                JdbcUtil.close();
            }
        }

    }
}
