package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.WL;
import com.erp.enums.TextEnum;
import com.erp.service.SequenceService;
import com.erp.service.ZSJService;
import com.erp.util.StringUtil;

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
        String seq = request.getParameter("seq");
        String random_session = (String) request.getSession().getAttribute("random_session");

        if (random_session == null || seq == null || !seq.equals(random_session)) {
            throw new IllegalArgumentException("非法请求方式.......");
        }
        boolean success = false;
        String msg = "";
        try {
            if (param != null && "add".equalsIgnoreCase(param)) {
                String name = "WL";
                String dbid = request.getParameter("dbid");
                String wlmc = request.getParameter("wlmc");
                String wlms = request.getParameter("wlms");
                String create_staffid = request.getParameter("create_staffid");
                String update_staffid = request.getParameter("update_staffid");
                String wlbm = null;
                if (StringUtil.isEmpty(dbid)) {
                    wlbm = name + SequenceService.initSequence(name, 4);
                }

                WL wl = new WL();
                wl.setWlId(StringUtil.isEmpty(dbid)?0L:Long.valueOf(dbid));
                wl.setWlbm(wlbm);
                wl.setWlmc(wlmc);
                wl.setWlms(wlms);
                wl.setCreate_staffId(Long.valueOf(create_staffid));
                wl.setUpdate_staffId(Long.valueOf(update_staffid));
                ZSJService.insertOrUpdateWl(wl);
            } else if (param != null && "delete".equalsIgnoreCase(param)) {
                String[] dbids = request.getParameterValues("dbids");
                String staffId = request.getParameter("update_staffid");
                ZSJService.deleteWl(dbids, Long.valueOf(staffId));
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
