package com.erp.service;

import com.erp.dao.IStyleDao;
import com.erp.dao.impl.StyleDaoImpl;
import com.erp.entity.Style;
import com.erp.exception.DAOException;
import com.erp.exception.ServiceException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by wang_ on 2016-08-18.
 */
public class StyleService {

    /**
     * 获取样式列表
     *
     * @return
     * @throws ServiceException
     */
    public static JSONArray queryStyleData() throws ServiceException {
        IStyleDao styleDao = new StyleDaoImpl();
        JSONArray style_array = new JSONArray();
        try {
            List<Style> styleList = styleDao.queryStyleList();
            if (styleList.size() > 0) {
                for (Style style : styleList) {
                    JSONObject styleObj = JSONObject.fromObject(style);
                    style_array.add(styleObj);
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

        return style_array;
    }

    /**
     * 更新样式
     *
     * @param style
     * @throws ServiceException
     */
    public static void updateStyle(Style style) throws ServiceException {
        IStyleDao styleDao = new StyleDaoImpl();
        try {
            styleDao.updateStyle(style);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
