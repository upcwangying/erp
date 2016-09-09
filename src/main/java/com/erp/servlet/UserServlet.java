package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.StaffInfo;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.LoginService;
import com.erp.service.UserService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;

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
 * Created by wang_ on 2016-07-27.
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserServlet() {
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

        if (random_session == null || seq == null || !seq.equals(random_session)) {
            throw new IllegalArgumentException("the request is illegal.");
        }
        String responseText = "";
        if ("user-query".equals(param)) {
            responseText = queryUser();
        } else if ("valid".equals(param)) {
            responseText = isValid(request);
        } else if ("insert".equals(param)
                || "update".equals(param)
                || "delete".equals(param)) {
            responseText = addOrUpdateUser(param, request);
        }

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.close();

    }

    /**
     * @return
     */
    private String queryUser() {
        JSONArray userData = null;
        try {
            userData = UserService.queryUserData(null);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        ResponseBean responseBean = new ResponseBean(userData);
        return responseBean.getResponseArray(true);
    }

    /**
     * @param request
     * @return
     */
    private String isValid(HttpServletRequest request) {
        String staffcode = request.getParameter("staffcode");
        String staffId = request.getParameter("staffId");
        boolean success = false;
        try {
//            if (!StringUtil.isEmpty(staffcode)) {
            StaffInfo staffInfo = LoginService.queryStaffByCode(staffId, staffcode);
            if (staffInfo == null) {
                success = true;
            }
//            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, "");
        return responseBean.getResponseBool();
    }

    /**
     * @param param
     * @param request
     * @return
     */
    private String addOrUpdateUser(String param, HttpServletRequest request) {
        boolean success = false;
        String msg = "";
        try {
            if ("insert".equals(param)) {
                String staffcode = request.getParameter("staffcode");
                String staffname = request.getParameter("staffname");
                String pwd = request.getParameter("pwd");
                String telphone = request.getParameter("telphone");
                String styleid = request.getParameter("styleid");
                StaffInfo staffInfo = new StaffInfo();
                staffInfo.setStaffCode(staffcode);
                staffInfo.setStaffName(staffname);
                staffInfo.setPassword(pwd);
                staffInfo.setTelephone(telphone);
                staffInfo.setStyleId(Long.valueOf(styleid));
                UserService.insertUserData(staffInfo);
            } else if ("update".equals(param)) {
                StaffInfo staffInfo = new StaffInfo();
                String dbid = request.getParameter("dbid");
                String staffcode = request.getParameter("staffcode");
                String staffname = request.getParameter("staffname");
                String pwd = request.getParameter("pwd");
                String telphone = request.getParameter("telphone");
                String styleid = request.getParameter("styleid");
                staffInfo.setStaffId(Long.valueOf(dbid));
                staffInfo.setStaffCode(staffcode);
                staffInfo.setStaffName(staffname);
                staffInfo.setPassword(pwd);
                staffInfo.setTelephone(telphone);
                staffInfo.setStyleId(Long.valueOf(styleid));
                UserService.updateUserData(staffInfo);
            } else if ("delete".equals(param)) {
                String[] dbids = request.getParameterValues("dbid");
                UserService.deleteUserData(dbids);
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
