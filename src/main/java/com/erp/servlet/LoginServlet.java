package com.erp.servlet;

import com.erp.entity.StaffInfo;
import com.erp.service.LoginService;
import com.erp.util.EncryptUtil;
import com.erp.util.SystemConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by wang_ on 2016-06-28.
 */
@WebServlet(name = "LoginServlet",
        urlPatterns = {"/LoginServlet"},
        loadOnStartup = 2)
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
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

        if (param != null && "login".equalsIgnoreCase(param)) {
            String staffCode = request.getParameter("username");
            String pwd = request.getParameter("password");

            try {
                StaffInfo staffInfo = LoginService.queryStaffByCode("", staffCode);
                if (staffInfo != null && pwd.equals(staffInfo.getPassword())) {
                    LoginService.updateStaffByCode(staffCode);
                    request.getSession().setAttribute("staffinfo", staffInfo);
                    response.sendRedirect("index.jsp");
                } else {
                    request.setAttribute("msg", "<span style='color:red'>用户名或密码错误！</span>");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("msg", "<span style='color:red'>" + e.getMessage() + "</span>");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                e.printStackTrace();
            }
        } else if (param != null && param.equalsIgnoreCase("loginout")) {
            HttpSession session = request.getSession();
            session.removeAttribute("staffinfo");
            session.invalidate();
            response.sendRedirect("login.jsp");
        }

    }

}
