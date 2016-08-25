package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.WL;
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
@WebServlet(name = "WlServlet", urlPatterns = {"/zsj/WlServlet"})
public class WlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WlServlet() {
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
                String name = "WL";
                String wlmc = request.getParameter("wlmc");
                String wlms = request.getParameter("wlms");
                String wlbm = SequenceService.initSequence(name, 4);
                WL wl = new WL();
                wlbm = name + wlbm;
                wl.setWlbm(wlbm);
                wl.setWlmc(wlmc);
                wl.setWlms(wlms);
                ZSJService.insertWl(wl);
            } else if (param != null && "delete".equalsIgnoreCase(param)) {
                String[] dbids = request.getParameterValues("dbids");
                ZSJService.deleteWl(dbids);
            } else if (param != null && "update".equalsIgnoreCase(param)) {
                WL wl = new WL();
                String dbid = request.getParameter("dbid");
                String wlmc = request.getParameter("wlmc");
                String wlms = request.getParameter("wlms");
                wl.setWlId(Long.valueOf(dbid));
                wl.setWlmc(wlmc);
                wl.setWlms(wlms);
                ZSJService.updateWl(wl);
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
        writer.flush();
    }

}
