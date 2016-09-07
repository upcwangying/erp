package com.erp.product.servlet;

import com.erp.bean.ResponseBean;
import com.erp.exception.ServiceException;
import com.erp.service.FileUploadLogService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-09-06.
 */
@WebServlet(name = "FileDownloadServlet",
        urlPatterns = {"/FileDownloadServlet"})
public class FileDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String param = request.getParameter("param");
        String productId = request.getParameter("productId");
        String responseText = "";
        if ("query".equals(param)) {
            responseText = queryFileUploadLog(productId);
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
            array = FileUploadLogService.queryFileUploadLog(productId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(false);
    }
}
