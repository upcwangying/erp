package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Style;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.StyleService;
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
 * Created by wang_ on 2016-08-18.
 */
@WebServlet(name = "StyleServlet",
        urlPatterns = {"/StyleServlet"})
public class StyleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public StyleServlet() {
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
        if (param == null) {
            throw new IllegalArgumentException("the request parameter param is null, please check your request path is correct.");
        }
        String seq = request.getParameter("seq");
        String random_session = (String) request.getSession().getAttribute("random_session");

        if (!"query-combo".equals(param) && (random_session == null || seq == null || !seq.equals(random_session))) {
            throw new IllegalArgumentException("the request is illegal.");
        }
        String responseText = "";
        if ("query-combo".equals(param)) {
            responseText = queryStyleData(false);
        } else if ("query".equals(param)) {
            responseText = queryStyleData(true);
        } else if ("insert".equals(param)
                || "delete".equals(param)
                || "update".equals(param)) {
            responseText = insertOrUpdateStyle(param, request);
        }

        PrintWriter pw = response.getWriter();
        pw.write(responseText);
        pw.close();

    }

    /**
     * 查询
     * @return
     */
    private String queryStyleData(boolean flag) {
        JSONArray styleArray = null;
        try {
            styleArray = StyleService.queryStyleData();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(styleArray);
        return responseBean.getResponseArray(flag);
    }

    /**
     * 增加、更新、删除方法
     * @param param
     * @param request
     * @return
     */
    private String insertOrUpdateStyle(String param, HttpServletRequest request) {
        boolean success = false;
        String msg = "";
        try {
            if ("insert".equals(param)) {

            } else if ("update".equals(param)) {
                String styleid = request.getParameter("styleid");
                String styleDesc = request.getParameter("styleDesc");
                Style style = new Style();
                style.setStyleId(Long.valueOf(styleid));
                style.setStyleDesc(styleDesc);
                StyleService.updateStyle(style);
            } else if ("delete".equals(param)) {

            }
            success = true;
            msg = TextEnum.getText(param, success);
        } catch (Exception e) {
            msg = TextEnum.getText(param, success) + e.getMessage();
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, msg);
        return responseBean.getResponseText();
    }

}
