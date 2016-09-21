package com.erp.servlet;

import com.erp.chart.factory.ChartsFactory;
import com.erp.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-07-16.
 */
@WebServlet(name = "ChartsServlet",
        urlPatterns = {"/ChartsServlet"},
        loadOnStartup = 1)
public class ChartsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ChartsServlet() {
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

        HttpSession session = request.getSession();

        String seq = request.getParameter("seq");
        String random_session = (String) request.getSession().getAttribute("random_session");

//        if (random_session == null || seq == null || !seq.equals(random_session)) {
//            throw new IllegalArgumentException("the request is illegal.");
//        }
        String wlbm = request.getParameter("wlbm");
        String chart_lx = request.getParameter("chart_lx");
        String module_lx = request.getParameter("module_lx");
        int width = 1500, height = 800;
        try {
            width = StringUtil.getIntValue(request.getParameter("width"), 1500);
            height = StringUtil.getIntValue(request.getParameter("height"), 800);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter pw = response.getWriter();
        String filename = ChartsFactory.createChart(session, chart_lx, module_lx, wlbm, width, height);
        pw.write(filename);
        pw.flush();
    }
}
