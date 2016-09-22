package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Group;
import com.erp.entity.Permission;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.GroupService;
import com.erp.service.PermissionService;
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
@WebServlet(name = "PermissionServlet",
        urlPatterns = {"/PermissionServlet"})
public class PermissionServlet extends HttpServlet {
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
            String moduleId = request.getParameter("moduleId");
            if (StringUtil.isEmpty(moduleId)) {
                throw new IllegalArgumentException("the request parameter moduleId is null, please check your request path is correct.");
            }
            responseText = queryPermission(moduleId);
        } else if ("add".equals(param) || "delete".equals(param)) {
            responseText = editPermission(request, param);
        } else if ("valid".equals(param)) {
            responseText = isValid(request);
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
    private String queryPermission(String moduleId) {
        JSONArray array = null;
        try {
            array = PermissionService.queryPermissionByModuleId(moduleId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(array);
        return responseBean.getResponseArray(true);
    }

    /**
     * @param request
     * @param param
     * @return
     */
    private String editPermission(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("delete".equals(param)) {
                String[] permissionId = request.getParameterValues("permissionId");
                PermissionService.deletePermission(permissionId);
            } else if ("add".equals(param)) {
                Permission permission = new Permission();
                String permissionId = request.getParameter("permissionId");
                String moduleId = request.getParameter("moduleId");
                String permissionCode = request.getParameter("permissionCode");
                String permissionName = request.getParameter("permissionName");
                String permissionDesc = request.getParameter("permissionDesc");
                permission.setPermissionId(StringUtil.isEmpty(permissionId) ? 0L : Long.valueOf(permissionId));
                permission.setModuleId(Long.valueOf(moduleId));
                permission.setPermissionCode(permissionCode);
                permission.setPermissionName(permissionName);
                permission.setPermissionDesc(permissionDesc);
                permission.setIs_del("0");
                PermissionService.insertOrUpdatePermission(permission);
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
        String permissionCode = request.getParameter("permissionCode");
        String permissionId = request.getParameter("permissionId");
        boolean success = false;
        try {
            Permission permission = PermissionService.queryPermissionById(permissionCode, permissionId);
            if (permission == null) {
                success = true;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        ResponseBean responseBean = new ResponseBean(success, "");
        return responseBean.getResponseBool();
    }
}
