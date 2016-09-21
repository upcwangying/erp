package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Gys;
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
        if (param == null) {
            throw new IllegalArgumentException("the request parameter param is null, please check your request path is correct.");
        }
        String seq = request.getParameter("seq");
        String random_session = (String) request.getSession().getAttribute("random_session");

//        if (random_session == null || seq == null || !seq.equals(random_session)) {
//            throw new IllegalArgumentException("the request is illegal.");
//        }
        boolean success = false;
        String msg = "";
        try {
            if ("add".equals(param)) {
                String name = "GYS";
                String dbid = request.getParameter("dbid");
                String gysmc = request.getParameter("gysmc");
                String gysms = request.getParameter("gysms");
                String create_staffid = request.getParameter("create_staffid");
                String update_staffid = request.getParameter("update_staffid");
                String gysbm = null;
                if (StringUtil.isEmpty(dbid)) {
                    gysbm = name + SequenceService.initSequence(name, 4);
                }
                Gys gys = new Gys();
                gys.setGysId(StringUtil.isEmpty(dbid)?0L:Long.valueOf(dbid));
                gys.setGysbm(gysbm);
                gys.setGysmc(gysmc);
                gys.setGysms(gysms);
                gys.setCreate_staffId(Long.valueOf(create_staffid));
                gys.setUpdate_staffId(Long.valueOf(update_staffid));

                ZSJService.insertOrUpdateGys(gys);
            } else if ("delete".equals(param)) {
                String[] dbids = request.getParameterValues("dbids");
                String staffId = request.getParameter("update_staffid");
                ZSJService.deleteGys(dbids, Long.valueOf(staffId));
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
