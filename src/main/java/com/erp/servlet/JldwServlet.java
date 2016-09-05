package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Jldw;
import com.erp.entity.StaffInfo;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.JldwService;
import com.erp.service.LoginService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;
import org.dom4j.Element;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-09-02.
 */
@WebServlet(name = "JldwServlet",
        urlPatterns = {"/JldwServlet"})
public class JldwServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
        String reponseText = "";
        if ("query".equals(param)) {
            reponseText = queryJldw(true);
        } else if ("delete".equals(param) || "add".equals(param)) {
            reponseText = editJldw(request, param);
        } else if ("valid".equals(param)) {
            reponseText = isValid(request);
        } else if ("query-combo".equals(param)) {
            reponseText = queryJldw(false);
        }

        PrintWriter writer = response.getWriter();
        writer.write(reponseText);
        writer.close();

    }

    /**
     *
     * @param flag
     * @return
     */
    private String queryJldw(boolean flag) {
        JSONArray array = null;
        try {
            array = JldwService.queryJldw();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(flag);
    }

    /**
     * @param request
     * @return
     */
    private String isValid(HttpServletRequest request) {
        String jldwmc = request.getParameter("jldwmc");
        String jldwId = request.getParameter("jldwId");
        boolean success = false;
        try {
            Jldw jldw = JldwService.queryJldwByJldwId(jldwmc, jldwId);
            if (jldw == null) {
                success = true;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, "");
        return responseBean.getResponseBool();
    }

    /**
     * @param request
     * @param param
     * @return
     */
    private String editJldw(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("delete".equals(param)) {
                String[] jldwIds = request.getParameterValues("jldwId");
                String staffId = request.getParameter("staffId");
                JldwService.deleteJldw(jldwIds, Long.valueOf(staffId));
            } else if ("add".equals(param)) {
                Jldw jldw = new Jldw();
                String jldwId = request.getParameter("jldwId");
                String jldwmc = request.getParameter("jldwmc");
                String jldwms = request.getParameter("jldwms");
                String create_by = request.getParameter("create_by");
                String update_by = request.getParameter("update_by");
                jldw.setJldwId(StringUtil.isEmpty(jldwId) ? 0L : Long.valueOf(jldwId));
                jldw.setJldwmc(jldwmc);
                jldw.setJldwms(jldwms);
                jldw.setIs_del("0");
                jldw.setCreate_staffId(Long.valueOf(create_by));
                jldw.setUpdate_staffId(Long.valueOf(update_by));
                JldwService.insertOrUpdateJldw(jldw);
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
