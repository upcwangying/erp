package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Role;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.RoleService;
import com.erp.util.StringUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wang_ on 2016-09-22.
 */
@WebServlet(name = "RoleServlet",
        urlPatterns = {"/RoleServlet"})
public class RoleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
        String responseText = "";
        if ("query".equals(param)) {
            responseText = queryRole(true);
        } else if ("add".equals(param) || "delete".equals(param)) {
            responseText = editRole(request, param);
        } else if ("valid".equals(param)) {
            responseText = isValid(request);
        } else if ("query-combo".equals(param)) {
            responseText = queryRole(false);
        }

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.close();

    }

    /**
     * ≤È—Ø
     *
     * @return
     */
    private String queryRole(boolean flag) {
        JSONArray array = null;
        try {
            array = RoleService.queryRole();
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        Role role = new Role();
        role.setRoleId(0);
        role.setRoleName("ø’");
        ResponseBean responseBean = new ResponseBean(array, role, !flag);
        return responseBean.getResponseArray(flag);
    }

    /**
     * @param request
     * @param param
     * @return
     */
    private String editRole(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("delete".equals(param)) {
                String[] roleId = request.getParameterValues("roleId");
                RoleService.deleteRole(roleId);
            } else if ("add".equals(param)) {
                Role role = new Role();
                String roleId = request.getParameter("roleId");
                String roleCode = request.getParameter("roleCode");
                String roleName = request.getParameter("roleName");
                String roleDesc = request.getParameter("roleDesc");
                String groupId = request.getParameter("groupId");
                String is_init_permission = request.getParameter("is_init_permission");
                String flag = request.getParameter("flag");
                role.setRoleId(StringUtil.isEmpty(roleId) ? 0L : Long.valueOf(roleId));
                role.setRoleCode(roleCode);
                role.setRoleName(roleName);
                role.setRoleDesc(roleDesc);
                role.setGroupId(Long.valueOf(groupId));
                role.setIs_init_permission(is_init_permission);
                RoleService.insertOrUpdateRole(role, Boolean.valueOf(flag));
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

    /**
     * @param request
     * @return
     */
    private String isValid(HttpServletRequest request) {
        String roleCode = request.getParameter("roleCode");
        String roleId = request.getParameter("roleId");
        boolean success = false;
        try {
            Role role = RoleService.queryRoleByRoleId(roleCode, roleId);
            if (role == null) {
                success = true;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, "");
        return responseBean.getResponseBool();
    }
}
