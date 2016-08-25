package com.erp.filter;

import com.erp.entity.StaffInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录验证Filter：如果未登录跳转到login.jsp
 * Created by wang_ on 2016-06-28.
 */
@WebFilter(filterName = "LoginFilter",
        urlPatterns = {"*.jsp"},
        initParams = {@WebInitParam(name = "loginPath", value = "/login.jsp;/erp/chart/chart.jsp")})
public class LoginFilter implements Filter {
    private FilterConfig filterConfig;

    public LoginFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();

        StaffInfo staffInfo = (StaffInfo) session.getAttribute("staffinfo");
        String loginPath = this.filterConfig.getInitParameter("loginPath");
        String[] paths = loginPath.split(";");

        if (staffInfo == null && !isContains(httpServletRequest.getRequestURI(), paths)) {
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write("<script language=javascript>window.location.href='" + httpServletRequest.getContextPath() + "/login.jsp';</script>");
            writer.close();
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isContains (String path, String[] regs) {
        for (String reg : regs) {
            if (path.indexOf(reg) != -1) return true;
        }
        return false;
    }
    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
