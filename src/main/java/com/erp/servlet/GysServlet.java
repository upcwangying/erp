package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Gys;
import com.erp.enums.TextEnum;
import com.erp.service.SequenceService;
import com.erp.service.ZSJService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016-07-26.
 */
@WebServlet(name = "GysServlet",
        urlPatterns = {"/zsj/GysServlet"})
public class GysServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GysServlet() {
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
        boolean success = false;
        String msg = "";
        try {
            if (param != null && "insert".equalsIgnoreCase(param)) {
                String name = "GYS";
                String gysmc = request.getParameter("gysmc");
                String gysms = request.getParameter("gysms");
                String gysbm = SequenceService.initSequence(name, 4);
                gysbm = name + gysbm;
                Gys gys = new Gys();
                gys.setGysbm(gysbm);
                gys.setGysmc(gysmc);
                gys.setGysms(gysms);
                ZSJService.insertGys(gys);
            } else if (param != null && "delete".equalsIgnoreCase(param)) {
                String[] dbids = request.getParameterValues("dbids");
                ZSJService.deleteGys(dbids);
            } else if (param != null && "update".equalsIgnoreCase(param)) {
                Gys gys = new Gys();
                String dbid = request.getParameter("dbid");
                String gysmc = request.getParameter("gysmc");
                String gysms = request.getParameter("gysms");
                gys.setGysId(Long.valueOf(dbid));
                gys.setGysmc(gysmc);
                gys.setGysms(gysms);
                ZSJService.updateGys(gys);
            }
            success = true;
            msg = TextEnum.getText(param, success);
        } catch (Exception e) {
            msg = TextEnum.getText(param, success) + e.getMessage();
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, msg);

        PrintWriter writer = response.getWriter();
        writer.write(responseBean.getResponseText());
        writer.close();
    }

}
