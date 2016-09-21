package com.erp.service;

import com.erp.dao.IGroupDao;
import com.erp.dao.IModuleDao;
import com.erp.dao.impl.GroupDaoImpl;
import com.erp.dao.impl.ModuleDaoImpl;
import com.erp.entity.Group;
import com.erp.entity.Module;
import com.erp.entity.Product;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import com.erp.util.JsonDateValueProcessor;
import com.erp.util.SystemConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.*;

/**
 * Created by wang_ on 2016-09-20.
 */
public class GroupService {

    /**
     * 查询
     *
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryGroups() throws ServiceException {
        IGroupDao groupDao = new GroupDaoImpl();
        JSONArray array = new JSONArray();
        try {
            List<Group> groupList = groupDao.queryGroups();
            if (groupList != null && groupList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

                for (Group group : groupList) {
                    JSONObject object = JSONObject.fromObject(group, config);
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
     * 增加或修改
     *
     * @param group
     * @throws ServiceException
     */
    public static void insertOrUpdateGroup(Group group) throws ServiceException {
        IGroupDao groupDao = new GroupDaoImpl();
        try {
            groupDao.insertOrUpdateGroup(group);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除
     *
     * @param groupId
     * @param update_staffId
     * @throws ServiceException
     */
    public static void deleteGroup(String[] groupId, String update_staffId) throws ServiceException {
        IGroupDao groupDao = new GroupDaoImpl();
        try {
            groupDao.deleteGroup(groupId, update_staffId);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
