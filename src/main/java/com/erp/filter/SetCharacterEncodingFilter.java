package com.erp.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Created by wang_ on 2016-06-28.
 */
@WebFilter(filterName = "SetCharacterEncodingFilter",
        urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encodeName", value = "UTF-8"),
                @WebInitParam(name = "enable", value = "true")})
public class SetCharacterEncodingFilter implements Filter {
    private FilterConfig filterConfig;
    private String encodeName;
    private boolean enable;

    public SetCharacterEncodingFilter() {
        this.encodeName = "UTF-8";
        this.enable = false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        loadParam();
    }

    private void loadParam() {
        this.encodeName = this.filterConfig.getInitParameter("encodeName");
        String enableStr = this.filterConfig.getInitParameter("enable");
        this.enable = Boolean.valueOf(enableStr);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (this.enable) {
            request.setCharacterEncoding(this.encodeName);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encodeName = null;
    }
}
