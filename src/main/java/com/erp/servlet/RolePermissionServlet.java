package com.erp.servlet;

import com.erp.bean.ResponseBean;
import com.erp.entity.Module;
import com.erp.entity.RolePermission;
import com.erp.enums.TextEnum;
import com.erp.exception.ServiceException;
import com.erp.service.RoleService;
import com.erp.util.JSONUtil;
import com.erp.util.ModuleUtil;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wang_ on 2016-09-23.
 */
@WebServlet(name = "RolePermissionServlet",
        urlPatterns = {"/RolePermissionServlet"})
public class RolePermissionServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(RolePermissionServlet.class);
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
            String roleId = request.getParameter("roleId");
            responseText = queryRolePermission(roleId);
        } else if ("insert".equals(param) || "delete".equals(param)) {
            responseText = editRolePermission(request, param);
        } else if ("query-modules".equals(param)) {
            String modules = request.getParameter("modules");
            if (modules != null) {
                String[] moduleId = modules.split(",");
                responseText = queryModules(moduleId);
            }
        }

        PrintWriter writer = response.getWriter();
        writer.write(responseText);
        writer.close();
    }

    /**
     * ��ѯģ��
     *
     * @return
     */
    private String queryModules(String[] moduleId) {
        try {
            logger.info("----------- The Module begin init -----------");
            ModuleUtil.initModules(false, moduleId);
            logger.info("----------- The Module init success -----------");
        } catch (ServiceException e) {
            logger.error("----------- The Module init failure:" + e.getMessage() + " -----------");
            e.printStackTrace();
        }

        Set<Module> modules = ModuleUtil.getModuleByProjectId();
        JSONArray moduleArray = JSONUtil.parseToJson(modules);

        ResponseBean responseBean = new ResponseBean(moduleArray);
        return responseBean.getResponseArray(false);
    }

    /**
     * ��ѯ
     *
     * @return
     */
    private String queryRolePermission(String roleId) {
        JSONArray array = null;
        try {
            array = RoleService.queryRolePermission(roleId);
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
    private String editRolePermission(HttpServletRequest request, String param) {
        boolean success = false;
        String msg = "";
        try {
            if ("delete".equals(param)) {
                String roleId = request.getParameter("roleId");
                String[] dbid = request.getParameterValues("dbid");
                String flag = request.getParameter("flag");
                RoleService.deleteRolePermission(roleId, dbid, Boolean.valueOf(flag));
            } else if ("insert".equals(param)) {
                String roleId = request.getParameter("roleId");
                String[] permissionIds = request.getParameterValues("permissionId");

                if (permissionIds != null && permissionIds.length > 0) {
                    List<RolePermission> rolePermissionList = new ArrayList<>();
                    for (int i=0;i<permissionIds.length;i++) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(Long.valueOf(roleId));
                        rolePermission.setPermissionId(Long.valueOf(permissionIds[i]));
                        rolePermissionList.add(rolePermission);
                    }
                    RoleService.insertRolePermission(rolePermissionList);
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
