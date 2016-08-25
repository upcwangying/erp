package com.erp.servlet;

import com.erp.exception.ServiceException;
import com.erp.service.ZSJService;
import com.erp.bean.ResponseBean;
import net.sf.json.JSONArray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-07-16.
 */
@WebServlet(name = "ComboBoxServlet",
        urlPatterns = {"/combo/ComboBoxServlet"})
public class ComboBoxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ComboBoxServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String param = request.getParameter("param");

        ResponseBean responseBean = new ResponseBean(getComboData(param));
        String responseText = responseBean.getResponseArray(getFlagValue(param));

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.flush();
    }

    /**
     * 根据请求参数param,获取返回的数据（供应商数据还是物料数据）
     * @param param
     * @return
     */
    private JSONArray getComboData(String param) {
        JSONArray array = null;
        try {
            if ("wl-combo".equalsIgnoreCase(param) ||
                            "wl-query".equalsIgnoreCase(param)) {
                array = ZSJService.initWlComboData();
            } else if ("gys-combo".equalsIgnoreCase(param) ||
                            "gys-query".equalsIgnoreCase(param)) {
                array = ZSJService.initGysComboData();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 判断返回数据格式，true 为“表格列”格式，false 为“下拉框”格式
     * @param param
     * @return
     */
    private boolean getFlagValue(String param) {
        boolean flag = false;
        if ("wl-query".equalsIgnoreCase(param) ||
                        "gys-query".equalsIgnoreCase(param)) {
            flag = true;
        } else if ("wl-combo".equalsIgnoreCase(param) ||
                        "gys-combo".equalsIgnoreCase(param)) {
            flag = false;
        }
        return flag;
    }

}
