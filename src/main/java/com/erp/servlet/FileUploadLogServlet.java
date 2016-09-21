package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.FileUploadLogService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-09-07.
 */
@WebServlet(name = "FileUploadLogServlet",
        urlPatterns = {"/FileUploadLogServlet"})
public class FileUploadLogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

//        if (random_session == null || seq == null || !seq.equals(random_session)) {
//            throw new IllegalArgumentException("the request is illegal.");
//        }

        String responseText = "";
        if ("query".equals(param)) {
            String productId = request.getParameter("productId");
            if (StringUtil.isEmpty(productId)) {
                throw new IllegalArgumentException("productId is null, please check your code in your jsp page.");
            }
            responseText = queryFileUploadLog(productId);
        } else if ("delete".equals(param) || "resume".equals(param)) {
            responseText = editFileUploadLog(request, param);
        }

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.close();

    }

    /**
     *
     * @param productId
     * @return
     */
    private String queryFileUploadLog(String productId) {
        JSONArray array = null;
        try {
            array = FileUploadLogService.queryFileUploadLog(productId, true);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(true);
    }

    /**
     *
     * @param request
     * @param param
     * @return
     */
    private String editFileUploadLog(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            String dbid = request.getParameter("dbid");
            String update_staffId = request.getParameter("update_staffId");
            FileUploadLogService.resumeOrDeleteFileUploadLog(dbid, "1", update_staffId, "delete".equals(param));
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
