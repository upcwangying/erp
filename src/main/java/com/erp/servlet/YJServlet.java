package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.StaffInfo;
import com.erp.entity.YJ;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.LoginService;
import com.erp.service.YJService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-08-04.
 */
@WebServlet(name = "YJServlet",
        urlPatterns = {"/YJServlet"})
public class YJServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public YJServlet() {
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

        String responseText = "";
        if ("query".equals(param)) {
            responseText = queryYJData();
        } else if ("init".equals(param)
                || "insert".equals(param)
                || "update".equals(param)
                || "delete".equals(param)) {
            responseText = addOrUpdateYJData(request, param);
        } else if ("valid".equals(param)) {
            responseText = isValid(request);
        }

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.close();

    }

    /**
     * 查询方法
     *
     * @return
     */
    private String queryYJData() {
        JSONArray array = null;
        try {
            array = YJService.queryYJData();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(true);
    }

    /**
     * @param request
     * @return
     */
    private String isValid(HttpServletRequest request) {
        String yjyf = request.getParameter("yjyf");
        String dbid = request.getParameter("dbid");
        boolean success = false;
        try {
            YJ yj = YJService.queryYJDataByDbid(yjyf, dbid);
            if (yj == null) {
                success = true;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, "");
        return responseBean.getResponseBool();
    }

    /**
     * 初始化、增加、删除、修改方法逻辑
     *
     * @param request
     * @param param
     * @return
     */
    private String addOrUpdateYJData(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("init".equals(param)
                    || "insert".equals(param)) {
                String yjyf = request.getParameter("yjyf");
                String yjzc = request.getParameter("yjzc");
                String yjhz = request.getParameter("yjhz");
                double yjye = StringUtil.getDoubleValue(yjhz) - StringUtil.getDoubleValue(yjzc);
                String staffId = request.getParameter("staffId");

                YJ yj = new YJ();
                yj.setYjyf(yjyf);
                yj.setYjzc(Double.valueOf(yjzc));
                yj.setYjhz(Double.valueOf(yjhz));
                yj.setYjye(yjye);
                yj.setStaffId(Long.valueOf(staffId));
                yj.setYjlx("init".equals(param) ? "1" : "0");
                YJService.insertYJData(yj);
                if ("init".equals(param)) {
                    ServletContext context = this.getServletContext();
                    context.setAttribute("initYJ", "1");
                }
            } else if ("update".equals(param)) {
                String dbid = request.getParameter("dbid");
                String yjyf = request.getParameter("yjyf");
                String yjzc = request.getParameter("yjzc");
                String yjhz = request.getParameter("yjhz");
                double yjye = StringUtil.getDoubleValue(yjhz) - StringUtil.getDoubleValue(yjzc);
                String staffId = request.getParameter("staffId");
                String del_flag = request.getParameter("del_flag");
                String yjlx = request.getParameter("yjlx");

                YJ yj = new YJ();
                yj.setDbid(Long.valueOf(dbid));
                yj.setYjyf(yjyf);
                yj.setYjzc(Double.valueOf(yjzc));
                yj.setYjhz(Double.valueOf(yjhz));
                yj.setYjye(yjye);
                yj.setStaffId(Long.valueOf(staffId));
                yj.setYjlx(yjlx);
                if ("true".equals(del_flag)) {
                    YJService.deleteOrInsert(dbid, yj);
                } else if ("false".equals(del_flag)) {
                    YJService.updateYJData(yj);
                }
            } else if ("delete".equals(param)) {
                String dbid = request.getParameter("dbid");
                String yjlx = request.getParameter("yjlx");
                YJService.deleteYJData(dbid);
                if ("1".equals(yjlx)) {
                    ServletContext context = this.getServletContext();
                    context.setAttribute("initYJ", "0");
                }
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
